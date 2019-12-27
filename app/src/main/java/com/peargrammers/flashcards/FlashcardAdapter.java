package com.peargrammers.flashcards;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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
        public TextView frontsize;
        public TextView backsize;


        public FlashcardViewHolder(View itemView) {
            super(itemView);
            frontsize = itemView.findViewById(R.id.tv_catalog_name);
            backsize = itemView.findViewById(R.id.tv_catalog_category);

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
        holder.frontsize.setText(currentFlashcard.getFrontside());
        holder.backsize.setText(currentFlashcard.getBackside());
    }

    @Override
    public int getItemCount() {
        return flashcards.size();
    }

}