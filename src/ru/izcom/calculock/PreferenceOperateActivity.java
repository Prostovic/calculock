package ru.izcom.calculock;

import java.util.List;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;

public class PreferenceOperateActivity extends PreferenceActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		addPreferencesFromResource(R.xml.operate);
	}
	
	@Override
	public void onBuildHeaders(List<Header> target) {
		loadHeadersFromResource(R.xml.options_headers, target);
//		super.onBuildHeaders(target);
	}

    public static class OptionsOperateFragment extends PreferenceFragment {
    	public OptionsOperateFragment() {
		}

    	@Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            // Make sure default values are applied.  In a real app, you would
            // want this in a shared function that is used to retrieve the
            // SharedPreferences wherever they are needed.
//            PreferenceManager.setDefaultValues(getActivity(),
//                    R.xml.advanced_preferences, false); // android:defaultValue 
//            PreferenceManager.setDefaultValues( getActivity().getApplicationContext(), R.xml.options_operation, false); // android:defaultValue 
            
            // Load the preferences from an XML resource
            addPreferencesFromResource(R.xml.options_operation);
        }
    }


}
