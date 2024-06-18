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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;
import androidx.core.app.NotificationCompat;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import com.google.android.gms.common.internal.ImagesContract;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Internet extends Activity {
    static String COMM = "com";
    static String CONTENT = FirebaseAnalytics.Param.CONTENT;
    static String Drive = "drive";
    static String FROM = "from";

    
    static String f192ID = "id";
    static String Number = "number";
    static String OPN = "opname";

    
    static String f193OT = "ot";
    static String PPRICE = FirebaseAnalytics.Param.PRICE;
    static String Paid = "paid";
    static String REG = "reg";
    static String ROL = "role";
    static String Service = NotificationCompat.CATEGORY_SERVICE;
    static String TIME = "time";
    static String TITLE = "title";
    private static final String[] distic = {"Recharge", "Bkash", "Rocket", "Bank", "All"};
    String FinalJSonObject;
    Internet_after adapter;
    ArrayList<HashMap<String, String>> arraylist;
    String device;
    Dialog dialog;
    JSONArray jsonarray;
    JSONObject jsonobject;
    ListView listview;
    ProgressDialog mProgressDialog;
    String number;
    String opn;
    String pwd;
    String rol;
    String service;

    /* renamed from: tn */
    private EditText f194tn;
    String token;
    String type;
    String type2;
    String url;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.internet_main);
        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
        Intent intent = getIntent();
        this.opn = intent.getExtras().getString("opt");
        this.service = intent.getExtras().getString("type3");
        this.number = intent.getExtras().getString("number");
        ((ImageButton) findViewById(R.id.search)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Internet.this.item_list();
            }
        });
        item_list();
    }

    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
    }

    /* developer by  sabbir */
    public void item_list() {
        Dialog dialog2 = new Dialog(this);
        this.dialog = dialog2;
        dialog2.requestWindowFeature(1);
        this.dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        this.dialog.setCancelable(false);
        this.dialog.setContentView(R.layout.custom_progress);
        this.dialog.show();
        String str = getPref(ImagesContract.URL, this) + "/apiapp/";
        this.url = str;
        this.url = str.replaceFirst("^(http[s]?://www\\.|http[s]?://|www\\.)", "");
        String str2 = "https://" + this.url;
        this.url = str2;
        Log.d("sabbir", str2);
        this.pwd = getPref("pass", this);
        this.token = getPref("token", this);
        this.device = getPref("device", this);
        if (getIntent().getExtras().getString("drive").indexOf("drive") >= 0) {
            this.rol = "getdrive";
        } else {
            this.rol = "getinternet";
        }
        EditText editText = (EditText) findViewById(R.id.numbers);
        this.f194tn = editText;
        StringRequest r1 = new StringRequest(1, this.url + "/" + this.rol + "?ot=" + this.opn + "&text=" + editText.getText().toString() + "&token=" + URLEncoder.encode(this.token) + "&deviceid=" + URLEncoder.encode(this.device), new Response.Listener<String>() {
            public void onResponse(String str) {
                Log.d("sabbir", str);
                Internet.this.FinalJSonObject = str;
                Internet internet = Internet.this;
                new ItemParseJSonDataClass(internet).execute(new Void[0]);
            }
        }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError volleyError) {
                Internet.this.dialog.dismiss();
                Toast.makeText(Internet.this, volleyError.toString(), Toast.LENGTH_LONG).show();
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
        Volley.newRequestQueue(this).add(r1);
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
            Internet.this.arraylist = new ArrayList<>();
            try {
                JSONObject jSONObject = new JSONObject(Internet.this.FinalJSonObject);
                Internet.this.jsonarray = jSONObject.getJSONArray("bmtelbd");
                Log.d("Create Response", Internet.this.jsonarray.toString());
                for (int i = 0; i < Internet.this.jsonarray.length(); i++) {
                    HashMap hashMap = new HashMap();
                    JSONObject jSONObject2 = Internet.this.jsonarray.getJSONObject(i);
                    hashMap.put("id", jSONObject2.getString("id"));
                    hashMap.put(FirebaseAnalytics.Param.PRICE, jSONObject2.getString(FirebaseAnalytics.Param.PRICE));
                    hashMap.put("title", jSONObject2.getString("title"));
                    hashMap.put("opname", jSONObject2.getString("opname"));
                    hashMap.put("com", jSONObject2.getString("com"));
                    hashMap.put("opname", Internet.this.opn);
                    hashMap.put("number", Internet.this.number);
                    hashMap.put("drive", "drive");
                    hashMap.put("paid", "");
                    hashMap.put("role", Internet.this.rol);
                    hashMap.put(NotificationCompat.CATEGORY_SERVICE, Internet.this.service);
                    hashMap.put("reg", jSONObject2.getString("reg"));
                    hashMap.put("from", "direct");
                    Internet.this.arraylist.add(hashMap);
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
            Internet.this.dialog.dismiss();
            Internet internet = Internet.this;
            internet.listview = (ListView) internet.findViewById(R.id.atachview);
            Internet internet2 = Internet.this;
            Internet internet3 = Internet.this;
            internet2.adapter = new Internet_after(internet3, internet3.arraylist);
            Internet.this.listview.setAdapter(Internet.this.adapter);
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

    private boolean isOnline(Context context) {
        @SuppressLint("WrongConstant") NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }
}
