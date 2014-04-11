package pl.mlethys.todolist.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MySqliteHelper extends SQLiteOpenHelper
{
	private final String LOG_TAG = "database";
	
	private final String TABLE_PROJECTS = "projects";
	private final String TABLE_TASKS = "tasks";
	
	private final static String DATABASE_NAME = "projects.db";
	private final static int DATABASE_VERSION = 1;
	
	public MySqliteHelper(Context context)
	{
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	public String getTableProjectsName()
	{
		return TABLE_PROJECTS;
	}
	
	public String getTableTasksName()
	{
		return TABLE_TASKS;
	}

	@Override
	public void onCreate(SQLiteDatabase database) 
	{
		String createTableTasks = "create table " + TABLE_TASKS 
									+"(id integer primary key autoincrement, "
									+"name varchar(450) not null, "
									+"deadline date, "
									+ "project_id integer, "
									+ "foreign key (project_id) references " + TABLE_PROJECTS + "(id))";
		String createTableProjects = "create table " + TABLE_PROJECTS 
									+ "(id integer primary key autoincrement, "
									+ "name varchar(250) not null, "
									+ "completed integer default 0)";
		database.execSQL(createTableTasks);
		database.execSQL(createTableProjects);
		Log.d(LOG_TAG, "onCreate database created");
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) 
	{
		String dropQuery = "drop table if exists " + TABLE_PROJECTS;
		database.execSQL(dropQuery);
		dropQuery = "drop table if exists " + TABLE_TASKS;
		database.execSQL(dropQuery);
		Log.d(LOG_TAG, "onUpgrade, tables dropped");
		
		this.onCreate(database);
		Log.d(LOG_TAG, "onUpgrade, onCreate called");
	}

}
