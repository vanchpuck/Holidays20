package com.jonnygold.holidays.fullcalendar.vk;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.Toast;

import com.jonnygold.holidays.fullcalendar.holiday.Holiday;
import com.perm.kate.api.Api;

public class VKShareMaster {

	public static final String APP_ID = "4319903";
	
	public static class PostStateReceiver extends BroadcastReceiver{
		@Override
		public void onReceive(Context context, Intent intent) {
			PostService.PostState response = 
	    			(PostService.PostState) intent.getExtras().getSerializable(PostService.UPDATE_STATUS);
	    	
			Toast.makeText(context, response.toString(), Toast.LENGTH_LONG).show();
		}		
	}
	
	private Context context;
	
	private VKAccount account;
	
	private Api api;
	
	public VKShareMaster(Context context) {
		account = VKAccount.restore(context);
		this.context = context;
		api = new Api(account.getAccessToken(), APP_ID);
		
		IntentFilter mStatusIntentFilter = new IntentFilter(
				PostService.BROADCAST_ACTION);

		PostStateReceiver postStateReceiver =
                new PostStateReceiver();
		
        LocalBroadcastManager.getInstance(context).registerReceiver(
        		postStateReceiver,
                mStatusIntentFilter);
	}
	
	public boolean isAuthorized(){
		return (account.getAccessToken() != null);
	}
	
	public void authorize(VKAccount account){
		Log.w("VKShareMaster", "TOKEN: "+account.getAccessToken());
		Log.w("VKShareMaster", "USER: "+account.getUserId());
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
	
	public void postToWall(Holiday holiday) {
		if(isAuthorized()){
			Intent serviceIntent = new Intent(context, PostService.class);
			serviceIntent.putExtra("title", holiday.getTitle());
			serviceIntent.putExtra("description", holiday.getDescription());
			serviceIntent.putExtra("dateStr", holiday.getDate().toString());
			serviceIntent.putExtra("vkPicture", holiday.getPicture().getVkPicture());
			serviceIntent.putExtra("link", "https://play.google.com/store/apps/details?id=com.jonnygold.holidays.fullcalendar");
			
			serviceIntent.putExtra("api", api);
			serviceIntent.putExtra("account", account);
			
	        context.startService(serviceIntent);
		}
		else{
			Toast.makeText(context, "Вы не авторизованы.", Toast.LENGTH_LONG).show();
		}
		
    }
	
}
