package com.softwarelab.mehedi;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.common.internal.ImagesContract;
import com.google.android.gms.measurement.api.AppMeasurementSdk;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.io.IOException;
import java.util.ArrayList;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

public class Addres extends Activity {
    private static final String TAG_SUCCESS = "success";
    ArrayAdapter aaa;
    /* developer by  sabbir */

    /* renamed from: bl */
    public EditText f154bl;
    private String cNumber;
    /* developer by  sabbir */

    
    public int f155dd;
    Dialog dialog;
    /* developer by  sabbir */
    public EditText ebirth;
    /* developer by  sabbir */
    public EditText email;
    private TextView err;
    int flag = 0;
    JSONParser jsonParser = new JSONParser();
    Button login;
    private dialogs mds;
    /* developer by  sabbir */

    /* renamed from: mm */
    public int f156mm;
    /* developer by  sabbir */
    public EditText name;
    /* developer by  sabbir */
    public EditText nick;
    /* developer by  sabbir */
    public EditText nid;
    /* developer by  sabbir */
    public EditText opin;
    private ProgressDialog pDialog;
    /* developer by  sabbir */
    public EditText pass;
    /* developer by  sabbir */
    public EditText pin;
    private RadioButton radioButton;
    private RadioGroup radioGroup;
    Button signin;
    String text;

    /* renamed from: yy */
    private int f157yy;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.add);
        getWindow().setSoftInputMode(2);
        Button button = (Button) findViewById(R.id.reg);
        String[] split = getPref("postlevel", getApplicationContext()).split("\",\"|\\[\"|\"\\]");
        this.ebirth = (EditText) findViewById(R.id.birth);
        this.nick = (EditText) findViewById(R.id.nick);
        this.email = (EditText) findViewById(R.id.email);
        this.pin = (EditText) findViewById(R.id.apin);
        this.name = (EditText) findViewById(R.id.aname);
        this.pass = (EditText) findViewById(R.id.apass);
        this.f154bl = (EditText) findViewById(R.id.phn);
        this.nid = (EditText) findViewById(R.id.nid);
        this.opin = (EditText) findViewById(R.id.opin);
        Spinner spinner = (Spinner) findViewById(R.id.lev);
        this.ebirth.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(Addres.this, 16973939, new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datePicker, int i, int i2, int i3) {
                        Addres.this.ebirth.setText(i3 + "-" + (i2 + 1) + "-" + i);
                    }
                }, 2000, Addres.this.f156mm, Addres.this.f155dd);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                datePickerDialog.show();
            }
        });
        getPref(FirebaseAnalytics.Param.LEVEL, getApplicationContext());
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, 17367048, split);
        this.aaa = arrayAdapter;
        if (arrayAdapter != null) {
            arrayAdapter.setDropDownViewResource(17367049);
            spinner.setAdapter(this.aaa);
        }
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onNothingSelected(AdapterView<?> adapterView) {
            }

            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long j) {
                Addres.this.text = adapterView.getItemAtPosition(i).toString();
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Addres addres = Addres.this;
                if (!addres.isOnline(addres)) {
                    Toast.makeText(Addres.this, "No network connection", Toast.LENGTH_LONG).show();
                } else if (Addres.this.nick.length() < 4) {
                    Toast.makeText(Addres.this, "Please Enter correct username", Toast.LENGTH_LONG).show();
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
            Addres.this.dialog = new Dialog(Addres.this);
            Addres.this.dialog.requestWindowFeature(1);
            Addres.this.dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
            Addres.this.dialog.setCancelable(false);
            Addres.this.dialog.setContentView(R.layout.custom_progress);
            Addres.this.dialog.show();
        }

        /* developer by  sabbir */
        public String doInBackground(String... strArr) {
            ArrayList arrayList = new ArrayList();
            String pref = Addres.getPref("token", Addres.this.getApplicationContext());
            String pref2 = Addres.getPref("device", Addres.this.getApplicationContext());
            String obj = Addres.this.nick.getText().toString();
            String obj2 = Addres.this.email.getText().toString();
            String obj3 = Addres.this.name.getText().toString();
            String obj4 = Addres.this.f154bl.getText().toString();
            String obj5 = Addres.this.pass.getText().toString();
            String obj6 = Addres.this.pin.getText().toString();
            String obj7 = Addres.this.opin.getText().toString();
            String obj8 = Addres.this.nid.getText().toString();
            String obj9 = Addres.this.ebirth.getText().toString();
            arrayList.add(new BasicNameValuePair(FirebaseAnalytics.Param.LEVEL, Addres.this.text));
            arrayList.add(new BasicNameValuePair("deviceid", pref2));
            arrayList.add(new BasicNameValuePair("token", pref));
            arrayList.add(new BasicNameValuePair("username", obj));
            arrayList.add(new BasicNameValuePair(AppMeasurementSdk.ConditionalUserProperty.NAME, obj3));
            arrayList.add(new BasicNameValuePair("email", obj2));
            arrayList.add(new BasicNameValuePair("pincode", obj7));
            arrayList.add(new BasicNameValuePair("phone", obj4));
            arrayList.add(new BasicNameValuePair("client_types", "16840"));
            arrayList.add(new BasicNameValuePair("password", obj5));
            arrayList.add(new BasicNameValuePair("nid", obj8));
            arrayList.add(new BasicNameValuePair("birth", obj9));
            arrayList.add(new BasicNameValuePair("resellerpin", obj6));
            JSONObject makeHttpRequest = null;
            try {
                makeHttpRequest = Addres.this.jsonParser.makeHttpRequest((Addres.getPref(ImagesContract.URL, Addres.this.getApplicationContext()) + "/apiapp/") + "reselleradd", HttpPost.METHOD_NAME, arrayList);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            String str = "success";
            try {
                int i = makeHttpRequest.getInt(str);
                int i2 = makeHttpRequest.getInt(str);
                final String string = makeHttpRequest.getString("message");
                if (i2 == 0) {
                    Addres.this.flag = 0;
                    Addres.this.runOnUiThread(new Runnable() {
                        public void run() {
                            Addres.this.showError(Addres.this, string);
                        }
                    });
                }
                if (i == 1) {
                    Addres.this.flag = 0;
                } else {
                    Addres.this.flag = 1;
                }
                if (i2 != 1) {
                    return null;
                }
                Addres.this.flag = 0;
                Addres.this.runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(Addres.this, string, Toast.LENGTH_LONG).show();
                        Addres.this.startActivity(new Intent(Addres.this.getApplicationContext(), Myreseller.class));
                        Addres.this.finish();
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
            Addres.this.dialog.dismiss();
            if (Addres.this.flag == 1) {
                Toast.makeText(Addres.this, "Please Enter Correct informations", Toast.LENGTH_LONG).show();
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

    public void onLoginClick(View view) {
        startActivity(new Intent(this, Welcome.class));
        overridePendingTransition(R.anim.slide_in_left, 17432579);
    }

    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, 17432579);
    }

    public void showError(Activity activity, String str) {
        @SuppressLint("ResourceType")
        Dialog dialog2 = new Dialog(activity, 2131886564);
        dialog2.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dialog2.requestWindowFeature(1);
        dialog2.setCancelable(true);
        dialog2.setContentView(R.layout.custom_dialog_view);
        ((TextView) dialog2.findViewById(R.id.dialogOpen)).setText(str);
        dialog2.show();
    }
}
