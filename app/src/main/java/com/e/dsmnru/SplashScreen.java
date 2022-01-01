package com.e.dsmnru;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SplashScreen extends AppCompatActivity {

    Animation topAnim, bottomAnim;
    ImageView chitra;
    TextView text,text1;
    private static int SPLASH_SCREEN=4000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splash_screen);


        topAnim = AnimationUtils.loadAnimation(this,R.anim.top_animation);
        bottomAnim= AnimationUtils.loadAnimation(this,R.anim.bottom_animation);


        chitra =findViewById(R.id.imageview);
        text =findViewById(R.id.textView);
        text1 =findViewById(R.id.textView1);

        chitra.setAnimation(topAnim);
        text.setAnimation(topAnim);
        text1.setAnimation(bottomAnim);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreen.this, Login.class);

                Pair[] pairs=new Pair[2];
                pairs[0]=new Pair<View,String>(chitra, "Splash_image");
                pairs[1]=new Pair<View,String>(text, "Splash_text");

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    ActivityOptions options =ActivityOptions.makeSceneTransitionAnimation(SplashScreen.this,pairs);

                    startActivity(intent,options.toBundle());
                }


            }
        },SPLASH_SCREEN);


    }

}