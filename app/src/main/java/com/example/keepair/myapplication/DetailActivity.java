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

import java.text.ParseException;
import java.util.Date;
import java.util.TimeZone;

import uk.co.senab.photoview.PhotoViewAttacher;

public class DetailActivity extends FragmentActivity {

    private ImageView mImage;
    private TextView mAuthor, mText, mTime;
    private PhotoViewAttacher mPhotoViewAttacher;
    private Date mDateToUse;
    private String mStriingDate;

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

        mStriingDate = flower.getCreated_date().substring(0, flower.getCreated_date().length()-1);

        java.text.SimpleDateFormat format = new java.text.SimpleDateFormat(
                "yyyy-MM-dd'T'HH:mm:ss.SSSSSS");
        format.setTimeZone(TimeZone.getTimeZone("GMT"));
        mDateToUse = null;
        try {
            mDateToUse = format.parse(mStriingDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        mAuthor.setText(flower.getAuthor());
        mText.setText(flower.getText());
        mTime.setText(formatTimeString(mDateToUse));
        Glide.with(getApplicationContext()).load(flower.getImage()).into(mImage);

    }

    private void configViews() {
        mImage = (ImageView) findViewById(R.id.iv_photo_detail);
        mPhotoViewAttacher = new PhotoViewAttacher(mImage);
        mText = (TextView) findViewById(R.id.tv_text_detail);
        mAuthor = (TextView) findViewById(R.id.tv_author_detail);
        mTime = (TextView) findViewById(R.id.tv_time_detail);
    }

    private static class TIME_MAXIMUM{
        public static final int SEC = 60;
        public static final int MIN = 60;
        public static final int HOUR = 24;
        public static final int DAY = 30;
        public static final int MONTH = 12;
    }


    public static String formatTimeString(Date tempDate) {

        long curTime = System.currentTimeMillis();
        long regTime = tempDate.getTime();
        long diffTime = (curTime - regTime) / 1000;

        String msg = null;
        if (diffTime < TIME_MAXIMUM.SEC) {
            // sec
            msg = "just now";
        } else if ((diffTime /= TIME_MAXIMUM.SEC) < TIME_MAXIMUM.MIN) {
            // min
            msg = diffTime + " minutes ago";
        } else if ((diffTime /= TIME_MAXIMUM.MIN) < TIME_MAXIMUM.HOUR) {
            // hour
            msg = (diffTime) + " hours ago";
        } else if ((diffTime /= TIME_MAXIMUM.HOUR) < TIME_MAXIMUM.DAY) {
            // day
            msg = (diffTime) + " days ago";
        } else if ((diffTime /= TIME_MAXIMUM.DAY) < TIME_MAXIMUM.MONTH) {
            // day
            msg = (diffTime) + " months ago";
        } else {
            msg = (diffTime) + " years ago";
        }

        return msg;
    }


}
