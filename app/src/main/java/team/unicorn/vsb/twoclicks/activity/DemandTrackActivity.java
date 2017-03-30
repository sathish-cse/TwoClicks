package team.unicorn.vsb.twoclicks.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import team.unicorn.vsb.twoclicks.R;
import team.unicorn.vsb.twoclicks.activity.constant.Constant;
import team.unicorn.vsb.twoclicks.activity.firebasehelper.JobStorageHelper;
import team.unicorn.vsb.twoclicks.activity.model.Job;


/*
1.INITIALIZE FIREBASE DB
2.INITIALIZE UI
3.DATA INPUT
 */

public class DemandTrackActivity extends AppCompatActivity implements View.OnClickListener{

    private DatabaseReference databaseReference;
    private JobStorageHelper firebaseHelper;
    private String name,jobcard,panchayath,district,mobileno,demandStatus,poStatus,gpStatus,duration;
    private Button button_demand,button_track;
    private ImageView img_demand, img_po, img_gp;

    @Override
    protected final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demand_track);

        initializeFirebaseDatabase();
        initializeViews();
    }

    private void initializeFirebaseDatabase()
    {
        databaseReference = FirebaseDatabase.getInstance().getReference();
        firebaseHelper = new JobStorageHelper(databaseReference);
    }

    private void initializeViews()
    {
        button_demand = (Button)findViewById(R.id.demand);
        button_track = (Button)findViewById(R.id.track);

        img_demand = (ImageView)findViewById(R.id.img_demand);

        getItemDetails();
        button_demand.setOnClickListener( this );

        button_track.setOnClickListener( this );
    }

    @Override
    public final void onClick(View v) {
        
        if ( v. equals( button_demand ) )
        {
           setData();
        }
        else
        {
            Intent next = new Intent(DemandTrackActivity.this,TrackActivity.class);
            startActivity(next);
        }
    }
    
    private void setData()
    {
        // Update Import Array
        Job product = new Job();
        product.setJobcard(jobcard);
        product.setName(name);
        product.setDemandStatus("True");
        product.setDistrict(district);
        product.setDuration(duration);
        product.setMobileno(mobileno);
        product.setPanchayath(panchayath);
        product.setGpStatus(gpStatus);
        product.setPoStatus(poStatus);

        if(firebaseHelper.update(product) && firebaseHelper.update(product)) {
            Toast.makeText(getApplicationContext(), "Demand Raised successfully..", Toast.LENGTH_SHORT).show();
            refresh();
        }
        else{
            Toast.makeText(getApplicationContext(),"Demand didn't raise..",Toast.LENGTH_SHORT).show();}

    }


    private String getCurrentDate()
    {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat(Constant.DATEFORMAT);

        return df.format(c.getTime());
    }


    private void getItemDetails()
    {
        DatabaseReference getChildListener = databaseReference.child(Constant.JOBCARD).child(Constant.name);
        getChildListener.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                {
                    Job product = dataSnapshot.getValue(Job.class);
                    name = product.getName();
                    jobcard = product.getJobcard();
                    panchayath = product.getPanchayath();
                    district = product.getDistrict();
                    mobileno = product.getMobileno();
                    demandStatus = product.getDemandStatus();
                    poStatus = product.getPoStatus();
                    gpStatus = product.getGpStatus();
                    duration = product.getDuration();

                    if(demandStatus == "True"){
                        img_demand.setImageResource(R.drawable.approval);}
                    else{
                        img_demand.setImageResource(R.drawable.notapproval);}

                }
                else
                {
                    Toast.makeText(getApplicationContext(),"..",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
    }

    public final void refresh()
    {
        Intent refresh = getIntent();
        finish();
        startActivity(refresh);
    }

    @Override
    public final void onBackPressed() {
        super.onBackPressed();
        finish();
    }
    
}

