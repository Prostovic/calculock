package ru.izcom.calculock;

import java.util.Random;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends Activity {
	private int nResult = 0,     // результат примера
	            countR = 0,      // кол-во правильных ответов
	            countW = 0,      // кол-во ошибок
	            nMaxPrimer = 3,  // кол-во примеров в тесте
	            nCouPrimer = 0;  // счетчик примеров
	private long tStart = 0L;

	OnClickListener getAnsver;  // обработчик ответов
	View oView;                 // view для фрагмента
	TextView tvTime,            // text view для времени
	         tvResult,          // text view для отображения правильных и неправильных ответов
	         tvPrimer;          // text view для примера
	LinearLayout lAnswer;       // для кнопок ответов
	Button bStart;              // кнопка старта
	final String LOG_TAG = "MainActivity";

    Handler timerHandler = new Handler();
    Runnable timerRunnable = new Runnable() {
        @Override
        public void run() {
            long millis = System.currentTimeMillis() - tStart;
            int seconds = (int) (millis / 1000);
            int minutes = seconds / 60;
            seconds = seconds % 60;

            tvTime.setText(String.format("%02d:%02d", minutes, seconds));

            timerHandler.postDelayed(this, 500);
        }
    };

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
		
		getAnsver = new OnClickListener() {
			@Override
			public void onClick(View v) {
				int nVal = Integer.parseInt(((Button)v).getText().toString());
				if( nVal == nResult ) {
					countR++;
					outResult(countR, countW);
					if( nCouPrimer < nMaxPrimer ) {
						showPrimer();
					}
					else {
						showItog();
						clearScreen();
					}
				}
				else {
					countW++;
					outResult(countR, countW);
				}
			}
		};
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		MenuItem mi = menu.add(0, 1, 0, getResources().getString(R.string.action_operate));
		mi.setIntent(new Intent(this, PreferenceOperateActivity.class));
		return super.onCreateOptionsMenu(menu);
//		getMenuInflater().inflate(R.menu.main, menu);
//		return true;
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		setViewData();
		clearScreen();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		Boolean bDone = false;
		switch (id) {
			case R.id.action_numbers:
				bDone = true;
				break;
	
			case R.id.action_operate:
				bDone = true;
				break;
	
			default:
				break;
		}
		return bDone ? bDone : super.onOptionsItemSelected(item);
	}
	
	public void setViewData() {
		Fragment oFr = getFragmentManager().findFragmentById(R.id.container); 
		oView = oFr.getView(); 
		tvTime = (TextView) oView.findViewById(R.id.textTime);
		tvResult = (TextView) oView.findViewById(R.id.textResult);
		lAnswer = (LinearLayout) oView.findViewById(R.id.answer);
		tvPrimer = (TextView) oView.findViewById(R.id.textPrimer);
		bStart = (Button) oView.findViewById(R.id.btnStart);
		bStart.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startNewTest();
			}
		});
	}

	private void outResult(int nR, int nW) {
		tvResult.setText(String.format("%02d - %02d", nR, nW));
//		Log.i(LOG_TAG, "outResult("+nR+", "+nW+")");
	}

	public void clearScreen() {
		stopTimer();
		tvTime.setText(String.format("%02d:%02d", 0, 0));
		outResult(0, 0);
		lAnswer.removeAllViews();
		tvPrimer.setText("");
	}
	
	public void startNewTest() {
		countR = 0;
		countW = 0;
		nCouPrimer = 0;
		bStart.setEnabled(false);
		startTimer();
		showPrimer();
	}

	public void showItog() {
		long nTime = stopTimer(),
		     dt = nTime / 1000,
		     nMin = dt / 60,
		     nSec = dt % 60;
		String sRes = String.format("%02d:%02d", nMin, nSec);
		sRes = getResources().getString(R.string.resultTime) + ": " + sRes;
		sRes += ", " + getResources().getString(R.string.resultOk) + ": " + countR;
		sRes += ", " + getResources().getString(R.string.resultWrong) + ": " + countW;
		sRes += ". " + getResources().getString(R.string.resultMore) + "?";
//		Log.i(LOG_TAG, sRes);
		ResultDialog dlg1;
		dlg1 = new ResultDialog();
		dlg1.sText = sRes;
		dlg1.show(getFragmentManager(), "tag");
		bStart.setEnabled(true);
	}

	public void showPrimer() {
		int v1 = randInt(1, 9), v2 = randInt(1, 9), n = randInt(1, 7);
		int [] aResult = {0, 0, 0, 0, 0, 0};
		int wrapContent = LinearLayout.LayoutParams.WRAP_CONTENT;
		LinearLayout.LayoutParams lParams = new LinearLayout.LayoutParams(wrapContent, wrapContent);
		lParams.gravity = Gravity.CENTER_HORIZONTAL;
		LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f);
		param.setMargins(0, 0, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2, getResources().getDisplayMetrics()), 0);
		
		aResult[0] = v1 + v2;
		nResult = aResult[0];
		for(int i = aResult.length - 1; i > 0; i--) {
			n = randInt(1, 7);
			aResult[i] = (aResult[0] > n) ? (aResult[0] - n) : (aResult[0] - n);  
//			Log.i(LOG_TAG, "aResult["+i+"] = " + aResult[i]);
		}
		tvPrimer.setText(v1 + " + " + v2);
		ShuffleArray(aResult);
		lAnswer.removeAllViews();
		for(int k : aResult) {
			Button oButton = new Button(this);
//			Button oButton = new Button(this, null, R.drawable.button_shape);
//			android:background="@drawable/button_shape"
			oButton.setText(k + "");
//			Log.i(LOG_TAG, "button = " + k);
			oButton.setOnClickListener(getAnsver);
			oButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_shape));
			oButton.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
			oButton.setLayoutParams(param);
			lAnswer.addView(oButton);
//			Log.i(LOG_TAG, "button fin = " + k);
		}
		nCouPrimer++;
	}

	public int randInt(int min, int max) {

	    // Usually this should be a field rather than a method variable so
	    // that it is not re-seeded every call.
	    Random rand = new Random();

	    // nextInt is normally exclusive of the top value,
	    // so add 1 to make it inclusive
	    int randomNum = rand.nextInt((max - min) + 1) + min;

	    return randomNum;
	}

	private void ShuffleArray(int[] array)
	{
	    int index;
	    Random random = new Random();
	    for (int i = array.length - 1; i > 0; i--)
	    {
	        index = random.nextInt(i + 1);
	        if (index != i)
	        {
	            array[index] ^= array[i];
	            array[i] ^= array[index];
	            array[index] ^= array[i];
	        }
	    }
	}
	
	private void startTimer() {
		tStart = System.currentTimeMillis();
		timerHandler.postDelayed(timerRunnable, 0);
	}
	
	private long stopTimer() {
        timerHandler.removeCallbacks(timerRunnable);
        return System.currentTimeMillis() - tStart; 
	}
	
	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			return rootView;
		}
		
	}

}
