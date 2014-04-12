package pl.mlethys.todolist.model;

import java.util.Date;


public class Task
{
	private String name;
	private Date deadline;
	
	public Task(String name, Date deadline)
	{
		this.name = name;
		this.deadline = deadline;
		
	}
	
	public String getName()
	{
		return name;
	}
	
	public Date getDeadline()
	{
		return deadline;
	}
}
