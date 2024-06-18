package com.softwarelab.mehedi;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

import java.io.IOException;
import java.util.ArrayList;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

public class Smssend extends Activity {
    private static final String TAG_SUCCESS = "success";
    private static final String[] distic = {"Recharge", "Bkash", "Rocket"};
    /* developer by  sabbir */

    
    public EditText f276am;
    private String cNumber;
    private EditText email_id;
    private TextView err;
    int flag = 0;
    private EditText hint;

    
    private String f277id;
    JSONParser jsonParser = new JSONParser();
    Button login;
    /* developer by  sabbir */

    /* textSize="13sp" */
    public dialog f278md;
    private dialog mdd;
    private dialogs mds;
    /* developer by  sabbir */

    
    public EditText f279mn;
    private String nicki;
    private String oldam;
    String opn;
    String optr;
    /* developer by  sabbir */
    public ProgressDialog pDialog;
    String paid;
    /* developer by  sabbir */
    public EditText pin;
    private RadioButton radioButton;
    private RadioGroup radioGroup;
    Button signin;
    String type;
    String type2;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.smsac);
        this.f279mn = (EditText) findViewById(R.id.number);
        this.pin = (EditText) findViewById(R.id.pin);
        ((Button) findViewById(R.id.sub)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Smssend smssend = Smssend.this;
                if (!smssend.isOnline(smssend)) {
                    Toast.makeText(Smssend.this, "No network connection", Toast.LENGTH_LONG).show();
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
            ProgressDialog unused = Smssend.this.pDialog = new ProgressDialog(Smssend.this);
            Smssend.this.pDialog.setMessage("please wait...");
            Smssend.this.pDialog.setIndeterminate(false);
            Smssend.this.pDialog.setCancelable(true);
            Smssend.this.pDialog.show();
        }

        /* developer by  sabbir */
        public String doInBackground(String... strArr) {
            ArrayList arrayList = new ArrayList();
            String pref = Smssend.getPref("phone", Smssend.this.getApplicationContext());
            String pref2 = Smssend.getPref("pass", Smssend.this.getApplicationContext());
            String pref3 = Smssend.getPref("pin", Smssend.this.getApplicationContext());
            Smssend smssend = Smssend.this;
            EditText unused = smssend.f276am = (EditText) smssend.findViewById(R.id.text);
            String obj = Smssend.this.f279mn.getText().toString();
            String obj2 = Smssend.this.f276am.getText().toString();
            String obj3 = Smssend.this.pin.getText().toString();
            String pref4 = Smssend.getPref("token", Smssend.this.getApplicationContext());
            String pref5 = Smssend.getPref("device", Smssend.this.getApplicationContext());
            String str = "pass";
            arrayList.add(new BasicNameValuePair("username", pref));
            arrayList.add(new BasicNameValuePair("password", pref2));
            arrayList.add(new BasicNameValuePair("deviceid", pref5));
            arrayList.add(new BasicNameValuePair("token", pref4));
            arrayList.add(new BasicNameValuePair("key", pref2));
            arrayList.add(new BasicNameValuePair("buy", "ok"));
            arrayList.add(new BasicNameValuePair(NotificationCompat.CATEGORY_MESSAGE, obj2));
            arrayList.add(new BasicNameValuePair("number", obj));
            arrayList.add(new BasicNameValuePair("item", "sms"));
            arrayList.add(new BasicNameValuePair("pincode", obj3));
            arrayList.add(new BasicNameValuePair("pin", pref3));
            JSONObject makeHttpRequest = null;
            try {
                makeHttpRequest = Smssend.this.jsonParser.makeHttpRequest((Smssend.getPref(ImagesContract.URL, Smssend.this.getApplicationContext()) + "/apiapp/") + "NewRequest", HttpPost.METHOD_NAME, arrayList);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            try {
                int i = makeHttpRequest.getInt("success");
                int i2 = makeHttpRequest.getInt(NotificationCompat.CATEGORY_STATUS);
                final String string = makeHttpRequest.getString("Message");
                makeHttpRequest.getString("cost");
                if (i2 == 0) {
                    Smssend.this.flag = 0;
                    Smssend.this.runOnUiThread(new Runnable() {
                        public void run() {
                            dialog unused = Smssend.this.f278md = new dialog(Smssend.this);
                            Smssend.this.f278md.build("Faild", string);
                        }
                    });
                }
                if (i == 1) {
                    Smssend.this.flag = 0;
                } else {
                    Smssend.this.flag = 1;
                    Smssend.this.SavePreferences("phone", "no");
                    Smssend.this.SavePreferences(str, "no");
                    Smssend.this.startActivity(new Intent(Smssend.this.getApplicationContext(), MainActivity.class));
                    Smssend.this.finish();
                }
                if (i2 != 1) {
                    return null;
                }
                Smssend.this.flag = 0;
                Smssend.this.runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(Smssend.this, "successful  Send sms ", Toast.LENGTH_LONG).show();
                        Smssend.this.startActivity(new Intent(Smssend.this.getApplicationContext(), Welcome.class));
                        Smssend.this.finish();
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
            Smssend.this.pDialog.dismiss();
            if (Smssend.this.flag == 1) {
                Toast.makeText(Smssend.this, "Please Enter Correct informations", Toast.LENGTH_LONG).show();
            }
        }
    }

    /* developer by  sabbir */
    public boolean isOnline(Context context) {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
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

    public class ViewDialog {
        public ViewDialog() {
        }

        public void showDialog(Activity activity, String str, String str2, String str3, String str4) {
            final Dialog dialog = new Dialog(activity);
            dialog.requestWindowFeature(1);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.confirm);
            ((TextView) dialog.findViewById(R.id.cnumber)).setText(str);
            ((TextView) dialog.findViewById(R.id.camount)).setText(str2);
            ((TextView) dialog.findViewById(R.id.ctype)).setText(str3);
            ((TextView) dialog.findViewById(R.id.cop)).setText(str4);
            ((Button) dialog.findViewById(R.id.btn_yes)).setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    new loginAccess().execute(new String[0]);
                    dialog.dismiss();
                }
            });
            ((Button) dialog.findViewById(R.id.btn_no)).setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });
            dialog.show();
        }
    }
}
