package ke.co.harambeeapps.privacypolicy;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.sufficientlysecure.htmltextview.HtmlResImageGetter;
import org.sufficientlysecure.htmltextview.HtmlTextView;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Created by 2ndgengod on 12/2/2019.
 */
public class NotePad extends AppCompatActivity implements View.OnClickListener{
    public HtmlTextView htmlTextView;
    private String html,spanned;
    public Button btnSave,btnCopy;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notepad);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        Intent intent = getIntent();
        html = Objects.requireNonNull(intent.getExtras()).getString("html");
        spanned = Objects.requireNonNull(intent.getExtras()).getString("txt");
        htmlTextView =findViewById(R.id.html_text);
        btnSave = findViewById(R.id.btnSave);
        btnCopy = findViewById(R.id.btnCopy);
        btnCopy.setOnClickListener(this);
        btnSave.setOnClickListener(this);
        displayText(html);
      // Toast.makeText(getApplicationContext(),spanned,Toast.LENGTH_LONG).show();
    }

    public void generateNoteOnSD(Context context, String sFileName, String sBody) {
        try {
            File root = new File(Environment.getExternalStorageDirectory(), "PrivacyPolicy");
            if (!root.exists()) {
                root.mkdirs();
            }
            File gpxfile = new File(root, sFileName);
            FileWriter writer = new FileWriter(gpxfile);
            writer.append(sBody);
            writer.flush();
            writer.close();
            Toast.makeText(getApplicationContext(),"Saved to PrivacyPolicy folder",Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    void displayText(String text){
// loads html from string
        htmlTextView.setHtml(text,
                new HtmlResImageGetter(getApplicationContext()));
    }
    void copyToClipBoard(String label,String text){
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText(label, text);
        assert clipboard != null;
        clipboard.setPrimaryClip(clip);
        Toast.makeText(getApplicationContext(),"Copied Successfully",Toast.LENGTH_LONG).show();
    }
    void launchTextReader(){
        Intent intent = new Intent(Intent.ACTION_EDIT);
        //  Uri uri = Uri.parse("file:///sdcard/PrivacyPolicy/policy.txt");
        //    Uri uri1 = Uri.parse(Environment.getExternalStorageDirectory()+"PrivacyPolicy/policy.txt");
        //    intent.setDataAndType(uri, "text/plain");
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSave: {
        generateNoteOnSD(getApplicationContext(),"Privacypolicy",html);

            }
            break;
            case R.id.btnCopy:{
            copyToClipBoard("Privacypolicy",spanned);
            }
            break;
        }
    }

    public void onBackPressed(){
        finish();
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);

    }
}
