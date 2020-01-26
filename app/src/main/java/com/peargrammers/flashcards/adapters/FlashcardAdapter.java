package com.peargrammers.flashcards.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.peargrammers.flashcards.R;
import com.peargrammers.flashcards.models.Flashcard;

import java.util.ArrayList;

public class FlashcardAdapter extends RecyclerView.Adapter<FlashcardAdapter.FlashcardViewHolder> {
    private ArrayList<Flashcard> flashcards;
    public static RecyclerViewClickInterface recyclerViewClickInterface;

    public FlashcardAdapter(ArrayList<Flashcard> flashcards, RecyclerViewClickInterface recyclerViewClickInterface) {
        this.flashcards = new ArrayList<>();
        this.flashcards = flashcards;
        this.recyclerViewClickInterface = recyclerViewClickInterface;
    }

    public static class FlashcardViewHolder extends RecyclerView.ViewHolder {
        public TextView frontside;
        public TextView backside;
        public TextView tvLevel;


        public FlashcardViewHolder(View itemView) {
            super(itemView);
            frontside = itemView.findViewById(R.id.tv_catalog_name);
            backside = itemView.findViewById(R.id.tv_catalog_category);
            tvLevel = itemView.findViewById(R.id.tv_level);

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
    public FlashcardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_catalog_item, parent, false);
        FlashcardViewHolder flashcardViewHolder = new FlashcardViewHolder(v);

        return flashcardViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FlashcardViewHolder holder, int position) {
        Flashcard currentFlashcard = flashcards.get(position);
        holder.frontside.setText(currentFlashcard.getFrontside());
        holder.backside.setText(currentFlashcard.getBackside());
        String lvl = String.valueOf(currentFlashcard.getSmallBox());
        holder.tvLevel.setText(lvl);
    }

    @Override
    public int getItemCount() {
        return flashcards.size();
    }

}
