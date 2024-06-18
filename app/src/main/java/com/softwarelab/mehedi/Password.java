package com.softwarelab.mehedi;

import android.annotation.SuppressLint;
import android.app.Activity;
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
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import com.google.android.gms.common.internal.ImagesContract;
import java.util.Hashtable;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

public class Password extends Activity {
    private static final String TAG_SUCCESS = "success";
    String FinalJSonObject;
    /* developer by  sabbir */
    public EditText conpass;

    /* renamed from: cp */
    String f215cp;
    String device;
    Dialog dialog;
    private TextView err;
    int flag = 0;
    JSONParser jsonParser = new JSONParser();
    Button login;

    /* textSize="13sp" */
    private com.softwarelab.mehedi.dialog f216md;
    private dialogs mds;
    String msg;
    /* developer by  sabbir */
    public EditText newpass;

    /* renamed from: np */
    String f217np;
    String number;

    /* renamed from: op */
    String f218op;
    /* developer by  sabbir */
    public EditText opass;
    private ProgressDialog pDialog;
    String pwd;
    Button signin;
    String token;
    String url;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.password);
        this.opass = (EditText) findViewById(R.id.oldpass);
        this.conpass = (EditText) findViewById(R.id.conpass);
        this.newpass = (EditText) findViewById(R.id.newpass);
        ((Button) findViewById(R.id.reg)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Password password = Password.this;
                password.f217np = password.newpass.getText().toString();
                Password password2 = Password.this;
                password2.f218op = password2.opass.getText().toString();
                Password password3 = Password.this;
                password3.f215cp = password3.conpass.getText().toString();
                Password password4 = Password.this;
                if (!password4.isOnline(password4)) {
                    Toast.makeText(Password.this, "No network connection", Toast.LENGTH_LONG).show();
                } else if (Password.this.newpass.length() < 6) {
                    Toast.makeText(Password.this, "Please Enter minimum 6 digit Password", Toast.LENGTH_LONG).show();
                } else {
                    Password.this.password();
                }
            }
        });
    }

    /* developer by  sabbir */
    public void password() {
        Dialog dialog2 = new Dialog(this);
        this.dialog = dialog2;
        dialog2.requestWindowFeature(1);
        this.dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        this.dialog.setCancelable(false);
        this.dialog.setContentView(R.layout.custom_progress);
        this.dialog.show();
        String str = getPref(ImagesContract.URL, getApplicationContext()) + "/apiapp/";
        this.url = str;
        this.url = str.replaceFirst("^(http[s]?://www\\.|http[s]?://|www\\.)", "");
        String str2 = "https://" + this.url;
        this.url = str2;
        Log.d("sabbir", str2);
        this.number = getPref("phone", getApplicationContext());
        this.pwd = getPref("pass", getApplicationContext());
        this.token = getPref("token", getApplicationContext());
        this.device = getPref("device", getApplicationContext());
        getPref("pin", getApplicationContext());
        final String pref = getPref("token", getApplicationContext());
        final String pref2 = getPref("device", getApplicationContext());
        StringRequest r3 = new StringRequest(1, this.url + "password", new Response.Listener<String>() {
            public void onResponse(String str) {
                Log.d("sabbir", str);
                Password.this.FinalJSonObject = str;
                Password password = Password.this;
                new ParseJSonDataClass(password).execute(new Void[0]);
            }
        }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError volleyError) {
                Password.this.dialog.dismiss();
                Toast.makeText(Password.this.getBaseContext(), volleyError.toString(), Toast.LENGTH_LONG).show();
            }
        }) {
            /* developer by  sabbir */
            public Map<String, String> getParams() throws AuthFailureError {
                Hashtable hashtable = new Hashtable();
                hashtable.put("oldpass", Password.this.f218op);
                hashtable.put("newpass", Password.this.f217np);
                hashtable.put("cnewpass", Password.this.f215cp);
                hashtable.put("token", pref);
                hashtable.put("username", Password.this.number);
                hashtable.put("password", Password.this.pwd);
                hashtable.put("deviceid", pref2);
                return hashtable;
            }
        };
        r3.setRetryPolicy(new DefaultRetryPolicy() {
            public int getCurrentRetryCount() {
                return 50000;
            }

            public int getCurrentTimeout() {
                return 50000;
            }

            public void retry(VolleyError volleyError) throws VolleyError {
            }
        });
        Volley.newRequestQueue(getApplicationContext()).add(r3);
    }

    private class ParseJSonDataClass extends AsyncTask<Void, Void, Void> {
        public Context context;

        public ParseJSonDataClass(Context context2) {
            this.context = context2;
        }

        /* developer by  sabbir */
        public void onPreExecute() {
            super.onPreExecute();
        }

        /* developer by  sabbir */
        public Void doInBackground(Void... voidArr) {
            try {
                if (new JSONObject(Password.this.FinalJSonObject).getInt("success") == 1) {
                    Password password = Password.this;
                    password.SavePreferences("pass", password.f215cp);
                    Password.this.flag = 0;
                    Password.this.runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(Password.this, "Password change successful", Toast.LENGTH_LONG).show();
                            Password.this.startActivity(new Intent(Password.this.getApplicationContext(), Welcome.class));
                            Password.this.finish();
                        }
                    });
                    return null;
                }
                Password.this.flag = 1;
                return null;
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }
        }

        /* developer by  sabbir */
        public void onPostExecute(Void voidR) {
            if (Password.this.flag == 1) {
                Toast.makeText(Password.this, "Please Enter Correct informations", Toast.LENGTH_LONG).show();
            }
            Password.this.dialog.dismiss();
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

    public void setTextInTextView(String str) {
        this.err.setText(str);
    }

    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, 17432579);
    }
}
