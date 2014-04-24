package pl.mlethys.todolist.model;

import java.util.TimerTask;

import org.joda.time.LocalDate;

import pl.mlethys.todolist.R;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

public class DateCheckTask extends TimerTask
{
//	private LocalDate deadline;
	private Context context;
	private boolean firstCall = false;
//	private String taskName;
//	private int choice;
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
		//this.deadline = task.getDeadline();
		//this.taskName = task.getName();
		//choice = 0;
	}
	
	private void checkDate(int choice, LocalDate deadline)
	{
		switch(choice)
		{
			case DEFAULT:
				if(deadline.equals(LocalDate.now()))
				{
					NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
					notificationManager.notify(0, createNotification());
					Log.d("notification", "notification id 0");
				}
				break;
			case DAY_REMIND:
				if(deadline.equals(LocalDate.now().plusDays(1)))
				{
					NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
					notificationManager.notify(1, createNotification(1));
					Log.d("notification", "notification id 1");
				}
				else if(deadline.equals(LocalDate.now()))
				{
					NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
					notificationManager.notify(0, createNotification());
					Log.d("notification", "notification id 0");
				}
				break;
			case WEEK_REMIND:
				if(deadline.equals(LocalDate.now().plusDays(7)))
				{
					NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
					notificationManager.notify(2, createNotification(7));
					Log.d("notification", "notification id 2");
				}
				else if(deadline.equals(LocalDate.now().plusDays(1)))
				{
					NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
					notificationManager.notify(1, createNotification(1));
					Log.d("notification", "notification id 1");
				}
				else if(deadline.equals(LocalDate.now()))
				{
					NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
					notificationManager.notify(0, createNotification());
					Log.d("notification", "notification id 0");
				}
				break;
			case NO_REMIND:
				//do noting
				break;
		}
	}
	
	private Notification createNotification(int daysToDeadline)
	{
		Notification notification = new NotificationCompat.Builder(context)
																.setContentTitle("Todo zadanie")
																.setContentText("Termin zadania" + " za " + daysToDeadline + " dni!")
																.setSmallIcon(R.drawable.ic_launcher)
																.setAutoCancel(true)
																.build();
		notification.defaults |= Notification.DEFAULT_SOUND;
		notification.flags |= Notification.FLAG_AUTO_CANCEL;
		
		return notification;
		
		
	}
	
	private Notification createNotification()
	{
		Notification notification = new NotificationCompat.Builder(context)
																.setContentTitle("Todo zadanie")
																.setContentText("Termin zadania" + " ju¿ dzisiaj!")
																.setSmallIcon(R.drawable.ic_launcher)
																.setAutoCancel(true)
																.build();
		notification.defaults |= Notification.DEFAULT_SOUND;
		notification.flags |= Notification.FLAG_AUTO_CANCEL;
		
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
		for(LocalDate deadline : dbManager.getDeadlines())
		{
			checkDate(getPreferences(), deadline);
		}
	}
	
	private int getPreferences()
	{
		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
		Log.d("service", String.valueOf(sharedPreferences.getInt("option", 0)));
		return sharedPreferences.getInt("option", 0);
		
	}

}
