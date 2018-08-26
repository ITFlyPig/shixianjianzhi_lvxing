package com.SelfTourGuide.singapore.util;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.SelfTourGuide.singapore.R;


public class NetReceiver extends BroadcastReceiver {

	private Dialog dialog;

	@Override
	public void onReceive(Context context, Intent intent) {
		String action = intent.getAction();
		 final  boolean isConnected;
		if (ConnectivityManager.CONNECTIVITY_ACTION.equals(action)) {
		 	 isConnected = NetUtils.isNetworkConnected(context);
	        System.out.println("网络状态：" + isConnected);
	        System.out.println("wifi状态：" + NetUtils.isWifiConnected(context));
	        System.out.println("移动网络状态：" + NetUtils.isMobileConnected(context));
	        System.out.println("网络连接类型：" + NetUtils.getConnectedType(context));
			dialog = new Dialog(context, R.style.MyDialog);
	        if (isConnected) {
				dialog.cancel();
	        } else {
				LayoutInflater inLayout= LayoutInflater.from(context);
				View layoutView=inLayout.inflate(R.layout.dialog_view,null);
				//实例化dialog的组件
				Button affirmbutton;
				affirmbutton=(Button) layoutView.findViewById(R.id.affirmbutton);
				affirmbutton.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View arg0) {
						Log.e("tag","无网络");
						try {

							java.lang.reflect.Field field = dialog.getClass().getSuperclass().getDeclaredField("mShowing");

							field.setAccessible(true);

							field.set(dialog, true);

						} catch (Exception e) {

							e.printStackTrace();

						}
						dialog.cancel();
						dialog.dismiss();
//						isConnected=true;
					}
				});
				dialog.setContentView(layoutView);
				dialog.show();
//
	        }
		}
	}
	// 网络提示dialog
	private void showCreateDialog() {

	}

}
