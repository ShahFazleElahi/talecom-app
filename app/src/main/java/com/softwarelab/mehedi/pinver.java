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
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.internal.ImagesContract;
import com.google.android.material.textfield.TextInputLayout;
import com.reginald.patternlockview.PatternLockView;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Executor;
import org.json.JSONException;
import org.json.JSONObject;

public class pinver extends AppCompatActivity {
    private static final String KEY_NAME = UUID.randomUUID().toString();
    /* developer by  sabbir */
    public static final String TAG = MainActivity.class.getName();
    private static final String TAG_SUCCESS = "success";
    private static final PatternLockView.Password sDefaultPassword = new PatternLockView.Password(Arrays.asList(new Integer[]{1, 2, 3, 4}));
    String FinalJSonObject;
    int bio = 0;
    BiometricPrompt biometricPrompt;
    String device;
    Dialog dialog;
    Executor executor;
    int flag = 0;
    JSONParser jsonParser = new JSONParser();
    Button login;
    private PatternLockView mCurLockView;
    private PatternLockView.Password mPassword = sDefaultPassword;
    private String mToBeSignedMessage;
    private EditText mobile_number;
    String msg;
    String number;
    String otpc;
    int otpck;
    private ProgressDialog pDialog;
    /* developer by  sabbir */
    public EditText password;
    BiometricPrompt.PromptInfo promptInfo;
    String pwd;
    String pwdp;
    Button signin;
    String token;
    String url;

