package ke.co.harambeeapps.privacypolicy;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import org.sufficientlysecure.htmltextview.HtmlTextView;

import java.util.Objects;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.text.HtmlCompat;
import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressFlower;

import static ke.co.harambeeapps.privacypolicy.R.id.individualRb;

public class Details extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {
    public static final String TAG = "DETAILSACTIVITY";
    private static final int PERMISSION_REQUEST_CODE = 1000;
    public ACProgressFlower dialog;
    public String companyName, websiteName, licenseType, weborApp, myorOur, iorWe, meorUs;
    public String htmlStart = "<html>";
    public String htmlEnd = "</html>";
    public String htmlBodyStart = "<body>";
    public String htmlBodyEnd = "</body>";
    public String htmlHeadingStart = "<h2>";
    public String htmlHeadingEndt = "</h2>";
    public String htmlParagraphStart = "<p>";
    public String htmlParagraphEnd = "</p>";
    public String htmUnorderedListStart = "<ul>";
    public String htmUnorderedListEnd = "</ul>";
    public String listItemStart = "<li>";
    public String listItemEnd = "</li>";
    public EditText txtName, txtWebsite;
    public CardView beforeGenerate, afterGenerate;
    public LinearLayout layoutButtons;
    public HtmlTextView htmlTextView;
    public RadioGroup radioTypeGroup, radioIorCompany;
    public RadioButton appRb, compRb;
    public Button btnGenerate, btnCopy, btnSave;
    public Spanned spanned;
    public  String txt;
    Menu mainmenu;
    String[] country = {"open source", "Free", "Freemium", "Ad supported", "Commercial"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        overridePendingTransition(0, 0);
        View relativeLayout = findViewById(R.id.mainLayout);
        Animation animation = AnimationUtils.loadAnimation(this, android.R.anim.fade_in);
        relativeLayout.startAnimation(animation);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        isStoragePermissionGranted();
        initView();

    }

