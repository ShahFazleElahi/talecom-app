package com.softwarelab.mehedi;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.core.app.NotificationCompat;
import com.google.android.gms.common.internal.ImagesContract;
import com.google.android.gms.measurement.api.AppMeasurementSdk;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.antlr.runtime.debug.DebugEventListener;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Banktransfer extends Activity {
    private static final String TAG_SUCCESS = "success";
    private static final String[] distic = {"Palli Bidyut(Prepaid)", "Desco(Prepaid)", "DPDC(prepaid)", "PBDB(prepaid) "};
    /* developer by  sabbir */
    public EditText amount;
    ArrayList<HashMap<String, String>> arraylist;
    String balanc;
    /* developer by  sabbir */
    public EditText branch;
    private String cNumber;
    Dialog dialog;
    private TextView err;
    int flag = 0;
    JSONParser jsonParser = new JSONParser();
    JSONArray jsonarray;
    JSONObject jsonobject;
    Button login;
    private com.softwarelab.mehedi.dialog mdd;
    String nam;
    /* developer by  sabbir */
    public EditText name;
    /* developer by  sabbir */
    public EditText nuumber;
    /* developer by  sabbir */
    public EditText pin;
    private RadioButton radioButton;
    private RadioGroup radioGroup;
    /* developer by  sabbir*/
    public EditText remark;
    List<String> responseList = new ArrayList();
    String samount;
    String sbranch;
    Button signin;
    String sname;
    String snumber;
    Spinner spinner2;
    String sremarks;
    String text;
    String type;
    String type2;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.banktransfer);
        this.spinner2 = (Spinner) findViewById(R.id.flot);
        this.name = (EditText) findViewById(R.id.name);
        this.nuumber = (EditText) findViewById(R.id.number);
        this.amount = (EditText) findViewById(R.id.amount);
        this.pin = (EditText) findViewById(R.id.pin);
        this.branch = (EditText) findViewById(R.id.branch);
        this.remark = (EditText) findViewById(R.id.remark);
        new DownloadJSONy().execute(new Void[0]);
        this.spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onNothingSelected(AdapterView<?> adapterView) {
            }

            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long j) {
                Banktransfer.this.text = adapterView.getItemAtPosition(i).toString();
            }
        });
        ((Button) findViewById(R.id.sub)).setOnClickListener(new View.OnClickListener() {
            private String paid;

            public void onClick(View view) {
                Banktransfer banktransfer = Banktransfer.this;
                banktransfer.snumber = banktransfer.nuumber.getText().toString();
                Banktransfer banktransfer2 = Banktransfer.this;
                banktransfer2.samount = banktransfer2.amount.getText().toString();
                Banktransfer banktransfer3 = Banktransfer.this;
                banktransfer3.sname = banktransfer3.name.getText().toString();
                Banktransfer banktransfer4 = Banktransfer.this;
                banktransfer4.sbranch = banktransfer4.branch.getText().toString();
                Banktransfer banktransfer5 = Banktransfer.this;
                banktransfer5.sremarks = banktransfer5.remark.getText().toString();
                Banktransfer banktransfer6 = Banktransfer.this;
                if (!banktransfer6.isOnline(banktransfer6)) {
                    Toast.makeText(Banktransfer.this, "No network connection", Toast.LENGTH_LONG).show();
                } else if (Banktransfer.this.snumber.length() < 5) {
                    Toast.makeText(Banktransfer.this, "Please Enter correct AC number", Toast.LENGTH_LONG).show();
                } else {
                    ViewDialog viewDialog = new ViewDialog();
                    Banktransfer banktransfer7 = Banktransfer.this;
                    viewDialog.showDialog(banktransfer7, banktransfer7.sname, Banktransfer.this.samount, Banktransfer.this.sbranch, Banktransfer.this.text);
                }
            }
        });
    }

    private class DownloadJSONy extends AsyncTask<Void, Void, Void> {
        private ImageButton retry;

        private DownloadJSONy() {
        }

        /* developer by  sabbir */
        public void onPreExecute() {
            super.onPreExecute();
            Banktransfer.this.dialog = new Dialog(Banktransfer.this);
            Banktransfer.this.dialog.requestWindowFeature(1);
            Banktransfer.this.dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
            Banktransfer.this.dialog.setCancelable(false);
            Banktransfer.this.dialog.setContentView(R.layout.custom_progress);
            Banktransfer.this.dialog.show();
            Banktransfer banktransfer = Banktransfer.this;
            if (!banktransfer.isOnline(banktransfer)) {
                Banktransfer.this.findViewById(R.id.progressbar).setVisibility(View.GONE);
                Banktransfer.this.finish();
            }
        }

        /* developer by  sabbir */
        public Void doInBackground(Void... voidArr) {
            Banktransfer banktransfer = Banktransfer.this;
            if (!banktransfer.isOnline(banktransfer)) {
                return null;
            }
            String pref = Banktransfer.getPref("token", Banktransfer.this.getApplicationContext());
            String pref2 = Banktransfer.getPref("device", Banktransfer.this.getApplicationContext());
            Banktransfer.this.arraylist = new ArrayList<>();
            Banktransfer.this.jsonobject = JSONfunctions.getJSONfromURL((Banktransfer.getPref(ImagesContract.URL, Banktransfer.this.getApplicationContext()) + "/apiapp/") + "banklist?token=" + URLEncoder.encode(pref) + "&deviceid=" + URLEncoder.encode(pref2));
            try {
                Banktransfer banktransfer2 = Banktransfer.this;
                banktransfer2.jsonarray = banktransfer2.jsonobject.getJSONArray("bmtelbd");
                Log.d("Create Response", Banktransfer.this.jsonarray.toString());
                for (int i = 0; i < Banktransfer.this.jsonarray.length(); i++) {
                    HashMap hashMap = new HashMap();
                    Banktransfer banktransfer3 = Banktransfer.this;
                    banktransfer3.jsonobject = banktransfer3.jsonarray.getJSONObject(i);
                    Banktransfer banktransfer4 = Banktransfer.this;
                    banktransfer4.nam = banktransfer4.jsonobject.getString(AppMeasurementSdk.ConditionalUserProperty.NAME);
                    Banktransfer.this.responseList.add(Banktransfer.this.nam);
                    Banktransfer.this.arraylist.add(hashMap);
                }
                return null;
            } catch (JSONException e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
                return null;
            }
        }

        /* developer by  sabbir */
        @SuppressLint("ResourceType")
        public void onPostExecute(Void voidR) {
            Banktransfer banktransfer = Banktransfer.this;
            if (banktransfer.isOnline(banktransfer)) {
                Banktransfer.this.dialog.dismiss();
                Banktransfer banktransfer2 = Banktransfer.this;
                ArrayAdapter arrayAdapter = new ArrayAdapter(banktransfer2, 17367048, banktransfer2.responseList);
                arrayAdapter.setDropDownViewResource(17367049);
                Banktransfer.this.spinner2.setAdapter(arrayAdapter);
            }
        }
    }

    class loginAccess extends AsyncTask<String, String, String> {
        loginAccess() {
        }

        /* developer by  sabbir */
        public void onPreExecute() {
            super.onPreExecute();
            Banktransfer.this.dialog = new Dialog(Banktransfer.this);
            Banktransfer.this.dialog.requestWindowFeature(1);
            Banktransfer.this.dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
            Banktransfer.this.dialog.setCancelable(false);
            Banktransfer.this.dialog.setContentView(R.layout.custom_progress);
            Banktransfer.this.dialog.show();
        }

        /* developer by  sabbir */
        public String doInBackground(String... strArr) {
            ArrayList arrayList = new ArrayList();
            String pref = Banktransfer.getPref("phone", Banktransfer.this.getApplicationContext());
            String pref2 = Banktransfer.getPref("pass", Banktransfer.this.getApplicationContext());
            String pref3 = Banktransfer.getPref("pin", Banktransfer.this.getApplicationContext());
            String obj = Banktransfer.this.pin.getText().toString();
            String pref4 = Banktransfer.getPref("token", Banktransfer.this.getApplicationContext());
            String pref5 = Banktransfer.getPref("device", Banktransfer.this.getApplicationContext());
            arrayList.add(new BasicNameValuePair("username", pref));
            arrayList.add(new BasicNameValuePair("password", pref2));
            arrayList.add(new BasicNameValuePair("deviceid", pref5));
            arrayList.add(new BasicNameValuePair("token", pref4));
            arrayList.add(new BasicNameValuePair("key", pref2));
            arrayList.add(new BasicNameValuePair("amount", Banktransfer.this.samount));
            arrayList.add(new BasicNameValuePair("bank_name", Banktransfer.this.text));
            arrayList.add(new BasicNameValuePair("note", Banktransfer.this.sremarks));
            arrayList.add(new BasicNameValuePair("number", Banktransfer.this.snumber));
            arrayList.add(new BasicNameValuePair("area", Banktransfer.this.sbranch));
            arrayList.add(new BasicNameValuePair("holdername", Banktransfer.this.sname));
            arrayList.add(new BasicNameValuePair("pinn", obj));
            arrayList.add(new BasicNameValuePair("pin", pref3));
            arrayList.add(new BasicNameValuePair(NotificationCompat.CATEGORY_SERVICE, "32"));
            arrayList.add(new BasicNameValuePair("item", Banktransfer.this.type));
            arrayList.add(new BasicNameValuePair("type", DebugEventListener.PROTOCOL_VERSION));
            JSONObject makeHttpRequest = null;
            try {
                makeHttpRequest = Banktransfer.this.jsonParser.makeHttpRequest((Banktransfer.getPref(ImagesContract.URL, Banktransfer.this.getApplicationContext()) + "/apiapp/") + "bank", HttpPost.METHOD_NAME, arrayList);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            try {
                int i = makeHttpRequest.getInt("success");
                int i2 = makeHttpRequest.getInt("success");
                Banktransfer.this.balanc = makeHttpRequest.getString("message");
                if (i2 == 0) {
                    Banktransfer.this.flag = 0;
                    Banktransfer.this.runOnUiThread(new Runnable() {
                        public void run() {
                            Banktransfer.this.showError(Banktransfer.this, Banktransfer.this.balanc);
                        }
                    });
                }
                if (i == 1) {
                    Banktransfer.this.flag = 0;
                } else {
                    Banktransfer.this.flag = 1;
                }
                if (i2 != 1) {
                    return null;
                }
                Banktransfer.this.flag = 0;
                Banktransfer.this.runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(Banktransfer.this, Banktransfer.this.balanc, Toast.LENGTH_LONG).show();
                        Banktransfer.this.startActivity(new Intent(Banktransfer.this.getApplicationContext(), Welcome.class));
                        Banktransfer.this.finish();
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
            Banktransfer.this.dialog.dismiss();
            if (Banktransfer.this.flag == 1) {
                Toast.makeText(Banktransfer.this, "Please Enter Correct informations", Toast.LENGTH_LONG).show();
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
            dialog.setContentView(R.layout.confirmbank);
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

    public void onLoginClick(View view) {
        startActivity(new Intent(this, Welcome.class));
        overridePendingTransition(R.anim.slide_in_left, 17432579);
    }

    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, 17432579);
    }

    public void showError(Activity activity, String str) {
        @SuppressLint("ResourceType") Dialog dialog2 = new Dialog(activity, 2131886564);
        dialog2.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dialog2.requestWindowFeature(1);
        dialog2.setCancelable(true);
        dialog2.setContentView(R.layout.custom_dialog_view);
        ((TextView) dialog2.findViewById(R.id.dialogOpen)).setText(str);
        dialog2.show();
    }
}
