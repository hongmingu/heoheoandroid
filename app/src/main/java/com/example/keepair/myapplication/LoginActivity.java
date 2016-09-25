package com.example.keepair.myapplication;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.keepair.myapplication.apiservice.LoginApiService;
import com.example.keepair.myapplication.helper.Constants;
import com.example.keepair.myapplication.loginhelper.ReferSharedPreference;
import com.example.keepair.myapplication.model.LoginData;
import com.example.keepair.myapplication.model.MyKey;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends FragmentActivity {

    MyKey keygiven;

    LoginApiService loginApiService;

    @Bind(R.id.usernameTextField)
    EditText mUsernameTextField;

    @Bind(R.id.passwordTextField)
    EditText mPasswordTextField;

    @Bind(R.id.loginButton)
    Button mLoginButton;

    @Bind(R.id.btn_start_RegistrationActivity)
    Button mStartRegistrationButton;

    @Bind(R.id.layout_Login)
    RelativeLayout mLayoutLogin;

    ReferSharedPreference mSavedUserInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        mSavedUserInfo = new ReferSharedPreference(getApplicationContext());


        mUsernameTextField.setText(mSavedUserInfo.getValue("SavedUserName", ""));
        mPasswordTextField.setText(mSavedUserInfo.getValue("SavedPassword", ""));


    }

    @OnClick(R.id.btn_start_RegistrationActivity)
    public void onClickToStartRegistration(View view){
        Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.loginButton)
    public void onClick(View view) {

        final String givenUserName = mUsernameTextField.getText().toString();
        String givenEmail = "";
        final String givenPassword = mPasswordTextField.getText().toString();
        LoginData loginData = new LoginData(givenUserName, givenPassword, givenEmail);

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        logging.setLevel(HttpLoggingInterceptor.Level.HEADERS);

        OkHttpClient client = new OkHttpClient();
        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        builder.interceptors().add(logging);

        client = builder.build();

        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Constants.Login_API_URL)
                .build();

        loginApiService = retrofit.create(LoginApiService.class);



        Call<MyKey> getget = loginApiService.getget(loginData);
        getget.enqueue(new Callback<MyKey>() {
            @Override
            public void onResponse(Call<MyKey> call, Response<MyKey> response) {
                if (response.isSuccessful()) {
                    keygiven = response.body();

                    Toast.makeText(getApplicationContext(),"We waited you, " + givenUserName, Toast.LENGTH_LONG).show();

                    mSavedUserInfo.put("SavedUserName", givenUserName);
                    mSavedUserInfo.put("SavedPassword", givenPassword);

                    ReferSharedPreference givenToken = new ReferSharedPreference(getApplicationContext());
                    givenToken.put("Token", "Token "+ keygiven.getKey());
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(getApplicationContext(),"Failed", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<MyKey> call, Throwable t) {
            }
        });
    }
}

