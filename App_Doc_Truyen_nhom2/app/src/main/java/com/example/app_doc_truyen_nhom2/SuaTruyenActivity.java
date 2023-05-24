package com.example.app_doc_truyen_nhom2;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.app_doc_truyen_nhom2.Database.BookDatabase;
import com.example.app_doc_truyen_nhom2.Database.TheLoai;
import com.example.app_doc_truyen_nhom2.Database.Truyen;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class SuaTruyenActivity extends AppCompatActivity {
    private TheLoai theLoai;
    private List<TheLoai> theLoaiList = new ArrayList<>();
    private BookDatabase database;
    private Spinner sptheloai;
    private ImageButton btnGalery;
    private  Bitmap selectedBitMap;
    private EditText tTentruyen, tTacGia, tMoTa;
    private Button btnThemmoi;
    private Button btnquaylai, btnhuy;
    private Toolbar toolbar;
    private ThemTruyenActivity.SpinTheLoaiAdapter theLoaiAdapter;
private static Truyen truyen;

    private final static int RESULT_LOAD_IMG = 1000;
    private final static int WRITE_STORAGE_REQUEST_PERMISSION = 2000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle=getIntent().getExtras();
        if (bundle==null){
            return;
        }
        setContentView(R.layout.activity_sua_truyen);

        sptheloai = findViewById(R.id.spinTheloai);
        tTentruyen = findViewById(R.id.edttentruyen);
        tTacGia = findViewById(R.id.edttacgia);
        tMoTa = findViewById(R.id.edtMota);
        btnThemmoi = findViewById(R.id.btnthem);
        btnhuy = findViewById(R.id.btnhuy);

        btnGalery = findViewById(R.id.btnChonAnh);
        toolbar = findViewById(R.id.toolbarthemtruyen);

        BookDatabase database =new BookDatabase(this);

        DatabaseReference databaseReference2 = FirebaseDatabase.getInstance().getReference("truyen");
        databaseReference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot truyensnap : snapshot.getChildren()) {
                    Truyen truyen1 = truyensnap.getValue(Truyen.class);

                    if(truyen1.getIDtruyen()==(Long) bundle.get("IDtruyen")){
                        truyen=truyen1;
                        tTentruyen.setText(truyen.getTenTruyen());
                        tTacGia.setText(truyen.getTacGia());
                        tMoTa.setText(truyen.getMoTa());
                        btnThemmoi.setText("Cập Nhật");
                        selectedBitMap=Utils.decode(truyen.getAnh());
                        btnGalery.setImageBitmap(Utils.decode(truyen.getAnh()));
                        setSupportActionBar(toolbar);
                        btnGalery.setOnClickListener(new View.OnClickListener() {
                            @RequiresApi(api = Build.VERSION_CODES.M)
                            @Override
                            public void onClick(View view) {
                                checkWriteStoragePermission();

                            }
                        });
                        btnhuy.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                onBackPressed();
                            }
                        });


                        btnThemmoi.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                FirebaseDatabase database = FirebaseDatabase.getInstance();
                                DatabaseReference myTruyen = database.getReference("truyen");
                                long idTruyen = truyen.getIDtruyen() ;
                                String tenTruyen = tTentruyen.getText().toString();
                                String tacGia = tTacGia.getText().toString();
                                long theLoai = ThemTruyenActivity.SpinTheLoaiAdapter.idTheloai;
                                String moTa = tMoTa.getText().toString();
                                long trangThai = 0;
                                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                                selectedBitMap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                                byte[] bytes = byteArrayOutputStream.toByteArray();
                                String imgEncoded = Base64.encodeToString(bytes, Base64.DEFAULT);
                                if(tenTruyen.isEmpty()){
                                    tTentruyen.setError("Không được để trống");
                                    tTentruyen.requestFocus();
                                    return;
                                }
                                if(tacGia.isEmpty()){
                                    tTacGia.setError("Không được để trống");
                                    tTacGia.requestFocus();
                                    return;
                                }if(moTa.isEmpty()){
                                    tMoTa.setError("Không được để trống");
                                    tMoTa.requestFocus();
                                    return;
                                }
                                myTruyen.child("" + idTruyen).child("IDtruyen").setValue(idTruyen);
                                myTruyen.child("" + idTruyen).child("tenTruyen").setValue(tenTruyen);
                                myTruyen.child("" + idTruyen).child("tacGia").setValue(tacGia);
                                myTruyen.child("" + idTruyen).child("theLoai").setValue(theLoai);
                                myTruyen.child("" + idTruyen).child("moTa").setValue(moTa);
                                myTruyen.child("" + idTruyen).child("trangThai").setValue(trangThai);
                                myTruyen.child("" + idTruyen).child("anh").setValue(imgEncoded);
                                Toast.makeText(SuaTruyenActivity.this,"Cập Nhật Thành Công", Toast.LENGTH_LONG).show();
                                finish();
                            }

                        });

                       BookDatabase database2 = new BookDatabase(SuaTruyenActivity.this);
                        theLoaiList = database2.getAllTheloai();
                        ThemTruyenActivity.SpinTheLoaiAdapter adapter = new ThemTruyenActivity.SpinTheLoaiAdapter(theLoaiList, SuaTruyenActivity.this);
                        sptheloai.setAdapter(adapter);
                        sptheloai.setSelection(getPositiontheloaiID(truyen.getTheLoai()));
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

    }
    public int getPositiontheloaiID(long idTheloai){
        for(TheLoai theLoai:theLoaiList){
            if(theLoai.getIdTheloai()==idTheloai){
                return theLoaiList.indexOf(theLoai);
            }
        }
        return 0;
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 200 && resultCode == RESULT_OK) {

            try {
                Uri imageUri = data.getData();
                selectedBitMap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                btnGalery.setImageBitmap(selectedBitMap);
            } catch (IOException e) {
                e.printStackTrace();
            }


        }

    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void checkWriteStoragePermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, WRITE_STORAGE_REQUEST_PERMISSION);

        } else {
            choosePicture();
        }
    }
    private void choosePicture() {
        Intent pickPhoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickPhoto, 200);
    }
    public static class SpinTheLoaiAdapter extends BaseAdapter {

        private List<TheLoai> theLoaiLists;
        private Context context;
        public static long idTheloai = 0;

        public SpinTheLoaiAdapter(List<TheLoai> theLoaiLists, Context context) {
            this.theLoaiLists = theLoaiLists;
            this.context = context;
        }

        @Override
        public int getCount() {
            if (theLoaiLists == null) {
                return 0;
            }
            return theLoaiLists.size();
        }

        @Override
        public TheLoai getItem(int i) {
            return theLoaiLists.get(i);
        }

        @Override
        public long getItemId(int i) {
            TheLoai theLoai = (TheLoai) getItem(i);
            return theLoai.getIdTheloai();
        }


        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            TheLoai theLoai = getItem(i);
            LayoutInflater inflater = LayoutInflater.from(context);
            view = inflater.inflate(R.layout.theloaiitem, null, true);
            TextView txtTheloai = view.findViewById(R.id.txttheloaiitem);
            idTheloai = theLoai.getIdTheloai();
            txtTheloai.setText(theLoai.getTenTheloai());

            return view;
        }
    }
}