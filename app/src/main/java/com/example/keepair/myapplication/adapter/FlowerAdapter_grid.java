package com.example.keepair.myapplication.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.keepair.myapplication.R;
import com.example.keepair.myapplication.model.Flower;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Keepair on 2016-09-17.
 */
public class FlowerAdapter_grid extends RecyclerView.Adapter<FlowerAdapter_grid.Holder> {

    private static final Object TAG = FlowerAdapter_grid.class.getSimpleName();
    private final FlowerClickListener mListener;
    private List<Flower> mFlowers;
    public FlowerAdapter_grid(FlowerClickListener listener) {
        mFlowers = new ArrayList<>();
        mListener = listener;
    }
    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item_grid, parent, false);
        return new Holder(row);
    }
    @Override
    public void onBindViewHolder(Holder holder, int position) {
        Flower currFlower = mFlowers.get(position);
        Glide.with(holder.itemView.getContext())
                .load(currFlower.getImage())
                .thumbnail(0.5f)
                .crossFade()
                .into(holder.mImage);
    }

    @Override
    public int getItemCount() {
        return mFlowers.size();
    }

    public void addFlower(Flower flower) {
        mFlowers.add(flower);
        notifyDataSetChanged();
    }
    public void clear(){
        mFlowers.clear();
        notifyDataSetChanged();
    }

    public Flower getSelectedFlower(int position) {
        return mFlowers.get(position);
    }

    public class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView mImage;
        public Holder(View itemView) {
            super(itemView);
            mImage = (ImageView) itemView.findViewById(R.id.iv_photo);
            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View view) {
            mListener.onClick(getLayoutPosition());
        }
    }

    public interface FlowerClickListener {
        void onClick(int position);
    }
}
