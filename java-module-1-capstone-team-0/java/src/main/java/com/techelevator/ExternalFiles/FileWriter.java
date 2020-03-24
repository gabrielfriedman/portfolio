package com.techelevator.ExternalFiles;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class FileWriter {

	public static File initFile(String fileName) { // init file in current directory
		File newFile = new File(fileName);
		if (newFile.exists()) {
			newFile.delete();
		}
		try {
			newFile.createNewFile();
		} catch (IOException e) {
			System.out.println("Invalid file name");
		}
		return newFile;
	}

	public static File initFile(String dirPath, String fileName) { // init file in specified directory
		File newFile = new File(dirPath, fileName);
		if (newFile.exists()) {
			newFile.delete();
		}
		try {
			newFile.createNewFile();
		} catch (IOException e) {
			System.out.println("Invalid dir/file name, terminating program");
			System.exit(0);
		}
		return newFile;
	}

	public static File copyFile(File originalFile, String dirPath, String fileName) {
		File newFile = initFile(dirPath, fileName);
		Scanner fileReader = fileReader(originalFile);
		while (fileReader.hasNextLine()) {
			writeLineToFile(newFile, fileReader.nextLine());
		}
		return newFile;
	}

	public static void clearDirectory(File directory) {
		for (String s : directory.list()) {
			File currentFile = new File(directory.getPath(), s);
			currentFile.delete();
		}
	}

	public static File initDirectory(String dirName) { // init directory in current directory
		File newDir = new File(dirName);
		if (newDir.exists()) {
			clearDirectory(newDir);
			newDir.delete();
		}
		newDir.mkdir();
		return newDir;
	}

	public static File initDirectory(String dirPath, String dirName) { // init directory in current directory
		File newDir = new File(dirPath, dirName);
		if (newDir.exists()) {
			clearDirectory(newDir);
			newDir.delete();
		}
		newDir.mkdir();
		return newDir;
	}

	public static Scanner fileReader(File file) { // returns scanner prepped to scan the file
		try {
			return new Scanner(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void writeLineToFile(File file, String s) {
		try (PrintWriter writer = new PrintWriter(new FileOutputStream(file, true))) {
			writer.println(s);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
