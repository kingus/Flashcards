package com.peargrammers.flashcards;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.peargrammers.flashcards.models.Flashcard;

import java.util.List;

public class ViewFlashcardsAdapter extends PagerAdapter {

    private List<Flashcard> flashcardsList;
    private LayoutInflater layoutInflater;
    private Context context;

    public ViewFlashcardsAdapter(List<Flashcard> flashcardsList, Context context){
        this.flashcardsList = flashcardsList;
        this.context = context;
    }


    @Override
    public int getCount() {
        return flashcardsList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.view_flashcards_item, container, false);
        TextView frontside, backside;
        frontside = view.findViewById(R.id.tv_frontside);
        backside = view.findViewById(R.id.tv_backside);
        frontside.setText(flashcardsList.get(position).getFrontside());
        backside.setText(flashcardsList.get(position).getBackside());
        System.out.println(flashcardsList.get(position).getFrontside());
        container.addView(view, 0);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }
}
