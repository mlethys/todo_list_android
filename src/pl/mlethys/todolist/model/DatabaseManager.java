package pl.mlethys.todolist.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.joda.time.LocalDate;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DatabaseManager
{
	private final String LOG_TAG = "database_manager";
	
	private SQLiteDatabase database;
	private MySqliteHelper databaseHelper;
	
	public DatabaseManager(MySqliteHelper databaseHelper)
	{
		this.databaseHelper = databaseHelper;
	}
	
	public void add(String projectName)
	{
		Log.d(LOG_TAG, "addProject is called");
		
		String columnKey = "name";
		
		database = databaseHelper.getWritableDatabase();
		
		ContentValues values = new ContentValues();
		values.put(columnKey, projectName);
		
		database.insert(databaseHelper.getTableProjectsName(), null, values);
		database.close();
	}
	
	public void add(String taskName, LocalDate tmpDate, int projectId)
	{
		Log.d(LOG_TAG, "addTask is called with date");
		
		String columnNameKey = "name";
		String columnDateKey = "deadline";
		String columnProjectIdKey = "project_id";
		
		database = databaseHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(columnNameKey, taskName);
		values.put(columnDateKey, tmpDate.toString());
		values.put(columnProjectIdKey, projectId);
		
		database.insert(databaseHelper.getTableTasksName(), null, values);
		database.close();
	}
	
	public void add(String taskName, int projectId)
	{
		Log.d(LOG_TAG, "addTask is called without date");
		
		String columnNameKey = "name";
		String columnProjectIdKey = "project_id";
		
		database = databaseHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(columnNameKey, taskName);
		values.put(columnProjectIdKey, projectId);
		
		database.insert(databaseHelper.getTableTasksName(), null, values);
		database.close();
	}
	
	public List<String> getCurrentProjects()
	{
		Log.d(LOG_TAG, "getCurrentProjects is called");
		List<String> currentProjects = new LinkedList<String>();
		
		String query = "select * from projects where completed=0";
		
		database = databaseHelper.getWritableDatabase();
		Cursor cursor = database.rawQuery(query, null);
		
		if(cursor.moveToFirst())
		{
			do
			{
				currentProjects.add(cursor.getString(1));
			} while(cursor.moveToNext());
		}
		database.close();
		Log.d(LOG_TAG, "getCurrentProjects, created list of projects");
		return currentProjects;
	}
	
	@SuppressLint("SimpleDateFormat")
	public List<Task> getTasks(int projectId)
	{
		Log.d(LOG_TAG, "getTasks is called");
		List<Task> tasks = new LinkedList<Task>();
		String query = "select * from tasks where project_id=" + projectId;
		
		database = databaseHelper.getWritableDatabase();
		Cursor cursor = database.rawQuery(query, null);
		if(cursor.moveToFirst())
		{
			do
			{
				Task task;
				if (cursor.getString(2) == null)
				{
					task = new Task(cursor.getString(1));
				}
				else
				{
					task = new Task(cursor.getString(1), new LocalDate(cursor.getString(2)));
					
				}
				tasks.add(task);
			}while(cursor.moveToNext());
		}
		database.close();
		Log.d(LOG_TAG, "getTasks ended");
		return tasks;
	}
	
	public int getProjectId(String name)
	{
		Log.d(LOG_TAG, "getProjectId is called");
		String query = "select id from projects where name=" + "'" + name + "'";
		database = databaseHelper.getWritableDatabase();
		Cursor cursor = database.rawQuery(query, null);
		if(cursor.moveToFirst())
		{
			do
			{
				return cursor.getInt(0);
			}while(cursor.moveToNext());
		}
		database.close();
		return -1;
	}
	
	public int getTaskId(String name)
	{
		Log.d(LOG_TAG, "getTaskId is called");
		String query = "select id from tasks where name=" + "'" + name + "'";
		database = databaseHelper.getWritableDatabase();
		Cursor cursor = database.rawQuery(query, null);
		if(cursor.moveToFirst())
		{
			do
			{
				return cursor.getInt(0);
			}while(cursor.moveToNext());
		}
		database.close();
		return -1;
	}
	
	public void deleteTask(int taskId)
	{
		Log.d(LOG_TAG, "deleteTask is called");
	//	String query = "delete from tasks where id="+taskId;
		database = databaseHelper.getWritableDatabase();
		database.delete(databaseHelper.getTableTasksName(), "id=" + taskId, null);
		database.close();
	}
}
