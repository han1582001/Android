package com.example.app_doc_truyen_nhom2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import com.example.app_doc_truyen_nhom2.Adapter.BookStoreAdapter;
import com.example.app_doc_truyen_nhom2.Database.Truyen;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class TacGiaActivity extends AppCompatActivity {
    private TextView txttitle;
    private Toolbar toolbar;
    private RecyclerView recyclerViewCungTacGia;
    private BookStoreAdapter bookStoreAdaptercungtacgia;
    private List<Truyen> listtr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        if (bundle == null) {
            return;
        }
        setContentView(R.layout.activity_tac_gia);
        txttitle=findViewById(R.id.title);
        toolbar=findViewById(R.id.toolbarcungtacgia);
        toolbar.setTitle("Phân Loại Theo Tác Giả");

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_baseline_logout_24);
        recyclerViewCungTacGia=findViewById(R.id.listbookstoretheotacgia);
        listtr=new ArrayList<>();
        bookStoreAdaptercungtacgia=new BookStoreAdapter(this, listtr);
        recyclerViewCungTacGia.setAdapter(bookStoreAdaptercungtacgia);
        recyclerViewCungTacGia.setLayoutManager(new LinearLayoutManager(this));
        txttitle.setText("Truyện Của Tác Giả "+ (String) bundle.get("tentacgia"));
        DatabaseReference databaseReference2 = FirebaseDatabase.getInstance().getReference("truyen");
        databaseReference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listtr.clear();
                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                    Truyen truyen=dataSnapshot.getValue(Truyen.class);
                    if(truyen.getTacGia().equals((String) bundle.get("tentacgia"))){
                        listtr.add(truyen);

                    }
                }
                bookStoreAdaptercungtacgia.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}