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

import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import iX.Windows.iXPAD;
import iX.Utilities.iXVariables;

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
			boolean custom = false;
			boolean customLight = false;

			if (osName.equals("Linux")) {
				if (list.contains(iXVariables.linuxGTKTheme)) {
					UIManager.setLookAndFeel(iXVariables.linuxGTKTheme);
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

			if (custom == true) {
				if (customLight == true) {
					UIManager.setLookAndFeel(iXVariables.lightThemeNimbus);
				} else {
					// TODO
					// Set the black theme
					// Create a custom class

				}
			}

		} catch (Exception e) {
			System.out.println("iXPAD : Warning : Problem occurs in setting theme(" + e.getMessage() + ").");
		}

		iXPAD ixpad = new iXPAD();
		ixpad.setVisible(true);
	}

}
