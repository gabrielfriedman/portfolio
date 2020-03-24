package com.techelevator.GlobalObjects;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.techelevator.ExternalFiles.FileWriter;
import com.techelevator.Hardware.VendingMachine;

public class Logger {

	private Logger() {};
	
	private static Logger instance = null;
	
	public static Logger getInstance() {
		if (instance == null) {
			instance = new Logger();
		}
		return instance;
	}
	
	private static Map<String, Integer> salesCache = new HashMap<String, Integer>();
	
	private static final SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
	
	public void delineateNewSession() {
		File logFile = new File("log.txt");
		try {
			if (!new File("log.txt").exists()) {
					logFile.createNewFile();
				} 
		}catch (IOException e) {
			e.printStackTrace();
		}
		FileWriter.writeLineToFile(logFile, "----------------------------------------------------------------------------");
	}
	
	public void writeToLog(String header, String previousBalance, String newBalance) {
		Date date = new Date();
		Timestamp timestamp = new Timestamp(date.getTime());
		File logFile = new File("log.txt");
		try {
			if (!new File("log.txt").exists()) {
					logFile.createNewFile();
				} 
		}catch (IOException e) {
			e.printStackTrace();
		}
		String logString = String.format("%-25s", sdf.format(timestamp));
		logString += String.format("%-25s", header);
		logString += String.format("%-8s", previousBalance);
		logString += String.format("%-8s", newBalance);
		FileWriter.writeLineToFile(logFile, logString);
	}
	
	public void updateSalesCache(String foodName) {
		int currentValue = salesCache.containsKey(foodName) ? salesCache.get(foodName) : 0;
		salesCache.put(foodName, currentValue + 1);
	}
	
	private String getTimeStamp() {
		Date date = new Date();
		Timestamp timestamp = new Timestamp(date.getTime());
		return sdf.format(timestamp).replace("/", "-").replace(" ", "_").replace(":", ".");
	}
	
	public void dumpSalesReport(VendingMachine vendingMachine) {
		String logString = "";
		BigDecimal totalMoney = new BigDecimal(0);
		String fileName = "sales_report_" + getTimeStamp() + ".txt";
	
		File logFile = new File(fileName);
		try {
			if (!new File(fileName).exists()) {
				logFile.createNewFile();
			} else {
				logFile.delete();
				logFile.createNewFile();
			}
		}catch (IOException e) {
			e.printStackTrace();
		}

		String titleString = String.format("%-25s", "Food Name");
		titleString += String.format("%-5s", "|");
		titleString += "Units Sold";
		titleString += "\n------------------------------------------";
		FileWriter.writeLineToFile(logFile, titleString);
	
		for (Map.Entry<String, Integer> itemLog: salesCache.entrySet()) {
			logString = String.format("%-25s", itemLog.getKey());
			logString += String.format("%-5s", "|");
			logString += itemLog.getValue();
			FileWriter.writeLineToFile(logFile, logString);
			
			BigDecimal itemCost = vendingMachine.getInventory().getFoodFromName(itemLog.getKey()).getPrice();
			itemCost = itemCost.multiply(BigDecimal.valueOf(itemLog.getValue()));
			totalMoney = totalMoney.add(itemCost);
		}
		FileWriter.writeLineToFile(logFile, "\nTotal Sales: $" + new DecimalFormat("0.00").format(totalMoney.doubleValue()));

	}
}
