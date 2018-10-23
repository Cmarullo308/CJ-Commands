package Tools;

import Tools.Exceptions.InvalidDateException;

public class Date
{
	private int day;
	private int month;
	private int year;
	private int dayOfWeek;
	Format format;

	public enum Format
	{
		GENERAL, LONGDATE, SHORTDATE, MONTHDAY, MONTHYEAR;
	}

	public Date()
	{
		day = 1;
		month = 1;
		year = 1970;
		dayOfWeek = 4;
		format = Format.GENERAL;
	}

	public Date(int month, int day, int year, Format format) throws InvalidDateException
	{
		try
		{
			checkDate(day, month, year);
		}
		catch (InvalidDateException e)
		{
			throw e;
		}

		this.day = day;
		this.month = month;
		this.year = year;
		this.dayOfWeek = getDayOfWeek();
		this.format = format;
	}

	public Date(Date d)
	{
		this.day = d.day;
		this.month = d.month;
		this.year = d.year;
		this.dayOfWeek = d.dayOfWeek;
		this.format = d.format;
	}

	public Date clone()
	{
		try
		{
			return new Date(day, month, year, format);
		}
		catch (InvalidDateException e)
		{
			System.err.println("Clone function fail");
		}

		return new Date();
	}

	private void checkDate(int day, int month, int year) throws InvalidDateException
	{
		if (day < 1)
		{
			throw new InvalidDateException("Day cannot be less than 1");
		}
		else if (month < 1 || month > 12)
		{
			throw new InvalidDateException("Month must be between 1 and 12");
		}
		else if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12)
		{
			if (day > 31)
			{
				throw new InvalidDateException("Day cannot be greater than 31 for the Month " + getMonthName(month));
			}
		}
		else if (month == 4 || month == 6 || month == 9 || month == 11)
		{
			if (day > 30)
			{
				throw new InvalidDateException("Day cannot be greater than 30 for the Month " + getMonthName(month));
			}
		}
		else if (month == 2)
		{
			if (day > 28)
			{
				if (!(day == 29 && year % 4 == 0))
				{
					throw new InvalidDateException("Day must between 1-28 or 1-29 on a leap year");
				}
			}
		}
	}

	public void changeDate(int day, int month, int year) throws InvalidDateException
	{
		try
		{
			checkDate(day, month, year);
		}
		catch (InvalidDateException e)
		{
			throw e;
		}

		this.day = day;
		this.month = month;
		this.year = year;
	}

	public void changeFormat(Format format)
	{
		this.format = format;
	}

	public String toString()
	{
		if (format == Format.GENERAL)
		{
			return month + "/" + day + "/" + year;
		}
		else if (format == Format.LONGDATE)
		{
			return getDayName(dayOfWeek) + ", " + getMonthName(month) + " " + day + ", " + year;
		}
		else if (format == Format.SHORTDATE)
		{
			return month + "/" + day + "/" + year; // Add time after
		}
		else if (format == Format.MONTHDAY)
		{
			return getMonthName(month) + " " + day;
		}
		else // format == Format.MONTHYEAR;
		{
			return getMonthName(month) + " " + year;
		}
	}

	/**
	 * Calculates and returns the day of the week
	 * 
	 * @return Day of the week as an int
	 *
	 */
	public int getDayOfWeek()
	{
		int tempYear = year;

		int[] t = { 0, 3, 2, 5, 0, 3, 5, 1, 4, 6, 2, 4 };

		if (month < 3)
		{
			tempYear -= 1;
		}
		else
		{
			tempYear -= 0;
		}

		return (tempYear + tempYear / 4 - tempYear / 100 + tempYear / 400 + t[month - 1] + day) % 7;
	}

	public String getDayName(int day)
	{
		switch (day)
		{
		case 0:
			return "Sunday";
		case 1:
			return "Monday";
		case 2:
			return "Tuesday";
		case 3:
			return "Wednesday";
		case 4:
			return "Thursday";
		case 5:
			return "Friday";
		case 6:
			return "Saturday";
		default:
			return "getDayNameFail";
		}
	}

	/**
	 * Takes a month number and returns the month as a string
	 * 
	 * @param month
	 *            Month number
	 * @return Month name as a string
	 */
	private String getMonthName(int month)
	{
		switch (month)
		{
		case 1:
			return "January";
		case 2:
			return "February";
		case 3:
			return "March";
		case 4:
			return "April";
		case 5:
			return "May";
		case 6:
			return "June";
		case 7:
			return "July";
		case 8:
			return "August";
		case 9:
			return "September";
		case 10:
			return "October";
		case 11:
			return "November";
		case 12:
			return "December";
		}
		return "MonthNameFail";
	}
}
