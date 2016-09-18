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

    public static final String PREF_COOKIES = "PREF_COOKIES";
    // We're storing our stuff in a database made just for cookies called PREF_COOKIES.
    // I reccomend you do this, and don't change this default value.
    private Context context;

    public AddCookiesInterceptor(Context context) {
        this.context = context;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request.Builder builder = chain.request().newBuilder();

        ReferSharedPreference forheader = new ReferSharedPreference(context);
        String token = forheader.getValue("Token", "Token 7f3115e69f21c0c29324489a89d3ead96f2124df");

//        HashSet<String> preferences = (HashSet<String>) PreferenceManager.getDefaultSharedPreferences(context).getStringSet(PREF_COOKIES, new HashSet<String>());

        // Use the following if you need everything in one line.
        // Some APIs die if you do it differently.
//        String cookiestring = "";
//        for (String cookie : preferences) {
//            String[] parser = cookie.split(":");
//            cookiestring = cookiestring + parser[0] + "; ";
//        }
//        builder.addHeader("Cookie", cookiestring);

//        for (String cookie : preferences) {
        builder.addHeader("Authorization", token);
//            builder.addHeader("Authorization", "Token " + cookie);
//        }

        return chain.proceed(builder.build());
    }
}
