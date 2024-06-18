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
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.exifinterface.media.ExifInterface;
import com.google.android.gms.common.internal.ImagesContract;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.antlr.runtime.debug.DebugEventListener;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DisplayActivitya extends Activity {
    private static final String TAG_SUCCESS = "success";
    private static final String[] distic = {"Recharge", "Bkash", "Rocket"};
    /* developer by  sabbir */

    
    public EditText f169am;
    ArrayList<HashMap<String, String>> arraylist;
    private String cNumber;
    Dialog dialog;
    private EditText email_id;
    private TextView err;
    int flag = 0;
    private EditText hint;
    JSONParser jsonParser = new JSONParser();
    JSONArray jsonarray;
    JSONObject jsonobject;
    Button login;
    private com.softwarelab.mehedi.dialog mdd;

    
    TextView f170mn;
    private String nam;
    /* developer by  sabbir * */
    public EditText nid;
    String number;
    /* developer by  sabbir * */
    public EditText pin;
    /* developer by  sabbir * */
    public RadioButton radioButton;
    /* developer by  sabbir * */
    public RadioGroup radioGroup;
    List<String> responseList = new ArrayList();
    Button signin;
    String type;
    String type2;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.mobilebank);
//        Button button = (Button) findViewById(R.id.cont);
        this.f170mn = (TextView) findViewById(R.id.number);
        this.f169am = (EditText) findViewById(R.id.amount);
        this.pin = (EditText) findViewById(R.id.pin);
        final Intent intent = getIntent();
        this.type = intent.getExtras().getString("type");
        this.type2 = intent.getExtras().getString("type2");
        String string = intent.getExtras().getString("number");
        this.number = string;
        this.f170mn.setText(string);
        new Handler().postDelayed(new Runnable() {
            public void run() {
                EditText editText = (EditText) DisplayActivitya.this.findViewById(R.id.amount);
                editText.dispatchTouchEvent(MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), 0, 0.0f, 0.0f, 0));
                editText.dispatchTouchEvent(MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), 1, 0.0f, 0.0f, 0));
            }
        }, 200);
        ((Button) findViewById(R.id.sub)).setOnClickListener(new View.OnClickListener() {
            private String paid;

            public void onClick(View view) {
                DisplayActivitya displayActivitya = DisplayActivitya.this;
                if (!displayActivitya.isOnline(displayActivitya)) {
                    Toast.makeText(DisplayActivitya.this, "No network connection", Toast.LENGTH_LONG).show();
                } else if (DisplayActivitya.this.f170mn.length() < 10) {
                    Toast.makeText(DisplayActivitya.this, "Please Enter correct mobile number", Toast.LENGTH_LONG).show();
                } else {
                    DisplayActivitya displayActivitya2 = DisplayActivitya.this;
                    RadioGroup unused = displayActivitya2.radioGroup = (RadioGroup) displayActivitya2.findViewById(R.id.typep);
                    int checkedRadioButtonId = DisplayActivitya.this.radioGroup.getCheckedRadioButtonId();
                    if (checkedRadioButtonId == R.id.cashout) {
                        this.paid = "Cash Out";
                    }
                    if (checkedRadioButtonId == R.id.cashin) {
                        this.paid = "Cash In";
                    }
                    if (checkedRadioButtonId == R.id.send) {
                        this.paid = "Send money";
                    }
                    String obj = DisplayActivitya.this.f169am.getText().toString();
                    DisplayActivitya.this.f170mn.getText().toString();
                    new ViewDialog().showDialog(DisplayActivitya.this, intent.getExtras().getString("number"), obj, this.paid, DisplayActivitya.this.type);
                }
            }
        });
    }

    public void action(View view) {
        if (view.getId() == R.id.ten) {
            this.f169am.setText("10");
        }
        if (view.getId() == R.id.twenty) {
            this.f169am.setText("20");
        }
        if (view.getId() == R.id.fifty) {
            this.f169am.setText("50");
        }
        if (view.getId() == R.id.hundread) {
            this.f169am.setText("100");
        }
    }

    class loginAccess extends AsyncTask<String, String, String> {
        loginAccess() {
        }

        /* developer by  sabbir * */
        public void onPreExecute() {
            super.onPreExecute();
            DisplayActivitya.this.dialog = new Dialog(DisplayActivitya.this);
            DisplayActivitya.this.dialog.requestWindowFeature(1);
            DisplayActivitya.this.dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
            DisplayActivitya.this.dialog.setCancelable(false);
            DisplayActivitya.this.dialog.setContentView(R.layout.custom_progress);
            DisplayActivitya.this.dialog.show();
        }

        /* developer by  sabbir * */
        public String doInBackground(String... strArr) {
            ArrayList arrayList = new ArrayList();
            String pref = DisplayActivitya.getPref("phone", DisplayActivitya.this.getApplicationContext());
            String pref2 = DisplayActivitya.getPref("pass", DisplayActivitya.this.getApplicationContext());
            String pref3 = DisplayActivitya.getPref("pin", DisplayActivitya.this.getApplicationContext());
            DisplayActivitya displayActivitya = DisplayActivitya.this;
            EditText unused = displayActivitya.f169am = (EditText) displayActivitya.findViewById(R.id.amount);
            DisplayActivitya displayActivitya2 = DisplayActivitya.this;
            EditText unused2 = displayActivitya2.nid = (EditText) displayActivitya2.findViewById(R.id.nid);
            DisplayActivitya displayActivitya3 = DisplayActivitya.this;
            RadioGroup unused3 = displayActivitya3.radioGroup = (RadioGroup) displayActivitya3.findViewById(R.id.typep);
            int checkedRadioButtonId = DisplayActivitya.this.radioGroup.getCheckedRadioButtonId();
            DisplayActivitya displayActivitya4 = DisplayActivitya.this;
            RadioButton unused4 = displayActivitya4.radioButton = (RadioButton) displayActivitya4.findViewById(checkedRadioButtonId);
            String obj = DisplayActivitya.this.f169am.getText().toString();
            String charSequence = DisplayActivitya.this.f170mn.getText().toString();
            String obj2 = DisplayActivitya.this.pin.getText().toString();
            String pref4 = DisplayActivitya.getPref("token", DisplayActivitya.this.getApplicationContext());
            String pref5 = DisplayActivitya.getPref("device", DisplayActivitya.this.getApplicationContext());
            arrayList.add(new BasicNameValuePair("username", pref));
            arrayList.add(new BasicNameValuePair("password", pref2));
            arrayList.add(new BasicNameValuePair("deviceid", pref5));
            arrayList.add(new BasicNameValuePair("token", pref4));
            arrayList.add(new BasicNameValuePair("amount", obj));
            arrayList.add(new BasicNameValuePair("number", charSequence));
            arrayList.add(new BasicNameValuePair(NotificationCompat.CATEGORY_SERVICE, DisplayActivitya.this.type));
            arrayList.add(new BasicNameValuePair("item", DisplayActivitya.this.type));
            if (checkedRadioButtonId == R.id.cashout) {
                arrayList.add(new BasicNameValuePair("type", "2"));
            }
            if (checkedRadioButtonId == R.id.cashin) {
                arrayList.add(new BasicNameValuePair("type", DebugEventListener.PROTOCOL_VERSION));
            }
            if (checkedRadioButtonId == R.id.send) {
                arrayList.add(new BasicNameValuePair("type", ExifInterface.GPS_MEASUREMENT_3D));
            }
            arrayList.add(new BasicNameValuePair("pincode", obj2));
            arrayList.add(new BasicNameValuePair("pin", pref3));
            JSONObject makeHttpRequest = null;
            try {
                makeHttpRequest = DisplayActivitya.this.jsonParser.makeHttpRequest((DisplayActivitya.getPref(ImagesContract.URL, DisplayActivitya.this.getApplicationContext()) + "/apiapp/") + "NewRequest", HttpPost.METHOD_NAME, arrayList);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            try {
                int i = makeHttpRequest.getInt("success");
                int i2 = makeHttpRequest.getInt("success");
                final String string = makeHttpRequest.getString("message");
                if (i2 == 0) {
                    DisplayActivitya.this.flag = 0;
                    DisplayActivitya.this.runOnUiThread(new Runnable() {
                        public void run() {
                            DisplayActivitya.this.showError(DisplayActivitya.this, string);
                        }
                    });
                }
                if (i == 1) {
                    DisplayActivitya.this.flag = 0;
                } else {
                    DisplayActivitya.this.flag = 1;
                }
                if (i2 != 1) {
                    return null;
                }
                DisplayActivitya.this.flag = 0;
                DisplayActivitya.this.runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(DisplayActivitya.this, string, Toast.LENGTH_LONG).show();
                        DisplayActivitya.this.startActivity(new Intent(DisplayActivitya.this.getApplicationContext(), Welcome.class));
                        DisplayActivitya.this.finish();
                    }
                });
                return null;
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }
        }

        /* developer by  sabbir * */
        public void onPostExecute(String str) {
            DisplayActivitya.this.dialog.dismiss();
            if (DisplayActivitya.this.flag == 1) {
                Toast.makeText(DisplayActivitya.this, "Please Enter Correct informations", Toast.LENGTH_LONG).show();
            }
        }
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

    /* developer by  sabbir * */
    public boolean isOnline(Context context) {
        @SuppressLint("WrongConstant")
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
        boolean isStart;
        int progressBarValue = 0;
        Handler progressHandler = new Handler();

        public ViewDialog() {
        }

        public void showDialog(Activity activity, String str, String str2, String str3, String str4) {
            @SuppressLint("ResourceType")
            final Dialog dialog = new Dialog(activity, 2131886564);
            dialog.requestWindowFeature(1);
            dialog.setCancelable(true);
            dialog.setContentView(R.layout.confirm2);
            ((TextView) dialog.findViewById(R.id.cnumber)).setText(str);
            ((TextView) dialog.findViewById(R.id.camount)).setText(str2);
            ((TextView) dialog.findViewById(R.id.ctype)).setText(str3);
            ImageView imageView = (ImageView) dialog.findViewById(R.id.opera);
            if (str4.indexOf("131072") >= 0) {
                imageView.setImageDrawable(ContextCompat.getDrawable(DisplayActivitya.this.getApplicationContext(), R.drawable.nogad));
            } else if (str4.indexOf("256") >= 0) {
                imageView.setImageDrawable(ContextCompat.getDrawable(DisplayActivitya.this.getApplicationContext(), R.drawable.rocket));
            } else if (str4.indexOf("128") >= 0) {
                imageView.setImageDrawable(ContextCompat.getDrawable(DisplayActivitya.this.getApplicationContext(), R.drawable.bkash));
            } else if (str4.indexOf("1048576") >= 0) {
                imageView.setImageDrawable(ContextCompat.getDrawable(DisplayActivitya.this.getApplicationContext(), R.drawable.upay));
            } else if (str4.indexOf("1024") >= 0) {
                imageView.setImageDrawable(ContextCompat.getDrawable(DisplayActivitya.this.getApplicationContext(), R.drawable.mcash));
            } else if (str4.indexOf("2048") >= 0) {
                imageView.setImageDrawable(ContextCompat.getDrawable(DisplayActivitya.this.getApplicationContext(), R.drawable.ucash));
            } else if (str4.indexOf("4096") >= 0) {
                imageView.setImageDrawable(ContextCompat.getDrawable(DisplayActivitya.this.getApplicationContext(), R.drawable.mycash));
            } else if (str4.indexOf("32768") >= 0) {
                imageView.setImageDrawable(ContextCompat.getDrawable(DisplayActivitya.this.getApplicationContext(), R.drawable.surecash));
            }
            final ProgressBar progressBar = (ProgressBar) dialog.findViewById(R.id.horizontal_progress_bar);
            progressBar.setProgressDrawable(new SeperatedProgressbar(DisplayActivitya.this));
            ((RelativeLayout) dialog.findViewById(R.id.tap)).setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    int action = motionEvent.getAction();
                    if (action == 0) {
                        ViewDialog.this.isStart = true;
                        return true;
                    } else if (action != 1) {
                        return false;
                    } else {
                        ViewDialog.this.isStart = false;
                        return true;
                    }
                }
            });
            Handler r3 = new Handler() {
                public void handleMessage(Message message) {
                    if (ViewDialog.this.isStart) {
                        ViewDialog.this.progressBarValue++;
                    } else {
                        ViewDialog.this.progressBarValue = 0;
                    }
                    progressBar.setProgress(ViewDialog.this.progressBarValue);
                    if (ViewDialog.this.progressBarValue == 100) {
                        dialog.dismiss();
                        new loginAccess().execute(new String[0]);
                    }
                    ViewDialog.this.progressHandler.sendEmptyMessageDelayed(0, 1);
                }
            };
            this.progressHandler = r3;
            r3.sendEmptyMessage(0);
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
}
