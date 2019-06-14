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

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.TreeMap;

import javax.swing.JFrame;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import iX.Utilities.iXUtility;
import iX.Utilities.iXVariables;

/**
 * @author abrar
 *
 */
public class iXTree extends JTree {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String rootName;
	private boolean isTreeEmpty;

	private HashMap<String, DefaultMutableTreeNode> treeStructureMap;

	public iXTree(String fileName, String rootName) {
		this.rootName = rootName;

		DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode(rootName);

		this.setModel(new DefaultTreeModel(rootNode));

		// Sorting on date time by descending order
		Comparator<Date> dateSort = new Comparator<Date>() {
			public int compare(Date obj1, Date obj2) {
				if (obj1 == null || obj2 == null)
					return 0;

				return obj2.compareTo(obj1); // Sort in descending order
			}
		};

		TreeMap<Date, String> sortedMap = new TreeMap<>(dateSort);

		sortedMap.putAll(readFileToHash(fileName));

		treeStructureMap = new HashMap<>();
		treeStructureMap.put(rootName, rootNode);

		if (sortedMap.isEmpty()) {
			isTreeEmpty = true;
			rootNode.setUserObject("No Activity");
		} else {
			isTreeEmpty = false;
			readHashToTree(sortedMap);
		}

		sortedMap.clear();
	}

	private void readHashToTree(TreeMap<Date, String> sortedMap) {
		for (Date key : sortedMap.keySet()) {
			String keyName = iXUtility.convertToDateText(key, iXVariables.DATE_FORMAT);// (new
																						// SimpleDateFormat(DATE_FORMAT).format(key)).toString();
			String value = sortedMap.get(key);

			addNodeTo(value, keyName);
		}
	}

	private Hashtable<Date, String> readFileToHash(String fileName) {
		Hashtable<Date, String> hash = new Hashtable<>();
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(fileName));
			String currentLine = null;
			while ((currentLine = br.readLine()) != null) {
				String[] tList = currentLine.split(iXVariables.VALUE_SEPERATOR);
				if (tList.length > 1) {
					try {
						Date key = new SimpleDateFormat(iXVariables.DATE_TIME_FORMAT).parse(tList[0]);
						String value = tList[1];
						hash.put(key, value);
					} catch (ParseException e) {
						System.out.println("iXPAD : Date time parsing problem.\n" + e.getMessage());
					}
				}
			}
		} catch (IOException e) {
			System.out.println("iXPAD : File reading problem.\n" + e.getMessage());
		} catch (Exception e) {
			System.out.println("iXPAD : " + e.getMessage());
		}

		return hash;
	}

	private DefaultMutableTreeNode createNode(String nodeName, DefaultMutableTreeNode parentNode) {
		DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(nodeName);
		treeStructureMap.put(nodeName, childNode);

		if (parentNode == null) {
			return childNode;
		}

		parentNode.add(childNode);

		return parentNode;
	}

	private DefaultMutableTreeNode getNode(String nodeName) {
		return (DefaultMutableTreeNode) (treeStructureMap.get(nodeName));
	}

	private void addNodeTo(String childNodeName, String parentNodeName) {
		DefaultMutableTreeNode parentNode = getNode(parentNodeName);
		DefaultMutableTreeNode childNode;

		System.out.println(parentNode + " " + parentNodeName);
		if (parentNode == null) {
			parentNode = createNode(parentNodeName, null);
		} else {
			childNode = createNode(childNodeName, parentNode);
			return;
		}

		childNode = createNode(childNodeName, parentNode);
		((DefaultTreeModel) this.getModel()).insertNodeInto(childNode, getNode(rootName),
				getNode(rootName).getChildCount());
	}

	public void expandNode(String nodeName) {
		if (getNode(nodeName) == null) {
			return;
		}

		this.expandPath(new TreePath(getNode(nodeName)));
	}

	public String getRootName() {
		return rootName;
	}

	public void clearAll() {
		((DefaultTreeModel) this.getModel()).setRoot(new DefaultMutableTreeNode("No Activity"));
		treeStructureMap.clear();
	}

	public boolean isEmpty() {
		return isTreeEmpty;
	}

	public static void main(String[] args) {
		JFrame jf = new JFrame();
		iXTree tree = new iXTree(System.getProperty("user.home") + "/iXPADActivity.txt", "Activity");
//		tree.setShowsRootHandles(false);
//		tree.expandNode(tree.getRootName());

		// show
		jf.getContentPane().add(tree);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.setBounds(0, 0, 500, 500);

		jf.setVisible(true);
	}
}
