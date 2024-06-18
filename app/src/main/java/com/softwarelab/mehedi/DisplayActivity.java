package com.softwarelab.mehedi;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import com.google.android.material.tabs.TabLayout;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

public class DisplayActivity extends AppCompatActivity {
    private static final String TAG_SUCCESS = "success";
    private static final String[] distic = {"50", "100", "200", "500", "1000"};

    
    private EditText f161am;
    ArrayList<HashMap<String, String>> arraylist;

    /* renamed from: bi */
    int f162bi = 0;
    private String cNumber;
    private EditText email_id;
    private TextView err;
    int flag = 0;

    /* renamed from: h */
    String f163h;
    private EditText hint;

    
    String f164id;
    Intent intent;
    JSONParser jsonParser = new JSONParser();
    JSONArray jsonarray;
    JSONObject jsonobject;
    JSONObject jsonobjects;
    ImageButton login;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;

    /* textSize="13sp" */
    private dialog f165md;
    private dialog mdd;
    private dialogs mds;

    
    private TextView f166mn;
    String nam;
    private String number;
    int offer = 0;
    int offercheck = 0;
    private String oid;
    ImageView opl;
    String opn;
    String optr;
    private ProgressDialog pDialog;
    String paid;

    /* renamed from: pg */
    ProgressBar f167pg;
    private EditText pin;

    
    private TextView f168pp;
    private RadioButton radioButton;
    private RadioGroup radioGroup;
    List<String> responseList = new ArrayList();
    String type;
    String type2;
    String type3;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.display_activity);
        Intent intent2 = getIntent();
        this.intent = intent2;
        this.opn = intent2.getExtras().getString("opt");
        this.mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
        this.mViewPager = viewPager;
        viewPager.setAdapter(this.mSectionsPagerAdapter);
        ((TabLayout) findViewById(R.id.tablayout)).setupWithViewPager(this.mViewPager);
        this.number = this.intent.getExtras().getString("number");
        TextView textView = (TextView) findViewById(R.id.number);
        this.f166mn = textView;
        textView.setText(this.number);
    }

    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, 17432579);
    }

    public static class PlaceholderFragment extends Fragment {
        private static final String ARG_SECTION_NUMBER = "section_number";

        public static Recharge.PlaceholderFragment newInstance(int i) {
            Recharge.PlaceholderFragment placeholderFragment = new Recharge.PlaceholderFragment();
            Bundle bundle = new Bundle();
            bundle.putInt(ARG_SECTION_NUMBER, i);
            placeholderFragment.setArguments(bundle);
            return placeholderFragment;
        }

        public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
            return layoutInflater.inflate(R.layout.tact, viewGroup, false);
        }
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        public int getCount() {
            return 3;
        }

        public CharSequence getPageTitle(int i) {
            if (i == 0) {
                return "Amount";
            }
            if (i == 1) {
                return "Drive";
            }
            if (i != 2) {
                return null;
            }
            return "Regular";
        }

        public SectionsPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        public Fragment getItem(int i) {
            if (i == 0) {
                return new Frag1();
            }
            if (i == 1) {
                return new Frag2();
            }
            if (i != 2) {
                return null;
            }
            return new Frag3();
        }
    }
}
