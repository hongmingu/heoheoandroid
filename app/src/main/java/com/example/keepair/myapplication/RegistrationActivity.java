package com.example.keepair.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.keepair.myapplication.apiservice.RegistrationApiService;
import com.example.keepair.myapplication.helper.Constants;
import com.example.keepair.myapplication.loginhelper.ReferSharedPreference;
import com.example.keepair.myapplication.model.MyKey;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegistrationActivity extends Activity {
    MyKey keygoven;

    RegistrationApiService mRegistrationApiService;
    EditText mUsernameRegistrationEditText;
    EditText mPasswordRegistration1EditText;
    EditText mPasswordRegistration2EditText;
    Button mRegisterButton;
    RelativeLayout mLayoutRegstration;

    ReferSharedPreference mSavedUserInfo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_registration);
        Display display = ((WindowManager) getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        int width = (int) (display.getWidth() * 0.999); //Display 사이즈의 70%
        int height = (int) (display.getHeight() * 0.9);  //Display 사이즈의 90%
        getWindow().getAttributes().width = width;
        getWindow().getAttributes().height = height;

        mSavedUserInfo = new ReferSharedPreference(getApplicationContext());



        mUsernameRegistrationEditText = (EditText) findViewById(R.id.et_username_registration);
        mPasswordRegistration1EditText = (EditText) findViewById(R.id.et_password_registration_1);
        mPasswordRegistration2EditText = (EditText) findViewById(R.id.et_password_registration_2);
        mRegisterButton = (Button) findViewById(R.id.btn_registration);
        mLayoutRegstration = (RelativeLayout) findViewById(R.id.layoutRegistration);

        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String givenRegistrationUserName = mUsernameRegistrationEditText.getText().toString();
                final String givenRegistrationPassword1 = mPasswordRegistration1EditText.getText().toString();
                String givenRegistrationPassword2 = mPasswordRegistration2EditText.getText().toString();
                String givenEmail = "";

                if(givenRegistrationUserName.length() >= 6){
                    if(givenRegistrationPassword1.equals(givenRegistrationPassword2)){
                        if(givenRegistrationPassword1.length() >= 8){
                            OkHttpClient client = new OkHttpClient();
                            OkHttpClient.Builder builder = new OkHttpClient.Builder();
                            client = builder.build();

                            Retrofit retrofit = new Retrofit.Builder()
                                    .client(client)
                                    .addConverterFactory(GsonConverterFactory.create())
                                    .baseUrl(Constants.Login_API_URL)
                                    .build();

                            mRegistrationApiService = retrofit.create(RegistrationApiService.class);

                            RequestBody username =
                                    RequestBody.create(
                                            MediaType.parse("multipart/form-data"), givenRegistrationUserName);
                            RequestBody email =
                                    RequestBody.create(
                                            MediaType.parse("multipart/form-data"), givenEmail);
                            RequestBody password1 =
                                    RequestBody.create(
                                            MediaType.parse("multipart/form-data"), givenRegistrationPassword1);
                            RequestBody password2 =
                                    RequestBody.create(
                                            MediaType.parse("multipart/form-data"), givenRegistrationPassword2);

                            Call<ResponseBody> call = mRegistrationApiService.registersecond(username, email, password1, password2);
                            call.enqueue(new Callback<ResponseBody>() {
                                @Override
                                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                    if (response.isSuccessful()){
                                        Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_LONG).show();
                                        mSavedUserInfo.put("SavedUserName", givenRegistrationUserName);
                                        mSavedUserInfo.put("SavedPassword", givenRegistrationPassword1);
                                        Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                    }
                                    else {
                                        Toast.makeText(getApplicationContext(), "Something wrong, Would you check password conditions?", Toast.LENGTH_LONG).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<ResponseBody> call, Throwable t) {

                                }
                            });
                        }
                        else{
                            Toast.makeText(getApplicationContext(),"Password has to be >= 8",Toast.LENGTH_LONG).show();
                        }
                    }
                    else {
                        Toast.makeText(getApplicationContext(), " password and password_entered are not equal", Toast.LENGTH_LONG).show();
                    }
                }
                else {
                    Toast.makeText(getApplicationContext(), "Username must be >= 6", Toast.LENGTH_LONG).show();
                }
            }
        });

    }


}

