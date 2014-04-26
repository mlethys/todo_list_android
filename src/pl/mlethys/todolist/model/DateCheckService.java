package pl.mlethys.todolist.model;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class DateCheckService extends Service
{

	
	@Override
	public IBinder onBind(Intent arg0) 
	{
		return null;
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId)
	{
		Timer timer = new Timer();
		TimerTask dateCheckTask = new DateCheckTask(this);
		timer.schedule(dateCheckTask, 0, 21600000);
		return START_STICKY;
	}
	
	@Override
	public void onDestroy()
	{
		super.onDestroy();
	}

}
