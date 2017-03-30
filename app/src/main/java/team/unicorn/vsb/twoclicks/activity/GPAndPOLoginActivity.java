package team.unicorn.vsb.twoclicks.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import team.unicorn.vsb.twoclicks.R;
import team.unicorn.vsb.twoclicks.activity.constant.Constant;
import team.unicorn.vsb.twoclicks.activity.helper.Helper;

public class GPAndPOLoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText email;
    private EditText password;
    private Button login;
    private TextView accountText;

    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;

    private AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gpand_pologin);

        initializeViews();
        initializeInstance();
        // checkUserStatus();
    }

    private void checkUserStatus()
    {
        if(firebaseAuth.getCurrentUser()!=null ) {
            Intent intent = new Intent(GPAndPOLoginActivity.this,GramaPanchayathActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private void initializeInstance()
    {
        // Get Instance for firebase authentication
        firebaseAuth = FirebaseAuth.getInstance();

        // Get Instance
        databaseReference = FirebaseDatabase.getInstance().getReference();
    }

    private void initializeViews() {

        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        login = (Button) findViewById(R.id.login);
        accountText = (TextView) findViewById(R.id.account_text);

        if (Constant.STORAGE == "po")
            accountText.setText("Program Officer");
        else
            accountText.setText("Grama Panchayath");

        login.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if (v == login) {
            // Handle clicks for login
            onLoginUser();
        }
    }

    private void onLoginUser() {
        if(getUserEmail().equals("") || getUserPassword().equals("")){
            showFieldsAreRequired();
        }else {
            logIn(getUserEmail(), getUserPassword());
        }
    }

    private void logIn(final String email, String password) {

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Log in..");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();


        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                progressDialog.dismiss();

                if(task.isSuccessful()){
                    setUserOnline();
                    goToMainActivity();
                }else {
                    showAlertDialog(task.getException().getMessage(),true);
                }
            }
        });
    }

    private void setUserOnline() {
        if(firebaseAuth.getCurrentUser()!=null ) {
            String userId = firebaseAuth.getCurrentUser().getUid();
            FirebaseDatabase.getInstance()
                    .getReference().
                    child("users").
                    child(userId).
                    child("connection").
                    setValue("online");
        }
    }

    private void showFieldsAreRequired() {
        showAlertDialog(getString(R.string.error_incorrect_email_pass),true);
    }

    private void goToMainActivity() {

        Constant.name = email.getText().toString().trim();
        Intent intent = new Intent(GPAndPOLoginActivity.this, LocationSelectionActivity.class);
        startActivity(intent);
    }

    private String getUserEmail() {
        return email.getText().toString().trim() + "@gmail.com";
    }

    private String getUserPassword() {
        return password.getText().toString().trim();
    }

    private void showAlertDialog(String message, boolean isCancelable){
        alertDialog = Helper.buildAlertDialog(getString(R.string.login_error_title), message,isCancelable,GPAndPOLoginActivity.this);
        alertDialog.show();
    }

    private void dismissAlertDialog() {
        alertDialog.dismiss();
    }
}

