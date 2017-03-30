package team.unicorn.vsb.twoclicks.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import team.unicorn.vsb.twoclicks.R;
import team.unicorn.vsb.twoclicks.activity.LoginActivity;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        /****** Create Thread that will sleep for 3 seconds *************/
        Thread background = new Thread() {
            public void run() {

                try {
                    // Thread will sleep for 5 seconds
                    sleep(3*1000);

                    // After 5 seconds redirect to another intent
                    Intent i=new Intent(getBaseContext(),SelectUserActivity.class);
                    startActivity(i);

                    //Remove activity
                    finish();

                } catch (Exception e) {

                }
            }
        };

        // start thread
        background.start();
    }
}

