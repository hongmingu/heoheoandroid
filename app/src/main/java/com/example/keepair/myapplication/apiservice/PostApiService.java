package com.example.keepair.myapplication.apiservice;

import com.example.keepair.myapplication.model.PostData;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by Keepair on 2016-09-18.
 */
public interface PostApiService {

    @Multipart
    @POST("posts/")
    Call<ResponseBody> uploadFile (@Part MultipartBody.Part part, @Part("point") RequestBody description);
}
