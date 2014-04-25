package com.jonnygold.holidays.fullcalendar.vk;

import java.util.Arrays;
import java.util.Collection;

import com.perm.kate.api.Api;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

public class PostService extends IntentService {

	public static final String BROADCAST_ACTION = "com.jonnygold.holidays.fullcalendar.vk.BROADCAST";
	
	public static final String UPDATE_STATUS = "com.jonnygold.holidays.fullcalendar.vk.STATUS";
	
	public static enum PostState {
		SUCCESS ("Запись обубликована."),
		FAULT ("Не удалось опубликовать запись.");
	
		private String message;
		
		private PostState(String message){
			this.message = message;
		}
		
		@Override
		public String toString() {
			return message;
		}
	}
	
	public PostService() {
		super("Post to wall service");
	}

	@Override
	protected void onHandleIntent(Intent intent) {		
//		Api api = (Api) intent.getSerializableExtra("api");
		VKAccount account = (VKAccount) intent.getSerializableExtra("account");
		Api api = new Api(account.getAccessToken(), VKShareMaster.APP_ID);
		
		if(api != null && account != null){
			Collection<String> attach = Arrays.asList(new String[]{intent.getStringExtra("vkPicture"), intent.getStringExtra("link")});
			try {
				api.createWallPost(
						account.getUserId(), intent.getStringExtra("dateStr").toString()+
						"\n\n"+intent.getStringExtra("title").toUpperCase()+"\n\n"+intent.getStringExtra("description"), 
						attach, null, false, false, false, null, null, null, null, null, null);
				report(PostState.SUCCESS);
			} catch (Exception e) {
				report(PostState.FAULT);
				e.printStackTrace();
			} 
		}
	}
	
	private void report(PostState state){
		Intent localIntent = new Intent(BROADCAST_ACTION)
		        .putExtra(UPDATE_STATUS, state);
		LocalBroadcastManager.getInstance(this).sendBroadcast(localIntent);
	}

}
