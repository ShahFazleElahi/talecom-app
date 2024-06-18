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

public class New_support extends Activity {
    private static final String TAG_Balance = "message";
    private static final String TAG_SUCCESS = "success";
    private static final String[] distic = {"Recharge", "Bkash", "Rocket"};
    String FinalJSonObject;
    private String cNumber;
    String device;
    Dialog dialog;
    private TextView err;
    int flag = 0;
    JSONParser jsonParser = new JSONParser();

    /* textSize="13sp" */
    private com.softwarelab.mehedi.dialog f208md;
    private dialogs mds;
    /* developer by  sabbir */
    public EditText msg;
    String number;
    private ProgressDialog pDialog;

    
    private TextView f209pp;
    String pwd;
    /* developer by  sabbir */
    public EditText subject;
    String token;
    String url;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.new_tricket);
        this.subject = (EditText) findViewById(R.id.subject);
        this.msg = (EditText) findViewById(R.id.msg);
        ((Button) findViewById(R.id.sub)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                New_support new_support = New_support.this;
                if (!new_support.isOnline(new_support)) {
                    Toast.makeText(New_support.this, "No network connection", Toast.LENGTH_LONG).show();
                } else if (New_support.this.msg.length() < 4) {
                    Toast.makeText(New_support.this, "Please Enter Message", Toast.LENGTH_LONG).show();
                } else if (New_support.this.subject.length() < 4) {
                    Toast.makeText(New_support.this, "Please Enter Subject", Toast.LENGTH_LONG).show();
                } else {
                    New_support.this.New_support();
                }
            }
        });
    }

    /* developer by  sabbir */
    public void New_support() {
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
        StringRequest r1 = new StringRequest(1, this.url + "tricket_new", new Response.Listener<String>() {
            public void onResponse(String str) {
                Log.d("sabbir", str);
                New_support.this.FinalJSonObject = str;
                New_support new_support = New_support.this;
                new ParseJSonDataClass(new_support).execute(new Void[0]);
            }
        }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError volleyError) {
                New_support.this.dialog.dismiss();
                Toast.makeText(New_support.this.getBaseContext(), volleyError.toString(), Toast.LENGTH_LONG).show();
            }
        }) {
            /*developer by  sabbir */
            public Map<String, String> getParams() throws AuthFailureError {
                Hashtable hashtable = new Hashtable();
                String obj = New_support.this.subject.getText().toString();
                String obj2 = New_support.this.msg.getText().toString();
                hashtable.put("subject", obj);
                hashtable.put(New_support.TAG_Balance, obj2);
                hashtable.put("token", New_support.this.token);
                hashtable.put("username", New_support.this.number);
                hashtable.put("password", New_support.this.pwd);
                hashtable.put("deviceid", New_support.this.device);
                return hashtable;
            }
        };
        r1.setRetryPolicy(new DefaultRetryPolicy() {
            public int getCurrentRetryCount() {
                return 50000;
            }

            public int getCurrentTimeout() {
                return 50000;
            }

            public void retry(VolleyError volleyError) throws VolleyError {
            }
        });
        Volley.newRequestQueue(getApplicationContext()).add(r1);
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
                if (new JSONObject(New_support.this.FinalJSonObject).getInt("success") == 1) {
                    New_support.this.runOnUiThread(new Runnable() {
                        public void run() {
                            New_support.this.startActivity(new Intent(New_support.this, Tricket_main.class));
                        }
                    });
                    New_support.this.flag = 0;
                    return null;
                }
                New_support.this.flag = 1;
                return null;
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }
        }

        /* developer by  sabbir */
        public void onPostExecute(Void voidR) {
            if (New_support.this.flag == 1) {
                Toast.makeText(New_support.this, "Please Enter Correct informations", Toast.LENGTH_LONG).show();
            }
            New_support.this.dialog.dismiss();
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

    public void setTextInTextViewb(String str) {
        this.f209pp.setText(str);
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
