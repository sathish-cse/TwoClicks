package team.unicorn.vsb.twoclicks.activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import team.unicorn.vsb.twoclicks.R;
import team.unicorn.vsb.twoclicks.activity.constant.Constant;
import team.unicorn.vsb.twoclicks.activity.customadapter.ProductCustomAdapter;
import team.unicorn.vsb.twoclicks.activity.model.Job;


public class GramaPanchayathActivity extends AppCompatActivity {

    private ProgressBar mProgressBarForProducts;
    private RecyclerView mProductRecyclerView;

    private String mCurrentUserUid;
    private List<String> mUsersKeyList;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference mProductDatabaseReference;
    private ChildEventListener mChildEventListener;
    private ProductCustomAdapter ProductCustomAdapter;
    public static String USERMAIL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grama_panchayath);

        mProgressBarForProducts = (ProgressBar)findViewById(R.id.progress_bar_users);
        mProductRecyclerView = (RecyclerView)findViewById(R.id.recycler_view_users);

        initializeAuthInstance();
        initializeUsersDatabase();
        initializeUserRecyclerView();
        initializeUsersKeyList();
        initializesetAuthListener();

    }

    private void initializeAuthInstance() {
        mAuth = FirebaseAuth.getInstance();
        USERMAIL = mAuth.getCurrentUser().getEmail().replace(".","_");
    }

    private void initializeUsersDatabase() {
        mProductDatabaseReference = FirebaseDatabase.getInstance().getReference().child(Constant.JOBCARD);
    }

    private void initializeUserRecyclerView() {
        ProductCustomAdapter = new ProductCustomAdapter(this, new ArrayList<Job>());
        mProductRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mProductRecyclerView.setHasFixedSize(true);
        mProductRecyclerView.setAdapter(ProductCustomAdapter);
    }

    private void initializeUsersKeyList() {
        mUsersKeyList = new ArrayList<String>();
    }

    private void initializesetAuthListener() {
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                hideProgressBarForUsers();
                FirebaseUser user = firebaseAuth.getCurrentUser();

                if (user != null) {
                    setUserData(user);
                    queryAllUsers();
                } else {
                    // Job is signed out
                    goToLogin();
                }
            }
        };
    }

    private void setUserData(FirebaseUser user) {
       // mCurrentUserUid = "emp1001";
    }

    private void queryAllUsers() {
        mChildEventListener = getChildEventListener();
        mProductDatabaseReference.limitToFirst(50).addChildEventListener(mChildEventListener);
    }

    private void goToLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // LoginActivity is a New Task
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK); // The old task when coming back to this activity should be cleared so we cannot come back to it.
        startActivity(intent);
    }

    @Override
    public void onStart() {
        super.onStart();
        showProgressBarForUsers();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();

        clearCurrentUsers();

        if (mChildEventListener != null) {
            mProductDatabaseReference.removeEventListener(mChildEventListener);
        }

        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }

    }

    private void clearCurrentUsers() {
        ProductCustomAdapter.clear();
        mUsersKeyList.clear();
    }


    private void showProgressBarForUsers(){
        mProgressBarForProducts.setVisibility(View.VISIBLE);
    }

    private void hideProgressBarForUsers(){
        if(mProgressBarForProducts.getVisibility()==View.VISIBLE) {
            mProgressBarForProducts.setVisibility(View.GONE);
        }
    }

    private ChildEventListener getChildEventListener() {
        return new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                if(dataSnapshot.exists()){

                    String userUid = dataSnapshot.getKey();

//                    if(dataSnapshot.getKey().equals(mCurrentUserUid)){
//                        Job currentUser = dataSnapshot.getValue(Job.class);
//
//                    }else {
                        Job recipient = dataSnapshot.getValue(Job.class);
                        recipient.setJobcard(userUid);
                        mUsersKeyList.add(userUid);
                        ProductCustomAdapter.refill(recipient);
//                    }
                }

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                if(dataSnapshot.exists()) {
                    String userUid = dataSnapshot.getKey();
                   // if(!userUid.equals(mCurrentUserUid)) {

                        Job user = dataSnapshot.getValue(Job.class);

                        int index = mUsersKeyList.indexOf(userUid);
                        if(index > -1) {
                            ProductCustomAdapter.changeUser(index, user);
                       // }
                    }

                }
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

        };


    }



}

