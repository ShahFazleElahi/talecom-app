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
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
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

public class PinActivity extends Activity {
    private static final String TAG_SUCCESS = "success";
    private static final String[] distic = {"50", "100", "200", "500", "1000"};
    ViewDialog alert = new ViewDialog();
    /* developer by  sabbir */
    public String amount;
    ArrayList<HashMap<String, String>> arraylist;

    /* renamed from: bi */
    int f225bi = 0;
    private String cNumber;
    Dialog dialog;
    private EditText email_id;
    int flag = 0;

    /* renamed from: h */
    String f226h;
    private EditText hint;

    
    String f227id;
    Intent intent;
    JSONParser jsonParser = new JSONParser();
    JSONArray jsonarray;
    JSONObject jsonobject;
    JSONObject jsonobjects;
    ImageButton login;
    private boolean longClickActive = false;
    private final TextWatcher mTextEditorWatcher = new TextWatcher() {
        public void afterTextChanged(Editable editable) {
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            charSequence.length();
        }
    };

    /* textSize="13sp" */
    private com.softwarelab.mehedi.dialog f228md;
    private com.softwarelab.mehedi.dialog mdd;
    /* developer by  sabbir */

    
    public TextView f229mn;
    private String number;
    int offer = 0;
    int offercheck = 0;
    private String oid;
    ImageView opl;
    String opn;
    String optr;
    /* developer by  sabbir */
    public String orderid;
    private ProgressDialog pDialog;
    String paid;
    String paidtype;

