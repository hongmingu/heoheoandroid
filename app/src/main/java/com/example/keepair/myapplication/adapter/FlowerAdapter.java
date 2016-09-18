package com.example.keepair.myapplication.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
public class FlowerAdapter extends RecyclerView.Adapter<FlowerAdapter.Holder> {

    private static final Object TAG = FlowerAdapter.class.getSimpleName();
    private final FlowerClickListener mListener;
    private List<Flower> mFlowers;

    public FlowerAdapter(FlowerClickListener listener) {

        mFlowers = new ArrayList<>();

        mListener = listener;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {

        View row = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item, null, false);
        return new Holder(row);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {

        Flower currFlower = mFlowers.get(position);

        holder.mText.setText("$" + currFlower.getText());

        Glide.with(holder.itemView.getContext()).load(currFlower.getImage()).into(holder.mImage);

    }

    @Override
    public int getItemCount() {
        return mFlowers.size();
//        return mFlowers.size();
    }

    public void addFlower(Flower flower) {
//        Log.d((String) TAG, flower.getImage());
        mFlowers.add(flower);
        notifyDataSetChanged();

    }

    public Flower getSelectedFlower(int position) {
        return mFlowers.get(position);
    }

    public class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView mImage;
        private TextView mAuthor, mText;


        public Holder(View itemView) {

            super(itemView);

            mImage = (ImageView) itemView.findViewById(R.id.iv_photo);
            mAuthor = (TextView) itemView.findViewById(R.id.tv_author);
            mText = (TextView) itemView.findViewById(R.id.tv_text);

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
