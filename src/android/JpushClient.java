package com.orange.plugin;

import java.util.Set;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.LOG;
import org.json.JSONArray;
import org.json.JSONException;

import android.content.Context;
import android.util.Log;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

public class JpushClient extends CordovaPlugin {
	@Override
	public boolean execute(String action, JSONArray data, CallbackContext callbackContext) throws JSONException {
		
		Log.i("CordovaPlugin action", action);
		
		if (action.equals("register")) {
			register(data, callbackContext);
		} else if (action.equals("stop")) {
			stopReceive();
		}
		return true;
	}
	
	public void register(JSONArray data, final CallbackContext callbackContext) throws JSONException {
		Context context = cordova.getActivity().getApplicationContext();
		if (JPushInterface.isPushStopped(context)) {
			JPushInterface.resumePush(context);
		}
    	JPushInterface.setAlias(context, data.getString(0), new TagAliasCallback() {
			
			@Override
			public void gotResult(int arg0, String arg1, Set<String> arg2) {
				// TODO Auto-generated method stub
				LOG.i("CordovaPlugin TagAliasCallback", "" + arg0);
				LOG.i("CordovaPlugin TagAliasCallback", arg1);
				if (arg0 == 0) {
					callbackContext.success();
				} else {
					callbackContext.error(arg0);
				}
			}
		});
	}
	
	public void stopReceive()
	{
		Context context = cordova.getActivity().getApplicationContext();
		JPushInterface.stopPush(context);
	}
}
