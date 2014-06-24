package pl.mlethys.todolist.model;

import org.joda.time.LocalDate;

public class Point
{
	private int value;
	private LocalDate date;
	
	public Point(int value, LocalDate date)
	{
		this.value = value;
		this.date = date;
	}
	
	public int getValue()
	{
		return value;
	}
	
	public LocalDate getDate()
	{
		return date;
	}
}
