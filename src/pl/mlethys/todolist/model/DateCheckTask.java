package pl.mlethys.todolist.model;

import java.util.TimerTask;

import org.joda.time.LocalDate;

import pl.mlethys.todolist.R;
import pl.mlethys.todolist.view.ProjectDetailsActivity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

public class DateCheckTask extends TimerTask
{
	private Context context;
	private boolean firstCall = false;
	private final int DEFAULT = 0;
	private final int DAY_REMIND = 1;
	private final int WEEK_REMIND = 2;
	private final int NO_REMIND = 3;
	
	private MySqliteHelper helper;
	private DatabaseManager dbManager;
	
	public DateCheckTask(Context context)
	{
		super();
		this.context = context;
	}
	
	private void checkDate(int choice, Task task)
	{
		switch(choice)
		{
			case DEFAULT:
				if(task.getDeadline().equals(LocalDate.now()))
				{
					
					NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
					String noti = context.getResources().getString(R.string.alternative_noti);
					notificationManager.notify(0, createNotification(task.getName(), noti));
					Log.d("notification", "notification id 0");
				}
				break;
			case DAY_REMIND:
				if(task.getDeadline().equals(LocalDate.now().plusDays(1)))
				{
					NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
					notificationManager.notify(1, createNotification(1, task.getName()));
					Log.d("notification", "notification id 1");
				}
				else if(task.getDeadline().equals(LocalDate.now()))
				{
					NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
					String noti = context.getResources().getString(R.string.alternative_noti);
					notificationManager.notify(0, createNotification(task.getName(), noti));
					Log.d("notification", "notification id 0");
				}
				break;
			case WEEK_REMIND:
				if(task.getDeadline().equals(LocalDate.now().plusDays(7)))
				{
					NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
					notificationManager.notify(2, createNotification(7, task.getName()));
					Log.d("notification", "notification id 2");
				}
				else if(task.getDeadline().equals(LocalDate.now().plusDays(1)))
				{
					NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
					notificationManager.notify(1, createNotification(1, task.getName()));
					Log.d("notification", "notification id 1");
				}
				else if(task.getDeadline().equals(LocalDate.now()))
				{
					NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
					String noti = context.getResources().getString(R.string.alternative_noti);
					notificationManager.notify(0, createNotification(task.getName(), noti));
					Log.d("notification", "notification id 0");
				}
				break;
			case NO_REMIND:
				//do noting
				break;
		}
		
		if(task.getDeadline().isBefore(LocalDate.now()))
		{
			NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
			String noti = context.getResources().getString(R.string.noti_fragment_overdue);
			notificationManager.notify(0, createNotification(task.getName(), noti));
		}
	}
	
	private Notification createNotification(int daysToDeadline, String taskName)
	{
		String notiPart1 = context.getResources().getString(R.string.noti_fragment_1);
		String notiPart2 = context.getResources().getString(R.string.noti_fragment_2);
		
		Intent intent = new Intent(context, ProjectDetailsActivity.class).putExtra("title", dbManager.getProjectNameFromTask(dbManager.getTaskId(taskName)));
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		PendingIntent pintent = PendingIntent.getActivity(context, 0, intent, 0);
		
		Notification notification = new NotificationCompat.Builder(context)
																.setContentTitle(taskName)
																.setContentText(notiPart1 + daysToDeadline + notiPart2)
																.setSmallIcon(R.drawable.ic_launcher)
																.setAutoCancel(true)
																.setContentIntent(pintent)
																.build();
		notification.defaults |= Notification.DEFAULT_SOUND;
		
		return notification;		
	}
	
	private Notification createNotification(String taskName, String noti)
	{
		
		
		Intent intent = new Intent(context, ProjectDetailsActivity.class).putExtra("title", dbManager.getProjectNameFromTask(dbManager.getTaskId(taskName)));
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		PendingIntent pintent = PendingIntent.getActivity(context, 0, intent, 0);
		
		Notification notification = new NotificationCompat.Builder(context)
																.setContentTitle(taskName)
																.setContentText(noti)
																.setSmallIcon(R.drawable.ic_launcher)
																.setAutoCancel(true)
																.setContentIntent(pintent)
																.build();
		notification.defaults |= Notification.DEFAULT_SOUND;
		
		return notification;
	}
	
	@Override
	public void run() 
	{
		if (!firstCall)
		{
			helper = new MySqliteHelper(context);
			dbManager = new DatabaseManager(helper);
			firstCall = true;
		}
		checkDateAndCreateNotification();
	}
	
	private void checkDateAndCreateNotification()
	{
		for(Task task : dbManager.getDeadlineTasks())
		{
			checkDate(getPreferences(), task);
		}
		
	}
	
	private int getPreferences()
	{
		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
		Log.d("service", String.valueOf(sharedPreferences.getInt("option", 0)));
		return sharedPreferences.getInt("option", 0);
		
	}

}
