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
import com.example.app_doc_truyen_nhom2.Fragment.Profile;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ThemTruyenActivity extends AppCompatActivity {
    private TheLoai theLoai;
    private List<TheLoai> theLoaiList = new ArrayList<>();
    private BookDatabase database;
    private Spinner sptheloai;
    private ImageButton btnGalery;
    private Bitmap selectedBitMap;
    private EditText tTentruyen, tTacGia, tMoTa;
    private Button btnThemmoi;
    private Button btnquaylai, btnhuy;
    private Toolbar toolbar;
    private ThemTruyenActivity.SpinTheLoaiAdapter theLoaiAdapter;


    private final static int RESULT_LOAD_IMG = 1000;
    private final static int WRITE_STORAGE_REQUEST_PERMISSION = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_truyen);
        sptheloai = findViewById(R.id.spinTheloai);
        tTentruyen = findViewById(R.id.edttentruyen);
        tTacGia = findViewById(R.id.edttacgia);
        tMoTa = findViewById(R.id.edtMota);
        btnThemmoi = findViewById(R.id.btnthem);
        btnhuy = findViewById(R.id.btnhuy);

        btnGalery = findViewById(R.id.btnChonAnh);
        toolbar = findViewById(R.id.toolbarthemtruyen);
        setSupportActionBar(toolbar);

        btnhuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        btnGalery.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                checkWriteStoragePermission();

            }
        });
        btnThemmoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myTruyen = database.getReference("truyen");
                long idTruyen = Calendar.getInstance().getTimeInMillis();
                String tenTruyen = tTentruyen.getText().toString();
                String tacGia = tTacGia.getText().toString();
                long theLoai = ThemTruyenActivity.SpinTheLoaiAdapter.idTheloai;
                String moTa = tMoTa.getText().toString();
                long trangThai = 0;
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

                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                selectedBitMap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                byte[] bytes = byteArrayOutputStream.toByteArray();
                String imgEncoded = Base64.encodeToString(bytes, Base64.DEFAULT);
Truyen truyenw=new Truyen(idTruyen, tenTruyen,tacGia,theLoai,moTa,trangThai,imgEncoded,0);

                myTruyen.child("" + idTruyen).setValue(truyenw).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(ThemTruyenActivity.this,"Cập Nhật Thành Công", Toast.LENGTH_LONG).show();
                            finish();
                        }
                    }
                });
               finish();
            }
        });

        database = new BookDatabase(this);
        theLoaiList = database.getAllTheloai();
        ThemTruyenActivity.SpinTheLoaiAdapter adapter = new ThemTruyenActivity.SpinTheLoaiAdapter(theLoaiList, this);
        sptheloai.setAdapter(adapter);
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