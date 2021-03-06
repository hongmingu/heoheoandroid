package com.example.keepair.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.keepair.myapplication.adapter.FlowerAdapter;
import com.example.keepair.myapplication.apiservice.RestManager;
import com.example.keepair.myapplication.helper.Constants;
import com.example.keepair.myapplication.loginhelper.ReferSharedPreference;
import com.example.keepair.myapplication.model.Flower;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Keepair on 2016-08-26.
 */
public class YellowFragment extends Fragment implements FlowerAdapter.FlowerClickListener{

    private SwipeRefreshLayout swipeContainer_yellow;

    private RecyclerView mRecyclerView;

    private RestManager mRestManager;

    private FlowerAdapter mFlowerAdapter;
    private TextView mCoordinatesTextLinear;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_yellow, container, false);

        swipeContainer_yellow = (SwipeRefreshLayout) view.findViewById(R.id.swipeContainer_yellow);

        setRetainInstance(true);

        mCoordinatesTextLinear = (TextView) view.findViewById(R.id.tv_coordinates_linear);
        getLinearPosts();



        swipeContainer_yellow.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getLinearPosts();
                    }
                });
        configViews(view);
        return view;
    }

    private void getLinearPosts() {
        ReferSharedPreference preferenceCoordinates = new ReferSharedPreference(getContext());
        String lat = preferenceCoordinates.getValue("Lat", "13");
        String lon = preferenceCoordinates.getValue("Lon", "15");
        mCoordinatesTextLinear.setText(lat + "  , " + lon);

        mRestManager = new RestManager();
        Call<List<Flower>> listCall = mRestManager.getmFlowerApiService(getActivity()).getAllFlowers(lat, lon);
        listCall.enqueue(new Callback<List<Flower>>() {
            @Override
            public void onResponse(Call<List<Flower>> call, Response<List<Flower>> response) {

                if (response.isSuccessful()) {
                    mFlowerAdapter.clear();

                    List<Flower> flowerList = response.body();
                    for(int i =0; i<flowerList.size(); i++) {
                        Flower flower = flowerList.get(i);
                        mFlowerAdapter.addFlower(flower);
                    }
                    swipeContainer_yellow.setRefreshing(false);
                }
            }
            @Override
            public void onFailure(Call<List<Flower>> call, Throwable t) {
            }
        });
    }

    private void configViews(View view) {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_flower_yellow);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setRecycledViewPool(new RecyclerView.RecycledViewPool());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        mFlowerAdapter = new FlowerAdapter(this);
        mRecyclerView.setAdapter(mFlowerAdapter);
    }
    @Override
    public void onClick(int position) {
        Flower selectedFlower = mFlowerAdapter.getSelectedFlower(position);
        Intent intent = new Intent(getContext(), DetailActivity.class);
        intent.putExtra(Constants.REFERENCE.FLOWER, selectedFlower);
        startActivity(intent);

    }
}
