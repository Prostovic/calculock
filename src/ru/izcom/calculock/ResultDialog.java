package ru.izcom.calculock;

import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

public class ResultDialog extends DialogFragment implements OnClickListener {
	  final String LOG_TAG = "ResultDialog";

	  public View onCreateView(LayoutInflater inflater, ViewGroup container,
	      Bundle savedInstanceState) {
	    getDialog().setTitle(getResources().getText(R.string.resultTitle));
	    View v = inflater.inflate(R.layout.resultdialog, null);
	    v.findViewById(R.id.btnYes).setOnClickListener(this);
	    v.findViewById(R.id.btnNo).setOnClickListener(this);
	    v.findViewById(R.id.btnMaybe).setOnClickListener(this);
	    return v;
	  }

	  public void onClick(View v) {
		int id = ((Button) v).getId(); 
	    Log.d(LOG_TAG, "ResultDialog 1: " + id + " " + ((Button) v).getText());
	    MainActivity oAct = (MainActivity) getActivity();
	    switch (id) {
		case R.id.btnYes:
			oAct.startNewTest();
			break;

		case R.id.btnNo:
			oAct.finish();
			break;

		case R.id.btnMaybe:
			break;

		default:
			break;
		}
	    dismiss();
	  }

	  public void onDismiss(DialogInterface dialog) {
	    super.onDismiss(dialog);
	    Log.d(LOG_TAG, "ResultDialog 1: onDismiss");
	  }

	  public void onCancel(DialogInterface dialog) {
	    super.onCancel(dialog);
	    Log.d(LOG_TAG, "ResultDialog 1: onCancel");
	  }
}
