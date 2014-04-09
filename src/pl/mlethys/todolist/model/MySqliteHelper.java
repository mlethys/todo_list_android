package pl.mlethys.todolist.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MySqliteHelper extends SQLiteOpenHelper
{
	private final String TABLE_PROJECTS = "projects";
	private final String TABLE_TASKS = "tasks";
	
	private final static String DATABASE_NAME = "projects.db";
	private final static int DATABASE_VERSION = 1;
	
	public MySqliteHelper(Context context)
	{
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase database) 
	{
		String createTableTasks = "create table " + TABLE_TASKS 
									+"(id integer primary key autoincrement, "
									+"name varchar(450) not null, "
									+"deadline date)";
//		String createTableProjects = "create table " + TABLE_PROJECTS 
//									+ "(id integer primary key autoincrement, "
//									+ "name varchar(250) not null"
		database.execSQL(createTableTasks);
		Log.d("database", "sql zrobiony");
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) 
	{
		// TODO Auto-generated method stub
		
	}

}
