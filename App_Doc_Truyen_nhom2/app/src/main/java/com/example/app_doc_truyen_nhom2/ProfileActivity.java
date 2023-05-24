package com.example.app_doc_truyen_nhom2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.app_doc_truyen_nhom2.FireBase.FirebaseQuery;
import com.example.app_doc_truyen_nhom2.Interface.checkcode;
import com.example.app_doc_truyen_nhom2.Fragment.Profile;
import com.example.app_doc_truyen_nhom2.Fragment.QuanLyTaiKhoan;
import com.example.app_doc_truyen_nhom2.Fragment.TaiKhoanFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ProfileActivity extends AppCompatActivity {
    private FirebaseUser taikhoan;
    private DatabaseReference reference;
    private String idTaikhoan;
   public static Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        taikhoan= FirebaseAuth.getInstance().getCurrentUser();
        reference= FirebaseDatabase.getInstance().getReference("taikhoan");

        loadFragment(new TaiKhoanFragment(taikhoan.getUid()));
        toolbar=findViewById(R.id.toolbarprofile);
        toolbar.setTitle("Tài Khoản");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);

    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                loadFragment(new TaiKhoanFragment(FirebaseAuth.getInstance().getCurrentUser().getUid()));
                return true;
            case R.id.quanlytruyen:
                loadFragment(new Profile());
                return true;
            case R.id.quanlytaikhoan:
                FirebaseQuery.checkLoaiTk(new checkcode() {
                    @Override
                    public void onComplete(boolean x) {
                        if(x){
                            loadFragment(new QuanLyTaiKhoan());
                        }else {
                            Toast.makeText(ProfileActivity.this,"Bạn Không Không Phải Thiên Đạo", Toast.LENGTH_LONG).show();
                            return;
                        }

                    }
                });
                return true;
            case R.id.dangxuat:
                FirebaseAuth.getInstance().signOut();
                Intent intent=new Intent(ProfileActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
                return true;
        }

        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menuprofile,menu);
        return super.onCreateOptionsMenu(menu);

    }

    public void loadFragment(Fragment fragment) {
        FragmentTransaction transaction =getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.containerquanly, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}