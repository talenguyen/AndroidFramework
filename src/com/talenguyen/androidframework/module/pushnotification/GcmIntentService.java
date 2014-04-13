package com.talenguyen.androidframework.module.pushnotification;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.gcm.GoogleCloudMessaging;

/**
 * This {@code IntentService} does the actual handling of the GCM message.
 * {@code GcmBroadcastReceiver} (a {@code WakefulBroadcastReceiver}) holds a
 * partial wake lock for this service while the service does its work. When the
 * service is finished, it calls {@code completeWakefulIntent()} to release the
 * wake lock.
 */
public class GcmIntentService extends IntentService {
	
	public static final String TAG = "GCM Demo";
	
    public GcmIntentService() {
        super("GcmIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Bundle extras = intent.getExtras();
        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
        // The getMessageType() intent parameter must be the intent you received
        // in your BroadcastReceiver.
        String messageType = gcm.getMessageType(intent);

        if (!extras.isEmpty()) {  // has effect of unparcelling Bundle
        	handlePNSMessage(messageType, extras);
        }
        // Release the wake lock provided by the WakefulBroadcastReceiver.
        GcmBroadcastReceiver.completeWakefulIntent(intent);
    }

    /**
     * Handle PNS message that is pushed from server.
     * @param messageType The GCM message type.
     * @param data The data.
     */
    protected void handlePNSMessage(String messageType, Bundle data) {
    	if (GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE.equals(messageType)) {
    		onHandlePNSMessage(data);
    	}
    }
    
    /**
     * Handle when PNS message has been push successfully. 
     * @param data The data that is push from server.
     */
    protected void onHandlePNSMessage(Bundle data) {
    	
    }
}
