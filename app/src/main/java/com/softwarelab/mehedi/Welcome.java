package com.softwarelab.mehedi;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.internal.ImagesContract;
import com.google.android.gms.measurement.api.AppMeasurementSdk;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.io.File;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import de.hdodenhof.circleimageview.CircleImageView;

public class Welcome extends AppCompatActivity {
    private static final int NOTIFICATION_PERMISSION_CODE = 123;
    private static final int PICK_FROM_GALLARY = 2;
    private static final int STORAGE_PERMISSION_CODE = 101;

    private BottomNavigationView bottomNavigationView;
    static String Service_id = "service";
    static String Service_n = "name";
    public static String TAG = "Welcome";
    private static final String TAG_Balance = "balance";
    private static final String TAG_SUCCESS = "success";
    static String TIME = "time";
    private static final String about = "about";
    static String act = "act";
    static String img = "img";
    final long DELAY_MS = 1000;
    String FinalJSonObject;
    final long PERIOD_MS = 3000;
    service_adafter adapter;


    private EditText f288am;
    ArrayList<HashMap<String, String>> arraylist;
    private TextView balanc;
    String btype;
    int currentPage = 0;
    String device;
    Dialog dialog;
    /* developer by  sabbir */
    public ImageView[] dots;
    /* developer by  sabbir */
    public int dotscount;
    private EditText email_id;
    int flag = 0;
    String[] imagesName = {"image1", "image2", "image3", "image4"};
    String img1;
    String img2;
    String img3;
    String img4;
    JSONParser jsonParser = new JSONParser();
    JSONArray jsonarray;
    JSONObject jsonobject;
    private TextView leve;
    Button login;
    ProgressDialog mProgressDialog;


    private EditText f289mn;
    String msg;
    private TextView namec;
    String number;
    private ProgressDialog pDialog;
    String[] permissions = {"android.permission.WRITE_EXTERNAL_STORAGE"};
    CircleImageView prof;
    String pwd;
    String shop;
    Button signi;
    LinearLayout sliderDotspanel;
    String telegram;
    Timer timer;
    String token;
    String type;
    String type2;
    String url;
    ViewPager viewPager;
    String whatsapp;
    String youtube;

    private void setSupportActionBar(Toolbar toolbar) {
    }

    /* developer by  sabbir */
    public void onCreate(Bundle bundle) {
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());
        super.onCreate(bundle);
        setContentView((int) R.layout.dashboard);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        FloatingActionButton fab = findViewById(R.id.fab);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int itemId = item.getItemId();
                if (itemId == R.id.home) {
                    startActivity(new Intent(Welcome.this, Welcome.class));
                } else if (itemId == R.id.histroy) {
                    startActivity(new Intent(Welcome.this, history_op.class));
                } else if (itemId == R.id.placeholder) {

                } else if (itemId == R.id.inbox) {
                    startActivity(new Intent(Welcome.this, News.class));
                } else if (itemId == R.id.profile) {
                    startActivity(new Intent(Welcome.this, Myprofile.class));
                    Toast.makeText(Welcome.this, " Profile ", Toast.LENGTH_LONG).show();
                }

