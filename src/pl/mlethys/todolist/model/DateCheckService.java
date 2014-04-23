package pl.mlethys.todolist.model;

import java.util.Timer;
import java.util.TimerTask;

import org.joda.time.LocalDate;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class DateCheckService extends Service
{
	private LocalDate date;
	private String taskName;
	
//	public DateCheckService(LocalDate date, String taskName)
//	{
//		super();
//		this.date = date;
//		this.taskName = taskName;
//	}
	
	@Override
	public IBinder onBind(Intent arg0) 
	{
		return null;
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId)
	{
		Timer timer = new Timer();
		TimerTask dateCheckTask = new DateCheckTask(getBaseContext() ,date, taskName);
		timer.schedule(dateCheckTask, 0, 10000);
		
		return START_STICKY;
	}
	
	@Override
	public void onDestroy()
	{
		super.onDestroy();
	}

}
