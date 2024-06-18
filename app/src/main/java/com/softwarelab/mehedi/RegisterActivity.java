package com.softwarelab.mehedi;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.common.internal.ImagesContract;
import com.google.android.gms.measurement.api.AppMeasurementSdk;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.luseen.autolinklibrary.AutoLinkMode;
import com.luseen.autolinklibrary.AutoLinkOnClickListener;
import com.luseen.autolinklibrary.AutoLinkTextView;

import java.io.IOException;
import java.util.ArrayList;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.ClientCookie;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {
    ArrayAdapter aaa;
    private EditText amount;
    AutoLinkTextView autoLinkTextView;
    /* developer by  sabbir */
    public EditText birth;
    Dialog dialog;
    /* developer by  sabbir */
    public EditText email;
    int flag = 0;
    String ibirth;
    String iname;
    String inid;
    JSONParser jsonParser = new JSONParser();
    /* developer by  sabbir */
    public EditText name;
    /* developer by  sabbir */
    public EditText nick;
    /* developer by  sabbir */
    public EditText nid;
    /* developer by  sabbir */
    public EditText pass;
    LinearLayout payline;
    /* developer by  sabbir */
    public EditText phone;
    /* developer by  sabbir */
    public EditText pin;

    
    private TextView f275pp;
    LinearLayout reglin;
    Button sub;
    String text;
    /* developer by  sabbir */
    public EditText trnx;
    int type = 0;

    /* developer by  sabbir */
    @SuppressLint("ResourceType")
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_register);
        getWindow().setSoftInputMode(2);
        String[] split = Addres.getPref("all_level", getApplicationContext()).split("\",\"|\\[\"|\"\\]");
        changeStatusBarColor();
        this.sub = (Button) findViewById(R.id.sub);
        this.f275pp = (TextView) findViewById(R.id.pp);
        this.autoLinkTextView = (AutoLinkTextView) findViewById(R.id.pp);
        this.reglin = (LinearLayout) findViewById(R.id.re);
        this.payline = (LinearLayout) findViewById(R.id.pay);
        this.trnx = (EditText) findViewById(R.id.trnx);
        this.amount = (EditText) findViewById(R.id.amount);
        this.nick = (EditText) findViewById(R.id.username);
        this.email = (EditText) findViewById(R.id.email);
        this.pin = (EditText) findViewById(R.id.pin);
        this.name = (EditText) findViewById(R.id.name);
        this.pass = (EditText) findViewById(R.id.pass);
        this.phone = (EditText) findViewById(R.id.phone);
        this.nid = (EditText) findViewById(R.id.nid);
        this.birth = (EditText) findViewById(R.id.birth);
        Spinner spinner = (Spinner) findViewById(R.id.lev);
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, 17367048, split);
        this.aaa = arrayAdapter;
        arrayAdapter.setDropDownViewResource(17367049);
        spinner.setAdapter(this.aaa);
        Intent intent = getIntent();
        this.inid = intent.getStringExtra("nid");
        this.iname = intent.getStringExtra(AppMeasurementSdk.ConditionalUserProperty.NAME);
        String stringExtra = intent.getStringExtra("birth");
        this.ibirth = stringExtra;
        this.birth.setText(stringExtra);
        this.nid.setText(this.inid);
        this.name.setText(this.iname);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onNothingSelected(AdapterView<?> adapterView) {
            }

            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long j) {
                RegisterActivity.this.text = adapterView.getItemAtPosition(i).toString();
            }
        });
        ((Button) findViewById(R.id.reg)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (RegisterActivity.this.nick.length() < 4) {
                    Toast.makeText(RegisterActivity.this, "Please Enter correct username", Toast.LENGTH_LONG).show();
                    return;
                }
                RegisterActivity.this.name.length();
                if (RegisterActivity.this.trnx.length() > 2) {
                    RegisterActivity.this.type = 1;
                }
                new loginAccess().execute(new String[0]);
            }
        });
        this.sub.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (RegisterActivity.this.nick.length() < 4) {
                    Toast.makeText(RegisterActivity.this, "Please Enter correct username", Toast.LENGTH_LONG).show();
                    return;
                }
                RegisterActivity.this.name.length();
                if (RegisterActivity.this.trnx.length() > 2) {
                    RegisterActivity.this.type = 1;
                }
                new loginAccess().execute(new String[0]);
            }
        });
    }

    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.addFlags(Integer.MIN_VALUE);
            window.setStatusBarColor(getResources().getColor(R.color.register_bk_color));
        }
    }

    public void onLoginClick(View view) {
        startActivity(new Intent(this, MainActivity.class));
        overridePendingTransition(R.anim.slide_in_left, 17432579);
    }

    class loginAccess extends AsyncTask<String, String, String> {
        loginAccess() {
        }

        /* developer by  sabbir */
        public void onPreExecute() {
            super.onPreExecute();
            RegisterActivity.this.dialog = new Dialog(RegisterActivity.this);
            RegisterActivity.this.dialog.requestWindowFeature(1);
            RegisterActivity.this.dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
            RegisterActivity.this.dialog.setCancelable(false);
            RegisterActivity.this.dialog.setContentView(R.layout.custom_progress);
            RegisterActivity.this.dialog.show();
        }

        /* Fdeveloper by  sabbir */
        public String doInBackground(String... strArr) {
            ArrayList arrayList = new ArrayList();
            String pref = Welcome.getPref("token", RegisterActivity.this.getApplicationContext());
            Welcome.getPref("device", RegisterActivity.this.getApplicationContext());
            final String obj = RegisterActivity.this.nick.getText().toString();
            String obj2 = RegisterActivity.this.email.getText().toString();
            String obj3 = RegisterActivity.this.name.getText().toString();
            String obj4 = RegisterActivity.this.phone.getText().toString();
            final String obj5 = RegisterActivity.this.pass.getText().toString();
            String obj6 = RegisterActivity.this.pin.getText().toString();
            String obj7 = RegisterActivity.this.nid.getText().toString();
            String obj8 = RegisterActivity.this.birth.getText().toString();
            String obj9 = RegisterActivity.this.trnx.getText().toString();
            String str = RegisterActivity.this.text;
            String str2 = FirebaseAnalytics.Param.SUCCESS;
            arrayList.add(new BasicNameValuePair(FirebaseAnalytics.Param.LEVEL, str));
            arrayList.add(new BasicNameValuePair("type", "" + RegisterActivity.this.type));
            arrayList.add(new BasicNameValuePair("trnx", obj9));
            arrayList.add(new BasicNameValuePair("token", pref));
            arrayList.add(new BasicNameValuePair("username", obj));
            arrayList.add(new BasicNameValuePair(AppMeasurementSdk.ConditionalUserProperty.NAME, obj3));
            arrayList.add(new BasicNameValuePair("email", obj2));
            arrayList.add(new BasicNameValuePair("birth", obj8));
            arrayList.add(new BasicNameValuePair("nid", obj7));
            arrayList.add(new BasicNameValuePair("phone", obj4));
            arrayList.add(new BasicNameValuePair("client_types", "16840"));
            arrayList.add(new BasicNameValuePair("password", obj5));
            arrayList.add(new BasicNameValuePair("resellerpin", obj6));
            JSONObject makeHttpRequest = null;
            try {
                makeHttpRequest = RegisterActivity.this.jsonParser.makeHttpRequest((Welcome.getPref(ImagesContract.URL, RegisterActivity.this.getApplicationContext()) + "/apiapp/") + "resellerautoadd", HttpPost.METHOD_NAME, arrayList);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            String str3 = str2;
            try {
                int i = makeHttpRequest.getInt(str3);
                int i2 = makeHttpRequest.getInt(str3);
                if (RegisterActivity.this.type == 0 && makeHttpRequest.getString("amount").equals("0")) {
                    RegisterActivity.this.runOnUiThread(new Runnable() {
                        public void run() {
                            RegisterActivity.this.type = 1;
                            new loginAccess().execute(new String[0]);
                        }
                    });
                }
                final String string = makeHttpRequest.getString("message");
                if (i2 == 0) {
                    RegisterActivity.this.flag = 0;
                    RegisterActivity.this.runOnUiThread(new Runnable() {
                        public void run() {
                            RegisterActivity.this.showError(RegisterActivity.this, string);
                        }
                    });
                }
                if (i == 1) {
                    RegisterActivity.this.flag = 0;
                } else {
                    RegisterActivity.this.flag = 1;
                }
                if (i2 != 1) {
                    return null;
                }
                RegisterActivity.this.flag = 0;
                RegisterActivity.this.runOnUiThread(new Runnable() {
                    public void run() {
                        RegisterActivity.this.reglin.setVisibility(View.GONE);
                        RegisterActivity.this.payline.setVisibility(View.VISIBLE);
                        RegisterActivity.this.setTextInTextViewb(string);
                        if (RegisterActivity.this.type == 1) {
                            loginAccess.this.SavePreferences("phone", obj);
                            loginAccess.this.SavePreferences("pass", obj5);
                            loginAccess.this.SavePreferences("pin", obj5);
                            loginAccess.this.SavePreferences(ClientCookie.PATH_ATTR, "");
                            loginAccess.this.SavePreferences("pathb", "");
                            Toast.makeText(RegisterActivity.this, string, Toast.LENGTH_LONG).show();
                            RegisterActivity.this.startActivity(new Intent(RegisterActivity.this.getApplicationContext(), Start.class));
                            RegisterActivity.this.finish();
                        }
                    }
                });
                return null;
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }
        }

        /* developer by  sabbir */
        public void onPostExecute(String str) {
            RegisterActivity.this.dialog.dismiss();
            if (RegisterActivity.this.flag == 1) {
                Toast.makeText(RegisterActivity.this, "Please Enter Correct informations", Toast.LENGTH_LONG).show();
            }
        }

        public void SavePreferences(String str, String str2) {
            SharedPreferences.Editor edit = PreferenceManager.getDefaultSharedPreferences(RegisterActivity.this.getApplicationContext()).edit();
            edit.putString(str, str2);
            edit.commit();
        }
    }

    public void setTextInTextViewb(String str) {
        this.autoLinkTextView.addAutoLinkMode(AutoLinkMode.MODE_PHONE);
        this.autoLinkTextView.setAutoLinkText(str);
        this.autoLinkTextView.setAutoLinkOnClickListener(new AutoLinkOnClickListener() {
            @SuppressLint("WrongConstant")
            public void onAutoLinkTextClick(AutoLinkMode autoLinkMode, String str) {
                ((ClipboardManager) RegisterActivity.this.getSystemService("clipboard")).setPrimaryClip(ClipData.newPlainText((CharSequence) null, str));
                Toast.makeText(RegisterActivity.this, "Copied " + str, Toast.LENGTH_LONG).show();
            }
        });
    }

    public void showError(Activity activity, String str) {
        @SuppressLint("ResourceType")
        Dialog dialog2 = new Dialog(activity, 2131886564);
        dialog2.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dialog2.requestWindowFeature(1);
        dialog2.setCancelable(true);
        dialog2.setContentView(R.layout.custom_dialog_view);
        ((TextView) dialog2.findViewById(R.id.dialogOpen)).setText(str);
        dialog2.show();
    }
}
