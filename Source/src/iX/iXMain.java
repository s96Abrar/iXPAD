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

package iX;

import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.plaf.metal.MetalLookAndFeel;

import iX.Windows.iXPAD;
import iX.Utilities.iXDarkTheme;
import iX.Utilities.iXVariables;

/**
 * @author abrar
 *
 */
public class iXMain {

	public static void main(String[] args) {
		try {
			String osName = System.getProperty("os.name");
			System.out.println("iXPAD : Info : OS Name - \"" + osName + "\"");

			String pathName = System.getProperty("user.home");
			System.out.println("iXPAD : Info : User home directrory - \"" + pathName + "\"");

			ArrayList<String> list = new ArrayList<>();
			for (LookAndFeelInfo f : UIManager.getInstalledLookAndFeels()) {
				list.add(f.getClassName());
			}
			System.out.println("iXPAD : Info : Available installed theme " + list);

			// TODO Move to settings
			boolean custom = true;
			boolean customLight = false;

			if (custom == true) {
				if (customLight == true) {
					UIManager.setLookAndFeel(iXVariables.lightThemeNimbus);
				} else {
					MetalLookAndFeel.setCurrentTheme(new iXDarkTheme().darkTheme);
					
			        try {
			            UIManager.setLookAndFeel(new MetalLookAndFeel());
			        } catch (Exception ev) {
			        	
			        }
				}
			} else {
				if (osName.equals("Linux")) {
					if (list.contains(iXVariables.linuxGTKTheme)) {
						// Freezes UI when system changes the theme.
						UIManager.setLookAndFeel(iXVariables.linuxGTKTheme);
//						UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					} else {
						custom = true;
					}
				} else {
					if (list.contains(iXVariables.windowsTheme)) {
						UIManager.setLookAndFeel(iXVariables.windowsTheme);
					} else {
						custom = true;
					}
				}
			}

		} catch (Exception e) {
			System.out.println("iXPAD : Warning : Problem occurs in setting theme(" + e.getMessage() + ").");
		}

		iXPAD ixpad = new iXPAD();
		JFrame.setDefaultLookAndFeelDecorated(true);
		ixpad.setVisible(true);
	}

}
