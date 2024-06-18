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
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.core.app.NotificationCompat;

import com.google.android.gms.common.internal.ImagesContract;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.io.IOException;
import java.util.ArrayList;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

public class Transfer extends Activity {
    private static final String TAG_SUCCESS = "success";
    private static final String[] distic = {"Recharge", "Bkash", "Rocket"};
    /* developer by  sabbir */

    
    public EditText f281am;
    private String cNumber;
    private TextView err;
    int flag = 0;
    JSONParser jsonParser = new JSONParser();
    Button login;

    /* textSize="13sp" */
    private dialog f282md;
    private dialog mdd;
    private dialogs mds;
    /* developer by  sabbir */
    public EditText msg;
    String opn;
    String optr;
    /* developer by  sabbir */
    public ProgressDialog pDialog;
    String paid;
    /* developer by  sabbir*/
    public EditText pin;
    private RadioButton radioButton;
    /* developer by  sabbir */
    public RadioGroup radioGroup;
    Button signin;
    String type;
    String type2;
    /* developer by  sabbir */
    public EditText username;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.transfer);
        this.username = (EditText) findViewById(R.id.username);
        this.f281am = (EditText) findViewById(R.id.amount);
        this.pin = (EditText) findViewById(R.id.pin);
        this.msg = (EditText) findViewById(R.id.msg);
        this.radioGroup = (RadioGroup) findViewById(R.id.bal);
        ((Button) findViewById(R.id.sub)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Transfer transfer = Transfer.this;
                if (!transfer.isOnline(transfer)) {
                    Toast.makeText(Transfer.this, "No network connection", Toast.LENGTH_LONG).show();
                    return;
                }
                Transfer.this.paid = "Transfer";
                Transfer.this.f281am.getText().toString();
                Transfer.this.username.getText().toString();
                new loginAccess().execute(new String[0]);
            }
        });
    }

    class loginAccess extends AsyncTask<String, String, String> {
        loginAccess() {
        }

        /* developer by  sabbir */
        public void onPreExecute() {
            super.onPreExecute();
            ProgressDialog unused = Transfer.this.pDialog = new ProgressDialog(Transfer.this);
            Transfer.this.pDialog.setMessage("please wait...");
            Transfer.this.pDialog.setIndeterminate(false);
            Transfer.this.pDialog.setCancelable(true);
            Transfer.this.pDialog.show();
        }

        /* developer by  sabbir */
        public String doInBackground(String... strArr) {
            ArrayList arrayList = new ArrayList();
            Transfer.getPref("phone", Transfer.this.getApplicationContext());
            Transfer.getPref("pass", Transfer.this.getApplicationContext());
            Transfer.getPref("pin", Transfer.this.getApplicationContext());
            String pref = Transfer.getPref("token", Transfer.this.getApplicationContext());
            String pref2 = Transfer.getPref("device", Transfer.this.getApplicationContext());
            Transfer transfer = Transfer.this;
            EditText unused = transfer.f281am = (EditText) transfer.findViewById(R.id.amount);
            String obj = Transfer.this.msg.getText().toString();
            String obj2 = Transfer.this.username.getText().toString();
            String obj3 = Transfer.this.f281am.getText().toString();
            String obj4 = Transfer.this.pin.getText().toString();
            arrayList.add(new BasicNameValuePair("deviceid", pref2));
            arrayList.add(new BasicNameValuePair("token", pref));
            arrayList.add(new BasicNameValuePair("amount", obj3));
            arrayList.add(new BasicNameValuePair("ucid", obj2));
            arrayList.add(new BasicNameValuePair(NotificationCompat.CATEGORY_MESSAGE, obj));
            arrayList.add(new BasicNameValuePair("item", "transfer"));
            arrayList.add(new BasicNameValuePair("type", "Transfer"));
            int checkedRadioButtonId = Transfer.this.radioGroup.getCheckedRadioButtonId();
            if (checkedRadioButtonId == R.id.main) {
                arrayList.add(new BasicNameValuePair(FirebaseAnalytics.Param.SOURCE, "main"));
            } else if (checkedRadioButtonId == R.id.drive) {
                arrayList.add(new BasicNameValuePair(FirebaseAnalytics.Param.SOURCE, "drive"));
            } else {
                arrayList.add(new BasicNameValuePair(FirebaseAnalytics.Param.SOURCE, "bank"));
            }
            arrayList.add(new BasicNameValuePair("pincode", obj4));
            JSONObject makeHttpRequest = null;
            try {
                makeHttpRequest = Transfer.this.jsonParser.makeHttpRequest((Transfer.getPref(ImagesContract.URL, Transfer.this.getApplicationContext()) + "/apiapp/") + "payment", HttpPost.METHOD_NAME, arrayList);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            try {
                int i = makeHttpRequest.getInt("success");
                int i2 = makeHttpRequest.getInt("success");
                final String string = makeHttpRequest.getString("message");
                if (i2 == 0) {
                    Transfer.this.flag = 0;
                    Transfer.this.runOnUiThread(new Runnable() {
                        public void run() {
                            Transfer.this.showError(Transfer.this, string);
                        }
                    });
                }
                if (i == 1) {
                    Transfer.this.flag = 0;
                } else {
                    Transfer.this.flag = 1;
                }
                if (i2 != 1) {
                    return null;
                }
                Transfer.this.flag = 0;
                Transfer.this.runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(Transfer.this, "successful", Toast.LENGTH_LONG).show();
                        Transfer.this.startActivity(new Intent(Transfer.this.getApplicationContext(), Myreseller.class));
                        Transfer.this.finish();
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
            Transfer.this.pDialog.dismiss();
            if (Transfer.this.flag == 1) {
                Toast.makeText(Transfer.this, "Please Enter Correct informations", Toast.LENGTH_LONG).show();
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

    public void SavePreferences(String str, String str2) {
        SharedPreferences.Editor edit = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit();
        edit.putString(str, str2);
        edit.commit();
    }

    public void setTextInTextView(String str) {
        this.err.setText(str);
    }

    public void onLoginClick(View view) {
        startActivity(new Intent(this, Welcome.class));
        overridePendingTransition(R.anim.slide_in_left, 17432579);
    }

    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, 17432579);
    }

    public void showError(Activity activity, String str) {
        @SuppressLint("ResourceType") Dialog dialog = new Dialog(activity, 2131886564);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dialog.requestWindowFeature(1);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.custom_dialog_view);
        ((TextView) dialog.findViewById(R.id.dialogOpen)).setText(str);
        dialog.show();
    }
}
