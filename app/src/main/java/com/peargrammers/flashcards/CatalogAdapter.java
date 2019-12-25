package com.peargrammers.flashcards;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.peargrammers.flashcards.models.Catalog;

import java.util.ArrayList;

public class CatalogAdapter extends RecyclerView.Adapter<CatalogAdapter.CatalogViewHolder> {
    private ArrayList<Catalog> catalogs;



    public static class CatalogViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextView1;
        public TextView mTextView2;

        public CatalogViewHolder(View itemView) {
            super(itemView);
            mTextView1 = itemView.findViewById(R.id.textView);
            mTextView2 = itemView.findViewById(R.id.textView2);
        }
    }
    @Override
    public int getItemCount() {
        return catalogs.size();
    }

    @NonNull
    @Override
    public CatalogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_catalog_item, parent, false);
        CatalogViewHolder cvh = new CatalogViewHolder(v);
        return cvh;

    }

    public CatalogAdapter(ArrayList<Catalog> exList){
        catalogs = new ArrayList<>();
        catalogs = exList;
    }

    @Override
    public void onBindViewHolder(@NonNull CatalogViewHolder holder, int position) {
        Catalog currentCatalog = catalogs.get(position);
        holder.mTextView1.setText(currentCatalog.getCategory());
        holder.mTextView2.setText(currentCatalog.getName());
    }


}
