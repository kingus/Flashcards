package com.peargrammers.flashcards.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.peargrammers.flashcards.R;
import com.peargrammers.flashcards.models.Catalog;

import java.util.ArrayList;

public class CatalogAdapter extends RecyclerView.Adapter<CatalogAdapter.CatalogViewHolder> {
    private ArrayList<Catalog> catalogs;
    public static RecyclerViewClickInterface recyclerViewClickInterface;

    public CatalogAdapter(ArrayList<Catalog> exList, RecyclerViewClickInterface recyclerViewClickInterface){
        catalogs = new ArrayList<>();
        catalogs = exList;
        this.recyclerViewClickInterface = recyclerViewClickInterface;
    }


    @Override
    public int getItemCount() {
        return catalogs.size();
    }

    public static class CatalogViewHolder extends RecyclerView.ViewHolder {
        public TextView catalogName;
        public TextView catalogCategory;


        public CatalogViewHolder(View itemView) {
            super(itemView);
            catalogName = itemView.findViewById(R.id.tv_catalog_name);
            catalogCategory = itemView.findViewById(R.id.tv_catalog_category);

            itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    recyclerViewClickInterface.onItemClick(getAdapterPosition());
                }
            });
        }
    }


    @NonNull
    @Override
    public CatalogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_catalog_item, parent, false);
        CatalogViewHolder cvh = new CatalogViewHolder(v);
        return cvh;

    }



    @Override
    public void onBindViewHolder(@NonNull CatalogViewHolder holder, int position) {
        Catalog currentCatalog = catalogs.get(position);
        holder.catalogName.setText(currentCatalog.getName());
        holder.catalogCategory.setText(currentCatalog.getCategory());
    }
}
