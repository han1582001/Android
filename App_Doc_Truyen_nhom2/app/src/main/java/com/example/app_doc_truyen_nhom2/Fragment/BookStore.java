package com.example.app_doc_truyen_nhom2.Fragment;

import android.content.Context;
import android.content.res.ColorStateList;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.app_doc_truyen_nhom2.R;
import com.google.android.material.tabs.TabLayout;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BookStore#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BookStore extends Fragment {
    private static SectionsPageAdapter2 sectionsPageAdapter;
   public static TabLayout tabLayout;
    public static ViewPager viewPager;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public BookStore() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BookStore.
     */
    // TODO: Rename and change types and number of parameters
    public static BookStore newInstance(String param1, String param2) {
        BookStore fragment = new BookStore();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public static BookStore newInstance() {
        BookStore fragment = new BookStore();
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
    final boolean isNetworkConnected(Context context1) {
        ConnectivityManager connectivityManager = (ConnectivityManager)
                context1.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_book_store, container, false);
        viewPager = (ViewPager) view.findViewById(R.id.boolstoreview_pager);
        tabLayout = (TabLayout) view.findViewById(R.id.tabssdetail);
        sectionsPageAdapter = new SectionsPageAdapter2(getActivity().getSupportFragmentManager(), 0);
        viewPager.setAdapter(sectionsPageAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabsFromPagerAdapter(sectionsPageAdapter);
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));
       tabLayout.setTabTextColors(ContextCompat.getColor(view.getContext(),R.color.unselect),ContextCompat.getColor(view.getContext(),R.color.selected));
       tabLayout.setSelectedTabIndicatorColor(ContextCompat.getColor(view.getContext(),R.color.selected));
        return view;
    }

    public class SectionsPageAdapter2 extends FragmentStatePagerAdapter {


        public SectionsPageAdapter2(@NonNull FragmentManager fm, int behavior) {
            super(fm, behavior);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new FragmentAllbookstore();

                case 1:
                    return new FragmentTheoTheLoai(0);

                case 2:
                    return new FragmentTheoTheLoai(1);
                case 3:
                    return new FragmentTheoTheLoai(2);
                case 4:
                    return new FragmentTheoTheLoai(3);
                case 5:
                    return new FragmentTheoTheLoai(4);
                case 6:
                    return new FragmentTheoTheLoai(5);
                case 7:
                    return new FragmentTheoTheLoai(6);
                case 8:
                    return new FragmentTheoTheLoai(7);
            }
            return null;

        }

        @Override
        public int getCount() {
            return 9;
        }
        public CharSequence getPageTitle(int position) {
            switch (position) {

                case 0:
                    return "Tất Cả";
                case 1:
                    return "Tiên Hiệp";
                case 2:
                    return "Linh Dị";
                case 3:
                    return "Ngôn Tình";
                case 4:
                    return "Xuyên Không";
                case 5:
                    return "Lịch Sử, Cổ Đại";
                case 6:
                    return "Hệ Thống";
                case 7:
                    return "Đô Thị";
                case 8:
                    return "Khác";
            }
            return null;
        }
        @Override
        public void notifyDataSetChanged() {
            super.notifyDataSetChanged();
        }
    }
}