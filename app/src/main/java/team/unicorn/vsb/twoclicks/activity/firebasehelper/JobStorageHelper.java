package team.unicorn.vsb.twoclicks.activity.firebasehelper;

/**
 * Created by lenovo on 27/1/17.
 * * 1.SAVE DATA TO FIREBASE
 * 2. RETRIEVE
 * 3.RETURN AN ARRAYLIST
 */

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

import team.unicorn.vsb.twoclicks.activity.HomeActivity;
import team.unicorn.vsb.twoclicks.activity.constant.Constant;
import team.unicorn.vsb.twoclicks.activity.model.Job;

public class JobStorageHelper {

    private DatabaseReference db;
    private Boolean saved,updated;
    private ArrayList<Job> products=new ArrayList<>();

    /*
       PASS DATABASE REFRENCE
  */
    public JobStorageHelper(DatabaseReference db) {
        this.db = db;
    }

    // UPDATE IF NOT NULL
    public Boolean update(Job job)
    {
            try
            {
                db.child(Constant.JOBCARD).child(job.getJobcard()).setValue(job);
                updated=true;
            }catch (DatabaseException e)
            {
                e.printStackTrace();
                updated=false;
            }

        return updated;
    }

    // IMPLEMENT FETCH DATA AND FILL ARRAYLIST
    private void fetchData(DataSnapshot dataSnapshot)
    {
        products.clear();
        Job product = dataSnapshot.getValue(Job.class);
        products.add(product);

    }

    // RETRIEVE
    public ArrayList<Job> retrieve()
    {
        db.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                fetchData(dataSnapshot);
            }
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                fetchData(dataSnapshot);
            }
            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
            }
            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        return products;
    }
}
