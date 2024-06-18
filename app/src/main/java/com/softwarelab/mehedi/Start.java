package com.softwarelab.mehedi;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.RequiresApi;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.internal.ImagesContract;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;
import com.romainpiel.shimmer.Shimmer;
import com.romainpiel.shimmer.ShimmerTextView;
import java.nio.charset.StandardCharsets;
import java.util.Hashtable;
import java.util.Map;
import java.util.Random;
import org.json.JSONException;
import org.json.JSONObject;

public class Start extends Activity {
    private static final String Pint = "otp";
    private static final String TAG_SUCCESS = "success";
    String FinalJSonObject;
    String device;
    int flag = 0;
    Button login;
    String msg;
    String number;
    private ProgressDialog pDialog;
    String pwd;
    Shimmer shimmer;
    Button signin;
    String token;

    /* renamed from: tv */
    ShimmerTextView f280tv;
    String url;

    /* developer by  sabbir */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void onCreate(Bundle bundle) {
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());
        super.onCreate(bundle);
        setContentView(R.layout.my2);
        SavePreferences(ImagesContract.URL, "https://fiverelectric.online");
        FirebaseMessaging.getInstance().subscribeToTopic(getPref(ImagesContract.URL, getApplicationContext()).replaceFirst("^(http[s]?://www\\.|http[s]?://|www\\.)", ""));
        isOnline(this);
        if (getPref23(ImagesContract.URL, getApplicationContext()).indexOf("never") >= 0) {
            startActivity(new Intent(getApplicationContext(), Url.class));
            finish();
            return;
        }
        FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
            public void onComplete(Task<InstanceIdResult> task) {
                if (task.isSuccessful()) {
                    Start.this.SavePreferences("ftoken", task.getResult().getToken());
                }
            }
        });
        start();
    }

    private boolean isOnline(Context context) {
        @SuppressLint("WrongConstant") NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public static String getPref(String str, Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getString(str, (String) null);
    }

    public static String getldata(String str, Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getString(str, "flexisoftwarebd");
    }

    public static String getPref23(String str, Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getString(str, "never");
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public String genAuthKey(String str) {
        byte[] decode = Base64.decode(str.getBytes(), 0);
        Log.e("key", new String(decode, StandardCharsets.UTF_8));
        return new String(decode, StandardCharsets.UTF_8);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void Dev(String str) {
        String genAuthKey = genAuthKey(str);
        SharedPreferences.Editor edit = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit();
        edit.putString(ImagesContract.URL, genAuthKey);
        edit.commit();
    }

    public void SavePreferences(String str, String str2) {
        SharedPreferences.Editor edit = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit();
        edit.putString(str, str2);
        edit.commit();
    }

    public static String getPref2(String str, Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getString(str, "no");
    }

    @SuppressLint("WrongConstant")
    private boolean isMyServiceRunning(Class<?> cls) {
        for (ActivityManager.RunningServiceInfo runningServiceInfo : ((ActivityManager) getSystemService("activity")).getRunningServices(Integer.MAX_VALUE)) {
            if (cls.getName().equals(runningServiceInfo.service.getClassName())) {
                return true;
            }
        }
        return false;
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

    private void start() {
        this.f280tv = (ShimmerTextView) findViewById(R.id.progressBar);
        Shimmer shimmer2 = new Shimmer();
        this.shimmer = shimmer2;
        shimmer2.start(this.f280tv);
        String str = getPref(ImagesContract.URL, getApplicationContext()) + "/apiapp/";
        this.url = str;
        this.url = str.replaceFirst("^(http[s]?://www\\.|http[s]?://|www\\.)", "");
        String str2 = "https://" + this.url;
        this.url = str2;
        Log.d("sabbir", str2);
        this.number = getldata("phone", getApplicationContext());
        this.pwd = getldata("pass", getApplicationContext());
        String pref2 = getPref2("device", getApplicationContext());
        this.device = pref2;
        if (pref2.indexOf("no") >= 0) {
            SavePreferences("device", random());
        }
        this.device = getPref2("device", getApplicationContext());
        StringRequest r1 = new StringRequest(1, this.url + FirebaseAnalytics.Param.INDEX, new Response.Listener<String>() {
            public void onResponse(String str) {
                Log.d("sabbir", str);
                Start.this.FinalJSonObject = str;
                Start start = Start.this;
                new ParseJSonDataClass(start).execute(new Void[0]);
            }
        }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError volleyError) {
                Start.this.shimmer.cancel();
                ((TextView) Start.this.findViewById(R.id.er)).setVisibility(View.VISIBLE);
                ((ImageButton) Start.this.findViewById(R.id.ret)).setVisibility(View.VISIBLE);
                Toast.makeText(Start.this.getBaseContext(), volleyError.toString(), Toast.LENGTH_LONG).show();
            }
        }) {
            /* developer by  sabbir */
            public Map<String, String> getParams() throws AuthFailureError {
                Hashtable hashtable = new Hashtable();
                hashtable.put("username", Start.this.number);
                hashtable.put("ftoken", Start.getPreff("ftoken", Start.this.getApplicationContext()));
                hashtable.put("password", Start.this.pwd);
                hashtable.put("deviceid", Start.this.device);
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

        /* developer by  sabbir */
        public Void doInBackground(Void... voidArr) {
            try {
                JSONObject jSONObject = new JSONObject(Start.this.FinalJSonObject);
                Start.this.msg = jSONObject.getString("message");
                if (jSONObject.getInt("success") == 1) {
                    Start.this.token = jSONObject.getString("token");
                    Start.this.SavePreferences("otpchoose", jSONObject.getString("choseotp"));
                    Start.this.SavePreferences("postlevel", jSONObject.getString("postlevel"));
                    Start start = Start.this;
                    start.SavePreferences("phone", start.number);
                    Start start2 = Start.this;
                    start2.SavePreferences("pass", start2.pwd);
                    Start start3 = Start.this;
                    start3.SavePreferences("token", start3.token);
                    Start.this.flag = 0;
                    if (jSONObject.getInt(Start.Pint) == 1) {
                        Intent intent = new Intent(Start.this.getApplicationContext(), pinver.class);
                        intent.putExtra("mobile_number", Start.this.number);
                        intent.putExtra("password", Start.this.pwd);
                        Start.this.startActivity(intent);
                        Start.this.overridePendingTransition(R.anim.slide_in_right, R.anim.stay);
                        Start.this.finish();
                        return null;
                    }
                    Intent intent2 = new Intent(Start.this.getApplicationContext(), Welcome.class);
                    intent2.putExtra("mobile_number", Start.this.number);
                    intent2.putExtra("password", Start.this.pwd);
                    Start.this.startActivity(intent2);
                    Start.this.overridePendingTransition(R.anim.slide_in_right, R.anim.stay);
                    Start.this.finish();
                    return null;
                }
                Start.this.flag = 1;
                Start.this.SavePreferences("all_level", jSONObject.getString("alllevel"));
                Intent intent3 = new Intent(Start.this.getApplicationContext(), MainActivity.class);
                intent3.putExtra("mobile_number", Start.this.number);
                intent3.putExtra("password", Start.this.pwd);
                Start.this.startActivity(intent3);
                Start.this.finish();
                return null;
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }
        }

        /* developer by  sabbir */
        public void onPostExecute(Void voidR) {
            if (Start.this.flag == 1) {
                Toast.makeText(Start.this, "" + Start.this.msg, Toast.LENGTH_LONG).show();
            }
            Start.this.shimmer.cancel();
        }
    }

    public void rettry(View view) {
        start();
    }

    public static String getPreff(String str, Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getString(str, "no");
    }
}
