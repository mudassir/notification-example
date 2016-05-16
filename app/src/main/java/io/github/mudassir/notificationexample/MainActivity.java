package io.github.mudassir.notificationexample;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.RemoteInput;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

	/**
	  * Keys for the intent extras
	  */
	public static final String INTENT_STRING_KEY = "intent.string.key";
	public static final String INTENT_INT_KEY = "intent.int.key";

	/**
	  * Key for the string that's delivered in the action's intent.
 	  */
	public static final String KEY_TEXT_REPLY = "key_text_reply";

	/**
	  * Random notification ID
	  */
	private static final int replyNotificationId = 1234;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		findViewById(R.id.reply_btn).setOnClickListener(view -> sendReplyNotif());
	}

	/**
	  * Method to send a notification with the option to reply from within the notification.
	  *
	  * This is done by attaching a RemoteInput to an action and binding that to an intent.
	  */
	private void sendReplyNotif() {
		// Create an intent for the reply action
		Intent replyIntent = new Intent(this, ReplyActivity.class);
		replyIntent.putExtra(INTENT_INT_KEY, replyNotificationId);
		PendingIntent replyPendingIntent =
				PendingIntent.getActivity(this, 0, replyIntent, PendingIntent.FLAG_UPDATE_CURRENT);

		// Generate reply action
		RemoteInput remoteInput = new RemoteInput.Builder(KEY_TEXT_REPLY)
				.setLabel("Type some text here")
				.build();

		Notification.Action action =
				new Notification.Action.Builder(R.drawable.ic_reply,
						"Reply", replyPendingIntent)
						.addRemoteInput(remoteInput)
						.build();

		// Pending intent for when user clicks on the notification, not reply action
		Intent resultIntent = new Intent(this, ResultActivity.class);
		resultIntent.putExtra(INTENT_STRING_KEY, "Result activity from reply notification");
		resultIntent.putExtra(INTENT_INT_KEY, replyNotificationId);
		PendingIntent resultPendingIntent =
				PendingIntent.getActivity(this, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);

		// Build the notification with both intents
		Notification.Builder newMessageNotificationBuilder =
				new Notification.Builder(this)
						.setSmallIcon(R.drawable.ic_message)
						.setContentTitle("Title")
						.setContentText("Content")
						.setContentIntent(resultPendingIntent)
						.addAction(action)
						// Add the history showing previous messages
						// This method not available on NotificationCompat
						.setRemoteInputHistory(new CharSequence[] {
							"Message 1",
							"Message 2"
						});

		// Show the notification
		NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		notificationManager.notify(replyNotificationId, newMessageNotificationBuilder.build());
	}
}
