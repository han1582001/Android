package com.example.app_doc_truyen_nhom2;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.app_doc_truyen_nhom2.Database.BookDatabase;
import com.example.app_doc_truyen_nhom2.Database.DSchuong;
import com.example.app_doc_truyen_nhom2.Database.Truyen;
import com.example.app_doc_truyen_nhom2.Dialog.DialogChonChuong;
import com.example.app_doc_truyen_nhom2.Interface.XoaTruyenThanhCongListener;
import com.example.app_doc_truyen_nhom2.Service.MyServiceReading;

import java.util.List;

public class HienThiChuong extends AppCompatActivity {
    TextView tTitle, tTenChuong, tNoidung;
    private DSchuong dSchuong;
    private Button btprev, btnext, btnprev2, btnnext2, btnDSchuong, btnDSchuong2;
    private final BookDatabase database = new BookDatabase(this);
    private long thutu;
    static Toolbar toolbar;
    private static long idtruyen;
    NestedScrollView scrollView;
    private int Ymin = 20;
    GestureDetector gestureDetector;
    LinearLayout linearLayoutcontainer;

Intent intent;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        if (bundle == null) {
            return;
        }
        setContentView(R.layout.activity_hien_thi_chuong);


        tTitle = findViewById(R.id.txttentruyenx);
        tTenChuong = findViewById(R.id.txttenchuongx);
        tNoidung = findViewById(R.id.txtndchuongx);
        btnext = findViewById(R.id.btnnext);
        btprev = findViewById(R.id.btnprev);

        btnDSchuong = findViewById(R.id.btndschuong);

        scrollView = findViewById(R.id.scroll);
        linearLayoutcontainer = findViewById(R.id.container);
        thutu = bundle.getLong("thutuchuong");

        idtruyen = bundle.getLong("IDtruyen");
        btndieuhuong();
        dSchuong = (DSchuong) database.getChuongIDthutu(idtruyen, bundle.getLong("thutuchuong"));
        btnDSchuong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<DSchuong> dSchuongList = database.getChuong(idtruyen);
                int pos = getPossition(dSchuongList, dSchuong);
                DialogChonChuong dialogChonChuong = new DialogChonChuong(HienThiChuong.this, HienThiChuong.this, dSchuongList, bundle.getLong("thutuchuong"), pos, new XoaTruyenThanhCongListener() {
                    @Override
                    public void onComplete() {
                        finish();
                    }
                });
                dialogChonChuong.show();
            }
        });
        btnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                database.capnhatchuongdangdoc(bundle.getLong("thutuchuong") + 1, dSchuong.getIdTruyen());
                Intent intent = new Intent(HienThiChuong.this, HienThiChuong.class);
                Bundle bundle2 = new Bundle();
                bundle2.putLong("IDtruyen", idtruyen);
                bundle2.putLong("thutuchuong", bundle.getLong("thutuchuong") + 1);
                intent.putExtras(bundle2);
                startActivity(intent);
                finish();
            }
        });
        btprev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                database.capnhatchuongdangdoc(bundle.getLong("thutuchuong") - 1, dSchuong.getIdTruyen());
                Intent intent = new Intent(HienThiChuong.this, HienThiChuong.class);
                Bundle bundle2 = new Bundle();
                bundle2.putLong("IDtruyen", idtruyen);
                bundle2.putLong("thutuchuong", bundle.getLong("thutuchuong") - 1);
                intent.putExtras(bundle2);
                startActivity(intent);
                finish();
            }
        });


        DSchuong ds = database.getChuongIDthutu(idtruyen, bundle.getLong("thutuchuong"));
        Truyen truyen = database.getTruyenID(idtruyen);
        tTitle.setText(truyen.getTenTruyen());
        tTenChuong.setText("Chương" + ds.getThutuchuong() + ": " + ds.getTenChuong());
        tNoidung.setText(ds.getnDchuong());
        toolbar = findViewById(R.id.toolbarhienthichuong);
        toolbar.setBackgroundColor(ContextCompat.getColor(getBaseContext(), R.color.black));
        toolbar.setTitle(truyen.getTenTruyen());

        toolbar.setTitleTextColor(ContextCompat.getColor(getBaseContext(), R.color.xanhla));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);
        intent = new Intent(HienThiChuong.this, MyServiceReading.class);
        intent.putExtra("thutuchuong", bundle.getLong("thutuchuong"));
        intent.putExtra("idtruyen", bundle.getLong("IDtruyen"));
        intent.putExtra("tentruyen", truyen.getTenTruyen());
        startService(intent);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
stopService(intent);
                return true;
            case R.id.reviewDetail:
                Intent intent = new Intent(HienThiChuong.this, Review_Detail.class);
                Bundle bundle = new Bundle();
                bundle.putLong("IDtruyen", bundle.getLong("IDtruyen"));
                intent.putExtras(bundle);
                startActivity(intent);
                return true;
            case R.id.tusach:
                Intent intent2 = new Intent(HienThiChuong.this, MainActivity.class);
                startActivity(intent2);
                return true;
        }
        return false;
    }

    private int getPossition(List<DSchuong> list, DSchuong dSchuong1) {
        for (DSchuong dSchuongx : list) {
            if (dSchuongx == dSchuong1) {
                return list.indexOf(dSchuongx);
            }
        }
        return 0;
    }

    private void btndieuhuong() {
        if (thutu == database.getChuongcuoi(idtruyen)) {
            btnext.setEnabled(false);

        }
        if (thutu < database.getChuongcuoi(idtruyen)) {
            btnext.setEnabled(true);

        }
        if (thutu == 1) {
            btprev.setEnabled(false);

        }
        if (thutu > 1) {
            btprev.setEnabled(true);
        }

    }


}