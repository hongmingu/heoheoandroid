package com.example.keepair.myapplication;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.keepair.myapplication.helper.Constants;
import com.example.keepair.myapplication.model.Flower;

public class DetailActivity extends AppCompatActivity {

    private ImageView mImage;
    private TextView mAuthor, mText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();

        Flower flower = (Flower) intent.getSerializableExtra(Constants.REFERENCE.FLOWER);

        configViews();

        mAuthor.setText(flower.getAuthor());
        mText.setText(flower.getText());

        Glide.with(getApplicationContext()).load(flower.getImage()).into(mImage);

    }

    private void configViews() {
        mImage = (ImageView) findViewById(R.id.iv_photo_detail);
        mText = (TextView) findViewById(R.id.tv_text_detail);
        mAuthor = (TextView) findViewById(R.id.tv_author_detail);
    }
}
