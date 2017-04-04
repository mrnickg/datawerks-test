package datawerks.common.logging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Log {

	public static Logger logger = LoggerFactory.getLogger("datawerks");
	
	public static void debug(String s, Object o) {
		logger.debug(s, o);
	}
	
	public static void info(String s, Object o) {
		logger.info(s, o);
	}
	
	public static void error(String s, Object o) {
		logger.error(s, o);
	}
}
