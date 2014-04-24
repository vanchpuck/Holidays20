package com.jonnygold.holidays.fullcalendar;

import java.util.Arrays;
import java.util.Collection;

import android.content.Context;
import android.widget.Toast;

import com.jonnygold.holidays.fullcalendar.holiday.Holiday;
import com.perm.kate.api.Api;

public class VKShareMaster {

	public static final String APP_ID = "4319903";
	
	private Context context;
	
	private VKAccount account;
	
	private Api api;
	
	public VKShareMaster(Context context) {
		account = VKAccount.restore(context);
		this.context = context;
		api = new Api(account.getAccessToken(), APP_ID);
	}
	
	public boolean isAuthorized(){
		return (account.getAccessToken() != null);
	}
	
	public void authorize(VKAccount account){
		this.account = account;
		this.account.save(context);
	}
	
	public void logOut(){
		api = null;
		authorize(new VKAccount(null, 0));
	}
	
	public VKAccount getAccount(){
		return account;
	}
	
	public void postToWall(final Holiday holiday) {
		if(isAuthorized()){
	        new Thread(){
	            @Override
	            public void run(){
	                try {
	                	Collection<String> att = Arrays.asList(new String[]{holiday.getPicture().getVkPicture(), "https://play.google.com/store/apps/details?id=com.jonnygold.holidays.fullcalendar"});
	                    api.createWallPost(
	                    		account.getUserId(), holiday.getDate().toString()+
	                    		"\n\n"+holiday.getTitle().toUpperCase()+"\n\n"+holiday.getDescription(), 
	                    		att, null, false, false, false, null, null, null, null, null, null);
	                    
	                } catch (Exception e) {
	                    e.printStackTrace();
	                }
	            }
	        }.start();
		}
    }
	
}
