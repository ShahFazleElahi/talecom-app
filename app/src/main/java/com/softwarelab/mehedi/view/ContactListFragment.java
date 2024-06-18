package com.softwarelab.mehedi.view;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.common.internal.ImagesContract;
import com.softwarelab.mehedi.DisplayActivity;
import com.softwarelab.mehedi.DisplayActivitya;
import com.softwarelab.mehedi.JSONfunctions;
import com.softwarelab.mehedi.PinActivity;
import com.softwarelab.mehedi.R;
import com.softwarelab.mehedi.databinding.FragmentContactListBinding;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ContactListFragment extends Fragment implements View.OnClickListener {
    private static final String TAG_SUCCESS = "success";
    ArrayList<HashMap<String, String>> arraylist;
    private FragmentContactListBinding binding;
    Dialog dialog;
    Intent intent;
    private boolean isSearch = false;
    JSONArray jsonarray;
    JSONObject jsonobject;
    JSONObject jsonobjects;
    RecyclerView listview;

    
    AutoCompleteTextView f341mn;
    String number;
    String opn;
    String optr;
    private ProgressDialog pDialog;
    List<String> responseList = new ArrayList();
    String type;
    String type2;
    String type3;
    View view;

    public static ContactListFragment newInstance() {
        return new ContactListFragment();
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getArguments();
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        FragmentContactListBinding inflate = FragmentContactListBinding.inflate(layoutInflater, viewGroup, false);
        this.binding = inflate;
        LinearLayout root = inflate.getRoot();
        this.view = root;
        this.f341mn = (AutoCompleteTextView) root.findViewById(R.id.et_search);
        Intent intent2 = getActivity().getIntent();
        this.intent = intent2;
        this.type = intent2.getExtras().getString("type");
        this.type2 = this.intent.getExtras().getString("type2");
        this.type3 = this.intent.getExtras().getString("type3");
        this.opn = this.intent.getExtras().getString("opt");
        this.optr = this.intent.getExtras().getString("opt2");
        new DownloadJSONy().execute(new Void[0]);
        ((ImageButton) this.view.findViewById(R.id.go)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (ContactListFragment.this.f341mn.length() < 10) {
                    Toast.makeText(ContactListFragment.this.getContext().getApplicationContext(), "Please Enter correct Number", Toast.LENGTH_LONG).show();
                } else if (!ContactListFragment.this.type.equals("rc")) {
                    Intent intent = new Intent(ContactListFragment.this.getContext().getApplicationContext(), DisplayActivitya.class);
                    intent.putExtra("opt", ContactListFragment.this.opn);
                    intent.putExtra("opt2", ContactListFragment.this.optr);
                    intent.putExtra("type", ContactListFragment.this.type);
                    intent.putExtra("number", ContactListFragment.this.f341mn.getText().toString());
                    intent.putExtra("type3", ContactListFragment.this.type3);
                    intent.putExtra("type2", ContactListFragment.this.type2);
                    ContactListFragment.this.startActivity(intent);
                } else if (ContactListFragment.this.intent.hasExtra("drive")) {
                    Intent intent2 = new Intent(ContactListFragment.this.getContext().getApplicationContext(), PinActivity.class);
                    intent2.putExtra("opt", ContactListFragment.this.intent.getExtras().getString("opt"));
                    intent2.putExtra("opt2", ContactListFragment.this.intent.getExtras().getString("opt2"));
                    intent2.putExtra("type", ContactListFragment.this.intent.getExtras().getString("type"));
                    intent2.putExtra("drive", ContactListFragment.this.intent.getExtras().getString("drive"));
                    intent2.putExtra("number", ContactListFragment.this.f341mn.getText().toString());
                    intent2.putExtra("type3", ContactListFragment.this.intent.getExtras().getString("type3"));
                    intent2.putExtra("type2", ContactListFragment.this.intent.getExtras().getString("type2"));
                    intent2.putExtra("id", ContactListFragment.this.intent.getExtras().getString("id"));
                    intent2.putExtra("paid", ContactListFragment.this.intent.getExtras().getString("paid"));
                    intent2.putExtra("amount", ContactListFragment.this.intent.getExtras().getString("amount"));
                    intent2.putExtra("pkg", ContactListFragment.this.intent.getExtras().getString("pkg"));
                    intent2.putExtra("rol", ContactListFragment.this.intent.getExtras().getString("rol"));
                    ContactListFragment.this.startActivity(intent2);
                } else {
                    new Getop().execute(new Void[0]);
                }
            }
        });
        return this.view;
    }

    private class Getop extends AsyncTask<Void, Void, Void> {
        private ImageButton retry;

        private Getop() {
        }

        /* developer by  sabbir */
        public void onPreExecute() {
            super.onPreExecute();
            ContactListFragment.this.dialog = new Dialog(ContactListFragment.this.getActivity());
            ContactListFragment.this.dialog.requestWindowFeature(1);
            ContactListFragment.this.dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
            ContactListFragment.this.dialog.setCancelable(false);
            ContactListFragment.this.dialog.setContentView(R.layout.custom_progress);
            ContactListFragment.this.dialog.show();
            ContactListFragment contactListFragment = ContactListFragment.this;
            if (!contactListFragment.isOnline(contactListFragment.getActivity().getBaseContext())) {
                ContactListFragment.this.dialog.dismiss();
            }
        }

        /* developer by  sabbir */
        public Void doInBackground(Void... voidArr) {
            ContactListFragment contactListFragment = ContactListFragment.this;
            if (!contactListFragment.isOnline(contactListFragment.getActivity().getBaseContext())) {
                return null;
            }
            DisplayActivitya.getPref("token", ContactListFragment.this.getActivity().getBaseContext());
            DisplayActivitya.getPref("device", ContactListFragment.this.getActivity().getBaseContext());
            String str = DisplayActivitya.getPref(ImagesContract.URL, ContactListFragment.this.getActivity().getBaseContext()) + "/apiapp/";
            ContactListFragment.this.arraylist = new ArrayList<>();
            String obj = ContactListFragment.this.f341mn.getText().toString();
            if (obj.length() > 3) {
                obj = obj.substring(0, 3);
            }
            ContactListFragment.this.jsonobjects = JSONfunctions.getJSONfromURL(str + "/oparetorList?three=" + obj);
            try {
                ContactListFragment contactListFragment2 = ContactListFragment.this;
                contactListFragment2.jsonarray = contactListFragment2.jsonobjects.getJSONArray("bmtelbd");
                Log.d("Create Response", ContactListFragment.this.jsonarray.toString());
                for (int i = 0; i < ContactListFragment.this.jsonarray.length(); i++) {
                    new HashMap();
                    ContactListFragment contactListFragment3 = ContactListFragment.this;
                    contactListFragment3.jsonobject = contactListFragment3.jsonarray.getJSONObject(i);
                    if (i == 0) {
                        ContactListFragment contactListFragment4 = ContactListFragment.this;
                        contactListFragment4.optr = contactListFragment4.jsonobject.getString("opname");
                        ContactListFragment contactListFragment5 = ContactListFragment.this;
                        contactListFragment5.opn = contactListFragment5.jsonobject.getString("pcode");
                        ContactListFragment.this.getActivity().runOnUiThread(new Runnable() {
                            public void run() {
                                Intent intent = new Intent(ContactListFragment.this.getContext().getApplicationContext(), DisplayActivity.class);
                                intent.putExtra("opt", ContactListFragment.this.opn);
                                intent.putExtra("opt2", ContactListFragment.this.optr);
                                intent.putExtra("type", ContactListFragment.this.type);
                                intent.putExtra("number", ContactListFragment.this.f341mn.getText().toString());
                                intent.putExtra("type3", ContactListFragment.this.type3);
                                intent.putExtra("type2", ContactListFragment.this.type2);
                                ContactListFragment.this.startActivity(intent);
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
            ContactListFragment.this.dialog.dismiss();
        }
    }

    /* developer by  sabbir */
    public boolean isOnline(Context context) {
        @SuppressLint("WrongConstant") NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }

    private class DownloadJSONy extends AsyncTask<Void, Void, Void> {
        private ImageButton retry;

        private DownloadJSONy() {
        }

        /* developer by  sabbir */
        public void onPreExecute() {
            super.onPreExecute();
        }

        /* developer by  sabbir */
        public Void doInBackground(Void... voidArr) {
            ContactListFragment contactListFragment = ContactListFragment.this;
            if (!contactListFragment.isOnline(contactListFragment.getContext().getApplicationContext())) {
                return null;
            }
            String pref = DisplayActivitya.getPref("token", ContactListFragment.this.getContext().getApplicationContext());
            String pref2 = DisplayActivitya.getPref("device", ContactListFragment.this.getContext().getApplicationContext());
            ContactListFragment.this.arraylist = new ArrayList<>();
            ContactListFragment.this.jsonobject = JSONfunctions.getJSONfromURL((DisplayActivitya.getPref(ImagesContract.URL, ContactListFragment.this.getContext().getApplicationContext()) + "/apiapp/") + "/relnumber?token=" + URLEncoder.encode(pref) + "&deviceid=" + URLEncoder.encode(pref2));
            try {
                ContactListFragment contactListFragment2 = ContactListFragment.this;
                contactListFragment2.jsonarray = contactListFragment2.jsonobject.getJSONArray("bmtelbd");
                Log.d("Create Response", ContactListFragment.this.jsonarray.toString());
                for (int i = 0; i < ContactListFragment.this.jsonarray.length(); i++) {
                    HashMap hashMap = new HashMap();
                    ContactListFragment contactListFragment3 = ContactListFragment.this;
                    contactListFragment3.jsonobject = contactListFragment3.jsonarray.getJSONObject(i);
                    if (ContactListFragment.this.jsonobject.getInt("success") == 1) {
                        ContactListFragment contactListFragment4 = ContactListFragment.this;
                        contactListFragment4.number = contactListFragment4.jsonobject.getString("number");
                    }
                    ContactListFragment.this.responseList.add(ContactListFragment.this.number);
                    ContactListFragment.this.arraylist.add(hashMap);
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
            ContactListFragment contactListFragment = ContactListFragment.this;
            if (contactListFragment.isOnline(contactListFragment.getContext().getApplicationContext())) {
                ContactListFragment.this.f341mn.setAdapter(new ArrayAdapter(ContactListFragment.this.getContext().getApplicationContext(), 17367043, ContactListFragment.this.responseList));
            }
        }
    }

    public void onClick(View view2) {
        if (view2.getId() == R.id.et_search) {
            this.isSearch = true;
        }
    }

    private void closeSearch() {
        if (this.isSearch) {
            this.isSearch = false;
        }
    }
}
