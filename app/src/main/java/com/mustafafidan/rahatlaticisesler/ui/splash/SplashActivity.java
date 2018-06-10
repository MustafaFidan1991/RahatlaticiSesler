package com.mustafafidan.rahatlaticisesler.ui.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.mustafafidan.rahatlaticisesler.R;
import com.mustafafidan.rahatlaticisesler.ui.MainActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed((Runnable) () -> {
            finish();
            startActivity(new Intent(SplashActivity.this,MainActivity.class));

        },2000);
    }
}
