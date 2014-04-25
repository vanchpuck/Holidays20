package com.jonnygold.holidays.fullcalendar.vk;

import java.io.Serializable;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import android.util.Log;

public class VKAccount implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7978025533736143333L;
	
	private final String accessToken;
	private final long userId;
	
	public VKAccount(String accessToken, long userId){
		this.accessToken = accessToken;
		this.userId = userId;
	}
	
	public void save(Context context){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        Editor editor=prefs.edit();
        Log.w("VKAccount", "TOKEN: "+getAccessToken());
		Log.w("VKAccount", "USER: "+getUserId());
        editor.putString("access_token", getAccessToken());
        editor.putLong("user_id", getUserId());
        editor.commit();
    }
    
    public static VKAccount restore(Context context){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);

        String token = prefs.getString("access_token", null);
        long user = prefs.getLong("user_id", 0);
        
        return new VKAccount(token, user);
    }
    
    public String getAccessToken() {
		return accessToken;
	}
    
    public long getUserId() {
		return userId;
	}
	
}