    void initView() {
        final Spinner spinner = findViewById(R.id.licences);
        spinner.setOnItemSelectedListener(this);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, country);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        txtName = findViewById(R.id.txtName);
        txtWebsite = findViewById(R.id.txtWebsite);
        btnGenerate = findViewById(R.id.btnGenerate);
        radioTypeGroup = findViewById(R.id.radioTypeGroup);
        radioIorCompany = findViewById(R.id.radioIorCompany);
        radioTypeGroup.clearCheck();
        radioIorCompany.clearCheck();
        radioTypeGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = group.findViewById(checkedId);
                if (null != rb && rb.isChecked()) {
                    weborApp = rb.getText().toString();
                }


            }
        });
        radioIorCompany.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == individualRb) {
                    iorWe = "I";
                    myorOur = "My";
                    meorUs = "Me";
                } else {
                    iorWe = "We";
                    myorOur = "Our";
                    meorUs = "Us";
                }


            }
        });

        btnGenerate.setOnClickListener(this);

    }

    public String createHtmlCode(String companyName, String appname, String licenseType, String my, String we, String us, String type) {
        return htmlStart + htmlBodyStart + htmlHeadingStart + getString(R.string.heading) + htmlHeadingEndt + htmlParagraphStart +
                companyName + " built the " + appname + weborApp+" as an " + licenseType + " " + type + " for use as is" + htmlParagraphStart + "This" +
                " page is used to inform website visitors regarding " + my + " policies with the collection, use, and " +
                "disclosure of Personal Information if anyone decided to use " + my + " Service." + htmlHeadingEndt +
                htmlParagraphStart + "If you choose to use " + my + " Service, then you agree to the collection and use of information in" +
                " relation with this policy. The Personal Information that " + we + " collect are used for providing and" +
                " improving the Service." + we + " will not use or share your information with anyone except as described " +
                "in this Privacy Policy." + htmlHeadingEndt + htmlParagraphStart + "The terms used in this Privacy Policy have the same meanings as in our Terms and Conditions," +
                " which is accessible at " + appname + ", unless otherwise defined in this Privacy Policy." + htmlHeadingEndt +
                htmlParagraphStart + "<strong>Information Collection and Use</strong>" + htmlHeadingEndt +
                htmlParagraphStart + "For a better experience while using our Service, " + we + " may require you to provide us with certain " +
                "personally identifiable information, including but not limited to users name , address , location $ pictures." +
                " The information that " + we + " request is retained on your device and is not " +
                "collected by " + us + " in any way]|[will be retained by us and used as described in this privacy policy." + htmlHeadingEndt +
                htmlParagraphStart + " The app does use third party services that may collect information used to identify you. e.g Google ,Facebook." + htmlParagraphStart + "<strong>Log Data</strong>" + htmlHeadingEndt +
                htmlParagraphStart + we + " want to inform you that whenever you use " + my + " Service, in case of an error in the app " + we +
                " collect data and information (through third party products) on your phone called Log Data. This Log Data" +
                " may include information such as your devices’s Internet Protocol (“IP”) address, device name," +
                "  operating system version, configuration of the app when utilising" + my + "Service, the time and date " +
                "of your use of the Service, and other statistics." + htmlHeadingEndt +

                htmlParagraphStart + "<strong>Cookies</strong>" + htmlHeadingEndt + htmlParagraphStart + "Cookies are files with small amount of data that is commonly used as " +
                "an anonymous unique identifier.These are sent to your browser from the website that you visit and are stored on your devices’s " +
                "internal memory." + htmlHeadingEndt + htmlParagraphStart + "This Services does not uses these “cookies” explicitly. However, the app may use third party code " +
                "and libraries that use “cookies” to collection information and to improve their services. You have the option to either accept or refuse these " +
                "cookies, and know when a cookie is being sent to your device. If you choose to refuse our cookies, you may not be able to use some portions of" +
                " this Service." + htmlHeadingEndt + htmlParagraphStart + "<strong>Service Providers</strong>" + htmlHeadingEndt + htmlParagraphStart + we +
                " may employ third-party companies and individuals due to the following reasons:" + htmlHeadingEndt + htmUnorderedListStart +
                listItemStart + "To facilitate our Service;" + listItemEnd +
                listItemStart + "To provide the Service on our behalf;" + listItemEnd +
                listItemStart + "To perform Service-related services; or" + listItemEnd +
                listItemStart + "To assist us in analyzing how our Service is used." + listItemEnd + htmUnorderedListEnd +
                htmlParagraphStart + we + " want to inform users of this Service that these third parties have access to your Personal Information. The reason is to perform the " +
                "tasks assigned to them on our behalf. However, they re obligated not to disclose or use the information for any other purpose." + htmlParagraphEnd +
                htmlParagraphStart + "<strong>Security</strong>" + htmlParagraphEnd + htmlParagraphStart + we + " value your trust in providing us your Personal Information, thus we are " +
                "striving to use commercially acceptable means of protecting it. But remember that no method of transmission over the internet, or method of electronic storage " +
                "is 100% secure and reliable, and" + we + "cannot guarantee its absolute security." + htmlParagraphEnd + htmlParagraphStart + "<strong>Links to Other Sites</strong>"
                + htmlParagraphEnd + htmlParagraphStart + "This Service may contain links to other sites. If you click on a third-party link, you will be directed to that site." +
                " Note that these external sites are not operated by " + us + ". Therefore, I strongly advise you to review the Privacy Policy of these websites. " + we + " have no control" +
                " over, and assume no responsibility for the content, privacy policies, or practices of any third-party sites or services." + htmlParagraphEnd + htmlParagraphStart +
                "<strong>Children’s Privacy</strong>" + htmlParagraphEnd + htmlParagraphStart + "This Services do not address anyone under the age of 13." + we + " do not knowingly collect" +
                " personal identifiable information from children under 13. In the case " + we + " discover that a child under 13 has provided " + us + " with personal information, " +
                we + " immediately delete this from our servers. If you are a parent or guardian and you are aware that your child has provided us with personal information, please " +
                "contact " + us + " so that " + we + " will be able to do the " + "necessary actions." + htmlParagraphEnd + htmlParagraphStart + "<strong>Changes to " +
                "This Privacy Policy</strong>" + htmlParagraphEnd + htmlParagraphStart + we + " may update our Privacy Policy from time to time. Thus, you are advised to review this page " +
                "periodically for any changes." + we + " will notify you of any changes by " + "posting the new Privacy Policy on this page. These changes are effective immediately, after" +
                " they are posted on this page." + htmlParagraphEnd + htmlParagraphStart + "<strong>Contact Us</strong>" + htmlParagraphEnd + htmlParagraphStart + "If you have any questions" +
                " or suggestions about Privacy Policy, do not " +
                "hesitate to contact" + us + htmlParagraphEnd + htmlParagraphStart+"Made by khiuware.ca "+htmlParagraphEnd+htmlBodyEnd + htmlEnd;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void isStoragePermissionGranted() {
        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED) {
            Log.v(TAG, "Permission is granted");
        } else {

            Log.v(TAG, "Permission is revoked");
            Toast.makeText(getApplicationContext(), "REQUEST", Toast.LENGTH_LONG).show();
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Log.v(TAG, "Permission: " + permissions[0] + "was " + grantResults[0]);
            //resume tasks needing this permission
        }
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        licenseType = country[position];
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnGenerate) {
            companyName = txtName.getText().toString();
            websiteName = txtWebsite.getText().toString();
            final String west = createHtmlCode(companyName, websiteName, licenseType, myorOur, iorWe, meorUs, weborApp);
            spanned = HtmlCompat.fromHtml(west, HtmlCompat.FROM_HTML_SEPARATOR_LINE_BREAK_PARAGRAPH);
            txt =spanned.toString();
            if (TextUtils.isEmpty(companyName)) {
                txtName.setError("Please Enter a name!");
            } else if (TextUtils.isEmpty(websiteName)) {
                txtWebsite.setError("Please Enter Url or App Name!");
            } else {
                dialog = new ACProgressFlower.Builder(this)
                        .direction(ACProgressConstant.DIRECT_ANTI_CLOCKWISE)
                        .themeColor(Color.parseColor("#9a0007"))
                        .fadeColor(Color.RED).build();
                dialog.setCanceledOnTouchOutside(true);
                dialog.show();
                Intent intentMain = new Intent(Details.this,
                        NotePad.class);
                intentMain.putExtra("html", west);
                intentMain.putExtra("txt",txt);
                Details.this.startActivity(intentMain);
                Thread t = new Thread() {
                    public void run() {
                        try {
                            sleep(15);
                        } catch (InterruptedException ie) {
                            ie.printStackTrace();
                        } finally {
                            dialog.cancel();

                        }
                    }
                };
                t.start();


            }
        }
    }


}
