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

import com.example.app_doc_truyen_nhom2.Adapter.BookStoreAdapter;
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
 * Use the {@link FragmentAllbookstore#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentAllbookstore extends Fragment {
    private RecyclerView recyclerViewAllbookstore;
    public BookStoreAdapter bookStoreAdapterallbook;
    private List<Truyen> listtr;
    private ImageButton btnopen, btnclose;
    private EditText edtsearch;
    private LinearLayout layoutsearch;
    private TextView textThongbao;
    private boolean isloading = false;
    private ProgressBar progressBar;
    private int start = 0;
    private String key = null;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmentAllbookstore() {
        // Required empty public constructor

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentAllbookstore.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentAllbookstore newInstance(String param1, String param2) {
        FragmentAllbookstore fragment = new FragmentAllbookstore();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public static FragmentAllbookstore newInstance() {
        FragmentAllbookstore fragment = new FragmentAllbookstore();
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
        if (getActivity() == null) return;
        if (menuVisible) getTruyen("");
    }

    private boolean isExist(long idtruyen) {
        boolean x = false;
        if (listtr != null) {
            for (Truyen truyen : listtr) {
                if (truyen.getIDtruyen() == idtruyen) {
                    x = true;
                }
            }
        }
        return x;
    }
private void reset(){
        key=null;
        listtr.clear();
}
    private void getTruyen(String y) {

        progressBar.setVisibility(View.VISIBLE);
        FirebaseQuery.get(key)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        List<Truyen> truyens = new ArrayList<>();
                        for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                            Truyen truyen = snapshot1.getValue(Truyen.class);
                            if (!isExist(truyen.getIDtruyen()) && truyen.getTenTruyen().toLowerCase(Locale.ROOT).contains(y)) {
                                truyens.add(truyen);
                                key = snapshot1.getKey();
                            }
                        }
                       listtr.addAll(truyens);
                        bookStoreAdapterallbook.notifyDataSetChanged();
                        isloading=false;
                        progressBar.setVisibility(View.GONE);
                        if (bookStoreAdapterallbook.getItemCount() == 0) {
                            textThongbao.setVisibility(View.VISIBLE);
                            textThongbao.setText("Không Tìm Thấy Truyện");
                        }
                        if (bookStoreAdapterallbook.getItemCount() > 0) {
                            textThongbao.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_allbookstore, container, false);
        recyclerViewAllbookstore = view.findViewById(R.id.listbookstorelayout);

        textThongbao = view.findViewById(R.id.txtthongbao);
        progressBar = view.findViewById(R.id.progessbarallbook);
        edtsearch = view.findViewById(R.id.txtsearchtutruyen);
        progressBar.setVisibility(View.VISIBLE);
        listtr=new ArrayList<>();
        bookStoreAdapterallbook = new BookStoreAdapter(view.getContext(),listtr);

        recyclerViewAllbookstore.setAdapter(bookStoreAdapterallbook);
        recyclerViewAllbookstore.setLayoutManager(new LinearLayoutManager(view.getContext()));

        recyclerViewAllbookstore.setOnScrollListener(new HidingScrollListener() {

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
        recyclerViewAllbookstore.addOnScrollListener(new OnScrollistener(7) {
            @Override
            protected void onScrollToBottom() {
                //LinearLayoutManager layoutManager=(LinearLayoutManager) recyclerView.getLayoutManager();
                //int totalItem=layoutManager.getItemCount();
                //int lastvisible=layoutManager.findFirstVisibleItemPosition();
                //if(totalItem < lastvisible +3){
                if (!isloading) {
                    getTruyen("");
                    isloading = true;

                }
                //}
            }
        });
        recyclerViewAllbookstore.addOnScrollListener(new OnScrollistener() {
            @Override
            protected void onScrollToBottom() {
                if(!isloading){
                    getTruyen("");
                    isloading=false;
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


        return view;
    }
}