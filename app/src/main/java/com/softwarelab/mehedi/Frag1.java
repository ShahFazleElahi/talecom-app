package com.softwarelab.mehedi;

import android.annotation.SuppressLint;
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
import android.os.Handler;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.common.internal.ImagesContract;
import com.squareup.picasso.Picasso;

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

public class Frag1 extends Fragment {
    private static final String TAG_SUCCESS = "success";
    private static final String[] distic = {"50", "100", "200", "500", "1000"};
    /* developer by  sabbir */

    
    public EditText f180am;
    ArrayList<HashMap<String, String>> arraylist;

    /* renamed from: bi */
    int f181bi = 0;
    private String cNumber;
    private EditText email_id;
    private TextView err;
    int flag = 0;

    /* renamed from: h */
    String f182h;
    private EditText hint;

    
    String f183id;
    Intent intent;
    JSONParser jsonParser = new JSONParser();
    JSONArray jsonarray;
    JSONObject jsonobject;
    JSONObject jsonobjects;
    ImageButton login;
    private final TextWatcher mTextEditorWatcher = new TextWatcher() {
        public void afterTextChanged(Editable editable) {
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            charSequence.length();
        }
    };
    /* developer by  sabbir */

    /* textSize="13sp" */
    public dialog f184md;
    private dialog mdd;
    /* developer by  sabbir */
    public dialogs mds;
    /* developer by  sabbir */

    
    public TextView f185mn;
    String nam;
    /* developer by  sabbir */
    public String number;
    int offer = 0;
    int offercheck = 0;
    private String oid;
    ImageView opl;
    String opn;
    String optr;
    /* developer by  sabbir */
    public ProgressDialog pDialog;
    String paid;

    /* renamed from: pg */
    ProgressBar f186pg;
    /* developer by  sabbir */
    public EditText pin;

    
//    private TextView f187pp;
    private RadioButton radioButton;
    /* developer by  sabbir */
    public RadioGroup radioGroup;
    List<String> responseList = new ArrayList();
    String type;
    String type2;
    String type3;

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.frag1_layout, viewGroup, false);
        this.login = (ImageButton) inflate.findViewById(R.id.next);
        TextView textView = (TextView) inflate.findViewById(R.id.ten);
        TextView textView2 = (TextView) inflate.findViewById(R.id.twenty);
        TextView textView3 = (TextView) inflate.findViewById(R.id.fifty);
        TextView textView4 = (TextView) inflate.findViewById(R.id.hundread);
        this.f180am = (EditText) inflate.findViewById(R.id.amount);
        this.f186pg = (ProgressBar) ((DisplayActivity) getContext()).findViewById(R.id.wait);
