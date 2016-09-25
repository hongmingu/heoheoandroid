package com.example.keepair.myapplication.loginhelper;

import android.content.Context;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Keepair on 2016-09-17.
 */
public class AddCookiesInterceptor implements Interceptor {

    private Context context;

    public AddCookiesInterceptor(Context context) {
        this.context = context;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request.Builder builder = chain.request().newBuilder();

        ReferSharedPreference forheader = new ReferSharedPreference(context);

        String token = forheader.getValue("Token", "Token 7f3115e69f21c0c29324489a89d3ead96f2124df");

        builder.addHeader("Authorization", token);

        return chain.proceed(builder.build());
    }
}