    /* renamed from: pg */
    ProgressBar f230pg;
    /* developer by  sabbir */
    public EditText pin;
    private TextView pkgn;
    /* developer by  sabbir */
    public RadioButton radioButton;
    /* developer by  sabbir */
    public RadioGroup radioGroup;
    List<String> responseList = new ArrayList();
    private TextView txtam;
    String type;
    String type2;
    String type3;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.pin_layout);
        this.f229mn = (TextView) findViewById(R.id.number);
        this.pkgn = (TextView) findViewById(R.id.pkgname);
        this.txtam = (TextView) findViewById(R.id.amount);
        EditText editText = (EditText) findViewById(R.id.pin);
        this.pin = editText;
        editText.requestFocus();
        new Handler().postDelayed(new Runnable() {
            public void run() {
                EditText editText = (EditText) PinActivity.this.findViewById(R.id.pin);
                editText.dispatchTouchEvent(MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), 0, 0.0f, 0.0f, 0));
                editText.dispatchTouchEvent(MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), 1, 0.0f, 0.0f, 0));
            }
        }, 200);
        this.login = (ImageButton) findViewById(R.id.next);
        this.intent = getIntent();
        this.opl = (ImageView) findViewById(R.id.opera);
        this.type = this.intent.getExtras().getString("type");
        this.type2 = this.intent.getExtras().getString("type2");
        this.type3 = this.intent.getExtras().getString("type3");
        this.opn = this.intent.getExtras().getString("opt");
        this.optr = this.intent.getExtras().getString("opt2");
        if (this.intent.hasExtra("pkg")) {
            this.pkgn.setText(this.intent.getExtras().getString("pkg"));
            this.orderid = this.intent.getExtras().getString("id");
        } else {
            this.pkgn.setVisibility(View.GONE);
        }
        if (this.intent.hasExtra("paid")) {
            this.paidtype = this.intent.getExtras().getString("paid");
            RadioButton radioButton2 = (RadioButton) findViewById(R.id.prepaid);
            RadioButton radioButton3 = (RadioButton) findViewById(R.id.postpaid);
            RadioButton radioButton4 = (RadioButton) findViewById(R.id.skitto);
            if (this.paidtype.indexOf("Prepaid") >= 0) {
                radioButton2.setChecked(true);
            } else if (this.paidtype.indexOf("Postpaid") >= 0) {
                radioButton3.setChecked(true);
            }
            if (this.opn.indexOf("SK") >= 0) {
                radioButton4.setChecked(true);
            }
        }
        this.number = this.intent.getExtras().getString("number");
        this.amount = this.intent.getExtras().getString("amount");
        this.f229mn.setText(this.number);
        this.txtam.setText(this.amount);
        this.radioGroup = (RadioGroup) findViewById(R.id.typep);
        if (this.opn.equals("GP")) {
            this.opl.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.grameenphone));
        } else if (this.opn.equals("RB")) {
            this.opl.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.robi));
        } else if (this.opn.equals("BL")) {
            this.opl.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.banglalink));
        } else if (this.opn.equals("AT")) {
            this.opl.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.airtel));
        } else if (this.opn.equals("SK")) {
            this.opl.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.skitto));
        } else if (this.opn.equals("TT")) {
            this.opl.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.teletalk));
        }
        this.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == R.id.skitto) {
                    PinActivity.this.optr = "Skitto";
                    PinActivity.this.opn = "SK";
                    PinActivity.this.f225bi = 1;
                    PinActivity.this.opl.setImageDrawable(ContextCompat.getDrawable(PinActivity.this.getApplicationContext(), R.drawable.skitto));
                }
            }
        });
        this.login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String str;
                PinActivity pinActivity;
                PinActivity pinActivity2 = PinActivity.this;
                if (!pinActivity2.isOnline(pinActivity2)) {
                    Toast.makeText(PinActivity.this, "No network connection", Toast.LENGTH_LONG).show();
                } else if (PinActivity.this.f229mn.length() < 10) {
                    Toast.makeText(PinActivity.this, "Please Enter correct mobile number", Toast.LENGTH_LONG).show();
                } else {
                    int checkedRadioButtonId = PinActivity.this.radioGroup.getCheckedRadioButtonId();
                    if (checkedRadioButtonId == R.id.postpaid) {
                        pinActivity = PinActivity.this;
                        str = "Postpaid";
                    } else {
                        pinActivity = PinActivity.this;
                        str = "Prepaid";
                    }
                    pinActivity.paid = str;
                    if (checkedRadioButtonId == R.id.skitto) {
                        PinActivity.this.optr = "Skitto";
                        PinActivity.this.opn = "SK";
                        PinActivity.this.paid = "Skitto";
                    }
                    PinActivity pinActivity3 = PinActivity.this;
                    pinActivity3.f226h = pinActivity3.amount;
                    PinActivity.this.f229mn.getText().toString();
                    ViewDialog viewDialog = PinActivity.this.alert;
                    PinActivity pinActivity4 = PinActivity.this;
                    viewDialog.showDialog(pinActivity4, pinActivity4.intent.getExtras().getString("number"), PinActivity.this.intent.getExtras().getString("amount"), PinActivity.this.paid, PinActivity.this.opn);
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
            PinActivity.this.dialog = new Dialog(PinActivity.this);
            PinActivity.this.dialog.requestWindowFeature(1);
            PinActivity.this.dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
            PinActivity.this.dialog.setCancelable(false);
            PinActivity.this.dialog.setContentView(R.layout.custom_progress);
            PinActivity.this.dialog.show();
        }

        /* developer by  sabbir */
        public String doInBackground(String... strArr) {
            ArrayList arrayList = new ArrayList();
            String pref = PinActivity.getPref("phone", PinActivity.this.getApplicationContext());
            String pref2 = PinActivity.getPref("pass", PinActivity.this.getApplicationContext());
            String pref3 = PinActivity.getPref("pin", PinActivity.this.getApplicationContext());
            PinActivity pinActivity = PinActivity.this;
            RadioGroup unused = pinActivity.radioGroup = (RadioGroup) pinActivity.findViewById(R.id.typep);
            int checkedRadioButtonId = PinActivity.this.radioGroup.getCheckedRadioButtonId();
            PinActivity pinActivity2 = PinActivity.this;
            RadioButton unused2 = pinActivity2.radioButton = (RadioButton) pinActivity2.findViewById(checkedRadioButtonId);
            PinActivity pinActivity3 = PinActivity.this;
            pinActivity3.f226h = pinActivity3.amount;
            PinActivity pinActivity4 = PinActivity.this;
            pinActivity4.f227id = pinActivity4.f229mn.getText().toString();
            String obj = PinActivity.this.pin.getText().toString();
            String pref4 = PinActivity.getPref("token", PinActivity.this.getApplicationContext());
            String pref5 = PinActivity.getPref("device", PinActivity.this.getApplicationContext());
            arrayList.add(new BasicNameValuePair("username", pref));
            arrayList.add(new BasicNameValuePair("password", pref2));
            arrayList.add(new BasicNameValuePair("deviceid", pref5));
            arrayList.add(new BasicNameValuePair("token", pref4));
            arrayList.add(new BasicNameValuePair(NotificationCompat.CATEGORY_SERVICE, PinActivity.this.type3));
            arrayList.add(new BasicNameValuePair("item", PinActivity.this.type));
            if (PinActivity.this.intent.hasExtra("rol")) {
                if (PinActivity.this.intent.getExtras().getString("rol").indexOf("getdrive") >= 0) {
                    arrayList.add(new BasicNameValuePair("orderid", PinActivity.this.orderid));
                } else if (PinActivity.this.intent.getExtras().getString("rol").indexOf("getinternet") >= 0) {
                    arrayList.add(new BasicNameValuePair("orderid", PinActivity.this.orderid));
                } else {
                    arrayList.add(new BasicNameValuePair("orderid", "0"));
                }
            }
            arrayList.add(new BasicNameValuePair("drive", "2022"));
            if (checkedRadioButtonId == R.id.postpaid) {
                arrayList.add(new BasicNameValuePair("type", "2"));
            } else {
                arrayList.add(new BasicNameValuePair("type", DebugEventListener.PROTOCOL_VERSION));
            }
            if (PinActivity.this.intent.getExtras().getString("type2").indexOf("internet") >= 0) {
                arrayList.add(new BasicNameValuePair("pincode", PinActivity.this.intent.getExtras().getString("pin")));
                arrayList.add(new BasicNameValuePair("number", PinActivity.this.intent.getExtras().getString("number")));
                arrayList.add(new BasicNameValuePair("amount", PinActivity.this.intent.getExtras().getString("amount")));
            } else {
                arrayList.add(new BasicNameValuePair("pincode", obj));
                arrayList.add(new BasicNameValuePair("number", PinActivity.this.f227id));
                arrayList.add(new BasicNameValuePair("amount", PinActivity.this.f226h));
            }
            arrayList.add(new BasicNameValuePair("optn", PinActivity.this.opn));
            arrayList.add(new BasicNameValuePair("pin", pref3));
            JSONObject makeHttpRequest = null;
            try {
                makeHttpRequest = PinActivity.this.jsonParser.makeHttpRequest((PinActivity.getPref(ImagesContract.URL, PinActivity.this.getApplicationContext()) + "/apiapp/") + "" + "NewRequest", HttpPost.METHOD_NAME, arrayList);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            try {
                int i = makeHttpRequest.getInt("success");
                int i2 = makeHttpRequest.getInt("success");
                final String string = makeHttpRequest.getString("message");
                if (i2 == 0) {
                    PinActivity.this.flag = 0;
                    PinActivity.this.runOnUiThread(new Runnable() {
                        public void run() {
                            PinActivity.this.showError(PinActivity.this, string);
                        }
                    });
                }
                if (i == 1) {
                    PinActivity.this.flag = 0;
                } else {
                    PinActivity.this.flag = 1;
                }
                if (i2 != 1) {
                    return null;
                }
                PinActivity.this.flag = 0;
                PinActivity.this.runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(PinActivity.this, "Request Sent Successfull", Toast.LENGTH_LONG).show();
                        PinActivity.this.startActivity(new Intent(PinActivity.this.getApplicationContext(), Welcome.class));
                        PinActivity.this.finish();
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
            PinActivity.this.dialog.dismiss();
        }
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

    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, 17432579);
    }

    public class ViewDialog {
        boolean isStart;
        int progressBarValue = 0;
        Handler progressHandler = new Handler();

        public ViewDialog() {
        }

        public void showDialog(Activity activity, String str, String str2, String str3, String str4) {
            @SuppressLint("ResourceType") final Dialog dialog = new Dialog(activity, 2131886564);
            dialog.requestWindowFeature(1);
            dialog.setCancelable(true);
            dialog.setContentView(R.layout.confirm);
            ((TextView) dialog.findViewById(R.id.cnumber)).setText(str);
            ((TextView) dialog.findViewById(R.id.camount)).setText(str2);
            ((TextView) dialog.findViewById(R.id.ctype)).setText(str3);
            PinActivity.this.opl = (ImageView) dialog.findViewById(R.id.opera);
            if (str4.equals("GP")) {
                PinActivity.this.opl.setImageDrawable(ContextCompat.getDrawable(PinActivity.this.getApplicationContext(), R.drawable.grameenphone));
            } else if (str4.equals("RB")) {
                PinActivity.this.opl.setImageDrawable(ContextCompat.getDrawable(PinActivity.this.getApplicationContext(), R.drawable.robi));
            } else if (str4.equals("BL")) {
                PinActivity.this.opl.setImageDrawable(ContextCompat.getDrawable(PinActivity.this.getApplicationContext(), R.drawable.banglalink));
            } else if (str4.equals("AT")) {
                PinActivity.this.opl.setImageDrawable(ContextCompat.getDrawable(PinActivity.this.getApplicationContext(), R.drawable.airtel));
            } else if (str4.equals("SK")) {
                PinActivity.this.opl.setImageDrawable(ContextCompat.getDrawable(PinActivity.this.getApplicationContext(), R.drawable.skitto));
            } else if (str4.equals("TT")) {
                PinActivity.this.opl.setImageDrawable(ContextCompat.getDrawable(PinActivity.this.getApplicationContext(), R.drawable.teletalk));
            }
            final ProgressBar progressBar = (ProgressBar) dialog.findViewById(R.id.horizontal_progress_bar);
            progressBar.setProgressDrawable(new SeperatedProgressbar(PinActivity.this));
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

    @SuppressLint("WrongConstant")
    public void showKeyboard() {
        ((InputMethodManager) getSystemService("input_method")).toggleSoftInput(2, 0);
    }

    @SuppressLint("WrongConstant")
    public void closeKeyboard() {
        ((InputMethodManager) getSystemService("input_method")).toggleSoftInput(1, 0);
    }
}
