package com.softwarelab.mehedi;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.Text;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;
import java.io.FileNotFoundException;

public class DocActivity extends AppCompatActivity {
    private static final String LOG_TAG = "Text API";
    private static final int PHOTO_REQUEST = 10;
    private static final int REQUEST_WRITE_PERMISSION = 20;
    private static final String SAVED_INSTANCE_RESULT = "result";
    private static final String SAVED_INSTANCE_URI = "uri";
    private TextRecognizer detector;
    private Uri imageUri;
    private TextView scanResults;

    /* developer by  sabbir */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.doc_layout);
        Button button = (Button) findViewById(R.id.button);
        TextView textView = (TextView) findViewById(R.id.results);
        this.scanResults = textView;
        if (bundle != null) {
            textView.setText(bundle.getString(SAVED_INSTANCE_RESULT));
        }
        this.detector = new TextRecognizer.Builder(getApplicationContext()).build();
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ActivityCompat.requestPermissions(DocActivity.this, new String[]{"android.permission.WRITE_EXTERNAL_STORAGE"}, 20);
            }
        });
    }

    public void onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
        super.onRequestPermissionsResult(i, strArr, iArr);
        if (i == 20) {
            if (iArr.length <= 0 || iArr[0] != 0) {
                Toast.makeText(this, "Permission Denied!", Toast.LENGTH_SHORT).show();
            } else {
                takePicture();
            }
        }
    }

    /* developer by  sabbir */
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i == 10 && i2 == -1) {
            launchMediaScanIntent();
            try {
                Uri data = intent.getData();
                this.imageUri = data;
                Bitmap decodeBitmapUri = decodeBitmapUri(this, data);
                if (!this.detector.isOperational() || decodeBitmapUri == null) {
                    this.scanResults.setText("Could not set up the detector!");
                    return;
                }
                SparseArray<TextBlock> detect = this.detector.detect(new Frame.Builder().setBitmap(decodeBitmapUri).build());
                String str = "";
                String str2 = str;
                String str3 = str2;
                for (int i3 = 0; i3 < detect.size(); i3++) {
                    TextBlock valueAt = detect.valueAt(i3);
                    str = str + valueAt.getValue() + "\n\n";
                    for (Text text : valueAt.getComponents()) {
                        str2 = str2 + text.getValue() + "\n";
                        for (Text value : text.getComponents()) {
                            str3 = str3 + value.getValue() + ", ";
                        }
                    }
                }
                if (detect.size() == 0) {
                    this.scanResults.setText("Scan Failed: Found nothing to scan");
                    return;
                }
                this.scanResults.setText(this.scanResults.getText() + "Blocks: \n");
                this.scanResults.setText(this.scanResults.getText() + str + "\n");
                this.scanResults.setText(this.scanResults.getText() + "---------\n");
                this.scanResults.setText(this.scanResults.getText() + "Lines: \n");
                this.scanResults.setText(this.scanResults.getText() + str2 + "\n");
                this.scanResults.setText(this.scanResults.getText() + "---------\n");
                this.scanResults.setText(this.scanResults.getText() + "Words: \n");
                this.scanResults.setText(this.scanResults.getText() + str3 + "\n");
                this.scanResults.setText(this.scanResults.getText() + "---------\n");
            } catch (Exception e) {
                Toast.makeText(this, "Failed to load Image", Toast.LENGTH_SHORT).show();
                Log.e(LOG_TAG, e.toString());
            }
        }
    }

    private void takePicture() {
        startActivityForResult(new Intent("android.intent.action.PICK", MediaStore.Images.Media.EXTERNAL_CONTENT_URI), 10);
    }

    /* developer by  sabbir */
    public void onSaveInstanceState(Bundle bundle) {
        Uri uri = this.imageUri;
        if (uri != null) {
            bundle.putString(SAVED_INSTANCE_URI, uri.toString());
            bundle.putString(SAVED_INSTANCE_RESULT, this.scanResults.getText().toString());
        }
        super.onSaveInstanceState(bundle);
    }

    private void launchMediaScanIntent() {
        Intent intent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
        intent.setData(this.imageUri);
        sendBroadcast(intent);
    }

    public String getRealPathFromURI(Uri uri, Activity activity) {
        Cursor managedQuery = activity.managedQuery(uri, new String[]{"_data"}, (String) null, (String[]) null, (String) null);
        if (managedQuery == null) {
            return null;
        }
        int columnIndexOrThrow = managedQuery.getColumnIndexOrThrow("_data");
        if (managedQuery.moveToFirst()) {
            return managedQuery.getString(columnIndexOrThrow);
        }
        return null;
    }

    private Bitmap decodeBitmapUri(Context context, Uri uri) throws FileNotFoundException {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(context.getContentResolver().openInputStream(uri), (Rect) null, options);
        int min = Math.min(options.outWidth / 600, options.outHeight / 600);
        options.inJustDecodeBounds = false;
        options.inSampleSize = min;
        return BitmapFactory.decodeStream(context.getContentResolver().openInputStream(uri), (Rect) null, options);
    }
}
