package com.example.keepair.myapplication.apiservice;

import android.content.Context;

import com.example.keepair.myapplication.helper.Constants;
import com.example.keepair.myapplication.loginhelper.AddCookiesInterceptor;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Keepair on 2016-09-17.
 */
public class RestManager {

    static Context mContext;

    private FlowerApiService mFlowerApiService;

    public FlowerApiService getmFlowerApiService(Context context) {
        if (mFlowerApiService==null) {

            mContext = context;

            OkHttpClient client = new OkHttpClient();
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.interceptors().add(new AddCookiesInterceptor(mContext));
            client = builder.build();

            Retrofit retrofit = new Retrofit.Builder()
                    .client(client)
                    .baseUrl(Constants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            mFlowerApiService = retrofit.create(FlowerApiService.class);

        }

        return mFlowerApiService;
    }
}
