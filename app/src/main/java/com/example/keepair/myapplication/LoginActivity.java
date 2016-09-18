package com.example.keepair.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class LoginActivity extends AppCompatActivity {

    MyKey keygiven;

    LoginApiService loginApiService;

    @Bind(R.id.testButton)
    Button mTestButton;

    @Bind(R.id.usernameTextField)
    EditText mUsernameTextField;

    @Bind(R.id.emailTextField)
    EditText mEmailTextField;

    @Bind(R.id.passwordTextField)
    EditText mPasswordTextField;

    @Bind(R.id.loginButton)
    Button mLoginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.loginButton)
    public void onClick(View view) {

        String givenUserName = mUsernameTextField.getText().toString();
        final String givenEmail = mEmailTextField.getText().toString();
        String givenPassword = mPasswordTextField.getText().toString();
        LoginData loginData = new LoginData(givenUserName, givenPassword, givenEmail);

        final ReferSharedPreference ref_key = new ReferSharedPreference(this);


        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        logging.setLevel(HttpLoggingInterceptor.Level.HEADERS);

        OkHttpClient client = new OkHttpClient();
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
//        builder.interceptors().add(new AddCookiesInterceptor(getApplicationContext()));
//        builder.interceptors().add(new RecievedCookiesInterceptor(getApplicationContext()));

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
                if (response.code() == 200) {
                    keygiven = response.body();
                    Toast.makeText(getApplicationContext(), keygiven.getKey(), Toast.LENGTH_LONG).show();
                    ReferSharedPreference givenToken = new ReferSharedPreference(getApplicationContext());
                    givenToken.put("Token", "Token "+ keygiven.getKey());
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                }
            }

            @Override
            public void onFailure(Call<MyKey> call, Throwable t) {
            }
        });
    }
}

