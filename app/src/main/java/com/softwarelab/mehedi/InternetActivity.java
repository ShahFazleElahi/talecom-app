package com.softwarelab.mehedi;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.exifinterface.media.ExifInterface;

import com.google.android.gms.common.internal.ImagesContract;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class InternetActivity extends Activity {
    private static final String TAG_SUCCESS = "success";
    private static final String[] distic = {"Recharge", "Bkash", "Rocket"};

    
    private EditText f195am;
    ArrayList<HashMap<String, String>> arraylist;
    private String cNumber;
    private EditText email_id;
    private TextView err;
    int flag = 0;
    private EditText hint;
    JSONParser jsonParser = new JSONParser();
    JSONArray jsonarray;
    JSONObject jsonobject;
    Button login;

    /* textSize="13sp" */
    private dialog f196md;
    private dialog mdd;
    private dialogs mds;

    
    AutoCompleteTextView f197mn;
    String nam;
    String opn;
    String optr;
    /*developer by  sabbir */
    public ProgressDialog pDialog;
    String paid;
    /* developer by  sabbir */
    public EditText pin;
    private RadioButton radioButton;
    /* developer by  sabbir */
    public RadioGroup radioGroup;
    List<String> responseList = new ArrayList();
    Button signin;
    String type;
    String type2;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.in_number);
        this.f197mn = (AutoCompleteTextView) findViewById(R.id.number);
        this.pin = (EditText) findViewById(R.id.pin);
        new DownloadJSONy().execute(new Void[0]);
        ((Button) findViewById(R.id.cont)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
            }
        });
        ((Button) findViewById(R.id.sub)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                InternetActivity internetActivity;
                String str;
                InternetActivity internetActivity2 = InternetActivity.this;
                if (!internetActivity2.isOnline(internetActivity2)) {
                    Toast.makeText(InternetActivity.this, "No network connection", Toast.LENGTH_LONG).show();
                } else if (InternetActivity.this.f197mn.length() < 10) {
                    Toast.makeText(InternetActivity.this, "Please Enter correct mobile number", Toast.LENGTH_LONG).show();
                } else if (InternetActivity.this.pin.length() < 3) {
                    Toast.makeText(InternetActivity.this, "Please Enter correct pin number", Toast.LENGTH_LONG).show();
                } else {
                    InternetActivity internetActivity3 = InternetActivity.this;
                    RadioGroup unused = internetActivity3.radioGroup = (RadioGroup) internetActivity3.findViewById(R.id.typep);
                    if (InternetActivity.this.radioGroup.getCheckedRadioButtonId() == R.id.postpaid) {
                        internetActivity = InternetActivity.this;
                        str = "Postpaid";
                    } else {
                        internetActivity = InternetActivity.this;
                        str = "Prepaid";
                    }
                    internetActivity.paid = str;
                    String obj = InternetActivity.this.f197mn.getText().toString();
                    String obj2 = InternetActivity.this.pin.getText().toString();
                    Intent intent = InternetActivity.this.getIntent();
                    Intent intent2 = new Intent(InternetActivity.this.getApplicationContext(), DisplayActivity.class);
                    intent2.putExtra("type3", "64");
                    intent2.putExtra("opt2", ExifInterface.GPS_DIRECTION_TRUE);
                    intent2.putExtra("opt", intent.getExtras().getString("opt"));
                    intent2.putExtra("type", "in");
                    intent2.putExtra("type2", "internet");
                    intent2.putExtra("opt2", intent.getExtras().getString("opt2"));
                    intent2.putExtra("number", obj);
                    intent2.putExtra("amount", intent.getExtras().getString("amount"));
                    intent2.putExtra("pin", obj2);
                    InternetActivity.this.startActivity(intent2);
                }
            }
        });
    }

    private class DownloadJSONy extends AsyncTask<Void, Void, Void> {
        private ImageButton retry;

        private DownloadJSONy() {
        }

        /* developer by  sabbir */
        public void onPreExecute() {
            super.onPreExecute();
            ProgressDialog unused = InternetActivity.this.pDialog = new ProgressDialog(InternetActivity.this);
            InternetActivity.this.pDialog.setMessage("please wait...");
            InternetActivity.this.pDialog.setIndeterminate(false);
            InternetActivity.this.pDialog.setCancelable(true);
            InternetActivity.this.pDialog.show();
            InternetActivity internetActivity = InternetActivity.this;
            if (!internetActivity.isOnline(internetActivity)) {
                InternetActivity.this.findViewById(R.id.progressbar).setVisibility(View.GONE);
                InternetActivity.this.finish();
            }
        }

        /* developer by  sabbir */
        public Void doInBackground(Void... voidArr) {
            InternetActivity internetActivity = InternetActivity.this;
            if (!internetActivity.isOnline(internetActivity)) {
                return null;
            }
            String pref = InternetActivity.getPref("token", InternetActivity.this.getApplicationContext());
            String pref2 = InternetActivity.getPref("device", InternetActivity.this.getApplicationContext());
            InternetActivity.this.arraylist = new ArrayList<>();
            InternetActivity.this.jsonobject = JSONfunctions.getJSONfromURL((InternetActivity.getPref(ImagesContract.URL, InternetActivity.this.getApplicationContext()) + "/apiapp/") + "/relnumber?token=" + URLEncoder.encode(pref) + "&deviceid=" + URLEncoder.encode(pref2));
            try {
                InternetActivity internetActivity2 = InternetActivity.this;
                internetActivity2.jsonarray = internetActivity2.jsonobject.getJSONArray("bmtelbd");
                Log.d("Create Response", InternetActivity.this.jsonarray.toString());
                for (int i = 0; i < InternetActivity.this.jsonarray.length(); i++) {
                    HashMap hashMap = new HashMap();
                    InternetActivity internetActivity3 = InternetActivity.this;
                    internetActivity3.jsonobject = internetActivity3.jsonarray.getJSONObject(i);
                    if (InternetActivity.this.jsonobject.getInt("success") == 1) {
                        InternetActivity internetActivity4 = InternetActivity.this;
                        internetActivity4.nam = internetActivity4.jsonobject.getString("number");
                    }
                    InternetActivity.this.responseList.add(InternetActivity.this.nam);
                    InternetActivity.this.arraylist.add(hashMap);
                }
                return null;
            } catch (JSONException e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
                return null;
            }
        }

        /* developer by  sabbir */
        @SuppressLint("ResourceType")
        public void onPostExecute(Void voidR) {
            InternetActivity internetActivity = InternetActivity.this;
            if (internetActivity.isOnline(internetActivity)) {
                InternetActivity.this.pDialog.dismiss();
                InternetActivity internetActivity2 = InternetActivity.this;
                InternetActivity.this.f197mn.setAdapter(new ArrayAdapter(internetActivity2, 17367043, internetActivity2.responseList));
            }
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
