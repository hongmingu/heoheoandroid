package com.example.keepair.myapplication.apiservice;

import com.example.keepair.myapplication.model.Flower;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Keepair on 2016-09-17.
 */
public interface FlowerApiService {

    @GET("posts/")
    Call<List<Flower>> getAllFlowers(@Query("user_lat") String lat, @Query("user_lon") String lon);
}
