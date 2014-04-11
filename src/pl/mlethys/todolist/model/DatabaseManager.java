package pl.mlethys.todolist.model;

import java.sql.Date;
import java.util.LinkedList;
import java.util.List;

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
	
	public void add(String taskName, Date date)
	{
		Log.d(LOG_TAG, "addTask is called");
		
		String columnNameKey = "name";
		String columnDateKey = "date";
		
		database = databaseHelper.getWritableDatabase();
		
		ContentValues values = new ContentValues();
		values.put(columnNameKey, taskName);
		values.put(columnDateKey, date.toString());
		
		database.insert(databaseHelper.getTableTasksName(), null, values);
		database.close();
	}
	
	public List<String> getCurrentProjects()
	{
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
		
		Log.d(LOG_TAG, "getCurrentProjects, created list of projects");
		return currentProjects;
	}
}
