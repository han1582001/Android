package com.example.app_doc_truyen_nhom2.Abstract;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public abstract class HidingScrollListener extends RecyclerView.OnScrollListener {
    private static final int HIDE_THRESHOLD=20;
    private int scrolledDistance=0;
    public boolean controlsVisible=true;

    @Override
    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        if(scrolledDistance>HIDE_THRESHOLD && controlsVisible){
            onHide();
            controlsVisible=false;
            scrolledDistance=0;
        }else if(scrolledDistance< -HIDE_THRESHOLD && !controlsVisible){
            onShow();
            controlsVisible=true;
            scrolledDistance=0;
        }
        if((controlsVisible && dy>0) || (!controlsVisible && dy<0)){
            scrolledDistance+=dy;
        }
    }
    public abstract void onHide();
    public abstract void onShow();
}
