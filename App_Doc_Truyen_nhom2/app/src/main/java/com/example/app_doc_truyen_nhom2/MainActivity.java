package com.example.app_doc_truyen_nhom2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;


import com.example.app_doc_truyen_nhom2.Database.BookDatabase;
import com.example.app_doc_truyen_nhom2.FireBase.FirebaseQuery;
import com.example.app_doc_truyen_nhom2.Fragment.BookStore;
import com.example.app_doc_truyen_nhom2.Fragment.Bookshelf;
import com.example.app_doc_truyen_nhom2.Fragment.FragmentQuanLy;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    public static Toolbar toolBar;
    public static BottomNavigationView navigation;
    private BookDatabase database;
    public static FrameLayout frameraw;
    private FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolBar = (Toolbar) findViewById(R.id.toolbarmain);
        frameraw = findViewById(R.id.frame_container);
        toolBar.setTitle("Tủ Truyện");
//toolBar.setTitleTextColor(ContextCompat.getColor(getBaseContext(),R.color.maudo));
        setSupportActionBar(toolBar);
        toolBar.setBackgroundColor(ContextCompat.getColor(getBaseContext(), R.color.maudo));

        database = new BookDatabase(this);
        FirebaseQuery.themChuongX(this);
        FirebaseQuery.loadTheLoai(this);
        navigation = (BottomNavigationView) findViewById(R.id.bottom_menu);
        navigation.setBackgroundColor(ContextCompat.getColor(getBaseContext(), R.color.white));
        navigation.setOnNavigationItemSelectedListener(onItemSelectedListener);
        loadFragment(new Bookshelf());
//hienthitruyen
    }


    final boolean isNetworkConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    public void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }


    private BottomNavigationView.OnNavigationItemSelectedListener onItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.tusach:
                    toolBar.setTitle("Tủ Truyện");
                    toolBar.setBackgroundColor(ContextCompat.getColor(getBaseContext(), R.color.maudo));
                    navigation.setBackgroundColor(ContextCompat.getColor(getBaseContext(), R.color.white));
                    fragment = new Bookshelf();
                    loadFragment(fragment);
                    return true;
                case R.id.storage:
                    toolBar.setTitle("Kho Truyện");
                    toolBar.setBackgroundColor(ContextCompat.getColor(getBaseContext(), R.color.maudo));
                    navigation.setBackgroundColor(ContextCompat.getColor(getBaseContext(), R.color.white));
                    fragment = new BookStore();
                    loadFragment(fragment);
                    return true;
                case R.id.profile:
                    toolBar.setTitle("Quản Lý");
                    toolBar.setBackgroundColor(ContextCompat.getColor(getBaseContext(), R.color.maudo));
                    navigation.setBackgroundColor(ContextCompat.getColor(getBaseContext(), R.color.white));
                    fragment = new FragmentQuanLy();
                    loadFragment(fragment);
                    return true;
            }
            return false;
        }
    };
}