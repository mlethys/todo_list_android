package pl.mlethys.todolist.model;

import org.joda.time.LocalDate;



public class Task
{
	private String name;
	private LocalDate deadline;
	
	public Task(String name)
	{
		this.name = name;
	}
	
	public Task(String name, LocalDate deadline)
	{
		this.name = name;
		this.deadline = deadline;
		
	}
	
	public String getName()
	{
		return name;
	}
	
	public LocalDate getDeadline()
	{
		return deadline;
	}
}
