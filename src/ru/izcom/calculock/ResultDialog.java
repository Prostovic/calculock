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
import android.widget.TextView;

public class ResultDialog extends DialogFragment implements OnClickListener {
	  final String LOG_TAG = "ResultDialog";
	  public String sText = "";

	  public View onCreateView(LayoutInflater inflater, ViewGroup container,
	      Bundle savedInstanceState) {
	    getDialog().setTitle(getResources().getText(R.string.resultTitle));
	    View v = inflater.inflate(R.layout.resultdialog, null);
	    ((TextView) v.findViewById(R.id.resultDialogTextView)).setText(sText);
	    v.findViewById(R.id.btnYes).setOnClickListener(this);
	    v.findViewById(R.id.btnNo).setOnClickListener(this);
	    return v;
	  }

	  public void onClick(View v) {
		int id = ((Button) v).getId(); 
	    MainActivity oAct = (MainActivity) getActivity();
	    switch (id) {
			case R.id.btnYes:
				break;
	
			case R.id.btnNo:
				oAct.finish();
				break;
	
			default:
				break;
		}
	    dismiss();
	  }

}
