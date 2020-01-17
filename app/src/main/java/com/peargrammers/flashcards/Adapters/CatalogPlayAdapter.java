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

public class CatalogPlayAdapter extends RecyclerView.Adapter<CatalogPlayAdapter.CatalogPlayViewHolder>  {

    private ArrayList<Catalog> catalogs;
    public static RecyclerViewClickInterface recyclerViewClickInterface;

    public CatalogPlayAdapter() {
    }

    public CatalogPlayAdapter(ArrayList<Catalog> exList, RecyclerViewClickInterface recyclerViewClickInterface){
        catalogs = new ArrayList<>();
        catalogs = exList;
        this.recyclerViewClickInterface = recyclerViewClickInterface;
    }

    public static class CatalogPlayViewHolder extends RecyclerView.ViewHolder {
        public TextView catalogName;

        public CatalogPlayViewHolder(View itemView) {
            super(itemView);
            catalogName = itemView.findViewById(R.id.category_name);

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
    public CatalogPlayViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item, parent, false);
        CatalogPlayViewHolder cvh = new CatalogPlayViewHolder(v);
        return cvh;

    }

    @Override
    public void onBindViewHolder(@NonNull CatalogPlayViewHolder holder, int position) {
        Catalog currentCatalog = catalogs.get(position);
        holder.catalogName.setText(currentCatalog.getName());
    }


    @Override
    public int getItemCount() {
        return catalogs.size();
    }
}
