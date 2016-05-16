package io.github.mudassir.notificationexample;

import android.content.Context;
import android.os.Bundle;
import android.app.NotificationManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
  * Class built to respond to the user clicking on the notification (and not the action)
  */
public class ResultActivity extends AppCompatActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_result);

		// Set text from intent
		((TextView) findViewById(R.id.result_txt)).setText(getIntent().getStringExtra(MainActivity.INTENT_STRING_KEY));

		// Dismiss notification
		((NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE)).cancel(getIntent().getIntExtra(MainActivity.INTENT_INT_KEY, -1));
	}
}
