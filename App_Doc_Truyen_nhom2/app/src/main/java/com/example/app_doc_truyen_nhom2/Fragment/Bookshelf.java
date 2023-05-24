package com.example.app_doc_truyen_nhom2.Fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.app_doc_truyen_nhom2.Database.BookDatabase;
import com.example.app_doc_truyen_nhom2.Adapter.BookshelfAdapter;
import com.example.app_doc_truyen_nhom2.Database.Truyen;
import com.example.app_doc_truyen_nhom2.Abstract.HidingScrollListener;
import com.example.app_doc_truyen_nhom2.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Bookshelf#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Bookshelf extends Fragment {
    private static List<Truyen> listtruyen;
    private RecyclerView recyclerViewBookshelf;
    private BookshelfAdapter bookshelfAdapter;
    private BookDatabase database;
    private TextView textThongbao;
    private EditText edtsearch;
    private static List<Truyen> listalltruyen;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Bookshelf() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Bookshelf.
     */
    // TODO: Rename and change types and number of parameters
    public static Bookshelf newInstance(String param1, String param2) {
        Bookshelf fragment = new Bookshelf();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
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


    public void xoatruyenkhongdoc(Context context) {
        BookDatabase db = new BookDatabase(context);
        List<Truyen> list = new ArrayList<>();
        list = db.getAllTruyen();
        for (Truyen truyen : list) {
            if (truyen.getChuongdangdoc() == 0) {
                db.xoatruyenID(truyen.getIDtruyen());
            }
        }
    }
private void loadTruyen(String y){
        listtruyen.clear();
    database = new BookDatabase(getContext());
    listalltruyen = database.getTruyenDangDoc();
    if(listalltruyen.isEmpty()){
        textThongbao.setVisibility(View.VISIBLE);
        textThongbao.setText("Bạn Chưa Lưu Truyện Nào");
    }else {
        for (Truyen truyen : listalltruyen) {
            if (truyen.getTenTruyen().toLowerCase(Locale.ROOT).contains(y)) {
                listtruyen.add(truyen);
            }
        }
        bookshelfAdapter.notifyDataSetChanged();
        if (listtruyen.size() == 0) {
            textThongbao.setVisibility(View.VISIBLE);
            textThongbao.setText("Không Tìm Thấy Truyện");
        }
        if (listtruyen.size() > 0) {
            textThongbao.setVisibility(View.GONE);
        }
    }
}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bookshelf, container, false);
        recyclerViewBookshelf = view.findViewById(R.id.recyclerTusach);
        textThongbao = view.findViewById(R.id.txtthongbao);
        edtsearch = view.findViewById(R.id.txtsearchtutruyen);
        listtruyen = new ArrayList<>();
        bookshelfAdapter = new BookshelfAdapter(view.getContext(), listtruyen);
        recyclerViewBookshelf.setAdapter(bookshelfAdapter);
        recyclerViewBookshelf.setLayoutManager(new GridLayoutManager(view.getContext(), 3));
        recyclerViewBookshelf.setOnScrollListener(new HidingScrollListener() {

            @Override
            public void onHide() {
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) edtsearch.getLayoutParams();

                int searrchmargin = layoutParams.bottomMargin + layoutParams.topMargin + edtsearch.getHeight();
                edtsearch.animate().translationY(-searrchmargin).setInterpolator(new AccelerateInterpolator(2));

            }

            @Override
            public void onShow() {
                edtsearch.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2));

            }
        });
loadTruyen("");
        edtsearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
loadTruyen(edtsearch.getText().toString().toLowerCase(Locale.ROOT));
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        return view;
    }
}