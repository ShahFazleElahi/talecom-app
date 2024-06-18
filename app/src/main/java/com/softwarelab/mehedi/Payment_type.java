package com.softwarelab.mehedi;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.firebase.analytics.FirebaseAnalytics;

public class Payment_type extends Activity {
    private static final String TAG_Balance = "message";
    private static final String TAG_SUCCESS = "success";
    private static final String[] distic = {"Recharge", "Bkash", "Rocket"};

    
    private EditText f222am;
    private String cNumber;
    Dialog dialog;
    private TextView err;
    int flag = 0;
    JSONParser jsonParser = new JSONParser();
    Button login;
    private dialogs mds;
    String opn;
    String optr;
    private ProgressDialog pDialog;
    String paid;

    
    private TextView f223pp;
    private RadioButton radioButton;
    /* developer by  sabbir */
    public RadioGroup radioGroup;
    /* developer by  sabbir */
    public RadioGroup radioGroup2;
    Button signin;

    /* renamed from: tr */
    private EditText f224tr;
    String type;
    String type2;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.add_balance_type);
        this.radioGroup = (RadioGroup) findViewById(R.id.bal);
        this.radioGroup2 = (RadioGroup) findViewById(R.id.p_type);
        ((Button) findViewById(R.id.sub)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent;
                int checkedRadioButtonId = Payment_type.this.radioGroup.getCheckedRadioButtonId();
                if (Payment_type.this.radioGroup2.getCheckedRadioButtonId() == R.id.manual) {
                    intent = new Intent(Payment_type.this, makepay.class);
                } else {
                    intent = new Intent(Payment_type.this, web.class);
                }
                if (checkedRadioButtonId == R.id.main) {
                    intent.putExtra(FirebaseAnalytics.Param.SOURCE, "main");
                } else if (checkedRadioButtonId == R.id.drive) {
                    intent.putExtra(FirebaseAnalytics.Param.SOURCE, "drive");
                } else {
                    intent.putExtra(FirebaseAnalytics.Param.SOURCE, "bank");
                }
                Payment_type.this.startActivity(intent);
            }
        });
    }

    public void onLoginClick(View view) {
        startActivity(new Intent(this, Welcome.class));
        overridePendingTransition(R.anim.slide_in_left, 17432579);
    }

    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, 17432579);
    }
}
