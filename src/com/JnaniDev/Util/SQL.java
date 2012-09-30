package com.JnaniDev.Util;

import java.io.*;
import java.sql.*;
import java.util.logging.*;

import org.bukkit.plugin.*;

public class SQL
{
	private final Plugin plugin;
	private final String url;
	private Logger log;
	
	/**
	 * Connect to a MySQL database
	 * 
	 * @param plugin the plugin handle
	 * @param host MySQL server address
	 * @param database MySQL database name
	 * @param user MySQL access username
	 * @param password MySQL access password
	 */
	public SQL(final Plugin plugin, final String host, final String database, final String user, final String password)
	{
		this.plugin = plugin;
		url = "jdbc:mysql://" + host + "/" + database + "?user=" + user + (password.equals("") ? "" : "&password=" + password);

		log = plugin.getServer().getLogger();
		
		initDriver("com.mysql.jdbc.Driver");
	}
	
	/**
	 * Connect/create a SQLite database
	 * 
	 * @param plugin the plugin handle
	 * @param filePath database storage path/name.extension
	 */
	public SQL(final Plugin plugin, final String filePath)
	{
		this.plugin = plugin;
		url = "jdbc:sqlite:" + new File(filePath).getAbsolutePath();
		log = plugin.getServer().getLogger();
		
		initDriver("org.sqlite.JDBC");
	}
	
	private void initDriver(final String driver)
	{
		try
		{
			Class.forName(driver);
		}
		catch(final Exception e)
		{
			log.severe("Database driver error:" + e.getMessage());
		}
	}
	
	/**
	 * Get a string from query(), automatically checks for null.
	 * 
	 * @param result the returned value of query()
	 * @param column the column number, starts from 1
	 * @return integer value of the column
	 */
	public int resultInt(ResultSet result, int column)
	{
		if(result == null)
			return 0;
		
		try
		{
			result.next();
			int integer = result.getInt(column);
			result.close();
			
			return integer;
		}
		catch(SQLException e)
		{
			log.severe("Database result error: " + e.getMessage());
		}
		
		return 0;
	}
	
	/**
	 * Get a string from query(), automatically checks for null.
	 * 
	 * @param result the returned value of query()
	 * @param column the column number, starts from 1
	 * @return string value of the column or null
	 */
	public String resultString(ResultSet result, int column)
	{
		if(result == null)
			return null;
		
		try
		{
			result.next();
			String string = result.getString(column);
			result.close();
			
			return string;
		}
		catch(SQLException e)
		{
			log.severe("Database result error: " + e.getMessage());
		}
		
		return null;
	}
	
	/**
	 * Sends a query to the SQL
	 * Returns a ResultSet if there's anything to return
	 * 
	 * @param query the SQL query string
	 * @return ResultSet or null
	 */
	public ResultSet query(final String query)
	{
		return query(query, false);
	}
	
	/**
	 * Sends a query to the SQL
	 * Returns a ResultSet if there's anything to return
	 * 
	 * @param query the SQL query string
	 * @param retry set to true to retry query if locked
	 * @return ResultSet or null
	 */
	public ResultSet query(final String query, final boolean retry)
	{
		try
		{
			final Connection connection = DriverManager.getConnection(url);
			final PreparedStatement statement = connection.prepareStatement(query);
			
			if(statement.execute())
				return statement.getResultSet();
		}
		catch(final SQLException e)
		{
			final String msg = e.getMessage();
			
			log.severe("Database query error: " + msg);
			
			if(retry && msg.contains("_BUSY"))
			{
				log.severe("Retrying query...");
				
				plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable()
				{
					@Override
					public void run()
					{
						query(query);
					}
				}, 20);
			}
		}
		
		return null;
	}
	
	/**
	 * Sends a prepared statement to the SQL
	 * Returns a ResultSet if there's anything to return
	 * 
	 * @param statement the SQL prepared statement
	 * @param retry set to true to retry query if locked
	 * @return ResultSet or null
	 */
	public ResultSet query(final PreparedStatement statement, final boolean retry)
	{
		try
		{			
			if(statement.execute())
				return statement.getResultSet();
		}
		catch(final SQLException e)
		{
			final String msg = e.getMessage();
			
			log.severe("Database query error: " + msg);
			
			if(retry && msg.contains("_BUSY"))
			{
				log.severe("Retrying query...");
				
				plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable()
				{
					@Override
					public void run()
					{
						query(statement);
					}
				}, 20);
			}
		}
		
		return null;
	}
	
	/**
	 * Sends a prepared statement to the SQL
	 * Returns a ResultSet if there's anything to return
	 * 
	 * @param statement the SQL prepared statement
	 * @return ResultSet or null
	 */
	public ResultSet query(final PreparedStatement statement)
	{	
		return query(statement, false);
	}
}