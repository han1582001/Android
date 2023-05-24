package com.example.app_doc_truyen_nhom2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.app_doc_truyen_nhom2.Adapter.QuanlychuongAdapter;
import com.example.app_doc_truyen_nhom2.Database.DSchuong;
import com.example.app_doc_truyen_nhom2.Database.Truyen;
import com.example.app_doc_truyen_nhom2.Dialog.DialogThemChuong;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class QuanlychuongActivity extends AppCompatActivity {
private static List<DSchuong> dSchuongList;
private static Truyen truyen;
private RecyclerView recyclerViewQuanlychuong;
private QuanlychuongAdapter danhSachChuongAdapter;
private static long idtruyen;
private Toolbar toolbar;
private ImageButton btnthemchuong;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle=getIntent().getExtras();
        if(bundle==null){
            return;
        }
        setContentView(R.layout.activity_quanlychuong);
        toolbar=(Toolbar) findViewById(R.id.toolbarquanlychuong);
        btnthemchuong=findViewById(R.id.btnthemchuong);
        recyclerViewQuanlychuong=findViewById(R.id.recyclerqlchuong);
        idtruyen=(Long) bundle.get("IDtruyen");
        dSchuongList=new ArrayList<>();

        danhSachChuongAdapter=new QuanlychuongAdapter(this, dSchuongList);
        recyclerViewQuanlychuong.setAdapter(danhSachChuongAdapter);
        recyclerViewQuanlychuong.setLayoutManager(new LinearLayoutManager(this));
        DatabaseReference databaseReference2 = FirebaseDatabase.getInstance().getReference("truyen");
        databaseReference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot truyensnapshot: snapshot.getChildren()){
                    Truyen truyen1= truyensnapshot.getValue(Truyen.class);
                    if(truyen1.getIDtruyen()==idtruyen){
                       truyen=truyen1;
                        toolbar.setTitle(truyen.getTenTruyen());

                            btnthemchuong.setEnabled(true);
                        btnthemchuong.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                final DialogThemChuong dialogThemChuong = new DialogThemChuong(QuanlychuongActivity.this, idtruyen, QuanlychuongActivity.this);
                                dialogThemChuong.show();
                            }

                        });

                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("DSchuong");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                dSchuongList.clear();
                for (DataSnapshot chuongSnapshot : snapshot.getChildren()) {
                    DSchuong dSchuong = chuongSnapshot.getValue(DSchuong.class);
                    if (dSchuong.getIdTruyen()== idtruyen){
                       dSchuongList.add(dSchuong);

                    }
                }danhSachChuongAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }
}