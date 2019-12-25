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
        public TextView catalogName;
        public TextView catalogCategory;

        public CatalogViewHolder(View itemView) {
            super(itemView);
            catalogName = itemView.findViewById(R.id.tv_catalog_name);
            catalogCategory = itemView.findViewById(R.id.tv_catalog_category);
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
        holder.catalogName.setText(currentCatalog.getName());
        holder.catalogCategory.setText(currentCatalog.getCategory());
    }
}
