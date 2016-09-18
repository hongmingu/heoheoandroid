package com.example.keepair.myapplication;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements OnColorButtonListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        RedFragment redFragment = new RedFragment();
//
//        getSupportFragmentManager().beginTransaction()
//                .add(R.id.fragment_ex, redFragment).commit();
//
//        ArrayList<Postitem> listItem = new ArrayList<>();
//
//        RecyclerView rvList = (RecyclerView) findViewById(R.id.rv_list);
//        LinearLayout Parent = (LinearLayout) findViewById(R.id.ll_croll);


//        for (int i = 0; i < 5; i++) {
//
//            Postitem item = new Postitem("gg", "hh", "tt", 1, true);
//            listItem.add(i, item);
//        }
//
//        PostAdapter adapter = new PostAdapter(this, listItem);
//
//        rvList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
//        rvList.setAdapter(adapter);

//        for (Postitem item : listItem) {
//
//            View v = View.inflate(this, R.layout.post_item, null);
//            TextView tvUserName = (TextView) v.findViewById(R.id.tv_username);
//            TextView tvPostText = (TextView) v.findViewById(R.id.tv_posttext);
//
//            tvUserName.setText(item.getUserName());
//            tvPostText.setText(item.getPostText());
//
////            Parent.addView(v);

    }

    //color  0 red 1 blue 2green 3 yellow
    @Override
    public void onColorClick(int color) {

        Fragment fragment = null;

        switch (color) {
            case 0:
                fragment = new RedFragment();
                break;

            case 1:
                fragment = new BlueFragment();
                break;

            case 2:
                fragment = new GreenFragment();
                break;

            case 3:
                fragment = new YellowFragment();
                break;
        }

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_ex, fragment).commit();

    }
}
