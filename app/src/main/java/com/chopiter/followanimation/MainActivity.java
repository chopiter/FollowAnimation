package com.chopiter.followanimation;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    int[] mImgs = new int[]{R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher,
            R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher,
            R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher};


    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_main);
        ViewGroup root = (ViewGroup) findViewById(R.id.root_layout);

        for (int i = 0;i < mImgs.length; i++) {
            View imageView = new ImageView(this);
            imageView.setBackgroundResource(mImgs[i]);
            root.addView(imageView,200,200);
        }
    }

}