//        this.f187pp = (TextView) inflate.findViewById(R.id.pp);
        EditText editText = (EditText) inflate.findViewById(R.id.amount);
        this.f180am = editText;
        editText.requestFocus();
        new Handler().postDelayed(new Runnable() {
            public void run() {
                Frag1.this.f180am.dispatchTouchEvent(MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), 0, 0.0f, 0.0f, 0));
                Frag1.this.f180am.dispatchTouchEvent(MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), 1, 0.0f, 0.0f, 0));
            }
        }, 200);
        this.f185mn = (TextView) ((DisplayActivity) getContext()).findViewById(R.id.number);
        this.intent = getActivity().getIntent();
        this.opl = (ImageView) ((DisplayActivity) getContext()).findViewById(R.id.opera);
        this.type = this.intent.getExtras().getString("type");
        this.type2 = this.intent.getExtras().getString("type2");
        this.type3 = this.intent.getExtras().getString("type3");
        this.opn = this.intent.getExtras().getString("opt");
        this.optr = this.intent.getExtras().getString("opt2");
        this.number = this.intent.getExtras().getString("number");
        this.radioGroup = (RadioGroup) ((DisplayActivity) getContext()).findViewById(R.id.typep);
        this.intent.hasExtra("img");
        if (this.intent.getExtras().getString("type2").indexOf("internet") >= 0) {
            new ViewDialog().showDialog(getActivity(), this.intent.getExtras().getString("number"), this.intent.getExtras().getString("amount"), this.type2, this.optr);
        }
        this.intent.hasExtra("img");
        textView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Frag1.this.f180am.setText("150");
            }
        });
        textView2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Frag1.this.f180am.setText("20");
            }
        });
        textView3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Frag1.this.f180am.setText("50");
            }
        });
        textView4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Frag1.this.f180am.setText("100");
            }
        });
        this.opl.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(Frag1.this.getActivity().getBaseContext(), Operator.class);
                intent.putExtra("type", Frag1.this.type);
                intent.putExtra("type3", Frag1.this.type3);
                intent.putExtra("type2", Frag1.this.type2);
                intent.putExtra("number", Frag1.this.number);
                Frag1.this.startActivity(intent);
            }
        });
        this.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == R.id.skitto) {
                    Frag1.this.optr = "Skitto";
                    Frag1.this.opn = "SK";
                    Frag1.this.f181bi = 1;
                    new Getop().execute(new Void[0]);
                }
            }
        });
        if (this.opn.equals("GP")) {
            this.opl.setImageDrawable(ContextCompat.getDrawable(getActivity().getBaseContext(), R.drawable.grameenphone));
        } else if (this.opn.equals("RB")) {
            this.opl.setImageDrawable(ContextCompat.getDrawable(getActivity().getBaseContext(), R.drawable.robi));
        } else if (this.opn.equals("BL")) {
            this.opl.setImageDrawable(ContextCompat.getDrawable(getActivity().getBaseContext(), R.drawable.banglalink));
        } else if (this.opn.equals("AT")) {
            this.opl.setImageDrawable(ContextCompat.getDrawable(getActivity().getBaseContext(), R.drawable.airtel));
        } else if (this.opn.equals("SK")) {
            this.opl.setImageDrawable(ContextCompat.getDrawable(getActivity().getBaseContext(), R.drawable.skitto));
        } else if (this.opn.equals("TT")) {
            this.opl.setImageDrawable(ContextCompat.getDrawable(getActivity().getBaseContext(), R.drawable.teletalk));
        }
        this.login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String str;
                Frag1 frag1;
                Frag1 frag12 = Frag1.this;
                if (!frag12.isOnline(frag12.getActivity().getBaseContext())) {
                    Toast.makeText(Frag1.this.getActivity().getBaseContext(), "No network connection", Toast.LENGTH_LONG).show();
                } else if (Frag1.this.f185mn.length() < 10) {
                    Toast.makeText(Frag1.this.getActivity().getBaseContext(), "Please Enter correct mobile number", Toast.LENGTH_LONG).show();
                } else {
                    int checkedRadioButtonId = Frag1.this.radioGroup.getCheckedRadioButtonId();
                    if (checkedRadioButtonId == R.id.postpaid) {
                        frag1 = Frag1.this;
                        str = "Postpaid";
                    } else {
                        frag1 = Frag1.this;
                        str = "Prepaid";
                    }
                    frag1.paid = str;
                    if (checkedRadioButtonId == R.id.skitto) {
                        Frag1.this.optr = "Skitto";
                        Frag1.this.opn = "SK";
                    }
                    Intent intent = new Intent(Frag1.this.getActivity().getBaseContext(), PinActivity.class);
                    intent.putExtra("paid", Frag1.this.paid);
                    intent.putExtra("opt", Frag1.this.opn);
                    intent.putExtra("opt2", Frag1.this.optr);
                    intent.putExtra("type", Frag1.this.type);
                    intent.putExtra("amount", Frag1.this.f180am.getText().toString());
                    intent.putExtra("number", Frag1.this.number);
                    intent.putExtra("type3", Frag1.this.type3);
                    intent.putExtra("type2", Frag1.this.type2);
                    Frag1.this.startActivity(intent);
                    Frag1.this.getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.stay);
                }
            }
        });
        return inflate;
    }

    class loginAccess extends AsyncTask<String, String, String> {
        loginAccess() {
        }

        /* developer by  sabbir */
        public void onPreExecute() {
            super.onPreExecute();
            ProgressDialog unused = Frag1.this.pDialog = new ProgressDialog(Frag1.this.getActivity().getBaseContext());
            Frag1.this.pDialog.setMessage("please wait...");
            Frag1.this.pDialog.setIndeterminate(false);
            Frag1.this.pDialog.setCancelable(false);
            Frag1.this.pDialog.show();
        }

        /* developer by  sabbir */
        public String doInBackground(String... strArr) {
            ArrayList arrayList = new ArrayList();
            String pref = Frag1.getPref("phone", Frag1.this.getActivity().getBaseContext());
            String pref2 = Frag1.getPref("pass", Frag1.this.getActivity().getBaseContext());
            String pref3 = Frag1.getPref("pin", Frag1.this.getActivity().getBaseContext());
            int checkedRadioButtonId = Frag1.this.radioGroup.getCheckedRadioButtonId();
            Frag1 frag1 = Frag1.this;
            frag1.f182h = frag1.f180am.getText().toString();
            Frag1 frag12 = Frag1.this;
            frag12.f183id = frag12.f185mn.getText().toString();
            String obj = Frag1.this.pin.getText().toString();
            String pref4 = Frag1.getPref("token", Frag1.this.getActivity().getBaseContext());
            String pref5 = Frag1.getPref("device", Frag1.this.getActivity().getBaseContext());
            arrayList.add(new BasicNameValuePair("username", pref));
            arrayList.add(new BasicNameValuePair("password", pref2));
            arrayList.add(new BasicNameValuePair("deviceid", pref5));
            arrayList.add(new BasicNameValuePair("token", pref4));
            arrayList.add(new BasicNameValuePair(NotificationCompat.CATEGORY_SERVICE, Frag1.this.type3));
            arrayList.add(new BasicNameValuePair("item", Frag1.this.type));
            if (checkedRadioButtonId == R.id.postpaid) {
                arrayList.add(new BasicNameValuePair("type", "2"));
            } else {
                arrayList.add(new BasicNameValuePair("type", DebugEventListener.PROTOCOL_VERSION));
            }
            if (Frag1.this.intent.getExtras().getString("type2").indexOf("internet") >= 0) {
                arrayList.add(new BasicNameValuePair("pincode", Frag1.this.intent.getExtras().getString("pin")));
                arrayList.add(new BasicNameValuePair("number", Frag1.this.intent.getExtras().getString("number")));
                arrayList.add(new BasicNameValuePair("amount", Frag1.this.intent.getExtras().getString("amount")));
            } else {
                arrayList.add(new BasicNameValuePair("pincode", obj));
                arrayList.add(new BasicNameValuePair("number", Frag1.this.f183id));
                arrayList.add(new BasicNameValuePair("amount", Frag1.this.f182h));
            }
            arrayList.add(new BasicNameValuePair("optn", Frag1.this.opn));
            arrayList.add(new BasicNameValuePair("pin", pref3));
            JSONObject makeHttpRequest = null;
            try {
                makeHttpRequest = Frag1.this.jsonParser.makeHttpRequest((Frag1.getPref(ImagesContract.URL, Frag1.this.getActivity().getBaseContext()) + "/apiapp/") + "" + "NewRequest", HttpPost.METHOD_NAME, arrayList);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            try {
                int i = makeHttpRequest.getInt("success");
                int i2 = makeHttpRequest.getInt("success");
                final String string = makeHttpRequest.getString("message");
                if (i2 == 0) {
                    Frag1.this.flag = 0;
                    Frag1.this.getActivity().runOnUiThread(new Runnable() {
                        public void run() {
                            dialog unused = Frag1.this.f184md = new dialog(Frag1.this.getActivity());
                            Frag1.this.f184md.build("Faild", string);
                        }
                    });
                }
                if (i == 1) {
                    Frag1.this.flag = 0;
                } else {
                    Frag1.this.flag = 1;
                }
                if (i2 != 1) {
                    return null;
                }
                Frag1.this.flag = 0;
                Frag1.this.getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        dialogs unused = Frag1.this.mds = new dialogs(Frag1.this.getActivity());
                        Toast.makeText(Frag1.this.getActivity().getBaseContext(), "Request Sent Successfull", Toast.LENGTH_LONG).show();
                        Frag1.this.startActivity(new Intent(Frag1.this.getActivity().getBaseContext(), Welcome.class));
                        Frag1.this.getActivity().finish();
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
            Frag1.this.pDialog.dismiss();
            if (Frag1.this.flag == 1) {
                Toast.makeText(Frag1.this.getActivity().getBaseContext(), "Please Enter Correct informations", Toast.LENGTH_LONG).show();
            }
        }
    }

    private class Getop extends AsyncTask<Void, Void, Void> {
        private ImageButton retry;

        private Getop() {
        }

        /* developer by  sabbir */
        public void onPreExecute() {
            super.onPreExecute();
            Frag1.this.f186pg.setVisibility(View.VISIBLE);
            Frag1 frag1 = Frag1.this;
            if (!frag1.isOnline(frag1.getActivity().getBaseContext())) {
                Frag1.this.f186pg.setVisibility(View.GONE);
            }
        }

        /* developer by  sabbir */
        public Void doInBackground(Void... voidArr) {
            String str;
            Frag1 frag1 = Frag1.this;
            if (!frag1.isOnline(frag1.getActivity().getBaseContext())) {
                return null;
            }
            Frag1.getPref("token", Frag1.this.getActivity().getBaseContext());
            Frag1.getPref("device", Frag1.this.getActivity().getBaseContext());
            String str2 = Frag1.getPref(ImagesContract.URL, Frag1.this.getActivity().getBaseContext()) + "/apiapp/";
            Frag1.this.arraylist = new ArrayList<>();
            if (Frag1.this.f181bi == 1) {
                str = "113";
            } else {
                str = Frag1.this.f185mn.getText().toString();
                if (str.length() > 3) {
                    str = str.substring(0, 3);
                }
            }
            Frag1.this.jsonobjects = JSONfunctions.getJSONfromURL(str2 + "/oparetorList?three=" + str);
            try {
                Frag1 frag12 = Frag1.this;
                frag12.jsonarray = frag12.jsonobjects.getJSONArray("bmtelbd");
                Log.d("Create Response", Frag1.this.jsonarray.toString());
                for (int i = 0; i < Frag1.this.jsonarray.length(); i++) {
                    new HashMap();
                    Frag1 frag13 = Frag1.this;
                    frag13.jsonobject = frag13.jsonarray.getJSONObject(i);
                    if (i == 0) {
                        Frag1 frag14 = Frag1.this;
                        frag14.optr = frag14.jsonobject.getString("opname");
                        Frag1 frag15 = Frag1.this;
                        frag15.opn = frag15.jsonobject.getString("pcode");
                        Frag1.this.getActivity().runOnUiThread(new Runnable() {
                            public void run() {
                                try {
                                    if (Frag1.this.opn.equals("GP")) {
                                        Frag1.this.opl.setImageDrawable(ContextCompat.getDrawable(Frag1.this.getActivity().getBaseContext(), R.drawable.grameenphone));
                                    } else if (Frag1.this.opn.equals("RB")) {
                                        Frag1.this.opl.setImageDrawable(ContextCompat.getDrawable(Frag1.this.getActivity().getBaseContext(), R.drawable.robi));
                                    } else if (Frag1.this.opn.equals("BL")) {
                                        Frag1.this.opl.setImageDrawable(ContextCompat.getDrawable(Frag1.this.getActivity().getBaseContext(), R.drawable.banglalink));
                                    } else if (Frag1.this.opn.equals("AT")) {
                                        Frag1.this.opl.setImageDrawable(ContextCompat.getDrawable(Frag1.this.getActivity().getBaseContext(), R.drawable.airtel));
                                    } else if (Frag1.this.opn.equals("SK")) {
                                        Frag1.this.opl.setImageDrawable(ContextCompat.getDrawable(Frag1.this.getActivity().getBaseContext(), R.drawable.skitto));
                                    } else if (Frag1.this.opn.equals("TT")) {
                                        Frag1.this.opl.setImageDrawable(ContextCompat.getDrawable(Frag1.this.getActivity().getBaseContext(), R.drawable.teletalk));
                                    } else {
                                        Picasso.get().load(Frag1.this.jsonobject.getString("img")).into(Frag1.this.opl);
                                    }
                                } catch (JSONException unused) {
                                }
                            }
                        });
                    }
                }
                return null;
            } catch (JSONException e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
                return null;
            }
        }

        /* developer by  sabbir */
        public void onPostExecute(Void voidR) {
            Frag1.this.f186pg.setVisibility(View.GONE);
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
        SharedPreferences.Editor edit = PreferenceManager.getDefaultSharedPreferences(getActivity().getBaseContext()).edit();
        edit.putString(str, str2);
        edit.commit();
    }

    public class ViewDialog {
        public ViewDialog() {
        }

        public void showDialog(Activity activity, String str, String str2, String str3, String str4) {
            final Dialog dialog = new Dialog(activity);
            dialog.getWindow().setLayout(-1, -1);
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
