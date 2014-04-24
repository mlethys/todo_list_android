package pl.mlethys.todolist.view;

import java.util.LinkedList;
import java.util.List;

import pl.mlethys.todolist.R;
import pl.mlethys.todolist.model.DatabaseManager;
import pl.mlethys.todolist.model.DateCheckService;
import pl.mlethys.todolist.model.DateCheckTask;
import pl.mlethys.todolist.model.MySqliteHelper;
import pl.mlethys.todolist.model.Task;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class CurrentProjectsActivity extends Activity implements OnItemClickListener
{
	private DatabaseManager dbManager;
	private MySqliteHelper databaseHelper;
	private ArrayAdapter<String> arrayAdapter;
	private ListView listView;
	private List<String> projectsList;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.current_projects_activity);
		
		databaseHelper = new MySqliteHelper(this);
		dbManager = new DatabaseManager(databaseHelper);
		projectsList = new LinkedList<String>();
		projectsList = dbManager.getCurrentProjects();
		
		listView = (ListView) findViewById(R.id.current_projects_listview);
		arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, projectsList);
		listView.setAdapter(arrayAdapter);
		listView.setOnItemClickListener(this);
	}
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) 
	{
		Intent projectDetailsIntent = new Intent(CurrentProjectsActivity.this, ProjectDetailsActivity.class).putExtra("title", projectsList.get(position));
		startActivity(projectDetailsIntent);
	}
	
	@Override
	public void onBackPressed()
	{
		Intent mainMenuIntent = new Intent(CurrentProjectsActivity.this, MainActivity.class);
		startActivity(mainMenuIntent);
	}
}
