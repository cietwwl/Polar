package com.game.utils;

import org.apache.log4j.Logger;
/**
 * 
 * @author  
 *
 */
public class LoggerProvider{
	/**
	 * Logger for this class
	 */
	private static final Logger dbconsuminglog = Logger.getLogger("DBLOGCONSUMING");

	public static Logger getDbconsuminglog() {
		return dbconsuminglog;
	}
	 
	
}
