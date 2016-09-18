package com.example.keepair.myapplication.apiservice;

import com.example.keepair.myapplication.model.LoginData;
import com.example.keepair.myapplication.model.MyKey;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Keepair on 2016-09-17.
 */
public interface LoginApiService {

    String string = "client_Id";


    @GET("comments")
    Call<ResponseBody> getComment(@Query("postId")int postId);

//    @POST("posts/")
//    Call<ResponseBody> gettest(@Body TextData textData);

    @POST("rest-auth/login/")
    Call<MyKey> getget(@Body LoginData loginData);
}
