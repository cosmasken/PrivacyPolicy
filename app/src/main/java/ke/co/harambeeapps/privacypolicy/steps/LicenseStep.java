package ke.co.harambeeapps.privacypolicy.steps;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.widget.AppCompatSpinner;
import ernestoyaquello.com.verticalstepperform.Step;

/**
 * Created by 2ndgengod on 2/28/2020.
 */
public class LicenseStep extends Step<String> implements AdapterView.OnItemSelectedListener {

    private Spinner licenses;
    String[] licenselist = {"open source", "Free", "Freemium", "Ad supported", "Commercial"};
    String mLicense,nLicense;

    public LicenseStep(String stepTitle) {
        super(stepTitle);
    }

    @Override
    protected View createStepContentLayout() {
        // Here we generate the view that will be used by the library as the content of the step.
        // In this case we do it programmatically, but we could also do it by inflating an XML layout.
        licenses = new AppCompatSpinner(getContext());
        // licenses.setOnItemSelectedListener(this);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, licenselist);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        licenses.setAdapter(arrayAdapter);

        return licenses;
    }

    @Override
    protected IsDataValid isStepDataValid(String stepData) {
        // The step's data (i.e., the user name) will be considered valid only if it is longer than
        // three characters. In case it is not, we will display an error message for feedback.
        // In an optional step, you should implement this method to always return a valid value.
        boolean isNameValid = stepData.length() >= 3;
        String errorMessage = !isNameValid ? "3 characters minimum" : "";

        return new IsDataValid(isNameValid, errorMessage);
    }

    @Override
    public String getStepData() {
        // We get the step's data from the value that the user has selected from the spinner/dropdown.

      //  mLicense = licenses.toString();
mLicense = licenses.getSelectedItem().toString();
        return mLicense != null ? mLicense.toString() : "";
    }

    @Override
    public String getStepDataAsHumanReadableString() {
        // Because the step's data is already a human-readable string, we don't need to convert it.
        // However, we return "(Empty)" if the text is empty to avoid not having any text to display.
        // This string will be displayed in the subtitle of the step whenever the step gets closed.
         mLicense = getStepData();
        return !mLicense.isEmpty() ? mLicense : "(Empty)";
    }

    @Override
    protected void onStepOpened(boolean animated) {
        // This will be called automatically whenever the step gets opened.
    }

    @Override
    protected void onStepClosed(boolean animated) {
        // This will be called automatically whenever the step gets closed.
    }

    @Override
    protected void onStepMarkedAsCompleted(boolean animated) {
        // This will be called automatically whenever the step is marked as completed.
    }

    @Override
    protected void onStepMarkedAsUncompleted(boolean animated) {
        // This will be called automatically whenever the step is marked as uncompleted.
    }

    @Override
    public void restoreStepData(String stepData) {
        // To restore the step after a configuration change, we restore the text of its EditText view.
        //   licenses.setSelection();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        nLicense = licenselist[position];
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}