    /* developer by  sabbir */
    public void onCreate(Bundle bundle) {
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());
        super.onCreate(bundle);
        setContentView((int) R.layout.pin);
        this.pwdp = getPref("pin", getApplicationContext());
        String pref = getPref("otpchoose", getApplicationContext());
        this.otpc = pref;
        this.otpck = 0;
        try {
            this.otpck = Integer.parseInt(pref);
        } catch (NumberFormatException e) {
            System.out.println("Could not parse " + e);
        }
        if (this.otpck == 5) {
            pattern_lock_view(this);
        }
        this.executor = ContextCompat.getMainExecutor(this);
        this.biometricPrompt = new BiometricPrompt((FragmentActivity) this, this.executor, (BiometricPrompt.AuthenticationCallback) new BiometricPrompt.AuthenticationCallback() {
            public void onAuthenticationSucceeded(BiometricPrompt.AuthenticationResult authenticationResult) {
                super.onAuthenticationSucceeded(authenticationResult);
                pinver.this.bio = 1;
                pinver pinver = pinver.this;
                pinver.pwd = pinver.getPref("pass", pinver.getApplicationContext());
                pinver.this.start();
            }

            public void onAuthenticationError(int i, CharSequence charSequence) {
                super.onAuthenticationError(i, charSequence);
                Log.d("sabbir", String.valueOf(charSequence));
                Toast.makeText(pinver.this, charSequence, Toast.LENGTH_LONG).show();
            }

            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                Toast.makeText(pinver.this, "FAILED", Toast.LENGTH_LONG).show();
            }
        });
        if (!TextUtils.isEmpty(this.pwdp) && this.pwdp.length() > 3 && this.otpck != 5) {
            BiometricPrompt.PromptInfo build = new BiometricPrompt.PromptInfo.Builder().setTitle("Use Fingerprint").setDescription("Touch Fingerprint sensor no pin required").setNegativeButtonText("Use Pin").build();
            this.promptInfo = build;
            this.biometricPrompt.authenticate(build);
        }
        this.login = (Button) findViewById(R.id.login);
        this.password = (EditText) findViewById(R.id.passwordpin);
        TextInputLayout textInputLayout = (TextInputLayout) findViewById(R.id.txtPassword);
        if (this.otpck == 0) {
            textInputLayout.setHint((CharSequence) "PIN");
        } else {
            textInputLayout.setHint((CharSequence) "OTP");
        }
        this.login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (pinver.this.password.length() < 4) {
                    Toast.makeText(pinver.this, "Please Enter correct pin", Toast.LENGTH_LONG).show();
                } else if (!isOnline(pinver.this)) {
                    Toast.makeText(pinver.this, "No network connection", Toast.LENGTH_LONG).show();
                } else {
                    pinver.this.bio = 0;
                    pinver.this.start();
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

    public void SavePreferences(String str, String str2) {
        SharedPreferences.Editor edit = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit();
        edit.putString(str, str2);
        edit.commit();
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
        this.device = getPref("device", getApplicationContext());
        this.token = getPref("token", getApplicationContext());
        this.number = getPref("phone", getApplicationContext());
        if (this.bio == 0) {
            this.pwdp = this.password.getText().toString();
        }
        StringRequest r1 = new StringRequest(1, this.url + "pinchk", new Response.Listener<String>() {
            public void onResponse(String str) {
                Log.d("sabbir", pinver.this.pwdp);
                pinver.this.FinalJSonObject = str;
                pinver pinver = pinver.this;
                new ParseJSonDataClass(pinver).execute(new Void[0]);
            }
        }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError volleyError) {
                pinver.this.dialog.dismiss();
                Toast.makeText(pinver.this.getBaseContext(), volleyError.toString(), Toast.LENGTH_LONG).show();
            }
        }) {
            /* developer by  sabbir */
            public Map<String, String> getParams() throws AuthFailureError {
                Hashtable hashtable = new Hashtable();
                hashtable.put("token", pinver.this.token);
                hashtable.put("pin", pinver.this.pwdp);
                hashtable.put("deviceid", pinver.this.device);
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
                if (new JSONObject(pinver.this.FinalJSonObject).getInt("success") == 1) {
                    pinver pinver = pinver.this;
                    pinver.SavePreferences("pin", pinver.pwdp);
                    pinver.this.flag = 0;
                    Intent intent = new Intent(pinver.this.getApplicationContext(), Welcome.class);
                    intent.putExtra("mobile_number", pinver.this.number);
                    intent.putExtra("password", pinver.this.pwd);
                    pinver.this.startActivity(intent);
                    pinver.this.overridePendingTransition(R.anim.slide_in_right, R.anim.stay);
                    pinver.this.finish();
                    return null;
                }
                pinver.this.SavePreferences("pin", "0");
                pinver.this.flag = 1;
                return null;
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }
        }

        /* developer by  sabbir */
        public void onPostExecute(Void voidR) {
            pinver.this.dialog.dismiss();
            if (pinver.this.flag == 1) {
                Toast.makeText(pinver.this, "Please Enter Correct informations", Toast.LENGTH_LONG).show();
            }
        }
    }

    public void pattern_lock_view(Activity activity) {
        @SuppressLint("ResourceType") Dialog dialog2 = new Dialog(activity, 2131886564);
        dialog2.requestWindowFeature(1);
        dialog2.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dialog2.setCancelable(false);
        dialog2.setContentView(R.layout.pattern_lock);
        PatternLockView patternLockView = (PatternLockView) dialog2.findViewById(R.id.lock_view);
        this.mCurLockView = patternLockView;
        patternLockView.setPatternVisible(true);
        this.mPassword = sDefaultPassword;
        this.mCurLockView.stopPasswordAnim();
        this.mCurLockView.reset();
        this.mCurLockView.setCallBack(new PatternLockView.CallBack() {
            public int onFinish(PatternLockView.Password password) {
                Log.d(pinver.TAG, "password length " + password.list.size());
                if (password.list.size() < 4) {
                    return 0;
                }
                Log.d(pinver.TAG, "password  " + password.string);
                pinver.this.pwdp = password.string;
                pinver.this.bio = 1;
                pinver.this.start();
                return 0;
            }
        });
        this.mCurLockView.setOnNodeTouchListener(new PatternLockView.OnNodeTouchListener() {
            public void onNodeTouched(int i) {
                Log.d(pinver.TAG, "node " + i + " has touched!");
            }
        });
        dialog2.show();
    }
}
