package ke.co.harambeeapps.privacypolicy;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import ernestoyaquello.com.verticalstepperform.VerticalStepperFormView;
import ernestoyaquello.com.verticalstepperform.listener.StepperFormListener;
import ke.co.harambeeapps.privacypolicy.steps.AppNameStep;
import ke.co.harambeeapps.privacypolicy.steps.LicenseStep;
import ke.co.harambeeapps.privacypolicy.steps.NameStep;

/**
 * Created by 2ndgengod on 2/28/2020.
 */
public class MainActivity extends AppCompatActivity implements StepperFormListener ,DialogInterface.OnClickListener {
    private ProgressDialog progressDialog;
    private  NameStep nameStep;
    private AppNameStep appNameStep;
    private LicenseStep licenseStep;
    private VerticalStepperFormView verticalStepperForm;
    public static final String STATE_NAME = "name";
    public static final String STATE_APP_NAME= "appname";
    public static final String STATE_LICENSE= "license";
    public static final String STATE_NEW_POLICY_CREATED = "new_policy_created";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.steps_layout);
        // Find the form view, set it up and initialize it.
        verticalStepperForm = findViewById(R.id.stepper_form);
        nameStep =new NameStep("Name");
        appNameStep = new AppNameStep("App Name");
        licenseStep = new LicenseStep("License Type");
        verticalStepperForm
                .setup(this, nameStep,appNameStep,licenseStep)
                .allowNonLinearNavigation(true)
                .displayBottomNavigation(false)
                .lastStepNextButtonText("Create Policy")
                .init();
    }
    @Override
    public void onCompletedForm() {
        final Thread dataSavingThread = saveData();

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(true);
        progressDialog.show();
        progressDialog.setMessage(getString(R.string.form_sending_data_message));
        progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                try {
                    dataSavingThread.interrupt();
                } catch (RuntimeException e) {
                    // No need to do anything here
                } finally {
                    verticalStepperForm.cancelFormCompletionOrCancellationAttempt();
                }
            }
        });
    }


    @Override
    public void onCancelledForm() {
        showCloseConfirmationDialog();
    }

    private Thread saveData() {

        // Fake data saving effect
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                    Intent intent = getIntent();
                    setResult(RESULT_OK, intent);
                    intent.putExtra(STATE_NEW_POLICY_CREATED, true);
                    intent.putExtra(STATE_NAME, nameStep.getStepData());
                    intent.putExtra(STATE_APP_NAME, appNameStep.getStepData());
                    intent.putExtra(STATE_LICENSE, licenseStep.getStepData());

                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();

        return thread;
    }

    private void finishIfPossible() {
        if(verticalStepperForm.isAnyStepCompleted()) {
            showCloseConfirmationDialog();
        } else {
            finish();
        }
    }

    private void showCloseConfirmationDialog() {
        new DiscardAlarmConfirmationFragment().show(getSupportFragmentManager(), null);
    }

    private void dismissDialogIfNecessary() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
        progressDialog = null;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home) {
            finishIfPossible();
            return true;
        }

        return false;
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int which) {
        switch (which) {

            // "Discard" button of the Discard Alarm dialog
            case -1:
                finish();
                break;

            // "Cancel" button of the Discard Alarm dialog
            case -2:
                verticalStepperForm.cancelFormCompletionOrCancellationAttempt();
                break;
        }
    }

    @Override
    public void onBackPressed(){
        finishIfPossible();
    }

    @Override
    protected void onPause() {
        super.onPause();

        dismissDialogIfNecessary();
    }

    @Override
    protected void onStop() {
        super.onStop();

        dismissDialogIfNecessary();
    }
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putString("name", nameStep.getStepData());
        savedInstanceState.putString("appname", appNameStep.getStepData());
        savedInstanceState.putString("license", licenseStep.getStepData());


        // IMPORTANT: The call to the super method must be here at the end.
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        if(savedInstanceState.containsKey("name")) {
            String name = savedInstanceState.getString("name");
            String appname = savedInstanceState.getString("appname");
            String license = savedInstanceState.getString("license");
            nameStep.restoreStepData(name);
            appNameStep.restoreStepData(appname);
            licenseStep.restoreStepData(license);
        }

        // IMPORTANT: The call to the super method must be here at the end.
        super.onRestoreInstanceState(savedInstanceState);
    }
    public static class DiscardAlarmConfirmationFragment extends DialogFragment {
        @Override
        @NonNull
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            MainActivity activity = (MainActivity)getActivity();
            if (activity == null) {
                throw new IllegalStateException("Fragment " + this + " not attached to an activity.");
            }

            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
            builder.setTitle(R.string.form_discard_question)
                    .setMessage(R.string.form_info_will_be_lost)
                    .setPositiveButton(R.string.form_discard, activity)
                    .setNegativeButton(R.string.form_discard_cancel, activity)
                    .setCancelable(false);
            Dialog dialog = builder.create();
            dialog.setCanceledOnTouchOutside(false);

            return dialog;
        }
    }
}
