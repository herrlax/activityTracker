package com.salmon.sports.spots;

import android.graphics.Outline;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mikael Malmqvist on 2015-05-28.
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private List<SportItem> mDataset;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public CardView cardView;
        public TextView cardText;
        public TextView cardDateText;
        public ImageView cardIcon;

        // TODO define stuff for the cardview here..
        public ViewHolder(View v) {
            super(v);

            // In this case v is an "instance" of card_layout
            // and cardView is the cardview inside
            cardView = (CardView)v.findViewById(R.id.card_view);
            cardText = (TextView)v.findViewById(R.id.card_text);
            cardIcon = (ImageView)v.findViewById(R.id.card_icon);
            cardDateText = (TextView)v.findViewById(R.id.date_text);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapter(List<SportItem> myDataset) {
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_layout, parent, false);
        // set the view's size, margins, paddings and layout parameters


        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.cardText.setText(mDataset.get(position).toString());
        holder.cardIcon.setImageResource(mDataset.get(position).getICON());
        holder.cardDateText.setText(mDataset.get(position).getDate());

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();

    }
}
