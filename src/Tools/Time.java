package Tools;

import Tools.Exceptions.InvalidTimeException;

public class Time {
	private int hour;
	private int minute;
	private int second;
	private int millisecond;

	private boolean showMilliseconds;

	public enum Format {
		CLOCK24, CLOCK12AM, CLOCK12PM;
	}

	private Format format;

	public Time() {
		hour = 0;
		minute = 0;
		second = 0;
		millisecond = 0;
		format = Format.CLOCK24;
		showMilliseconds = false;
	}

	/**
	 * 
	 * @param hour
	 * @param minute
	 * @param second
	 * @param millisecond
	 * @param format
	 * @throws InvalidTimeException
	 */
	public Time(int hour, int minute, int second, int millisecond, Format format) throws InvalidTimeException {
		try {
			validTime(hour, minute, second, millisecond, format);
		} catch (InvalidTimeException e) {
			throw e;
		}

		this.hour = hour;
		this.minute = minute;
		this.second = second;
		this.millisecond = millisecond;
		this.format = format;
		showMilliseconds = false;
	}

	private void validTime(int hour, int minute, int second, int millisecond, Format format)
			throws InvalidTimeException {
		// Check Hour
		if (format.equals(Format.CLOCK24)) { // If 24hour clock
			if (hour > 23 || hour < 0) {
				throw new InvalidTimeException("Hour must be between 0 and 23 for 24 hour clock. Recieved: " + hour);
			}
		} else { // if 12 hour clock
			if (hour < 1 || hour > 12) {
				throw new InvalidTimeException("Hour must be between 1 and 12 for 12 hour clock. Recieved: " + hour);
			}
		}

		// Check Minute
		if (minute < 0 || minute > 59) {
			throw new InvalidTimeException("Minute must be between 0 and 59. Recieved: " + minute);
		}

		// Check Second
		if (second < 0 || second > 59) {
			throw new InvalidTimeException("Second must be between 0 and 59. Recieved: " + second);
		}

		// Check Millisecond
		if (millisecond < 0 || millisecond > 999) {
			throw new InvalidTimeException("Millisecond must be between 0 and 999. Recieved: " + millisecond);
		}
	}

	public int getHour() {
		return hour;
	}

	public void setHour(int hour) {
		boolean validTime = true;

		try {
			validTime(hour, this.minute, this.second, this.millisecond, this.format);
		} catch (InvalidTimeException e) {
			e.printStackTrace();
			validTime = false;
		}

		if (validTime) {
			this.hour = hour;
		}
	}

	public int getMinute() {
		return minute;
	}

	public void setMinute(int minute) {
		boolean validTime = true;

		try {
			validTime(this.hour, minute, this.second, this.millisecond, this.format);
		} catch (InvalidTimeException e) {
			e.printStackTrace();
			validTime = false;
		}

		if (validTime) {
			this.minute = minute;
		}
	}

	public int getSecond() {
		return second;
	}

	public void setSecond(int second) {
		boolean validTime = true;

		try {
			validTime(this.hour, this.minute, second, this.millisecond, this.format);
		} catch (InvalidTimeException e) {
			e.printStackTrace();
			validTime = false;
		}

		if (validTime) {
			this.second = second;
		}
	}

	public int getMillisecond() {
		return this.millisecond;
	}

	public void setMillisecond(int millisecond) {
		boolean validTime = true;

		try {
			validTime(this.hour, this.minute, this.second, millisecond, this.format);
		} catch (InvalidTimeException e) {
			e.printStackTrace();
			validTime = false;
		}

		if (validTime) {
			this.millisecond = millisecond;
		}
	}

	public Format getFormat() {
		return format;
	}

	public void showMilliseconds(boolean showMilliseconds) {
		this.showMilliseconds = showMilliseconds;
	}

	public void switchAMPM() {
		if (format.equals(Format.CLOCK12AM)) {
			format = Format.CLOCK12PM;
		} else if (format.equals(Format.CLOCK12PM)) {
			format = Format.CLOCK12AM;
		}
	}

	public void switchClockFormat() {
		if (format.equals(Format.CLOCK12AM)) {
			if (hour == 12) {
				hour = 0;
			}
			format = Format.CLOCK24;
		} else if (format.equals(Format.CLOCK12PM)) {
			if (hour == 12) {
				format = Format.CLOCK24;
			} else {
				hour += 12;
				format = Format.CLOCK24;
			}
		} else if (format.equals(Format.CLOCK24)) {
			if (hour == 0) { // if between 00:00:00 and 00:59:59
				hour = 12;
				format = Format.CLOCK12AM;
			} else if (hour == 12) {
				hour = 12;
				format = Format.CLOCK12PM;
			} else if (hour > 12) { // if between 12:00:00 and 23:59:59
				hour -= 12;
				format = Format.CLOCK12PM;
			} else { // if between 00:00:00 and 11:59:59
				format = Format.CLOCK12AM;
			}
		}
	}

	public void addSecond(int add) {
		if (add > 0) {
			second += add;
		} else {
			return;
		}

		while (second > 59) {
			second -= 60;
			addMinute(1);
		}
	}

	public void addMinute(int add) {
		if (add > 0) {
			minute += add;
		} else {
			return;
		}

		while (minute > 59) {
			minute -= 60;
			addHour(1);
		}
	}

	public void addMillisecond(int add) {
		if (add > 0) {
			millisecond += add;
		} else {
			return;
		}

		while (millisecond > 999) {
			millisecond -= 1000;
			addSecond(1);
		}
	}

	public void addHour(int add) {
		if (add > 0) {
			hour += add;
		} else {
			return;
		}

		if (format.equals(Format.CLOCK24)) {
			while (hour > 23) {
				hour -= 24;
			}
		} else if (format.equals(Format.CLOCK12AM) || format.equals(Format.CLOCK12PM)) {
			while (hour > 23) {
				hour -= 24;
			}

			// TEST THIS MORE THAN DO EQUALS AND COMPARE

			if (hour == 0) { // if between 00:00:00 and 00:59:59
				hour = 12;
				format = Format.CLOCK12AM;
			} else if (hour == 12) {
				hour = 12;
				format = Format.CLOCK12PM;
			} else if (hour > 12) { // if between 12:00:00 and 23:59:59
				hour -= 12;
				format = Format.CLOCK12PM;
			} else { // if between 01:00:00 and 11:59:59
				format = Format.CLOCK12AM;
			}
		}
	}

	public String toString() {
		String time = "";

		if (hour < 10) {
			time += "0" + hour + ":";
		} else {
			time += hour + ":";
		}

		if (minute < 10) {
			time += "0" + minute + ":";
		} else {
			time += minute + ":";
		}

		if (second < 10) {
			time += "0" + second;
		} else {
			time += second;
		}
		
		if (showMilliseconds) {
			time += ":";
			if (millisecond > 99) {
				time += millisecond;
			} else if (millisecond < 100 && millisecond > 9) {
				time += "0" + millisecond;
			} else {
				time += "00" + millisecond;
			}
		}

		if (!format.equals(Format.CLOCK24)) {
			if (format.equals(Format.CLOCK12AM)) {
				time += " AM";
			} else {
				time += " PM";
			}
		}

		return time;
	}
}