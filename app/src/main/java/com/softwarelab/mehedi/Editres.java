package com.softwarelab.mehedi;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.common.internal.ImagesContract;
import com.google.android.gms.measurement.api.AppMeasurementSdk;

import java.io.IOException;
import java.util.ArrayList;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

public class Editres extends Activity {
    private static final String TAG_SUCCESS = "success";
    /*developer by  sabbir */
    public EditText birth;
    /* developer by  sabbir */
    public String birtn;
    /* developer by  sabbir */

    
    public int f173dd;
    Dialog dialog;
    /* developer by  sabbir */
    public EditText email;
    private String emaili;
    int flag = 0;
    /* developer by  sabbir */

    
    public String f174id;
    JSONParser jsonParser = new JSONParser();

    /* textSize="13sp" */
    private com.softwarelab.mehedi.dialog f175md;
    /* developer by  sabbir */

    /* renamed from: mm */
    public int f176mm;
    private String mom;
    /* developer by  sabbir */
    public EditText name;
    private String namei;
    /* developer by  sabbir */
    public EditText nick;
    private String nicki;
    /* developer by  sabbir */
    public EditText nid;
    private String nidn;
    private ProgressDialog pDialog;
    /* developer by  sabbir */
    public EditText pin;
    /* developer by  sabbir */

    /* renamed from: te */
    public EditText f177te;
    private String tel;

    /* renamed from: yy */
    private int f178yy;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.editr);
        getWindow().setSoftInputMode(2);
        Button button = (Button) findViewById(R.id.reg);
        this.nick = (EditText) findViewById(R.id.nick);
        this.email = (EditText) findViewById(R.id.email);
        this.pin = (EditText) findViewById(R.id.opin);
        this.name = (EditText) findViewById(R.id.aname);
        this.f177te = (EditText) findViewById(R.id.phn);
        this.birth = (EditText) findViewById(R.id.birth);
        this.nid = (EditText) findViewById(R.id.nid);
        Intent intent = getIntent();
        this.nicki = intent.getStringExtra("nick");
        this.namei = intent.getStringExtra(AppMeasurementSdk.ConditionalUserProperty.NAME);
        this.emaili = intent.getStringExtra("email");
        this.f174id = intent.getStringExtra("id");
        this.tel = intent.getStringExtra("tel");
        this.mom = intent.getStringExtra("model");
        this.birtn = intent.getStringExtra("birth");
        this.nidn = intent.getStringExtra("nid");
        this.nick.setText(this.nicki);
        this.name.setText(this.namei);
        this.birth.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (Editres.this.birtn.indexOf("null") >= 0) {
                    DatePickerDialog datePickerDialog = new DatePickerDialog(Editres.this, 16973939, new DatePickerDialog.OnDateSetListener() {
                        public void onDateSet(DatePicker datePicker, int i, int i2, int i3) {
                            Editres.this.birth.setText(i3 + "-" + (i2 + 1) + "-" + i);
                        }
                    }, 2000, Editres.this.f176mm, Editres.this.f173dd);
                    datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                    datePickerDialog.show();
                }
            }
        });
        if (this.nidn.indexOf("null") < 0) {
            this.nid.setText(this.nidn);
            this.nid.setClickable(false);
            this.nid.setFocusable(false);
        }
        if (this.birtn.indexOf("null") < 0) {
            this.birth.setText(this.birtn);
        }
        String str = this.emaili;
        if (str != null) {
            this.email.setText(str);
        }
        if (this.tel.indexOf("null") < 0) {
            this.f177te.setText(this.tel);
        }
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Editres editres = Editres.this;
                if (!editres.isOnline(editres)) {
                    Toast.makeText(Editres.this, "No network connection", Toast.LENGTH_LONG).show();
                } else if (Editres.this.nick.length() < 4) {
                    Toast.makeText(Editres.this, "Please Enter correct username", Toast.LENGTH_LONG).show();
                } else {
                    new loginAccess().execute(new String[0]);
                }
            }
        });
    }

    class loginAccess extends AsyncTask<String, String, String> {
        loginAccess() {
        }

        /* developer by  sabbir */
        public void onPreExecute() {
            super.onPreExecute();
            Editres.this.dialog = new Dialog(Editres.this);
            Editres.this.dialog.requestWindowFeature(1);
            Editres.this.dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
            Editres.this.dialog.setCancelable(false);
            Editres.this.dialog.setContentView(R.layout.custom_progress);
            Editres.this.dialog.show();
        }

        /* developer by  sabbir */
        public String doInBackground(String... strArr) {
            ArrayList arrayList = new ArrayList();
            Editres.getPref("phone", Editres.this.getApplicationContext());
            Editres.getPref("pass", Editres.this.getApplicationContext());
            Editres.getPref("pin", Editres.this.getApplicationContext());
            Editres.this.nick.getText().toString();
            String obj = Editres.this.email.getText().toString();
            String obj2 = Editres.this.name.getText().toString();
            String obj3 = Editres.this.f177te.getText().toString();
            String obj4 = Editres.this.pin.getText().toString();
            String obj5 = Editres.this.nid.getText().toString();
            String obj6 = Editres.this.birth.getText().toString();
            String pref = Editres.getPref("token", Editres.this.getApplicationContext());
            arrayList.add(new BasicNameValuePair("deviceid", Editres.getPref("device", Editres.this.getApplicationContext())));
            arrayList.add(new BasicNameValuePair("token", pref));
            arrayList.add(new BasicNameValuePair("email", obj));
            arrayList.add(new BasicNameValuePair("id", Editres.this.f174id));
            arrayList.add(new BasicNameValuePair("mobile", obj3));
            arrayList.add(new BasicNameValuePair("nid", obj5));
            arrayList.add(new BasicNameValuePair("birth", obj6));
            arrayList.add(new BasicNameValuePair("mypin", obj4));
            arrayList.add(new BasicNameValuePair(AppMeasurementSdk.ConditionalUserProperty.NAME, obj2));
            JSONObject makeHttpRequest = null;
            try {
                makeHttpRequest = Editres.this.jsonParser.makeHttpRequest((Editres.getPref(ImagesContract.URL, Editres.this.getApplicationContext()) + "/apiapp/") + "resellerEdit", HttpPost.METHOD_NAME, arrayList);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            try {
                int i = makeHttpRequest.getInt("success");
                int i2 = makeHttpRequest.getInt("success");
                final String string = makeHttpRequest.getString("message");
                if (i2 == 0) {
                    Editres.this.flag = 0;
                    Editres.this.runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(Editres.this, "Faild:" + string, Toast.LENGTH_LONG).show();
                        }
                    });
                }
                if (i == 1) {
                    Editres.this.flag = 0;
                } else {
                    Editres.this.flag = 1;
                }
                if (i2 != 1) {
                    return null;
                }
                Editres.this.flag = 0;
                Editres.this.runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(Editres.this, "Update successful", Toast.LENGTH_LONG).show();
                        Editres.this.startActivity(new Intent(Editres.this.getApplicationContext(), Myreseller.class));
                        Editres.this.finish();
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
            Editres.this.dialog.dismiss();
        }
    }

    /* developer by  sabbir */
    public boolean isOnline(Context context) {
        @SuppressLint("WrongConstant") NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }

    public static String getPref(String str, Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getString(str, (String) null);
    }

    public void SavePreferences(String str, String str2) {
        SharedPreferences.Editor edit = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit();
        edit.putString(str, str2);
        edit.commit();
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
