package iX;

import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

public class Utilities {
	public static boolean saveFile(String str, String filePath) {
		try {
			DataOutputStream d = new DataOutputStream(new FileOutputStream(filePath));
			String line = str;
			d.writeBytes(line);
			d.close();
		} catch (Exception ex) {
			System.out.println("File not found");
			return false;
		}

		return true;
	}

	public static void appendStrToFile(String str, String filePath) {
		try {
			// Open given file in append mode. 
			BufferedWriter out = new BufferedWriter(new FileWriter(filePath, true));
			out.write(str);
			out.close();
		} catch (IOException e) {
			System.out.println("exception occoured" + e);
		}
	}

}
