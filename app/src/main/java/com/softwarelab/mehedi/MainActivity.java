package com.softwarelab.mehedi;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import com.google.android.gms.common.internal.ImagesContract;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.Hashtable;
import java.util.Map;
import java.util.Random;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    private static final String Pint = "otp";
    private static final String TAG_SUCCESS = "success";
    String FinalJSonObject;
    TextView dev;
    String device;
    MaterialCardView card_watch_tiotorial;
    Dialog dialog;
    int flag = 0;
    Button login;
    private EditText mobile_number;
    String msg;
    String number;
    private ProgressDialog pDialog;
    /* developer by  sabbir */
    public EditText password;
    String pwd;
    String res;
    Button signin;
    String token;
    String url;

    /* developer by  sabbir */
    public void onCreate(Bundle bundle) {
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_main);

        card_watch_tiotorial = findViewById(R.id.card_watch_tiotorial);
        card_watch_tiotorial.setOnClickListener(view -> {
            
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("https://youtu.be/ttrSMxK3DtU?si=UXgC9RzW3ngezZE_"));
            startActivity(intent);

        });





        ((TextView) findViewById(R.id.band)).setText(getString(R.string.app_name));
        this.login = (Button) findViewById(R.id.login);
        this.mobile_number = (EditText) findViewById(R.id.mobile_number);
        this.password = (EditText) findViewById(R.id.password);
        this.login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (MainActivity.this.password.length() < 4) {
                    Toast.makeText(MainActivity.this, "Please Enter correct password", Toast.LENGTH_LONG).show();
                } else if (!isOnline(MainActivity.this)) {
                    Toast.makeText(MainActivity.this, "No network connection", Toast.LENGTH_LONG).show();
                } else {
                    MainActivity.this.start();
                }
            }

            private boolean isOnline(Context context) {
                @SuppressLint("WrongConstant") NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
                return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public static String getPref(String str, Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getString(str, (String) null);
    }

    public static String getPref2(String str, Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getString(str, "no");
    }

    public static String random() {
        char[] charArray = "ABCDEF012GHIJKL345MNOPQR678STUVWXYZ9".toCharArray();
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 36; i++) {
            sb.append(charArray[random.nextInt(charArray.length)]);
        }
        return sb.toString();
    }

    public void SavePreferences(String str, String str2) {
        SharedPreferences.Editor edit = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit();
        edit.putString(str, str2);
        edit.commit();
    }

    public void onLoginClick(View view) {
        startActivity(new Intent(this, Addpic.class));
        overridePendingTransition(R.anim.slide_in_right, R.anim.stay);
    }

    public void onLoginClickb(View view) {
        String str = getPref(ImagesContract.URL, getApplicationContext()) + "/policy";
        this.url = str;
        this.url = str.replaceFirst("^(http[s]?://www\\.|http[s]?://|www\\.)", "");
        this.url = "https://" + this.url;
        startActivity(new Intent("android.intent.action.VIEW", Uri.parse(this.url)));
    }

    /* developer by  sabbir */
    public void start() {
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
        this.url = "https://" + this.url;
        this.number = this.mobile_number.getText().toString();
        this.pwd = this.password.getText().toString();
        this.device = getPref2("device", getApplicationContext());
        this.token = getPref("token", getApplicationContext());
        if (this.device.indexOf("no") >= 0) {
            SavePreferences("device", random());
        }
        this.device = getPref2("device", getApplicationContext());
        StringRequest r1 = new StringRequest(1, this.url + FirebaseAnalytics.Param.INDEX, new Response.Listener<String>() {
            public void onResponse(String str) {
                Log.d("sabbir", str);
                MainActivity.this.FinalJSonObject = str;
                MainActivity mainActivity = MainActivity.this;
                new ParseJSonDataClass(mainActivity).execute(new Void[0]);
            }
        }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError volleyError) {
                MainActivity.this.dialog.dismiss();
                Toast.makeText(MainActivity.this.getBaseContext(), volleyError.toString(), Toast.LENGTH_LONG).show();
            }
        }) {
            /* developer by  sabbir */
            public Map<String, String> getParams() throws AuthFailureError {
                Hashtable hashtable = new Hashtable();
                hashtable.put("username", MainActivity.this.number);
                hashtable.put("password", MainActivity.this.pwd);
                hashtable.put("deviceid", MainActivity.this.device);
                hashtable.put("aut", "auto");
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

        /* developer by  sabbir*/
        public Void doInBackground(Void... voidArr) {
            try {
                JSONObject jSONObject = new JSONObject(MainActivity.this.FinalJSonObject);
                if (jSONObject.getInt("stat") == 1) {
                    MainActivity mainActivity = MainActivity.this;
                    mainActivity.SavePreferences("phone", mainActivity.number);
                    MainActivity mainActivity2 = MainActivity.this;
                    mainActivity2.SavePreferences("pass", mainActivity2.pwd);
                }
                MainActivity.this.msg = jSONObject.getString("message");
                if (jSONObject.getInt("success") == 1) {
                    MainActivity.this.token = jSONObject.getString("token");
                    MainActivity mainActivity3 = MainActivity.this;
                    mainActivity3.SavePreferences("token", mainActivity3.token);
                    MainActivity mainActivity4 = MainActivity.this;
                    mainActivity4.SavePreferences("phone", mainActivity4.number);
                    MainActivity mainActivity5 = MainActivity.this;
                    mainActivity5.SavePreferences("pass", mainActivity5.pwd);
                    MainActivity.this.SavePreferences("otpchoose", jSONObject.getString("choseotp"));
                    MainActivity.this.SavePreferences("postlevel", jSONObject.getString("postlevel"));
                    MainActivity.this.flag = 0;
                    if (jSONObject.getInt(MainActivity.Pint) == 1) {
                        Intent intent = new Intent(MainActivity.this.getApplicationContext(), pinver.class);
                        intent.putExtra("mobile_number", MainActivity.this.number);
                        intent.putExtra("password", MainActivity.this.pwd);
                        MainActivity.this.startActivity(intent);
                        MainActivity.this.overridePendingTransition(R.anim.slide_in_right, R.anim.stay);
                        MainActivity.this.finish();
                        return null;
                    }
                    Intent intent2 = new Intent(MainActivity.this.getApplicationContext(), Welcome.class);
                    intent2.putExtra("mobile_number", MainActivity.this.number);
                    intent2.putExtra("password", MainActivity.this.pwd);
                    MainActivity.this.startActivity(intent2);
                    MainActivity.this.overridePendingTransition(R.anim.slide_in_right, R.anim.stay);
                    MainActivity.this.finish();
                    return null;
                }
                MainActivity.this.flag = 1;
                return null;
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }
        }

        /* developer by  sabbir */
        public void onPostExecute(Void voidR) {
            MainActivity.this.dialog.dismiss();
            if (MainActivity.this.flag == 1) {
                Toast.makeText(MainActivity.this, "" + MainActivity.this.msg, Toast.LENGTH_LONG).show();
            }
        }
    }
}