                return true;
            }
        });


        // Set default selection
        bottomNavigationView.setSelectedItemId(R.id.placeholder);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle FAB click
                // Example: show a toast
                Toast.makeText(Welcome.this, "FAB Clicked", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Welcome.this, Payment_type.class));
            }
        });
        checkPermissions();
        requestNotificationPermission();
        item_list();
        String pref = getPref("phone", getApplicationContext());
        String pref2 = getPref("profilepic", getApplicationContext());
        this.namec = (TextView) findViewById(R.id.fd);
        this.balanc = (TextView) findViewById(R.id.balance);
        this.leve = (TextView) findViewById(R.id.lev);
        this.namec.setText(pref);
        this.prof = (CircleImageView) findViewById(R.id.iv_circular_user_image);
        if (!TextUtils.isEmpty(pref2)) {
            File file = new File(pref2);
            if (file.exists()) {
                this.prof.setImageDrawable(new BitmapDrawable(getResources(), BitmapFactory.decodeFile(file.getAbsolutePath())));
            }
        }
        this.prof.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Welcome.this.startActivityForResult(new Intent("android.intent.action.PICK", MediaStore.Images.Media.EXTERNAL_CONTENT_URI), 2);
            }
        });
        this.balanc.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Welcome welcome = Welcome.this;
                welcome.showDialog_balance(welcome);
            }
        });
    }

    public void action(View view) {
        Intent intent;
        boolean z;
        boolean z2 = true;
        if (view.getId() == R.id.myusers) {
            intent = new Intent(this, Myreseller.class);
            z = true;
        } else {
            z = false;
            intent = null;
        }
        if (view.getId() == R.id.myhistory) {
            intent = new Intent(this, history_op.class);
            z = true;
        }
        if (view.getId() == R.id.notic) {
            intent = new Intent(this, News.class);
            z = true;
        }
        if (view.getId() == R.id.adduser) {
            intent = new Intent(this, Addres.class);
            z = true;
        }
        if (view.getId() == R.id.sendmoney) {
            intent = new Intent(this, Transfer.class);
            z = true;
        }
        if (view.getId() == R.id.addbalance) {
            intent = new Intent(this, Payment_type.class);
            z = true;
        }
        if (view.getId() == R.id.passchange) {
            intent = new Intent(this, Password.class);
            z = true;
        }
        if (view.getId() == R.id.pinchange) {
            intent = new Intent(this, Pinc.class);
            z = true;
        }
        if (view.getId() == R.id.dlock) {
            intent = new Intent(this, Dlock.class);
            z = true;
        }
        if (view.getId() == R.id.secstep) {
            intent = new Intent(this, Twostep.class);
            z = true;
        }
        if (view.getId() == R.id.noticeb) {
            intent = new Intent(this, News.class);
            z = true;
        }
        if (view.getId() == R.id.support) {
            intent = new Intent(this, Tricket_main.class);
        } else {
            z2 = z;
        }
        if (view.getId() == R.id.chpic) {
            startActivity(new Intent(getApplicationContext(), Myprofile.class));
        }
        if (view.getId() == R.id.logout) {
            SavePreferences("phone", "no");
            SavePreferences("pass", "no");
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }
        if (view.getId() == R.id.offer) {
            showDialog(this);
        }
        if (view.getId() == R.id.whatsapp) {
            intent = new Intent("android.intent.action.VIEW", Uri.parse(this.whatsapp));
            z2 = Patterns.WEB_URL.matcher(this.whatsapp).matches();
        }
        if (view.getId() == R.id.telegram) {
            intent = new Intent("android.intent.action.VIEW", Uri.parse(this.telegram));
            z2 = Patterns.WEB_URL.matcher(this.telegram).matches();
        }
        if (view.getId() == R.id.youtube) {
            intent = new Intent("android.intent.action.VIEW", Uri.parse(this.youtube));
            z2 = Patterns.WEB_URL.matcher(this.youtube).matches();
        }
        if (view.getId() == R.id.shoping) {
            intent = new Intent("android.intent.action.VIEW", Uri.parse(this.shop));
            z2 = Patterns.WEB_URL.matcher(this.shop).matches();
        }
        if (z2) {
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.stay);
        }
    }

    public void showDialogmenu(Activity activity) {
        final Dialog dialog2 = new Dialog(activity);
        dialog2.requestWindowFeature(1);
        dialog2.setCancelable(false);
        dialog2.setContentView(R.layout.home_menu);
        ((Button) dialog2.findViewById(R.id.sup)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                dialog2.dismiss();
                Welcome.this.startActivity(new Intent(Welcome.this.getApplicationContext(), Tricket_main.class));
                Welcome.this.overridePendingTransition(R.anim.slide_in_left, 17432579);
            }
        });
        ((Button) dialog2.findViewById(R.id.lock)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                dialog2.dismiss();
                Welcome.this.startActivity(new Intent(Welcome.this.getApplicationContext(), Dlock.class));
                Welcome.this.overridePendingTransition(R.anim.slide_in_left, 17432579);
            }
        });
        ((Button) dialog2.findViewById(R.id.logo)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                dialog2.dismiss();
                Welcome.this.SavePreferences("phone", "no");
                Welcome.this.SavePreferences("pass", "no");
                Welcome.this.startActivity(new Intent(Welcome.this.getApplicationContext(), MainActivity.class));
                Welcome.this.overridePendingTransition(R.anim.slide_in_left, 17432579);
                Welcome.this.finish();
            }
        });
        ((Button) dialog2.findViewById(R.id.pass)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                dialog2.dismiss();
                Welcome.this.startActivity(new Intent(Welcome.this, Password.class));
                Welcome.this.overridePendingTransition(R.anim.slide_in_right, R.anim.stay);
            }
        });
        ((Button) dialog2.findViewById(R.id.pinm)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                dialog2.dismiss();
                Welcome.this.startActivity(new Intent(Welcome.this, Pinc.class));
                Welcome.this.overridePendingTransition(R.anim.slide_in_right, R.anim.stay);
            }
        });
        ((ImageView) dialog2.findViewById(R.id.close)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                dialog2.dismiss();
            }
        });
        dialog2.show();
    }

    public void showDialog_balance(Activity activity) {
        final Dialog dialog2 = new Dialog(activity);
        dialog2.requestWindowFeature(1);
        dialog2.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dialog2.setCancelable(false);
        dialog2.setContentView(R.layout.balance_d);
        ((Button) dialog2.findViewById(R.id.balance_c)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Welcome welcome;
                String str;
                int checkedRadioButtonId = ((RadioGroup) dialog2.findViewById(R.id.typep)).getCheckedRadioButtonId();
                if (checkedRadioButtonId == R.id.drive) {
                    welcome = Welcome.this;
                    str = "drive";
                } else if (checkedRadioButtonId == R.id.bank) {
                    welcome = Welcome.this;
                    str = "bank";
                } else {
                    welcome = Welcome.this;
                    str = "main";
                }
                welcome.btype = str;
                dialog2.dismiss();
                Welcome.this.balance();
            }
        });
        ((ImageView) dialog2.findViewById(R.id.close)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                dialog2.dismiss();
            }
        });
        dialog2.show();
    }

    public void showDialog(Activity activity) {
        final Dialog dialog2 = new Dialog(activity);
        dialog2.requestWindowFeature(1);
        dialog2.setCancelable(false);
        dialog2.setContentView(R.layout.offer);
        ((Button) dialog2.findViewById(R.id.pass)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                dialog2.dismiss();
                Intent intent = new Intent(Welcome.this, Operator.class);
                intent.putExtra("type", "internet");
                intent.putExtra("drive", "drive");
                intent.putExtra("type3", "64");
                Welcome.this.startActivity(intent);
                Welcome.this.overridePendingTransition(R.anim.slide_in_right, R.anim.stay);
            }
        });
        ((Button) dialog2.findViewById(R.id.pinm)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                dialog2.dismiss();
                Intent intent = new Intent(Welcome.this, Operator.class);
                intent.putExtra("type", "internet");
                intent.putExtra("type3", "16384");
                Welcome.this.startActivity(intent);
                Welcome.this.overridePendingTransition(R.anim.slide_in_right, R.anim.stay);
            }
        });
        ((ImageView) dialog2.findViewById(R.id.close)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                dialog2.dismiss();
            }
        });
        dialog2.show();
    }

    /* developer by  sabbir */
    public void balance() {
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
        String str2 = "https://" + this.url;
        this.url = str2;
        Log.d("sabbir", str2);
        this.number = getPref("phone", getApplicationContext());
        this.pwd = getPref("pass", getApplicationContext());
        this.token = getPref("token", getApplicationContext());
        this.device = getPref("device", getApplicationContext());
        StringRequest r1 = new StringRequest(1, this.url + TAG_Balance, new Response.Listener<String>() {
            public void onResponse(String str) {
                Log.d("sabbir", str);
                Welcome.this.FinalJSonObject = str;
                Welcome welcome = Welcome.this;
                new ParseJSonDataClass(welcome).execute(new Void[0]);
            }
        }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError volleyError) {
                Welcome.this.dialog.dismiss();
                Toast.makeText(Welcome.this.getBaseContext(), volleyError.toString(), Toast.LENGTH_LONG).show();
            }
        }) {
            /* developer by  sabbir */
            public Map<String, String> getParams() throws AuthFailureError {
                Hashtable hashtable = new Hashtable();
                hashtable.put("type", Welcome.this.btype);
                hashtable.put("token", Welcome.this.token);
                hashtable.put("username", Welcome.this.number);
                hashtable.put("password", Welcome.this.pwd);
                hashtable.put("deviceid", Welcome.this.device);
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
                JSONObject jSONObject = new JSONObject(Welcome.this.FinalJSonObject);
                final String string = jSONObject.getString("message");
                if (jSONObject.getInt("success") == 1) {
                    final String string2 = jSONObject.getString("notice");
                    Welcome.this.runOnUiThread(new Runnable() {
                        public void run() {
                            String str = string2;
                            if (str != null) {
                                str.isEmpty();
                            }
                            Welcome.this.setTextInTextView(string);
                        }
                    });
                    new Timer(false).schedule(new TimerTask() {
                        public void run() {
                            Welcome.this.runOnUiThread(new Runnable() {
                                public void run() {
                                    Welcome.this.setTextInTextView("My Balance");
                                }
                            });
                        }
                    }, 5000);
                    Log.d(Welcome.TAG, string);
                    Welcome.this.flag = 0;
                    return null;
                }
                Welcome.this.flag = 1;
                return null;
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }
        }

        /* developer by  sabbir */
        public void onPostExecute(Void voidR) {
            if (Welcome.this.flag == 1) {
                Toast.makeText(Welcome.this, "Please Enter Correct informations", Toast.LENGTH_LONG).show();
            }
            Welcome.this.dialog.dismiss();
        }
    }

    public void setTextInTextView(String str) {
        this.balanc.setText(str);
    }

    public void setTextInTextViewl(String str) {
        this.leve.setText(str);
    }

    private boolean isOnline(Context context) {
        @SuppressLint("WrongConstant") NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }

    public void SavePreferences(String str, String str2) {
        SharedPreferences.Editor edit = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit();
        edit.putString(str, str2);
        edit.commit();
    }

    public static String getPref(String str, Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getString(str, (String) null);
    }

    private void item_list() {
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
        String str2 = "https://" + this.url;
        this.url = str2;
        Log.d("sabbir", str2);
        this.number = getPref("phone", getApplicationContext());
        this.pwd = getPref("pass", getApplicationContext());
        this.token = getPref("token", getApplicationContext());
        this.device = getPref("device", getApplicationContext());
        StringRequest r1 = new StringRequest(1, this.url + "/role?token=" + URLEncoder.encode(this.token) + "&deviceid=" + URLEncoder.encode(this.device), new Response.Listener<String>() {
            public void onResponse(String str) {
                Log.d("sabbir", str);
                Welcome.this.FinalJSonObject = str;
                Welcome welcome = Welcome.this;
                new ItemParseJSonDataClass(welcome).execute(new Void[0]);
            }
        }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError volleyError) {
                Welcome.this.dialog.dismiss();
                Toast.makeText(Welcome.this.getBaseContext(), volleyError.toString(), Toast.LENGTH_LONG).show();
            }
        }) {
            /* developer by  sabbir */
            public Map<String, String> getParams() throws AuthFailureError {
                Hashtable hashtable = new Hashtable();
                hashtable.put("goto", "ok");
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

    private class ItemParseJSonDataClass extends AsyncTask<Void, Void, Void> {
        public Context context;

        public ItemParseJSonDataClass(Context context2) {
            this.context = context2;
        }

        /* developer by  sabbir */
        public void onPreExecute() {
            super.onPreExecute();
        }

        /* developer by  sabbir */
        public Void doInBackground(Void... voidArr) {
            Welcome.this.arraylist = new ArrayList<>();
            try {
                JSONObject jSONObject = new JSONObject(Welcome.this.FinalJSonObject);
                Welcome.this.jsonarray = jSONObject.getJSONArray("bmtelbd");
                Log.d("Create Response", Welcome.this.jsonarray.toString());
                for (int i = 0; i < Welcome.this.jsonarray.length(); i++) {
                    HashMap hashMap = new HashMap();
                    JSONObject jSONObject2 = Welcome.this.jsonarray.getJSONObject(i);
                    if (i == 0) {
                        jSONObject2.getString("message");
                        final String string = jSONObject2.getString(FirebaseAnalytics.Param.LEVEL);
                        final String string2 = jSONObject2.getString("notice");
                        Welcome.this.whatsapp = jSONObject2.getString("whatsapp");
                        Welcome.this.telegram = jSONObject2.getString("telegram");
                        Welcome.this.youtube = jSONObject2.getString("youtube");
                        Welcome.this.shop = jSONObject2.getString("shop");
                        Welcome.this.img1 = jSONObject2.getString("img1");
                        Welcome.this.img2 = jSONObject2.getString("img2");
                        Welcome.this.img3 = jSONObject2.getString("img3");
                        Welcome.this.img4 = jSONObject2.getString("img4");
                        Welcome.this.runOnUiThread(new Runnable() {
                            public void run() {
                                Welcome.this.slide(Welcome.this.img1, Welcome.this.img2, Welcome.this.img3, Welcome.this.img4);
                                String str = string2;
                                if (str != null && !str.isEmpty()) {
                                    Welcome.this.showAlert(string2);
                                }
                                Welcome.this.setTextInTextViewl(string);
                                Welcome.this.SavePreferences(FirebaseAnalytics.Param.LEVEL, string);
                            }
                        });
                    }
                    String string3 = jSONObject2.getString("act");
                    if (!string3.toString().toLowerCase().equals("addres") && !string3.toString().toLowerCase().equals("transfer") && !string3.toString().toLowerCase().equals("make") && !string3.toString().toLowerCase().equals("myres")) {
                        hashMap.put(NotificationCompat.CATEGORY_SERVICE, jSONObject2.getString(NotificationCompat.CATEGORY_SERVICE));
                        hashMap.put(AppMeasurementSdk.ConditionalUserProperty.NAME, jSONObject2.getString(AppMeasurementSdk.ConditionalUserProperty.NAME));
                        hashMap.put("img", jSONObject2.getString("img"));
                        hashMap.put("act", jSONObject2.getString("act"));
                        Welcome.this.arraylist.add(hashMap);
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
            ExpandableHeightGridView expandableHeightGridView = (ExpandableHeightGridView) Welcome.this.findViewById(R.id.atachviewx);
            expandableHeightGridView.setExpanded(true);
            Welcome welcome = Welcome.this;
            Welcome welcome2 = Welcome.this;
            welcome.adapter = new service_adafter(welcome2, welcome2.arraylist);
            expandableHeightGridView.setAdapter(Welcome.this.adapter);
            Welcome.this.dialog.dismiss();
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.dv:
                startActivityForResult(new Intent(this, Dlock.class), 0);
                return true;
            case R.id.logout:
                SavePreferences("phone", "no");
                SavePreferences("pass", "no");
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
                return true;
            case R.id.pasc:
                startActivityForResult(new Intent(this, Password.class), 0);
                return true;
            case R.id.pinc:
                startActivityForResult(new Intent(this, Pinc.class), 0);
                return true;
            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }

    public void onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
        super.onRequestPermissionsResult(i, strArr, iArr);
        if (i == 100 && iArr.length > 0) {
            int i2 = iArr[0];
        }
    }

    private boolean checkPermissions() {
        ArrayList arrayList = new ArrayList();
        for (String str : this.permissions) {
            if (ContextCompat.checkSelfPermission(this, str) != 0) {
                arrayList.add(str);
            }
        }
        if (arrayList.isEmpty()) {
            return true;
        }
        showDialog_permission(this);
        return false;
    }

    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, 17432579);
    }

    /* developer by  sabbir */
    public void showAlert(String str) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(str).setTitle("Notice").setCancelable(false).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        builder.create().show();
    }

    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i == 2 && i2 == -1) {
            String[] strArr = {"_data"};
            Cursor query = getContentResolver().query(intent.getData(), strArr, (String) null, (String[]) null, (String) null);
            query.moveToFirst();
            @SuppressLint("Range") String string = query.getString(query.getColumnIndex(strArr[0]));
            query.close();
            BitmapFactory.decodeFile(string);
            File file = new File(string);
            if (file.exists()) {
                this.prof.setImageBitmap(BitmapFactory.decodeFile(file.getAbsolutePath()));
                SavePreferences("profilepic", string);
            }
        }
    }

    public void slide(String str, String str2, String str3, String str4) {
        this.sliderDotspanel = (LinearLayout) findViewById(R.id.SliderDots);
        this.viewPager = (ViewPager) findViewById(R.id.viewpager);
        CustomAdapter customAdapter = new CustomAdapter(this, new Integer[]{1, 2, 3, 4}, new String[]{str, str2, str3, str4});
        this.viewPager.setAdapter(customAdapter);
        int count = customAdapter.getCount();
        this.dotscount = count;
        this.dots = new ImageView[count];
        for (int i = 0; i < this.dotscount; i++) {
            this.dots[i] = new ImageView(this);
            this.dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.non_active_dot));
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-2, -2);
            layoutParams.setMargins(8, 0, 8, 0);
            this.sliderDotspanel.addView(this.dots[i], layoutParams);
        }
        this.dots[0].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));
        this.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            public void onPageScrollStateChanged(int i) {
            }

            public void onPageScrolled(int i, float f, int i2) {
            }

            public void onPageSelected(int i) {
                for (int i2 = 0; i2 < Welcome.this.dotscount; i2++) {
                    Welcome.this.dots[i2].setImageDrawable(ContextCompat.getDrawable(Welcome.this.getApplicationContext(), R.drawable.non_active_dot));
                }
                Welcome.this.dots[i].setImageDrawable(ContextCompat.getDrawable(Welcome.this.getApplicationContext(), R.drawable.active_dot));
            }
        });
        final Handler handler = new Handler();
        final Runnable r9 = new Runnable() {
            public void run() {
                if (Welcome.this.currentPage == 4) {
                    Welcome.this.currentPage = 0;
                }
                ViewPager viewPager = Welcome.this.viewPager;
                Welcome welcome = Welcome.this;
                int i = welcome.currentPage;
                welcome.currentPage = i + 1;
                viewPager.setCurrentItem(i, true);
            }
        };
        Timer timer2 = new Timer();
        this.timer = timer2;
        timer2.schedule(new TimerTask() {
            public void run() {
                handler.post(r9);
            }
        }, 1000, 3000);
    }

    @SuppressLint("ResourceType")
    public void showDialog_permission(Activity activity) {
        final Dialog dialog2 = new Dialog(activity, 2131886564);
        dialog2.requestWindowFeature(1);
        dialog2.setCancelable(true);
        dialog2.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog2.setContentView(R.layout.permission_close);
        ((LinearLayout) dialog2.findViewById(R.id.cem)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                dialog2.dismiss();
            }
        });
        ((LinearLayout) dialog2.findViewById(R.id.gal)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                dialog2.dismiss();
                ArrayList arrayList = new ArrayList();
                for (String str : Welcome.this.permissions) {
                    if (ContextCompat.checkSelfPermission(Welcome.this, str) != 0) {
                        arrayList.add(str);
                    }
                }
                if (!arrayList.isEmpty()) {
                    ActivityCompat.requestPermissions(Welcome.this, (String[]) arrayList.toArray(new String[arrayList.size()]), 100);
                }
            }
        });
        dialog2.show();
    }

    private void requestNotificationPermission() {
        if (ContextCompat.checkSelfPermission(this, "android.permission.ACCESS_NOTIFICATION_POLICY") != 0) {
            ActivityCompat.shouldShowRequestPermissionRationale(this, "android.permission.ACCESS_NOTIFICATION_POLICY");
            ActivityCompat.requestPermissions(this, new String[]{"android.permission.ACCESS_NOTIFICATION_POLICY"}, 123);
        }
    }
}
