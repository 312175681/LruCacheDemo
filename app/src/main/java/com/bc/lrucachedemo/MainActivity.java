package com.bc.lrucachedemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bc.lrucachedemo.utils.LruCacheUtils;

public class MainActivity extends AppCompatActivity {

    private ImageView imageView;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (Button) findViewById(R.id.btn);
        imageView = (ImageView) findViewById(R.id.images);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LruCacheUtils
                        .getIntances()
                        .requestImage("https://ss0.bdstatic.com/5aV1bjqh_Q23odCf/static/superman/img/logo/bd_logo1_31bdc765.png",
                                imageView);
            }
        });
    }
}
