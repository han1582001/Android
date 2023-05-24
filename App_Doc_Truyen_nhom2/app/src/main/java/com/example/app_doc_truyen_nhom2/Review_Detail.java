package com.example.app_doc_truyen_nhom2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.app_doc_truyen_nhom2.Database.BookDatabase;
import com.example.app_doc_truyen_nhom2.Database.Truyen;
import com.example.app_doc_truyen_nhom2.Fragment.DSchuongFragment;
import com.example.app_doc_truyen_nhom2.Fragment.TinhTrang;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Review_Detail extends AppCompatActivity {
    private static Truyen truyen;
    private static long idtruyen;
    Context context;
    private ImageView anhBia, anhDaiDienTruyen;
    private TextView tTenTruyen, tTacgia, tTheloai, tTinhtrang, tSochuong, tnoidung;
    TabLayout tabLayout;
    private SectionsPageAdapter sectionsPageAdapter;
    private ViewPager viewPager;
    private Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        if (bundle == null) {
            return;
        }
        setContentView(R.layout.activity_review_detail);
        anhBia = findViewById(R.id.imgbacground);
        anhDaiDienTruyen = findViewById(R.id.imgavatruyen);
        tTenTruyen = findViewById(R.id.txtdttentruyen);
        tTacgia = (TextView) findViewById(R.id.txtdttacgia2);
        tTheloai = findViewById(R.id.txtdttheloai2);
        tTinhtrang = findViewById(R.id.txtdttinhtrang);
        tSochuong = findViewById(R.id.txtdtsochuong2);


        idtruyen=(Long) bundle.get("IDtruyen");
        BookDatabase db = new BookDatabase(this);
        truyen=db.getTruyenID(idtruyen);
        anhBia.setImageBitmap(Utils.decode(truyen.getAnh().toString()));
        anhDaiDienTruyen.setImageBitmap(Utils.decode(truyen.getAnh().toString()));
        sectionsPageAdapter = new SectionsPageAdapter(getSupportFragmentManager());
        viewPager = (ViewPager) findViewById(R.id.dtview_pager);
        viewPager.setAdapter(sectionsPageAdapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabsdetail);
        tabLayout.setupWithViewPager(viewPager);
        //loadFragment(TinhTrang.newInstances(truyen.getIDtruyen()));
        tTenTruyen.setText(truyen.getTenTruyen());

        tTacgia.setText(truyen.getTacGia());
        tTheloai.setText(db.getTenTheLoaiID(truyen.getTheLoai()));
        Resources res = getResources();
        if (truyen.getTrangThai() == 0) {
            tTinhtrang.setText("Đang ra");
            tTinhtrang.setTextColor(res.getColor(R.color.xanhduong));
        } else {
            tTinhtrang.setText("Đã Full");
            tTinhtrang.setTextColor(res.getColor(R.color.xanhla));
        }

        tSochuong.setText(db.getSoChuong(truyen.getIDtruyen()));

        toolbar=findViewById(R.id.toolbarreviewdetail);
        toolbar.setBackgroundColor(ContextCompat.getColor(getBaseContext(),R.color.trongsuot));
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
getSupportActionBar().setDisplayShowHomeEnabled(true);
getSupportActionBar().setDisplayHomeAsUpEnabled(true);
toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
        }
        return false;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menudetail,menu);
        return super.onCreateOptionsMenu(menu);

    }
    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.dtview_pager, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public class SectionsPageAdapter extends FragmentPagerAdapter {

        public SectionsPageAdapter(@NonNull FragmentManager fm) {
            super(fm);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return TinhTrang.newInstances(truyen.getIDtruyen());

                case 1:
                    return DSchuongFragment.newInstances(truyen.getIDtruyen());
            }
            return null;
        }

        @Override
        public int getCount() {
            return 2;
        }

        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Giới Thiệu";
                case 1:
                    return "Mục Lục";
            }
            return null;
        }
    }

}