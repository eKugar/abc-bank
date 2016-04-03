package com.abc;

import java.util.Calendar;
import java.util.Date;

public class DateProvider
{
	private static final DateProvider INSTANCE = new DateProvider();
	private static final Calendar CALENDAR = Calendar.getInstance();

	private DateProvider()
	{
	}

	public static DateProvider getInstance()
	{
		return INSTANCE;
	}

	public Date now()
	{
		return CALENDAR.getTime();
	}
}
