package com.orange.plugin;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import cn.jpush.android.api.BasicPushNotificationBuilder;
import cn.jpush.android.api.JPushInterface;

public class PushReceiverClient extends BroadcastReceiver {
	
	public static boolean isGroupSilent = false;
	public static boolean isSingleSilent = false;
	
	private static final String TAG = "PushReceiverClient";
	@Override
	public void onReceive(Context context, Intent intent) {
		BasicPushNotificationBuilder builder = new BasicPushNotificationBuilder(context);
  		builder.notificationDefaults = Notification.DEFAULT_LIGHTS;
  		JPushInterface.setDefaultPushNotificationBuilder(builder);
  		
		Bundle bundle = intent.getExtras();
		Log.d(TAG, "bundle=" + bundle);
		int noId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
		String contentTitle = bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE);
		String contentText = bundle.getString(JPushInterface.EXTRA_ALERT);
		
		//cn.jpush.android.NOTIFICATION_CONTENT_TITLE
		if (bundle.containsKey(JPushInterface.EXTRA_NOTIFICATION_TITLE)) {
			if (bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE).equals("Ⱥ��Ϣ")){
				if (isGroupSilent) {
					sendSilentNotification(context, noId, contentTitle, contentText);
				} else {
					sendNotification(context, noId, contentTitle, contentText);
				}
				
			} else if (bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE).equals("��Ϣ")){
				if (isSingleSilent) {
					sendSilentNotification(context, noId, contentTitle, contentText);
				} else {
					sendNotification(context, noId, contentTitle, contentText);
				}
				
			}
		}
	
	}
	
	/**
	 * ���ʹ�������֪ͨ
	 * @param context
	 * @param notificationId
	 * @param contentTitle
	 * @param contentText
	 */
	private void sendNotification(Context context, int notificationId, String contentTitle, String contentText) {
		//����NotificationManager
        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = wrapNotification(context, contentTitle, contentText);
        notification.defaults = Notification.DEFAULT_VIBRATE | Notification.DEFAULT_SOUND ;
        //��mNotificationManager��notify����֪ͨ�û����ɱ�������Ϣ֪ͨ
        mNotificationManager.notify(notificationId, notification);
	}
	
	/**
	 * ���Ͳ���������֪ͨ
	 * @param context
	 * @param notificationId
	 * @param contentTitle
	 * @param contentText
	 */
	private void sendSilentNotification(Context context, int notificationId, String contentTitle, String contentText) {
		//����NotificationManager
        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = wrapNotification(context, contentTitle, contentText);
        notification.defaults = Notification.DEFAULT_VIBRATE ;
        //��mNotificationManager��notify����֪ͨ�û����ɱ�������Ϣ֪ͨ
        mNotificationManager.notify(notificationId, notification);
	}
	
	/**
	 * ֪ͨ��װ��
	 * @param context
	 * @param contentTitle
	 * @param contentText
	 * @return
	 */
	private Notification wrapNotification(Context context, String contentTitle, String contentText) {
		//����֪ͨ��չ�ֵ�������Ϣ
        int icon = context.getResources().getIdentifier("icon", "drawable", context.getPackageName());
        Log.d(TAG, "icon id="+icon);
        CharSequence tickerText = contentTitle;//֪ͨ������
        Notification notification = new Notification(icon, tickerText, System.currentTimeMillis());
      
        //��������֪ͨ��ʱҪչ�ֵ�������Ϣ
        Intent notificationIntent = new Intent(context, PushReceiverClient.class);
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0,notificationIntent, 0);
        notification.setLatestEventInfo(context, contentTitle, contentText,contentIntent); 
        return notification;
	}

}