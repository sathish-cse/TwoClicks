package team.unicorn.vsb.twoclicks.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import team.unicorn.vsb.twoclicks.R;
import team.unicorn.vsb.twoclicks.activity.constant.Constant;

public class SelectUserActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView img_worker, img_po, img_gp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_user);

        img_worker = (ImageView) findViewById(R.id.employe);
        img_po = (ImageView) findViewById(R.id.po);
        img_gp = (ImageView) findViewById(R.id.gp);

        img_po.setOnClickListener(this);
        img_worker.setOnClickListener(this);
        img_gp.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        if (v.equals(img_po)) {
            Constant.STORAGE = "po";
            Intent gotoHome1 = new Intent(SelectUserActivity.this, GPAndPOLoginActivity.class);
            startActivity(gotoHome1);
        } else if (v.equals(img_worker)) {
            Constant.STORAGE = "worker";
            Intent gotoHome2 = new Intent(SelectUserActivity.this, LoginActivity.class);
            startActivity(gotoHome2);
        } else if (v.equals(img_gp)) {
            Constant.STORAGE = "gp";
            Intent gotoHome3 = new Intent(SelectUserActivity.this, GPAndPOLoginActivity.class);
            startActivity(gotoHome3);
        }
    }

}
