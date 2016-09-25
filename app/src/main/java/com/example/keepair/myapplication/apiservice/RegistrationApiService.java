package com.example.keepair.myapplication.apiservice;

import com.example.keepair.myapplication.model.MyKey;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by Keepair on 2016-09-25.
 */
public interface RegistrationApiService {




    @Multipart
    @POST("rest-auth/registration/")
    Call<ResponseBody> registersecond (@Part("username") RequestBody username, @Part("email") RequestBody email, @Part("password1") RequestBody password1, @Part("password2") RequestBody password2);

}
