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
import android.os.Handler;
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

public class Tricket_main_read extends Activity {
    static String CONTENT = FirebaseAnalytics.Param.CONTENT;
    private static final String TAG_SUCCESS = "success";
    static String TITLE = NotificationCompat.CATEGORY_MESSAGE;
    static String aid = "ankid";
    static String date = "date";
    private static final String[] distic = {"Recharge", "Bkash", "Rocket", "Bank", "All"};

    
    static String f284id = "id";
    static String uid = "userid";
    String FinalJSonObject;
    Tricket_main_adafter_read adapter;
    ArrayList<HashMap<String, String>> arraylist;
    String device;
    int flag = 0;
    int flagd = 0;
    Intent intent;
    JSONParser jsonParser = new JSONParser();
    JSONArray jsonarray;
    JSONObject jsonobject;
    ProgressDialog mProgressDialog;

    /* textSize="13sp" */
    private dialog f285md;
    EditText msg;
    String number;
    String pwd;
    String token;
    String type;
    String type2;
    String url;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.tricket_main_read);
        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
        this.msg = (EditText) findViewById(R.id.stext);
        this.intent = getIntent();
        item_list();
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                Tricket_main_read.this.flagd = 1;
                Tricket_main_read.this.item_list();
                handler.postDelayed(this, 30000);
            }
        }, 30000);
        ((ImageButton) findViewById(R.id.send)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Tricket_main_read tricket_main_read = Tricket_main_read.this;
                if (!tricket_main_read.isOnline(tricket_main_read)) {
                    Toast.makeText(Tricket_main_read.this, "No network connection", Toast.LENGTH_LONG).show();
                } else if (Tricket_main_read.this.msg.length() < 4) {
                    Toast.makeText(Tricket_main_read.this, "Please Enter Message", Toast.LENGTH_LONG).show();
                } else {
                    Tricket_main_read.this.New_support();
                    Tricket_main_read.this.msg.setText("");
                }
            }
        });
    }

    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
    }

    /* developer by  sabbir */
    public void New_support() {
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
                Tricket_main_read.this.FinalJSonObject = str;
                Tricket_main_read tricket_main_read = Tricket_main_read.this;
                new ParseJSonDataClass(tricket_main_read).execute(new Void[0]);
            }
        }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(Tricket_main_read.this.getBaseContext(), volleyError.toString(), Toast.LENGTH_LONG).show();
            }
        }) {
            /* developer by  sabbir */
            public Map<String, String> getParams() throws AuthFailureError {
                Hashtable hashtable = new Hashtable();
                String obj = Tricket_main_read.this.msg.getText().toString();
                hashtable.put("id", Tricket_main_read.this.intent.getExtras().getString("id"));
                hashtable.put("message", obj);
                hashtable.put("token", Tricket_main_read.this.token);
                hashtable.put("username", Tricket_main_read.this.number);
                hashtable.put("password", Tricket_main_read.this.pwd);
                hashtable.put("deviceid", Tricket_main_read.this.device);
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
                if (new JSONObject(Tricket_main_read.this.FinalJSonObject).getInt("success") == 1) {
                    Tricket_main_read.this.runOnUiThread(new Runnable() {
                        public void run() {
                            Tricket_main_read.this.item_list();
                        }
                    });
                    Tricket_main_read.this.flag = 0;
                    return null;
                }
                Tricket_main_read.this.flag = 1;
                return null;
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }
        }

        /* developer by  sabbir */
        public void onPostExecute(Void voidR) {
            if (Tricket_main_read.this.flag == 1) {
                Toast.makeText(Tricket_main_read.this, "Please Enter Correct informations", Toast.LENGTH_LONG).show();
            }
        }
    }

    /* developer by  sabbir */
    public void item_list() {
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
        StringRequest r1 = new StringRequest(1, this.url + "/trickets?id=" + this.intent.getExtras().getString("id") + "&token=" + URLEncoder.encode(this.token) + "&deviceid=" + URLEncoder.encode(this.device), new Response.Listener<String>() {
            public void onResponse(String str) {
                Log.d("sabbir", str);
                Tricket_main_read.this.FinalJSonObject = str;
                Tricket_main_read tricket_main_read = Tricket_main_read.this;
                new ItemParseJSonDataClass(tricket_main_read).execute(new Void[0]);
            }
        }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(Tricket_main_read.this.getBaseContext(), volleyError.toString(), Toast.LENGTH_LONG).show();
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
            Tricket_main_read.this.arraylist = new ArrayList<>();
            try {
                JSONObject jSONObject = new JSONObject(Tricket_main_read.this.FinalJSonObject);
                Tricket_main_read.this.jsonarray = jSONObject.getJSONArray("bmtelbd");
                Log.d("Create Response", Tricket_main_read.this.jsonarray.toString());
                for (int i = 0; i < Tricket_main_read.this.jsonarray.length(); i++) {
                    HashMap hashMap = new HashMap();
                    JSONObject jSONObject2 = Tricket_main_read.this.jsonarray.getJSONObject(i);
                    hashMap.put("id", jSONObject2.getString("id"));
                    hashMap.put("date", jSONObject2.getString("date"));
                    hashMap.put(NotificationCompat.CATEGORY_MESSAGE, jSONObject2.getString(NotificationCompat.CATEGORY_MESSAGE));
                    hashMap.put("userid", jSONObject2.getString("userid"));
                    hashMap.put("ankid", jSONObject2.getString("ankid"));
                    Tricket_main_read.this.arraylist.add(hashMap);
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
            Tricket_main_read tricket_main_read = Tricket_main_read.this;
            Tricket_main_read tricket_main_read2 = Tricket_main_read.this;
            tricket_main_read.adapter = new Tricket_main_adafter_read(tricket_main_read2, tricket_main_read2.arraylist);
            ((ListView) Tricket_main_read.this.findViewById(R.id.atachview)).setAdapter(Tricket_main_read.this.adapter);
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

    /* developer by  sabbir*/
    public boolean isOnline(Context context) {
        @SuppressLint("WrongConstant") NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }
}
