package com.example.keepair.myapplication;

import android.content.Intent;
import android.graphics.Rect;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.keepair.myapplication.adapter.FlowerAdapter;
import com.example.keepair.myapplication.adapter.FlowerAdapter_grid;
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
public class RedFragment extends Fragment implements FlowerAdapter_grid.FlowerClickListener {



    private SwipeRefreshLayout swipeContainer_red;
    private RecyclerView mRecyclerView;
    private RestManager mRestManager;
    private FlowerAdapter_grid mFlowerAdapter;
    private TextView mCoordinatesTextGrid;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_red, container, false);
        swipeContainer_red = (SwipeRefreshLayout) view.findViewById(R.id.swipeContainer_red);
        setRetainInstance(true);

        mCoordinatesTextGrid = (TextView) view.findViewById(R.id.tv_coordinates_grid);
        getGridPosts();
        swipeContainer_red.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                getGridPosts();

            }
        });
        configViews(view);

        return view;
    }

    private void getGridPosts() {
        ReferSharedPreference preferenceCoordinates = new ReferSharedPreference(getContext());
        String lat = preferenceCoordinates.getValue("Lat", "13");
        String lon = preferenceCoordinates.getValue("Lon", "15");
        mCoordinatesTextGrid.setText(lat + "  , " + lon);
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
                    swipeContainer_red.setRefreshing(false);
                }
            }
            @Override
            public void onFailure(Call<List<Flower>> call, Throwable t) {
            }
        });
    }

    private void configViews(View view) {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_flower_red);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setRecycledViewPool(new RecyclerView.RecycledViewPool());
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity().getApplicationContext(), 3));
        mFlowerAdapter = new FlowerAdapter_grid(this);
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

