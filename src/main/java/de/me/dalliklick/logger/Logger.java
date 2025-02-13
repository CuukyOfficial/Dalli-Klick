package de.me.dalliklick.logger;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {

	public enum LogLevel {

		ERROR("ERROR"),
		DEBUG("DEBUG"),
		INFO("INFO"),
		WARN("WARN");

		private String name;

		LogLevel(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}
	}

	private SimpleDateFormat timeFormatLog = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss"), formatFile = new SimpleDateFormat("dd-MM-yyyy_HH-mm");
	private PrintWriter pw;
	private File file;
	private boolean debug, error, console;
	
	public Logger() {
		file = createFile("logs/" + formatFile.format(new Date()) + ".log");

		try {
			pw = new PrintWriter(new FileWriter(file, true));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param path The path where the file should be created at
	 * @return Returns the new file
	 */
	private File createFile(String path) {
		file = new File("logs/" + formatFile.format(new Date()) + ".log");
		if (!file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}

		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return file;
	}
	
	/**
	 * @param string The string that will printed into the file
	 */
	private void printToFile(String string) {
		pw.print(string);
		pw.flush();
	}
	
	/**
	 * @param level The LogLevel the log should be printed with
	 * @param message A message the log will be appended with
	 * @param throwable A throwable the log will be appended with 
	 */
	public void log(LogLevel level, String message, Throwable throwable) {
		LogEntry entry = new LogEntry(level, timeFormatLog, new Date());

		if (message != null) {
			entry.append(message);
		}

		if (throwable != null) {
			entry.appendThrowable(throwable);
		}

		String print = entry.toString();
		if (console && (!error && throwable == null)) {
			System.out.print(print);
		}
		
		printToFile(print);
	}

	public void debug(String message) {
		debug(message, null);
	}

	public void debug(String message, Throwable throwable) {
		if (!debug)
			return;

		log(LogLevel.DEBUG, message, throwable);
	}

	public void error(String message) {
		error(message, null);
	}

	public void error(String message, Throwable throwable) {
		log(LogLevel.ERROR, message, throwable);
	}

	public void error(Throwable throwable) {
		error(null, throwable);
	}

	public void info(String message) {
		info(message, null);
	}

	public void info(String message, Throwable throwable) {
		log(LogLevel.INFO, message, throwable);
	}

	/**
	 * @param timeFormatLog Sets the timeformat of the logs
	 */
	public void setTimeFormatLog(SimpleDateFormat timeFormatLog) {
		this.timeFormatLog = timeFormatLog;
	}
	
	public void warn(String message) {
		warn(message, null);
	}
	
	public void warn(String message, Throwable throwable) {
		log(LogLevel.WARN, message, throwable);
	}
	
	/**
	 * @param console Sets if the logger should print the logs into the console
	 */
	public void setConsole(boolean console) {
		this.console = console;
	}

	/**
	 * @param debug Sets if the logger should print debug messages
	 */
	public void setDebug(boolean debug) {
		this.debug = debug;
	}
	
	/**
	 * 
	 * @param error set if errors should be printed
	 */
	public void setError(boolean error) {
		this.error = error;
	}
}