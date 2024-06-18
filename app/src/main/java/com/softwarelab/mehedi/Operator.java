package com.softwarelab.mehedi;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.core.app.NotificationCompat;

import com.google.android.gms.common.internal.ImagesContract;
import java.util.ArrayList;
import java.util.HashMap;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Operator extends Activity {
    public static String TAG = Welcome.class.getSimpleName();
    private static final String TAG_Balance = "Balance";
    private static final String TAG_SUCCESS = "success";
    private static final String about = "about";
    static String drive = "drive";
    static String img = "img";
    static String number = "number";
    static String opn = "opname";

    
    static String f212ot = "pcode";
    static String otype = "type";
    static String serv = "service";
    Operator_adafter adapter;

    
    private EditText f213am;
    ArrayList<HashMap<String, String>> arraylist;
    private TextView balanc;
    Dialog dialog;
    private EditText email_id;
    int flag = 0;
    Intent intent;
    JSONParser jsonParser = new JSONParser();
    JSONArray jsonarray;
    JSONObject jsonobject;
    Button login;

    
    private EditText f214mn;
    private ProgressDialog pDialog;
    Button signi;
    String type;
    String type2;

    /* developer by  sabbir */
    public void onCreate(Bundle bundle) {
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());
        super.onCreate(bundle);
        setContentView(R.layout.operator);
        this.intent = getIntent();
        new DownloadJSONy().execute(new Void[0]);
    }

    private class DownloadJSONy extends AsyncTask<Void, Void, Void> {
        private ImageButton retry;

        private DownloadJSONy() {
        }

        /* developer by  sabbir */
        public void onPreExecute() {
            super.onPreExecute();
            Operator.this.dialog = new Dialog(Operator.this);
            Operator.this.dialog.requestWindowFeature(1);
            Operator.this.dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
            Operator.this.dialog.setCancelable(false);
            Operator.this.dialog.setContentView(R.layout.custom_progress);
            Operator.this.dialog.show();
            Operator operator = Operator.this;
            if (!operator.isOnline(operator)) {
                Operator.this.dialog.dismiss();
                Operator.this.finish();
            }
        }

        /* developer by  sabbir */
        public Void doInBackground(Void... voidArr) {
            Operator operator = Operator.this;
            if (!operator.isOnline(operator)) {
                return null;
            }
            Operator.getPref("token", Operator.this.getApplicationContext());
            Operator.getPref("device", Operator.this.getApplicationContext());
            Operator.this.arraylist = new ArrayList<>();
            Operator.this.jsonobject = JSONfunctions.getJSONfromURL((Operator.getPref(ImagesContract.URL, Operator.this.getApplicationContext()) + "/apiapp/") + "/oparetorList");
            try {
                Operator operator2 = Operator.this;
                operator2.jsonarray = operator2.jsonobject.getJSONArray("bmtelbd");
                Log.d("Create Response", Operator.this.jsonarray.toString());
                for (int i = 0; i < Operator.this.jsonarray.length(); i++) {
                    HashMap hashMap = new HashMap();
                    Operator operator3 = Operator.this;
                    operator3.jsonobject = operator3.jsonarray.getJSONObject(i);
                    hashMap.put("opname", Operator.this.jsonobject.getString("opname"));
                    hashMap.put("pcode", Operator.this.jsonobject.getString("pcode"));
                    hashMap.put("img", Operator.this.jsonobject.getString("img"));
                    hashMap.put("type", Operator.this.intent.getExtras().getString("type"));
                    hashMap.put(NotificationCompat.CATEGORY_SERVICE, Operator.this.intent.getExtras().getString("type3"));
                    if (Operator.this.intent.hasExtra("number")) {
                        hashMap.put("number", Operator.this.intent.getExtras().getString("number"));
                    }
                    if (Operator.this.intent.hasExtra("drive")) {
                        hashMap.put("drive", Operator.this.intent.getExtras().getString("drive"));
                    } else {
                        hashMap.put("drive", "x");
                    }
                    Operator.this.arraylist.add(hashMap);
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
            Operator operator = Operator.this;
            if (operator.isOnline(operator)) {
                Operator operator2 = Operator.this;
                Operator operator3 = Operator.this;
                operator2.adapter = new Operator_adafter(operator3, operator3.arraylist);
                ((GridView) Operator.this.findViewById(R.id.atachview)).setAdapter(Operator.this.adapter);
                Operator.this.dialog.dismiss();
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
}
