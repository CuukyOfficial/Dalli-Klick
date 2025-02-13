package de.me.dalliklick.logger;

import java.text.SimpleDateFormat;
import java.util.Date;

import de.me.dalliklick.logger.Logger.LogLevel;

public class LogEntry {

	private StringBuilder builder;
	private LogLevel level;
	private String prefix;

	/**
	 * @param level The LogLevel the logger should print 
	 * @param format Format of time the logger uses as prefix
	 * @param date The time the log should be appended with
	 */
	public LogEntry(LogLevel level, SimpleDateFormat format, Date date) {
		this.builder = new StringBuilder();
		this.level = level;
		this.prefix = this.getPrefix(format.format(date));
	}

	/**
	 * @param s Appends string to log
	 */
	public void append(String s) {
		this.builder.append(this.prefix).append(s).append(System.lineSeparator());
	}

	/**
	 * @param throwable Appends the throwable formatted to the log
	 */
	public void appendThrowable(Throwable throwable) {
		this.append(throwable.getClass().getName() + ": " + throwable.getMessage());
		for (StackTraceElement e : throwable.getStackTrace())
			this.append("at " + e);
	}

	private String getPrefix(String time) {
		StringBuilder builder = new StringBuilder();
		builder.append("[").append(time).append(" ").append(level.getName()).append("] ");
		return builder.toString();
	}

	/**
	 * @return Returns a printable LogEntry
	 */
	@Override
	public String toString() {
		return this.builder.toString();
	}
}