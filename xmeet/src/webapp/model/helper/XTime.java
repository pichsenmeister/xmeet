package webapp.model.helper;

import java.util.Date;

import com.google.gwt.i18n.client.DateTimeFormat;

public class XTime {

	public static final String IN = "in";
	public static final String MIN = "minuten";
	public static final String HOUR = "stunden";
	public static final String DAYS = "tagen";
	public static final String CLOCK = "uhr";

	public static long MIN_1 = 60000;
	public static long MIN_59 = 3540000;
	public static long HOUR_1 = 3600000;
	public static long HOURS_2 = 7200000;
	public static long HOURS_12 = 43200000;
	public static long DAY = 86400000;
	public static long DAYS_2 = 172800000;
	public static long WEEK = 604800000;

	public static final String FORMAT_MIN = "mm";
	public static final String FORMAT_HOUR = "HH";
	public static final String FORMAT_DAY = "d";
	public static final String FORMAT_YEAR = "yyyy";

	public static final String FORMAT_TIME = "HH:mm";
	public static final String FORMAT_DMY = "dd.MM.yyyy";
	public static final String FORMAT_DATE = "EEE, d.MMM - HH:mm";
	public static final String FORMAT_DATE_YEAR = "EEE, d.MMM yyyy - HH:mm";
	public static final String FORMAT_DATE_YEAR_SHORT = "d.MMM yy - HH:mm";

	public static String getDateString(Date date) {
		return DateTimeFormat.getFormat(FORMAT_DATE_YEAR).format(date) + " " + CLOCK;
	}

	public static String getDateString(long time) {
		return DateTimeFormat.getFormat(FORMAT_DATE_YEAR).format(new Date(time)) + " " + CLOCK;
	}

	public static String getShortDateString(long time) {
		return DateTimeFormat.getFormat(FORMAT_DATE_YEAR_SHORT).format(new Date(time)) + " " + CLOCK;
	}

	public static String getTimeString(Date date) {
		long now = System.currentTimeMillis();
		long time = date.getTime();
		if (time < (now + MIN_59)) {
			Date format = new Date(time - now);
			return IN + " " + DateTimeFormat.getFormat(FORMAT_MIN).format(format) + " " + IN + " " + MIN;
		} else if (time < (now + DAY)) {
			Date format = new Date(time - now);
			return IN + " " + DateTimeFormat.getFormat(FORMAT_HOUR).format(format) + " " + IN + " " + HOUR;
		} else {
			Date format = new Date(time - now);
			return IN + " " + DateTimeFormat.getFormat(FORMAT_DATE_YEAR).format(format) + " " + DAYS;
		}
	}

	public static String getTimeString(long time) {
		long now = System.currentTimeMillis();
		if (time < (now + MIN_59)) {
			Date format = new Date(time - now);
			return IN + " " + DateTimeFormat.getFormat(FORMAT_MIN).format(format) + " " + MIN;
		} else if (time < (now + DAY)) {
			Date format = new Date(time - now);
			return IN + " " + DateTimeFormat.getFormat(FORMAT_HOUR).format(format) + " " + HOUR;
		} else {
			Date format = new Date(time - now);
			return IN + " " + DateTimeFormat.getFormat(FORMAT_DAY).format(format) + " " + DAYS;
		}
	}

}
