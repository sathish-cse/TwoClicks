package team.unicorn.vsb.twoclicks.activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;

import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import team.unicorn.vsb.twoclicks.R;
import team.unicorn.vsb.twoclicks.activity.constant.Constant;

public class LocationSelectionActivity extends AppCompatActivity {

    private MaterialBetterSpinner state_materialDesignSpinner, district_materialDesignSpinner, panchayath_materialDesignSpinner;
    private Button Submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_selection);

        String[] state_Array = {"Andra Pradesh", "Arunachal Pradesh", "Assam", "Bihar", "Chhattisgarh", "Goa", "Gujarat", "Haryana", "Himachal Pradesh",
                "Jammu and Kashmir", "Jharkhand", "Karnataka", "Kerala", "Madya Pradesh", "Maharashtra", "Manipur","Meghalaya", "Mizoram",
                "Nagaland", "Orissa", "Punjab", "Rajasthan", "Sikkim", "Tamil Nadu","Tripura", "Uttaranchal", "Uttar Pradesh", "West Bengal",
                "Chandigarh", "Delhi", "Pondicherry" };

        String[] district_Array = {"Viluppuram", "Tirunelveli", "Tiruvannamalai", "Vellore", "Dindigul", "Erode", "Salem", "Tirupur", "Krishnagiri",
                "Pudukkottai", "Coimbatore", "Thoothukudi", "Dharmapuri", "Tiruchirappalli", "Kanchipuram", "Virudhunagar", "Ramanathapuram",
                "Sivaganga", "Cuddalore", "Madurai", "Tiruvallur", "Thanjavur", "Namakkal", "Karur", "Theni", "Nilgiris", "Nagapattinam",
                "Tiruvarur", "Ariyalur", "Perambalur", "Kanyakumari", "Chennai" };

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line,state_Array );
        state_materialDesignSpinner = (MaterialBetterSpinner)
                findViewById(R.id.state);
        state_materialDesignSpinner.setAdapter(arrayAdapter);

        ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, district_Array);
        district_materialDesignSpinner = (MaterialBetterSpinner)
                findViewById(R.id.district);
        district_materialDesignSpinner.setAdapter(arrayAdapter2);

        ArrayAdapter<String> arrayAdapter3 = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, district_Array);
        panchayath_materialDesignSpinner = (MaterialBetterSpinner)
                findViewById(R.id.panchayath);
        panchayath_materialDesignSpinner.setAdapter(arrayAdapter3);

        Typeface tf = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/Lato-Light.ttf");
        state_materialDesignSpinner.setTypeface(tf);
        district_materialDesignSpinner.setTypeface(tf);
        panchayath_materialDesignSpinner.setTypeface(tf);

        Submit = (Button)findViewById(R.id.submit);

        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent next = new Intent();
                if (Constant.STORAGE == "po")
                 next = new Intent(LocationSelectionActivity.this, ProgramOfficerActivity.class);
                else
                    next = new Intent(LocationSelectionActivity.this, GramaPanchayathActivity.class);
                startActivity(next);
            }
        });

    }
}
