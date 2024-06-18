package com.softwarelab.mehedi;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.core.app.NotificationCompat;

import com.google.android.gms.common.internal.ImagesContract;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.cookie.ClientCookie;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Url extends Activity {
    private static final String Pint = "otp";
    private static final String TAG_SUCCESS = "success";
    String FinalJSonObject;

    /* renamed from: al */
    boolean f287al;
    TextView dev;
    String device;
    boolean flag;
    JSONObject json;
    JSONArray jsonArray;
    JSONParser jsonParser = new JSONParser();
    Button login;
    String msg;
    /*developer by  sabbir */
    public ProgressDialog pDialog;
    Button signin;
    /* developer by  sabbir */
    public EditText surl;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.url);
        this.signin = (Button) findViewById(R.id.url_save);
        this.surl = (EditText) findViewById(R.id.url_name);
        this.signin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (Url.this.surl.length() < 5) {
                    Toast.makeText(Url.this, "Please Enter correct url", Toast.LENGTH_LONG).show();
                    return;
                }
                ProgressDialog unused = Url.this.pDialog = new ProgressDialog(Url.this);
                Url.this.pDialog.setMessage("Checking please wait...");
                Url.this.pDialog.setIndeterminate(false);
                Url.this.pDialog.setCancelable(true);
                Url.this.pDialog.show();
                new RequestTask().execute(new String[]{"https://fiverelectric.online/controll/index?domain=" + Url.this.surl.getText().toString() + "&type=user"});
            }
        });
    }

    class RequestTask extends AsyncTask<String, String, String> {
        RequestTask() {
        }

        /* developer by  sabbir */
        public String doInBackground(String... strArr) {
            try {
                HttpResponse execute = new DefaultHttpClient().execute(new HttpGet(strArr[0]));
                StatusLine statusLine = execute.getStatusLine();
                if (statusLine.getStatusCode() == 200) {
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    execute.getEntity().writeTo(byteArrayOutputStream);
                    String byteArrayOutputStream2 = byteArrayOutputStream.toString();
                    byteArrayOutputStream.close();
                    return byteArrayOutputStream2;
                }
                execute.getEntity().getContent().close();
                throw new IOException(statusLine.getReasonPhrase());
            } catch (IOException unused) {
                return null;
            }
        }

        /* developer by  sabbir */
        public void onPostExecute(String str) {
            super.onPostExecute(str);
            Url.this.FinalJSonObject = str;
            try {
                if (Url.this.FinalJSonObject != null) {
                    try {
                        JSONArray jSONArray = new JSONArray(Url.this.FinalJSonObject);
                        for (int i = 0; i < jSONArray.length(); i++) {
                            JSONObject jSONObject = jSONArray.getJSONObject(i);
                            if (jSONObject.getInt(NotificationCompat.CATEGORY_STATUS) == 1) {
                                Url.this.SavePreferences(ImagesContract.URL, "http://" + jSONObject.getString(ClientCookie.DOMAIN_ATTR));
                                Url.this.f287al = true;
                            } else {
                                Url.this.flag = true;
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            Url.this.pDialog.dismiss();
            if (Url.this.flag) {
                Toast.makeText(Url.this, "Domain not found/Inactive", Toast.LENGTH_LONG).show();
            }
            if (Url.this.f287al) {
                Url.this.startActivity(new Intent(Url.this.getApplicationContext(), MainActivity.class));
                Url.this.finish();
            }
        }
    }

    public static String getPref(String str, Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getString(str, (String) null);
    }

    public static String getPref2(String str, Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getString(str, "no");
    }

    public void SavePreferences(String str, String str2) {
        SharedPreferences.Editor edit = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit();
        edit.putString(str, str2);
        edit.commit();
    }
}
