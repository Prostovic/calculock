package ru.izcom.calculock;

import android.app.Application;
import android.preference.PreferenceManager;

public class CalcApplication extends Application {

		  @Override
		  public void onCreate() {
		    super.onCreate();
		    PreferenceManager.setDefaultValues(this, R.xml.options_operation, false);
		  }
		  
}
