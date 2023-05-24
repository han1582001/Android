package com.example.app_doc_truyen_nhom2.Abstract;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public abstract class OnScrollistener extends RecyclerView.OnScrollListener {
        private int itemCountPerFetch=-1;

    public OnScrollistener(int itemCountPerFetch) {
        this.itemCountPerFetch = itemCountPerFetch;
    }

    public OnScrollistener() {
    }

    @Override
    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        LinearLayoutManager layoutManager=(LinearLayoutManager) recyclerView.getLayoutManager();
        int visibleItemCount=layoutManager.getChildCount();
        int pastVisibleItemCount=layoutManager.findFirstVisibleItemPosition();
        int totalItemCount=layoutManager.getItemCount();
        if(visibleItemCount + pastVisibleItemCount>=totalItemCount && (itemCountPerFetch<0||totalItemCount>=itemCountPerFetch)){
            onScrollToBottom();
        }

    }
    protected abstract void onScrollToBottom();
}
