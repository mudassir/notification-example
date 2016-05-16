package io.github.mudassir.notificationexample;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.RemoteInput;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
  * Class built to deal with replying to the notification.
  * Ideally this would be a BroadcastReceiver but yeah.
  */
public class ReplyActivity extends AppCompatActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reply);

		// Set text from intent
		CharSequence message = getMessageText(getIntent());
		((TextView) findViewById(R.id.reply_txt)).setText(message);

		/*
		 * Build a new notification, which informs the user that the system
		 * handled their interaction with the previous notification by
		 * replacing the current notification with the confirmation.
		 *
		 * This is supposed to be done from the BroadcastReceiver, and so the
		 * context used for the notification should be the context that was
		 * passed to the BroadcastReceiver.onReceive() method.
		 */
		Context context = this;
		Notification.Builder repliedNotification =
				new Notification.Builder(context)
						.setSmallIcon(R.drawable.ic_message)
						.setContentText("Reply sent");

		int notificationId = getIntent().getIntExtra(MainActivity.INTENT_INT_KEY, -1);
		NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		notificationManager.notify(notificationId, repliedNotification.build());
	}

	/**
	  * Obtain the intent that started this activity by calling Activity.getIntent()
	  * and pass it into this method to get the associated string.
	  */
	private CharSequence getMessageText(Intent intent) {
		Bundle remoteInput = RemoteInput.getResultsFromIntent(intent);
		if (remoteInput != null) {
			return remoteInput.getCharSequence(MainActivity.KEY_TEXT_REPLY);
		}
		return "No message received";
	}
}
