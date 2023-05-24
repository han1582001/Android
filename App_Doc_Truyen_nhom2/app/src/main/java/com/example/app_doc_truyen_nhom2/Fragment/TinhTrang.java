package com.example.app_doc_truyen_nhom2.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.app_doc_truyen_nhom2.Database.BookDatabase;
import com.example.app_doc_truyen_nhom2.Database.Truyen;
import com.example.app_doc_truyen_nhom2.R;
import com.example.app_doc_truyen_nhom2.TacGiaActivity;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TinhTrang#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TinhTrang extends Fragment {
    private TextView tNoidung, tgotocungTacGia;
    private static Truyen truyen;
    private String NoiDung;
    Context context;

    private static List<Truyen> listtruyen;
    private static List<Truyen> listtruyen2;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static final String ARG_DATA = "arg_data";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public TinhTrang() {

    }


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TinhTrang.
     */
    // TODO: Rename and change types and number of parameters
    public static TinhTrang newInstance(String param1, String param2) {
        TinhTrang fragment = new TinhTrang();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public static TinhTrang newInstances(Long idtruyen1) {
        TinhTrang fragment = new TinhTrang();
        Bundle bdl = new Bundle();
        bdl.putLong(ARG_DATA, idtruyen1);
        fragment.setArguments(bdl);
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


        View view = inflater.inflate(R.layout.fragment_tinh_trang, container, false);

        tNoidung = view.findViewById(R.id.dtnoidung);
tgotocungTacGia=view.findViewById(R.id.txttitleTruyencungTacGia);

        BookDatabase database=new BookDatabase(view.getContext());
        truyen = database.getTruyenID(getArguments().getLong(TinhTrang.ARG_DATA));
        tgotocungTacGia.setText("Xem Thêm Truyện Của Tác Giả " + truyen.getTacGia());
tNoidung.setText(truyen.getMoTa());
tgotocungTacGia.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent intent=new Intent(getContext(), TacGiaActivity.class);
        Bundle bundle=new Bundle();
        bundle.putString("tentacgia", truyen.getTacGia());
        intent.putExtras(bundle);
        view.getContext().startActivity(intent);
    }
});
        return view;
    }
}