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

import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import iX.Widgets.iXPAD; 

public class iXMain {

	public static void main(String[] args) {
		try {
			String windows = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
			String linuxGTK = "com.sun.java.swing.plaf.gtk.GTKLookAndFeel";
			
			String osName = System.getProperty("os.name");
			System.out.println(osName);
			
			if (osName.equals("Linux")) {
				System.out.println(osName + linuxGTK);
				UIManager.setLookAndFeel(linuxGTK);
			} else {
				UIManager.setLookAndFeel(windows);
			}
			
			for (LookAndFeelInfo f : UIManager.getInstalledLookAndFeels()) {
				System.out.println(f.toString());
			}
		} catch(Exception e) {
			
		}
		iXPAD ixpad = new iXPAD();
		ixpad.setVisible(true);
	}

}
