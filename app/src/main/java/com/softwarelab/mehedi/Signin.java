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
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

public class Signin extends Activity {
    private static final String TAG_SUCCESS = "success";
    /* developer by  sabbir */
    public static String url = "https://fiverelectric.online/map/register.php";
    /* developer by  sabbir */
    public EditText email_id;
    int flag = 0;
    /* developer by  sabbir */
    public EditText hint;
    JSONParser jsonParser = new JSONParser();
    Button login;
    /* developer by  sabbir */
    public EditText mobile_number;
    /* developer by  sabbir */
    public ProgressDialog pDialog;
    /* developer by  sabbir */
    public EditText password;
    Button signin;

    /* developer by  sabbir */
    public void onCreate(Bundle bundle) {
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());
        super.onCreate(bundle);
        setContentView(R.layout.signin);
        Button button = (Button) findViewById(R.id.login);
        this.login = button;
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Signin.this.startActivity(new Intent(Signin.this.getApplicationContext(), MainActivity.class));
            }
        });
        this.signin = (Button) findViewById(R.id.signin);
        this.mobile_number = (EditText) findViewById(R.id.mobile_number);
        this.password = (EditText) findViewById(R.id.password);
        this.hint = (EditText) findViewById(R.id.hint);
        this.email_id = (EditText) findViewById(R.id.email_id);
        this.signin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (Signin.this.mobile_number.length() < 10) {
                    Toast.makeText(Signin.this, "Please Enter correct mobile number", Toast.LENGTH_LONG).show();
                } else if (Signin.this.password.length() < 4) {
                    Toast.makeText(Signin.this, "Please Enter minimum 4 letters in password", Toast.LENGTH_LONG).show();
                } else if (Signin.this.hint.length() < 2) {
                    Toast.makeText(Signin.this, "Please Enter minimum 2 leters in First name", Toast.LENGTH_LONG).show();
                } else if (Signin.this.email_id.length() < 2) {
                    Toast.makeText(Signin.this, "Please Enter Last name", Toast.LENGTH_LONG).show();
                } else if (!isOnline(Signin.this)) {
                    Toast.makeText(Signin.this, "No network connection", Toast.LENGTH_LONG).show();
                } else {
                    new loginAccess().execute(new String[0]);
                }
            }

            private boolean isOnline(Context context) {
                @SuppressLint("WrongConstant") NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
                return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
            }
        });
    }

    class loginAccess extends AsyncTask<String, String, String> {
        loginAccess() {
        }

        /* developer by  sabbir */
        public void onPreExecute() {
            super.onPreExecute();
            ProgressDialog unused = Signin.this.pDialog = new ProgressDialog(Signin.this);
            Signin.this.pDialog.setMessage("Sig in...");
            Signin.this.pDialog.setIndeterminate(false);
            Signin.this.pDialog.setCancelable(true);
            Signin.this.pDialog.show();
        }

        /* developer by  sabbir */
        public String doInBackground(String... strArr) {
            ArrayList arrayList = new ArrayList();
            String obj = Signin.this.mobile_number.getText().toString();
            String obj2 = Signin.this.password.getText().toString();
            String obj3 = Signin.this.hint.getText().toString();
            String obj4 = Signin.this.email_id.getText().toString();
            arrayList.add(new BasicNameValuePair("phone", obj));
            arrayList.add(new BasicNameValuePair("pass", obj2));
            arrayList.add(new BasicNameValuePair("first_name", obj3));
            arrayList.add(new BasicNameValuePair("last_name", obj4));
            JSONObject makeHttpRequest = null;
            try {
                makeHttpRequest = Signin.this.jsonParser.makeHttpRequest(Signin.url, HttpPost.METHOD_NAME, arrayList);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Log.d("Create Response", makeHttpRequest.toString());
            try {
                if (makeHttpRequest.getInt("success") == 1) {
                    Signin.this.flag = 0;
                    Signin.this.SavePreferences("phone", obj);
                    Signin.this.SavePreferences("pass", obj2);
                    Intent intent = new Intent(Signin.this.getApplicationContext(), Welcome.class);
                    intent.putExtra("mobile_number", obj);
                    intent.putExtra("password", obj2);
                    Signin.this.startActivity(intent);
                    Signin.this.finish();
                    return null;
                }
                Signin.this.flag = 1;
                return null;
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }
        }

        /* developer by  sabbir */
        public void onPostExecute(String str) {
            Signin.this.pDialog.dismiss();
            if (Signin.this.flag == 1) {
                Toast.makeText(Signin.this, "Please Enter Correct informations", Toast.LENGTH_LONG).show();
            }
        }
    }

    public static String getPref(String str, Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getString(str, (String) null);
    }

    public void SavePreferences(String str, String str2) {
        SharedPreferences.Editor edit = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit();
        edit.putString(str, str2);
        edit.commit();
    }
}
