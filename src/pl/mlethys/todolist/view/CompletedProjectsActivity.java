package pl.mlethys.todolist.view;

import java.util.LinkedList;
import java.util.List;

import pl.mlethys.todolist.R;
import pl.mlethys.todolist.model.DatabaseManager;
import pl.mlethys.todolist.model.MySqliteHelper;
import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class CompletedProjectsActivity extends Activity implements OnItemClickListener
{
	private DatabaseManager dbManager;
	private MySqliteHelper databaseHelper;
	private ArrayAdapter<String> arrayAdapter;
	private ListView listView;
	private List<String> projectsList;
	private Dialog dialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.completed_projects_activity);
		
		databaseHelper = new MySqliteHelper(this);
		dbManager = new DatabaseManager(databaseHelper);
		projectsList = new LinkedList<String>();
		projectsList = dbManager.getCompletedProjects();
		
		listView = (ListView) findViewById(R.id.completed_projects_listview);
		arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, projectsList);
		listView.setAdapter(arrayAdapter);
		listView.setOnItemClickListener(this);
	}
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) 
	{
		createDeleteDialog(position);
	}
	
	private void createDeleteDialog(final int position)
	{
		dialog = new Dialog(this);
		dialog.setContentView(R.layout.delete_dialog);
		dialog.setTitle(R.string.delete_project);
		dialog.show();
		
		Button yesButton = (Button) dialog.findViewById(R.id.confirm_delete_button);
		yesButton.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v) 
			{
				dbManager.deleteProject(dbManager.getProjectId(projectsList.get(position)));
				dialog.dismiss();
				finish();
				startActivity(getIntent());
			}
		});
		
		Button noButton = (Button) dialog.findViewById(R.id.cancel_delete_button);
		noButton.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v) 
			{
				dialog.dismiss();
			}
		});
	}
}
