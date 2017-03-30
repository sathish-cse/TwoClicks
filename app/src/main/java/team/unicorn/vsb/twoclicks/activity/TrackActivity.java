package team.unicorn.vsb.twoclicks.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import team.unicorn.vsb.twoclicks.R;
import team.unicorn.vsb.twoclicks.activity.constant.Constant;

public class TrackActivity extends AppCompatActivity {

    private ImageView  img_po, img_gp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track);

        img_gp = (ImageView) findViewById(R.id.img_gp);
        img_po = (ImageView) findViewById(R.id.img_po);


        if(Constant.POSTATUS == "True"){
            img_po.setImageResource(R.drawable.approval);}
        else{
            img_po.setImageResource(R.drawable.notapproval);}

        if(Constant.GPSTATUS == "True"){
            img_gp.setImageResource(R.drawable.approval);}
        else{
            img_gp.setImageResource(R.drawable.notapproval);}

    }
}
