package com.softwarelab.mehedi;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.GridView;
import android.widget.Toast;
import androidx.core.app.NotificationCompat;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.internal.ImagesContract;
import com.google.android.gms.measurement.api.AppMeasurementSdk;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class history_op extends Activity {
    static String CONTENT = FirebaseAnalytics.Param.CONTENT;
    static String Model = "id";
    static String OPN = "opname";

    
    static String f336OT = "ot";
    static String Pini = "pin";
    static String Service_id = NotificationCompat.CATEGORY_SERVICE;
    static String Service_n = AppMeasurementSdk.ConditionalUserProperty.NAME;
    static String TIME = "time";
    static String TYPe = "type";
    static String Uptime = "uptime";
    static String act = "act";
    private static final String[] distic = {"Recharge", "Bkash", "Rocket", "Bank", "All"};
    static String img = "img";
    static String mmm = "model";
    String FinalJSonObject;
    history_adafter adapter;
    ArrayList<HashMap<String, String>> arraylist;
    String device;
    Dialog dialog;
    JSONArray jsonarray;
    JSONObject jsonobject;
    ProgressDialog mProgressDialog;
    String number;
    String pwd;
    String token;
    String type;
    String type2;
    String url;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.service_main);
        item_list();
    }

    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
    }

    private void item_list() {
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
        StringRequest r1 = new StringRequest(1, this.url + "/role?item=history&token=" + URLEncoder.encode(this.token) + "&deviceid=" + URLEncoder.encode(this.device), new Response.Listener<String>() {
            public void onResponse(String str) {
                Log.d("sabbir", str);
                history_op.this.FinalJSonObject = str;
                history_op history_op = history_op.this;
                new ItemParseJSonDataClass(history_op).execute(new Void[0]);
            }
        }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError volleyError) {
                history_op.this.dialog.dismiss();
                Toast.makeText(history_op.this.getBaseContext(), volleyError.toString(), Toast.LENGTH_LONG).show();
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
            history_op.this.arraylist = new ArrayList<>();
            try {
                JSONObject jSONObject = new JSONObject(history_op.this.FinalJSonObject);
                history_op.this.jsonarray = jSONObject.getJSONArray("bmtelbd");
                Log.d("Create Response", history_op.this.jsonarray.toString());
                for (int i = 0; i < history_op.this.jsonarray.length(); i++) {
                    HashMap hashMap = new HashMap();
                    JSONObject jSONObject2 = history_op.this.jsonarray.getJSONObject(i);
                    hashMap.put(NotificationCompat.CATEGORY_SERVICE, jSONObject2.getString(NotificationCompat.CATEGORY_SERVICE));
                    hashMap.put(AppMeasurementSdk.ConditionalUserProperty.NAME, jSONObject2.getString(AppMeasurementSdk.ConditionalUserProperty.NAME));
                    hashMap.put("img", jSONObject2.getString("img"));
                    hashMap.put("act", jSONObject2.getString("act"));
                    history_op.this.arraylist.add(hashMap);
                }
                return null;
            } catch (JSONException e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
                return null;
            }
        }

        /* developer by  sabbir*/
        public void onPostExecute(Void voidR) {
            history_op history_op = history_op.this;
            history_op history_op2 = history_op.this;
            history_op.adapter = new history_adafter(history_op2, history_op2.arraylist);
            ((GridView) history_op.this.findViewById(R.id.atachview)).setAdapter(history_op.this.adapter);
            history_op.this.dialog.dismiss();
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
