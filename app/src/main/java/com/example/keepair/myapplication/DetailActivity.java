package com.example.keepair.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.keepair.myapplication.helper.Constants;
import com.example.keepair.myapplication.model.Flower;

import uk.co.senab.photoview.PhotoViewAttacher;

public class DetailActivity extends FragmentActivity {

    private ImageView mImage;
    private TextView mAuthor, mText;
    private PhotoViewAttacher mPhotoViewAttacher;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_detail);

        Display display = ((WindowManager) getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        int width = (int) (display.getWidth() * 0.999); //Display 사이즈의 70%
        int height = (int) (display.getHeight() * 0.9);  //Display 사이즈의 90%
        getWindow().getAttributes().width = width;
        getWindow().getAttributes().height = height;


        Intent intent = getIntent();

        Flower flower = (Flower) intent.getSerializableExtra(Constants.REFERENCE.FLOWER);

        configViews();

        mAuthor.setText(flower.getAuthor());
        mText.setText(flower.getText());
        Glide.with(getApplicationContext()).load(flower.getImage()).into(mImage);

    }

    private void configViews() {
        mImage = (ImageView) findViewById(R.id.iv_photo_detail);
        mPhotoViewAttacher = new PhotoViewAttacher(mImage);
        mText = (TextView) findViewById(R.id.tv_text_detail);
        mAuthor = (TextView) findViewById(R.id.tv_author_detail);
    }
}
