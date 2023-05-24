package com.example.app_doc_truyen_nhom2.Fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.app_doc_truyen_nhom2.Adapter.DanhSachChuongAdapter;
import com.example.app_doc_truyen_nhom2.Database.BookDatabase;
import com.example.app_doc_truyen_nhom2.Database.DSchuong;
import com.example.app_doc_truyen_nhom2.Database.Truyen;
import com.example.app_doc_truyen_nhom2.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DSchuongFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DSchuongFragment extends Fragment {
    private TextView tTitle;
    private DSchuong dSchuong;
    private static List<DSchuong> dSchuongList;
    Context context;
    private RadioGroup radioGroup;
    private RadioButton nomal, lastest;
    private RecyclerView viewDschuong;
    private static Truyen truyen;
    private DanhSachChuongAdapter danhSachChuongAdapter;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static final String AR_DATA = "truyens";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DSchuongFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DSchuongFragment newInstance(String param1, String param2) {
        DSchuongFragment fragment = new DSchuongFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public static DSchuongFragment newInstances() {
        DSchuongFragment fragment = new DSchuongFragment();
        return fragment;
    }

    public DSchuongFragment() {
        // Required empty public constructor
    }

    public static DSchuongFragment newInstances(long idtruyen) {
        DSchuongFragment fragment = new DSchuongFragment();
        Bundle bundle = new Bundle();
        bundle.putLong(AR_DATA, idtruyen);
        fragment.setArguments(bundle);
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_d_schuong, container, false);
        tTitle = view.findViewById(R.id.tieudechuong);
        viewDschuong = view.findViewById(R.id.rclDschuong);
        nomal=view.findViewById(R.id.rdSapxepnomal);
        lastest=view.findViewById(R.id.rdSapxepmoinhat);

        dSchuongList = new ArrayList<>();
        BookDatabase database= new BookDatabase(view.getContext());
        long idtruyen=(Long) getArguments().getLong(DSchuongFragment.AR_DATA);
        dSchuongList=database.getChuong(idtruyen);
        danhSachChuongAdapter = new DanhSachChuongAdapter(view.getContext(), dSchuongList, getArguments().getLong(DSchuongFragment.AR_DATA));
        viewDschuong.setAdapter(danhSachChuongAdapter);
        viewDschuong.setLayoutManager(new LinearLayoutManager(view.getContext()));
        nomal.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){

            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    BookDatabase database= new BookDatabase(view.getContext());
                    long idtruyen=(Long) getArguments().getLong(DSchuongFragment.AR_DATA);
                    dSchuongList=database.getChuong(idtruyen);
                    danhSachChuongAdapter = new DanhSachChuongAdapter(view.getContext(), dSchuongList, getArguments().getLong(DSchuongFragment.AR_DATA));
                    viewDschuong.setAdapter(danhSachChuongAdapter);
                    viewDschuong.setLayoutManager(new LinearLayoutManager(view.getContext()));
                }
            }
        });

        lastest.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){

            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    BookDatabase database= new BookDatabase(view.getContext());
                    long idtruyen=(Long) getArguments().getLong(DSchuongFragment.AR_DATA);
                    dSchuongList=database.getChuongMoitruoc(idtruyen);
                    danhSachChuongAdapter = new DanhSachChuongAdapter(view.getContext(), dSchuongList, getArguments().getLong(DSchuongFragment.AR_DATA));
                    viewDschuong.setAdapter(danhSachChuongAdapter);
                    viewDschuong.setLayoutManager(new LinearLayoutManager(view.getContext()));
                }
            }
        });
        return view;
    }

}