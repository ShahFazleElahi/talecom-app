package com.softwarelab.mehedi;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;


public class Historyl extends AppCompatActivity {
    public static String TAG = "Historyl";
    private static final String TAG_Balance = "Balance";
    private static final String TAG_SUCCESS = "success";
    private static final String about = "about";

    
    private EditText f190am;
    private TextView balanc;
    private EditText email_id;
    int flag = 0;
    JSONParser jsonParser = new JSONParser();
    Button login;

    
    private EditText f191mn;
    private ProgressDialog pDialog;
    Button signi;
    String type;
    String type2;

    /* developer by  sabbir */
    public void onCreate(Bundle bundle) {
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());
        super.onCreate(bundle);
        setContentView((int) R.layout.historylist);
    }

    public void action(View view) {
        if (view.getId() == R.id.flex) {
            Intent intent = new Intent(getApplicationContext(), Mainlist.class);
            intent.putExtra("type", "64");
            startActivity(intent);
        }
        if (view.getId() == R.id.all) {
            Intent intent2 = new Intent(getApplicationContext(), Mainlist.class);
            intent2.putExtra("type", "0");
            startActivity(intent2);
        }
        if (view.getId() == R.id.roc) {
            Intent intent3 = new Intent(getApplicationContext(), Mainlist.class);
            intent3.putExtra("type", "256");
            startActivity(intent3);
        }
    }

    private boolean isOnline(Context context) {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }

    public void SavePreferences(String str, String str2) {
        SharedPreferences.Editor edit = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit();
        edit.putString(str, str2);
        edit.commit();
    }

    public static String getPref(String str, Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getString(str, (String) null);
    }
}
