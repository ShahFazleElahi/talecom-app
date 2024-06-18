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

public class Pinc extends Activity {
    private static final String TAG_SUCCESS = "success";
    String FinalJSonObject;
    /* developer by  sabbir */
    public EditText conpass;

    /* renamed from: cp */
    String f231cp;
    String device;
    Dialog dialog;
    private TextView err;
    int flag = 0;
    JSONParser jsonParser = new JSONParser();
    Button login;

    /* textSize="13sp" */
    private com.softwarelab.mehedi.dialog f232md;
    private dialogs mds;
    String msg;
    /* developer by  sabbir */
    public EditText newpass;

    /* renamed from: np */
    String f233np;
    String number;

    /* renamed from: op */
    String f234op;
    /* developer by  sabbir */
    public EditText opass;
    private ProgressDialog pDialog;
    String pwd;
    Button signin;
    String token;
    String url;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.pinc);
        this.opass = (EditText) findViewById(R.id.oldpass);
        this.conpass = (EditText) findViewById(R.id.conpass);
        this.newpass = (EditText) findViewById(R.id.newpass);
        ((Button) findViewById(R.id.reg)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Pinc pinc = Pinc.this;
                pinc.f233np = pinc.newpass.getText().toString();
                Pinc pinc2 = Pinc.this;
                pinc2.f234op = pinc2.opass.getText().toString();
                Pinc pinc3 = Pinc.this;
                pinc3.f231cp = pinc3.conpass.getText().toString();
                Pinc pinc4 = Pinc.this;
                if (!pinc4.isOnline(pinc4)) {
                    Toast.makeText(Pinc.this, "No network connection", Toast.LENGTH_LONG).show();
                } else if (Pinc.this.newpass.length() < 4) {
                    Toast.makeText(Pinc.this, "Please Enter correct mobile number", Toast.LENGTH_LONG).show();
                } else {
                    Pinc.this.pin();
                }
            }
        });
    }

    /* developer by  sabbir */
    public void pin() {
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
        StringRequest r4 = new StringRequest(1, this.url + "pin", new Response.Listener<String>() {
            public void onResponse(String str) {
                Log.d("sabbir", str);
                Pinc.this.FinalJSonObject = str;
                Pinc pinc = Pinc.this;
                new ParseJSonDataClass(pinc).execute(new Void[0]);
            }
        }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError volleyError) {
                Pinc.this.dialog.dismiss();
                Toast.makeText(Pinc.this.getBaseContext(), volleyError.toString(), Toast.LENGTH_LONG).show();
            }
        }) {
            /* developer by  sabbir */
            public Map<String, String> getParams() throws AuthFailureError {
                Hashtable hashtable = new Hashtable();
                hashtable.put("oldpin", Pinc.this.f234op);
                hashtable.put("newpin", Pinc.this.f233np);
                hashtable.put("cnewpin", Pinc.this.f231cp);
                hashtable.put("token", pref);
                hashtable.put("username", Pinc.this.number);
                hashtable.put("password", Pinc.this.pwd);
                hashtable.put("deviceid", pref2);
                return hashtable;
            }
        };
        r4.setRetryPolicy(new DefaultRetryPolicy() {
            public int getCurrentRetryCount() {
                return 50000;
            }

            public int getCurrentTimeout() {
                return 50000;
            }

            public void retry(VolleyError volleyError) throws VolleyError {
            }
        });
        Volley.newRequestQueue(getApplicationContext()).add(r4);
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
                if (new JSONObject(Pinc.this.FinalJSonObject).getInt("success") == 1) {
                    Pinc.this.flag = 0;
                    Pinc.this.runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(Pinc.this, "Pin change successful", Toast.LENGTH_LONG).show();
                            Pinc.this.startActivity(new Intent(Pinc.this.getApplicationContext(), Welcome.class));
                            Pinc.this.finish();
                        }
                    });
                    return null;
                }
                Pinc.this.flag = 1;
                return null;
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }
        }

        /* developer by  sabbir */
        public void onPostExecute(Void voidR) {
            if (Pinc.this.flag == 1) {
                Toast.makeText(Pinc.this, "Please Enter Correct informations", Toast.LENGTH_LONG).show();
            }
            Pinc.this.dialog.dismiss();
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
