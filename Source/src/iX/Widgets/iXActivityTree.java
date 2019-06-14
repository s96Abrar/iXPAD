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

package iX.Widgets;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import iX.Utilities.iXVariables;

/**
 * @author abrar
 *
 */
public class iXActivityTree extends iXTree {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	// Sorting by descending order
	private Comparator<String> compare = new Comparator<String>() {
		public int compare(String obj1, String obj2) {
			Date o1 = null;
			Date o2 = null;
			try {
				o1 = new SimpleDateFormat(iXVariables.DATE_TIME_FORMAT).parse(obj1);
				o2 = new SimpleDateFormat(iXVariables.DATE_TIME_FORMAT).parse(obj2);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if (o1 == null || o2 == null)
				return 0;

			return o2.compareTo(o1); // Sort in descending order
		}
	};
	

	public iXActivityTree(String fileName, String rootName) {
		super(rootName, "Activity");
		
		setComparator(compare);
		
		initializeTree(fileName, true);
	}

}
