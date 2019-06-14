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

import java.util.Comparator;

/**
 * @author abrar
 *
 */
public class iXPinTree extends iXTree {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Comparator<String> compare = new Comparator<String>() {
		public int compare(String obj1, String obj2) {
			if (obj1 == null || obj2 == null)
				return 0;

			return obj2.compareTo(obj1); // Sort in descending order
		}
	};

	public iXPinTree(String fileName, String rootName) {
		super(rootName, "Pins");

		setComparator(compare);
		
		initializeTree(fileName, false);
	}

}
