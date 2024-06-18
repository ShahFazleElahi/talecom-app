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
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import com.google.android.gms.common.internal.ImagesContract;
import com.google.android.gms.measurement.api.AppMeasurementSdk;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Myprofile extends Activity {
    private static final String TAG_SUCCESS = "success";
    String FinalJSonObject;
    /* developer by  sabbir */
    public EditText birth;
    /* developer by  sabbir */
    public String birtn;
    /* developer by  sabbir */

    
    public int f201dd;
    String device;
    Dialog dialog;
    /* developer by  sabbir */
    public EditText email;
    /* developer by  sabbir*/
    public String emaili;
    int flag = 0;
    /* developer by  sabbir */

    
    public String f202id;
    JSONParser jsonParser = new JSONParser();
    JSONArray jsonarray;

    /* textSize="13sp" */
    private com.softwarelab.mehedi.dialog f203md;
    /* developer by  sabbir */

    /* renamed from: mm */
    public int f204mm;
    private String mom;
    String msg;
    /* developer by  sabbir */
    public EditText name;
    /* developer by  sabbir */
    public String namei;
    /* developer by  sabbir */
    public EditText nick;
    /* developer by  sabbir */
    public String nicki;
    /* developer by  sabbir */
    public EditText nid;
    /* developer by  sabbir */
    public String nidn;
    String number;
    private ProgressDialog pDialog;
    /* developer by  sabbir */
    public EditText pin;
    String pwd;
    /* developer by  sabbir */

    /* renamed from: te */
    public EditText f205te;
    /* developer by  sabbir */
    public String tel;
    String token;
    String url;

    /* renamed from: yy */
    private int f206yy;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.myprofile);
        getWindow().setSoftInputMode(2);
        this.nick = (EditText) findViewById(R.id.nick);
        this.email = (EditText) findViewById(R.id.email);
        this.pin = (EditText) findViewById(R.id.opin);
        this.name = (EditText) findViewById(R.id.aname);
        this.f205te = (EditText) findViewById(R.id.phn);
        this.birth = (EditText) findViewById(R.id.birth);
        this.nid = (EditText) findViewById(R.id.nid);
        myinfo();
        this.birth.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String obj = Myprofile.this.birth.getText().toString();
                if (obj == null || obj.isEmpty() || obj.equals("null")) {
                    @SuppressLint("ResourceType") DatePickerDialog datePickerDialog = new DatePickerDialog(Myprofile.this, 16973939, new DatePickerDialog.OnDateSetListener() {
                        public void onDateSet(DatePicker datePicker, int i, int i2, int i3) {
                            Myprofile.this.birth.setText(i3 + "-" + (i2 + 1) + "-" + i);
                        }
                    }, 2000, Myprofile.this.f204mm, Myprofile.this.f201dd);
                    datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                    datePickerDialog.show();
                }
            }
        });
        ((Button) findViewById(R.id.reg)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Myprofile myprofile = Myprofile.this;
                if (!myprofile.isOnline(myprofile)) {
                    Toast.makeText(Myprofile.this, "No network connection", Toast.LENGTH_LONG).show();
                } else if (Myprofile.this.nick.length() < 4) {
                    Toast.makeText(Myprofile.this, "Please Enter correct username", Toast.LENGTH_LONG).show();
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
            Myprofile.this.dialog = new Dialog(Myprofile.this);
            Myprofile.this.dialog.requestWindowFeature(1);
            Myprofile.this.dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
            Myprofile.this.dialog.setCancelable(false);
            Myprofile.this.dialog.setContentView(R.layout.custom_progress);
            Myprofile.this.dialog.show();
        }

        /* developer by  sabbir */
        public String doInBackground(String... strArr) {
            ArrayList arrayList = new ArrayList();
            Myprofile.getPref("phone", Myprofile.this.getApplicationContext());
            Myprofile.getPref("pass", Myprofile.this.getApplicationContext());
            Myprofile.getPref("pin", Myprofile.this.getApplicationContext());
            Myprofile.this.nick.getText().toString();
            String obj = Myprofile.this.email.getText().toString();
            String obj2 = Myprofile.this.name.getText().toString();
            String obj3 = Myprofile.this.f205te.getText().toString();
            String obj4 = Myprofile.this.pin.getText().toString();
            String obj5 = Myprofile.this.nid.getText().toString();
            String obj6 = Myprofile.this.birth.getText().toString();
            String pref = Myprofile.getPref("token", Myprofile.this.getApplicationContext());
            arrayList.add(new BasicNameValuePair("deviceid", Myprofile.getPref("device", Myprofile.this.getApplicationContext())));
            arrayList.add(new BasicNameValuePair("token", pref));
            arrayList.add(new BasicNameValuePair("email", obj));
            arrayList.add(new BasicNameValuePair("id", Myprofile.this.f202id));
            arrayList.add(new BasicNameValuePair("mobile", obj3));
            arrayList.add(new BasicNameValuePair("nid", obj5));
            arrayList.add(new BasicNameValuePair("birth", obj6));
            arrayList.add(new BasicNameValuePair("mypin", obj4));
            arrayList.add(new BasicNameValuePair(AppMeasurementSdk.ConditionalUserProperty.NAME, obj2));
            arrayList.add(new BasicNameValuePair("self", "yes"));
            JSONObject makeHttpRequest = null;
            try {
                makeHttpRequest = Myprofile.this.jsonParser.makeHttpRequest((Myprofile.getPref(ImagesContract.URL, Myprofile.this.getApplicationContext()) + "/apiapp/") + "resellerEdit", HttpPost.METHOD_NAME, arrayList);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            try {
                int i = makeHttpRequest.getInt("success");
                int i2 = makeHttpRequest.getInt("success");
                final String string = makeHttpRequest.getString("message");
                if (i2 == 0) {
                    Myprofile.this.flag = 0;
                    Myprofile.this.runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(Myprofile.this, "Faild: " + string, Toast.LENGTH_LONG).show();
                        }
                    });
                }
                if (i == 1) {
                    Myprofile.this.flag = 0;
                } else {
                    Myprofile.this.flag = 1;
                }
                if (i2 != 1) {
                    return null;
                }
                Myprofile.this.flag = 0;
                Myprofile.this.runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(Myprofile.this, "Update successful", Toast.LENGTH_LONG).show();
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
            Myprofile.this.dialog.dismiss();
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

    private void myinfo() {
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
        this.token = getPref("token", getApplicationContext());
        this.device = getPref("device", getApplicationContext());
        StringRequest r1 = new StringRequest(1, this.url + "/reseller?self=yes&token=" + URLEncoder.encode(this.token) + "&deviceid=" + URLEncoder.encode(this.device), new Response.Listener<String>() {
            public void onResponse(String str) {
                Log.d("sabbir", str);
                Myprofile.this.FinalJSonObject = str;
                Myprofile myprofile = Myprofile.this;
                new ItemParseJSonDataClass(myprofile).execute(new Void[0]);
            }
        }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError volleyError) {
                Myprofile.this.dialog.dismiss();
                Toast.makeText(Myprofile.this.getBaseContext(), volleyError.toString(), Toast.LENGTH_LONG).show();
            }
        }) {
            /* developer by  sabbir */
            public Map<String, String> getParams() throws AuthFailureError {
                Hashtable hashtable = new Hashtable();
                hashtable.put("goto", "ok");
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

    private class ItemParseJSonDataClass extends AsyncTask<Void, Void, Void> {
        public Context context;

        public ItemParseJSonDataClass(Context context2) {
            this.context = context2;
        }

        /* developer by  sabbir */
        public void onPreExecute() {
            super.onPreExecute();
        }

        /* developer by  sabbir */
        public Void doInBackground(Void... voidArr) {
            try {
                JSONObject jSONObject = new JSONObject(Myprofile.this.FinalJSonObject);
                Myprofile.this.jsonarray = jSONObject.getJSONArray("bmtelbd");
                for (int i = 0; i < Myprofile.this.jsonarray.length(); i++) {
                    new HashMap();
                    JSONObject jSONObject2 = Myprofile.this.jsonarray.getJSONObject(i);
                    String unused = Myprofile.this.nicki = jSONObject2.getString("username");
                    String unused2 = Myprofile.this.namei = jSONObject2.getString(AppMeasurementSdk.ConditionalUserProperty.NAME);
                    String unused3 = Myprofile.this.emaili = jSONObject2.getString("email");
                    String unused4 = Myprofile.this.f202id = jSONObject2.getString("id");
                    String unused5 = Myprofile.this.tel = jSONObject2.getString("tel");
                    String unused6 = Myprofile.this.birtn = jSONObject2.getString("birth");
                    String unused7 = Myprofile.this.nidn = jSONObject2.getString("nid");
                    Myprofile.this.runOnUiThread(new Runnable() {
                        public void run() {
                            Myprofile.this.nick.setText(Myprofile.this.nicki);
                            Myprofile.this.name.setText(Myprofile.this.namei);
                            if (Myprofile.this.nidn.indexOf("null") < 0) {
                                Myprofile.this.nid.setText(Myprofile.this.nidn);
                                Myprofile.this.nid.setClickable(false);
                                Myprofile.this.nid.setFocusable(false);
                            }
                            if (Myprofile.this.birtn.indexOf("null") < 0) {
                                Myprofile.this.birth.setText(Myprofile.this.birtn);
                            }
                            if (Myprofile.this.emaili != null) {
                                Myprofile.this.email.setText(Myprofile.this.emaili);
                            }
                            if (Myprofile.this.tel.indexOf("null") < 0) {
                                Myprofile.this.f205te.setText(Myprofile.this.tel);
                            }
                        }
                    });
                }
                return null;
            } catch (JSONException e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
                return null;
            }
        }

        /* developer by  sabbir */
        public void onPostExecute(Void voidR) {
            Myprofile.this.dialog.dismiss();
        }
    }
}
