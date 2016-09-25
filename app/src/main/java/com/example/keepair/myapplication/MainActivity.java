package com.example.keepair.myapplication;

import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.keepair.myapplication.adapter.MyPagerAdapter;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

public class MainActivity extends AppCompatActivity {

    private View btn_red;
    private View btn_blue;
    private View btn_yellow;
    private View btn_green;

    private ViewPager vp_pager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        vp_pager = (ViewPager) findViewById(R.id.vp_pager);


        Fragment[] arrFragmnets = new Fragment[4];
        arrFragmnets[0] = new YellowFragment();
        arrFragmnets[1] = new RedFragment();
        arrFragmnets[2] = new GreenFragment();
        arrFragmnets[3] = new BlueFragment();


        MyPagerAdapter adapter = new MyPagerAdapter(getSupportFragmentManager(), arrFragmnets);
        vp_pager.setOffscreenPageLimit(4);
        vp_pager.setAdapter(adapter);


        btn_blue = (View) findViewById(R.id.btn_blue);
        btn_red = (View) findViewById(R.id.btn_red);
        btn_yellow = (View) findViewById(R.id.btn_yellow);
        btn_green = (View) findViewById(R.id.btn_green);

        btn_yellow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vp_pager.setCurrentItem(0);
            }
        });
        btn_red.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vp_pager.setCurrentItem(1);
            }
        });
        btn_green.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vp_pager.setCurrentItem(2);
            }
        });
        btn_blue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vp_pager.setCurrentItem(3);
            }
        });




        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
    }

/*
    @Override
    public void onColorClick(int color) {
        switch (color) {
            case 0:
            case 1:
            case 2:
            case 3:
        }
    }
*/


}

    //color  0 red 1 blue 2green 3 yellow
/*    @Override
    public void onColorClick(int color) {

        Fragment frag = null;

        switch (color) {
            case 0:
                viewPager.setCurrentItem(arrFragmnets[0]);
                break;
            case 1:
                frag_blue = getSupportFragmentManager().findFragmentByTag("blue");
                frag = frag_blue;
                if(frag_blue==null){
                    frag_blue=new BlueFragment();
                    getSupportFragmentManager().beginTransaction().add(R.id.fragment_ex, frag_blue, "blue").commit();
                }
                break;
            case 2:
                frag_green = getSupportFragmentManager().findFragmentByTag("green");
                frag = frag_green;
                if(frag_green==null){
                    frag_green=new GreenFragment();
                    getSupportFragmentManager().beginTransaction().add(R.id.fragment_ex, frag_green, "green").commit();
                }
                break;
            case 3:
                frag = frag_yellow;
                frag_yellow = getSupportFragmentManager().findFragmentByTag("yellow");
                if(frag_yellow==null){
                    frag_yellow=new YellowFragment();
                    getSupportFragmentManager().beginTransaction().add(R.id.fragment_ex, frag_yellow, "yellow").commit();
                }
                break;
        }
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_ex, frag)
                .commit();

    }*/



/*    @Override
    public void onColorClick(int color) {

        Fragment frag = null;
        switch (color) {
            case 0:
                frag = getSupportFragmentManager().findFragmentByTag("red");
                if (frag == null) {
                    frag = new RedFragment();
                    getSupportFragmentManager().beginTransaction().add(R.id.fragment_ex, frag, "red").commit();
                }
                break;
            case 1:
                frag = getSupportFragmentManager().findFragmentByTag("blue");
                if (frag == null) {
                    frag = new BlueFragment();
                    getSupportFragmentManager().beginTransaction().add(R.id.fragment_ex, frag, "blue").commit();
                }
                break;
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_ex, frag).commit();

    }*/
/*
    Fragment frag_red;
    Fragment frag_blue;
    Fragment frag_green;
    Fragment frag_yellow;


    //    Fragment fragment = new Fragment();
    //color  0 red 1 blue 2green 3 yellow
    @Override
    public void onColorClick(int color) {

        Fragment frag = null;

        switch (color) {
            case 0:
                frag_red = getSupportFragmentManager().findFragmentByTag("red");
                frag = frag_red;
                if(frag_red==null){
                    frag_red=new RedFragment();
                    getSupportFragmentManager().beginTransaction().add(R.id.fragment_ex, frag_red, "red").commit();
                }
                break;
            case 1:
                frag_blue = getSupportFragmentManager().findFragmentByTag("blue");
                frag = frag_blue;
                if(frag_blue==null){
                    frag_blue=new BlueFragment();
                    getSupportFragmentManager().beginTransaction().add(R.id.fragment_ex, frag_blue, "blue").commit();
                }
                break;
            case 2:
                frag_green = getSupportFragmentManager().findFragmentByTag("green");
                frag = frag_green;
                if(frag_green==null){
                    frag_green=new GreenFragment();
                    getSupportFragmentManager().beginTransaction().add(R.id.fragment_ex, frag_green, "green").commit();
                }
                break;
            case 3:
                frag = frag_yellow;
                frag_yellow = getSupportFragmentManager().findFragmentByTag("yellow");
                if(frag_yellow==null){
                    frag_yellow=new YellowFragment();
                    getSupportFragmentManager().beginTransaction().add(R.id.fragment_ex, frag_yellow, "yellow").commit();
                }
                break;
        }
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_ex, frag)
                .addToBackStack(null)
                .commit();

    }
}
*/



/*
        switch (color) {
            case 0:
                if (getSupportFragmentManager().findFragmentByTag("red") != null)
                getSupportFragmentManager().beginTransaction().show(redFragment).commit();
                else getSupportFragmentManager().beginTransaction().add(R.id.fragment_ex, redFragment, "red").commit();
                break;
            case 1:
                if (getSupportFragmentManager().findFragmentByTag("blue") != null)
                    getSupportFragmentManager().beginTransaction().show(blueFragment).commit();
                else getSupportFragmentManager().beginTransaction().replace(R.id.fragment_ex, blueFragment, "blue").commit();
                break;
            case 2:
                if (getSupportFragmentManager().findFragmentByTag("green") != null)
                    getSupportFragmentManager().beginTransaction().show(greenFragment).commit();
                else getSupportFragmentManager().beginTransaction().add(R.id.fragment_ex, greenFragment, "green").commit();
                break;
            case 3:
                if (getSupportFragmentManager().findFragmentByTag("yellow") != null)
                    getSupportFragmentManager().beginTransaction().show(yellowFragment).commit();
                else getSupportFragmentManager().beginTransaction().add(R.id.fragment_ex, yellowFragment, "yellow").commit();
                break;

        }*/

//
//        getSupportFragmentManager().beginTransaction()
////                    .add(R.id.fragment_ex, fragment)
//                    .replace(R.id.fragment_ex, frag)
//                    .commit();

