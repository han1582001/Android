package com.example.app_doc_truyen_nhom2.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.app_doc_truyen_nhom2.Database.BookDatabase;
import com.example.app_doc_truyen_nhom2.Adapter.BookStoreAdapter;
import com.example.app_doc_truyen_nhom2.Database.TheLoai;
import com.example.app_doc_truyen_nhom2.Database.Truyen;
import com.example.app_doc_truyen_nhom2.FireBase.FirebaseQuery;
import com.example.app_doc_truyen_nhom2.Abstract.HidingScrollListener;
import com.example.app_doc_truyen_nhom2.Abstract.OnScrollistener;
import com.example.app_doc_truyen_nhom2.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentTheoTheLoai#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentTheoTheLoai extends Fragment {
private RecyclerView recyclerViewTheotheloai;
public BookStoreAdapter bookStoreAdaptertheotheloai;
private List<Truyen> listtruyen;
private  long idtheloai;
private ProgressBar progressBar;
    private ImageButton btnopen, btnclose;
    private EditText edtsearch;
    private LinearLayout layoutsearch;
    private TextView textThongbao, txtMotaTheloai;
    private String key=null;
    private boolean isloading=false;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmentTheoTheLoai(long idtheloai) {
        // Required empty public constructor
this.idtheloai=idtheloai;

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentTheoTheLoai.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentTheoTheLoai newInstance(String param1, String param2) {
        FragmentTheoTheLoai fragment = new FragmentTheoTheLoai(0);
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    public static FragmentTheoTheLoai newInstance() {
        FragmentTheoTheLoai fragment = new FragmentTheoTheLoai(0);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public void setMenuVisibility(boolean menuVisible) {
        super.setMenuVisibility(menuVisible);
        if(getActivity()==null) return;
        if(menuVisible) getTruyen("");
    }


    private void getTruyen(String y) {
progressBar.setVisibility(View.VISIBLE);
textThongbao.setVisibility(View.GONE);
      FirebaseQuery.get(key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Truyen> truyens=new ArrayList<>();
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    Truyen truyen = snapshot1.getValue(Truyen.class);
                    if(truyen.getTenTruyen().toLowerCase(Locale.ROOT).contains(y) && truyen.getTheLoai()==idtheloai)
                       truyens.add(truyen);
                    key=snapshot1.getKey();
                }
                listtruyen.addAll(truyens);

                bookStoreAdaptertheotheloai.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);
                isloading=false;
                if (bookStoreAdaptertheotheloai.getItemCount() == 0) {
                    textThongbao.setVisibility(View.VISIBLE);
                    textThongbao.setText("Không Tìm Thấy Truyện");
                }
                if (bookStoreAdaptertheotheloai.getItemCount() > 0) {
                    textThongbao.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void reset(){
        key=null;
        listtruyen.clear();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view= inflater.inflate(R.layout.fragment_theo_the_loai, container, false);
       recyclerViewTheotheloai=view.findViewById(R.id.listbookstorelayout2);
layoutsearch=view.findViewById(R.id.layoutmotatheloai);
txtMotaTheloai=view.findViewById(R.id.motatheloai);
        edtsearch = view.findViewById(R.id.txtsearchtutruyen3);
progressBar=view.findViewById(R.id.progessbartheotheloai);
progressBar.setVisibility(View.VISIBLE);
        textThongbao = view.findViewById(R.id.txtthongbao);
textThongbao.setVisibility(View.GONE);
       BookDatabase bookDatabases=new BookDatabase(view.getContext());
      final TheLoai theLoai= bookDatabases.getTenTheLoaiIDx(idtheloai);
      txtMotaTheloai.setText(theLoai.getMoTa());
      listtruyen=new ArrayList<>();
        bookStoreAdaptertheotheloai=new BookStoreAdapter(view.getContext(),listtruyen);
        recyclerViewTheotheloai.setAdapter(bookStoreAdaptertheotheloai);
        recyclerViewTheotheloai.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerViewTheotheloai.setOnScrollListener(new HidingScrollListener(){
            @Override
            public void onHide() {
                RelativeLayout.LayoutParams layoutParams=(RelativeLayout.LayoutParams) layoutsearch.getLayoutParams();

                int searrchmargin=layoutParams.bottomMargin+layoutParams.topMargin+layoutsearch.getHeight();
                layoutsearch.animate().translationY(-searrchmargin).setInterpolator(new AccelerateInterpolator(2));

            }

            @Override
            public void onShow() {
                layoutsearch.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2));

            }
        });
recyclerViewTheotheloai.addOnScrollListener(new OnScrollistener() {
    @Override
    protected void onScrollToBottom() {
        if(!isloading){
            isloading=true;
        getTruyen("");
        }
    }
});
        getTruyen("");
        edtsearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
             reset();

                getTruyen(edtsearch.getText().toString().toLowerCase(Locale.ROOT));

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        return  view;
    }
}