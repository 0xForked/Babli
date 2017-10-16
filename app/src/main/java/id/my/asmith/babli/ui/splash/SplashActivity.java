package id.my.asmith.babli.ui.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import id.my.asmith.babli.R;
import id.my.asmith.babli.ui.main.MainActivity;

public class SplashActivity extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 3000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {

                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                finish();
                SplashActivity.this.overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_left);
                // close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);

    }
}
