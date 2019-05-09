/*
 * iXPAD is a simple text editor with bookmark, syntax highlighting, recent activity, spell check
 * 
 * Copyright (C) 2019  Abrar
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.

 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.

 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package iX.Utilities;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.KeyStroke;

/**
 * @author abrar
 * 
 */
public class iXUtility {
		
	/**
	 * Image resource path
	 */
	private final String IMAGE_RESOURCE_PATH = "/image/";
	
	/**
	 * Get resources from inside the "resources" folder based on given folder name.
	 * @param resourceName A valid name of the resource.
	 * @param resourceFolder Folder which is under "resources" folder.
	 * @return InputStream based on resource name if resource not found return null.
	 */
	private InputStream getiXResource(String resourceName, String resourceFolder) {
		try {
			return iXUtility.class.getResourceAsStream(resourceFolder + resourceName);
		} catch (Exception ex) {
			System.out.println("Resource not found (" + resourceName + ")!!!");
			ex.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Get resources from "resources/image/" folder. 
	 * @param resourceName A valid name of the image resource.
	 * @return ImageIcon of the resource.
	 */
	public ImageIcon getImageResource(String resourceName) {
		ImageIcon img = null;
		
		if (getiXResource(resourceName, IMAGE_RESOURCE_PATH) == null) {
			System.out.println("Null resource (" + resourceName + ")");
			return img;
		}
		
		try {
			img = new ImageIcon(ImageIO.read(getiXResource(resourceName, IMAGE_RESOURCE_PATH)));
		} catch (IOException e) {
			System.out.println("Image cannot be loaded");
			e.printStackTrace();
		}
		return img;
	}
	
	/**
	 * Add shortcut for a JComponent.
	 * @param actionComponent Where action will be added.
	 * @param actionText Name of action.
	 * @param actionKey KeyStroke for the action.
	 * @param action An AbstractAction for the component.
	 */
	public void addKeyShortcut(JComponent actionComponent, String actionText, KeyStroke actionKey, AbstractAction action) {
		InputMap inputMap = actionComponent.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
		inputMap.put(actionKey, actionText);
		actionComponent.getActionMap().put(actionText, action);
	}
	
	public String openFromFile(String filePath) {
		String text = "";
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(filePath));
			
			String tempLine = null;
			while ((tempLine = br.readLine()) != null) {
				text += tempLine + "\n";
			}
			
			br.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		return text;
	}

	public boolean saveToFile(String text, String filePath) {
		return saveToFile(text, filePath, false);
	}
	
	public boolean saveToFile(String text, String filePath, boolean append) {
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(filePath, append));
			bw.write(text);
			bw.close();
			return true; 
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	public static String convertToDateText(Date date, String format) {
		String given = (new SimpleDateFormat(format).format(date)).toString();

	    if (LocalDate.now().format(DateTimeFormatter.ofPattern(format)) == given) {
	        return "Today";
	    } else {
	        return new SimpleDateFormat("MMMM dd").format(date).toString();
	    }
	}
	
}
