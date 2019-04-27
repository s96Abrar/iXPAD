package iX.Utilities;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

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
		String text = null;
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
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(filePath));
			bw.write(text);
			bw.close();
			return true; 
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
}
