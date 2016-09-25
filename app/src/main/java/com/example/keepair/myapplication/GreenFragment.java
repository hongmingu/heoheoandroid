package com.example.keepair.myapplication;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.keepair.myapplication.apiservice.LoginApiService;
import com.example.keepair.myapplication.apiservice.PostApiService;
import com.example.keepair.myapplication.helper.Constants;
import com.example.keepair.myapplication.loginhelper.AddCookiesInterceptor;
import com.example.keepair.myapplication.loginhelper.ReferSharedPreference;
import com.example.keepair.myapplication.model.Point;
import com.example.keepair.myapplication.model.PostData;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.Bind;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okio.BufferedSink;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.POST;

/**
 * Created by Keepair on 2016-08-26.
 */
public class GreenFragment extends Fragment {


    Button mGetImageFromGalleryButton;

    ImageView mImageview;
    TextView mEditTextdialogOpenField;
    Uri returnUri;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_green, container, false);
        mImageview = (ImageView) view.findViewById(R.id.iv_beforesendpost);
        mEditTextdialogOpenField = (TextView) view.findViewById(R.id.tv_textToPost);
        setRetainInstance(true);

        mEditTextdialogOpenField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog();
            }
        });


        view.findViewById(R.id.btn_post).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (returnUri != null) {
                    if (!mEditTextdialogOpenField.getText().toString().equals("")) {
                        Bitmap bitmap = null;

                        try {
                            bitmap = getBitmapFromUri(returnUri);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        File imageFile = null;
                        try {
                            imageFile = createFileFromBitmap(bitmap);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        String givenText = mEditTextdialogOpenField.getText().toString();
                        OkHttpClient client = new OkHttpClient();
                        OkHttpClient.Builder builder = new OkHttpClient.Builder();
                        builder.interceptors().add(new AddCookiesInterceptor(view.getContext()));
                        client = builder.build();
                        Retrofit retrofit = new Retrofit.Builder()
                                .client(client)
                                .addConverterFactory(GsonConverterFactory.create())
                                .baseUrl(Constants.HTTP.BASE_URL)
                                .build();

                        PostApiService postApiService = retrofit.create(PostApiService.class);
                        RequestBody requestFile =
                                RequestBody.create(MediaType.parse("multipart/form-data"), imageFile);
                        MultipartBody.Part body =
                                MultipartBody.Part.createFormData("image", makeImageFileName(), requestFile);

                        ReferSharedPreference preferenceCoordinates = new ReferSharedPreference(getContext());
                        final String lat = preferenceCoordinates.getValue("Lat", "13");
                        final String lon = preferenceCoordinates.getValue("Lon", "15");
                        String pointString = "{ \"longitude\": \""+lon+"\",     \"latitude\": \""+lat+"\" } ";

                        RequestBody coordinatePoint =
                                RequestBody.create(
                                        MediaType.parse("multipart/form-data"), pointString);
                        RequestBody textToPost =
                                RequestBody.create(
                                        MediaType.parse("multipart/form-data"), givenText);


                        Call<ResponseBody> call = postApiService.uploadFile(body, coordinatePoint, textToPost);
                        call.enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                Snackbar.make(getView(), "Success, posted at \n" + lat + "  , " + lon , Snackbar.LENGTH_LONG).show();

                            }
                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {
                            }
                        });
                        mImageview.setImageBitmap(null);
                        returnUri = null;
                        mEditTextdialogOpenField.setText("");
                    }
                    else {
                        Snackbar.make(getView(), "Hi, You have to say any words", Snackbar.LENGTH_LONG).show();
                    }
                }
                else {
                    Snackbar.make(getView(), "Hi, You have to get any image from gallery", Snackbar.LENGTH_LONG).show();
                }
            }
        });

        mGetImageFromGalleryButton = (Button) view.findViewById(R.id.btn_getgallery);
        mGetImageFromGalleryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ActivityCompat.checkSelfPermission(getActivity(),
                        Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
                {
                    requestPermissions(
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            2000);
                }
                else {
                    startGallery();
                }
            }
        });
        return view;
    }

    private void openDialog() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View subView = inflater.inflate(R.layout.dialog_layout, null);
        final EditText subEditText = (EditText) subView.findViewById(R.id.dialogEditText);
        subEditText.setText(mEditTextdialogOpenField.getText().toString());
        subEditText.setSelection(subEditText.length());
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Text to Post");
        builder.setView(subView);
        AlertDialog alertDialog = builder.create();

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mEditTextdialogOpenField.setText(subEditText.getText().toString());
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Snackbar.make(getView(), "Cancel", Snackbar.LENGTH_LONG).show();
            }
        });
        builder.show();
    }


    private void startGallery() {
        Intent cameraIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        cameraIntent.setType("image/*");
        if (cameraIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(cameraIntent, 1000);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == Activity.RESULT_OK) {
            if(requestCode == 1000){
                Uri returnUri = data.getData();
                Bitmap bitmapImage = null;
                try {
                    bitmapImage = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), returnUri);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                mImageview.setImageBitmap(bitmapImage);
            }
            returnUri = data.getData();
            Glide.with(this)
                    .load(returnUri)
                    .override(1280, 1280)
                    .centerCrop()
                    .crossFade()
                    .into(mImageview);
        }

    }



    private Bitmap getBitmapFromUri(Uri uri) throws IOException {
        ParcelFileDescriptor parcelFileDescriptor =
                getActivity().getContentResolver().openFileDescriptor(uri, "r");
        FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();

        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inJustDecodeBounds = true;
        BitmapFactory.decodeFileDescriptor(fileDescriptor, null, opts);

        int width = opts.outWidth;
        int height = opts.outHeight;

        float sampleRatio = getSampleRatio(width, height);

        opts.inJustDecodeBounds = false;
        opts.inSampleSize = (int) sampleRatio;

        Bitmap resizedBitmap = BitmapFactory.decodeFileDescriptor(fileDescriptor, null, opts);
        Log.d("Resizing", "Resized Width / Height : " + resizedBitmap.getWidth() + "/" + resizedBitmap.getHeight());
        parcelFileDescriptor.close();
        return resizedBitmap;
    }

    private float getSampleRatio(int width, int height) {

        final int targetWidth = 1280;
        final int targetheight = 1280;

        float ratio;

        if (width > height) {
            // Landscape
            if (width > targetWidth) {
                ratio = (float) width / (float) targetWidth;
            } else ratio = 1f;
        } else {
            // Portrait
            if (height > targetheight) {
                ratio = (float) height / (float) targetheight;
            } else ratio = 1f;
        }
        return Math.round(ratio);
    }

    private File createFileFromBitmap(Bitmap bitmap) throws IOException {
        File newFile = new File(getActivity().getFilesDir(), makeImageFileName());
        FileOutputStream fileOutputStream = new FileOutputStream(newFile);
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
        fileOutputStream.close();
        return newFile;
    }

    private String makeImageFileName() {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd_hhmmss");
        Date date = new Date();
        String strDate = simpleDateFormat.format(date);
        return strDate + ".png";
    }
  }
