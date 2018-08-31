package yishengma.demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;


import yishengma.App;
import yishengma.imageloader.R;

/**
 * Created by asus on 18-8-29.
 */

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private List<String> mImageList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initData();
        initView();
    }

    private void initData() {
        mImageList = new ArrayList<>();
        for (int i=0;i<10;i++){
            mImageList.add("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=3730771372,3127282750&fm=27&gp=0.jpg");
            mImageList.add("https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=2364244149,3298797080&fm=27&gp=0.jpg");
            mImageList.add("https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=612545450,2731747183&fm=27&gp=0.jpg");
        }

    }


    private void initView() {
        RecyclerView imageRecyclerImage = findViewById(R.id.rv_images);
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        imageRecyclerImage.setLayoutManager(manager);
        ImageAdapter adapter = new ImageAdapter(mImageList);
        imageRecyclerImage.setAdapter(adapter);

    }

}
