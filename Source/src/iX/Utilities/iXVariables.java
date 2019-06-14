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

/**
 * @author abrar
 *
 */
public class iXVariables {

	public static final String homeDirectory = System.getProperty("user.home");
	public static final String iXPADPinFile = homeDirectory + "/iXPADPin.txt";
	public static final String iXPADPinSectionFile = homeDirectory + "/iXPADPinSection.txt";
	public static final String iXPADActivityFile = homeDirectory + "/iXPADActivity.txt";

	public static final String VALUE_SEPERATOR = "\t\t\t";
	public static final String DATE_FORMAT = "dd.MM.yyyy";
	public static final String DATE_TIME_FORMAT = "HH.mm.ss.SSS dd.MM.yyyy";

	public static final String lightThemeNimbus = "javax.swing.plaf.nimbus.NimbusLookAndFeel";
	public static final String linuxGTKTheme = "com.sun.java.swing.plaf.gtk.GTKLookAndFeel";
	public static final String windowsTheme = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";

}
