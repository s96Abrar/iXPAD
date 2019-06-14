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
import java.util.Comparator;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.TreeMap;

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
	private String className;
	private boolean isTreeEmpty;

	private HashMap<String, DefaultMutableTreeNode> treeStructureMap;

	private Comparator<String> compare = null;
	
	public iXTree(String rootName, String className) {
		this.rootName = rootName;
		this.className = className;

		DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode(rootName);
//this.setCellRenderer(new DefaultTreeCellRenderer() {
//	
//	@Override
//	public Component getTreeCellRendererComponent(JTree tree,
//	    Object value, boolean selected, boolean expanded,
//	    boolean leaf, int row, boolean hasFocus) {
//	        super.getTreeCellRendererComponent(tree, value, selected,expanded, leaf, row, hasFocus);
//	        DefaultMutableTreeNode nodo = (DefaultMutableTreeNode) value;
//	        if (tree.getModel().getRoot().equals(nodo)) {
////	            setIcon(root);
//	        } else if (nodo.getChildCount() > 0) {
////	            setIcon(parent);
//	        } else {
//	        	Icon leaf1 = FileSystemView.getFileSystemView().getSystemIcon( new File(nodo.toString()) );
//	            setIcon(leaf1);
//	        }
//	        return this;
//	}
//});
		this.setModel(new DefaultTreeModel(rootNode));
		
		treeStructureMap = new HashMap<>();
		treeStructureMap.put(rootName, rootNode);
	}
	
	public void initializeTree(String fileName, boolean convertToDate) {
		TreeMap<String, String> sortedMap = new TreeMap<>(compare);

		sortedMap.putAll(readFileToHash(fileName));

		if (sortedMap.isEmpty()) {
			isTreeEmpty = true;
			clearAll();
		} else {
			isTreeEmpty = false;
			readHashToTree(sortedMap, convertToDate);
		}

		sortedMap.clear();
	}
	
	private void readHashToTree(TreeMap<String, String> sortedMap, boolean convertToDate) {
		for (String key : sortedMap.keySet()) {
			String keyName = null;
			if (convertToDate == true) {
				keyName = iXUtility.convertToDateText(key, iXVariables.DATE_FORMAT);
			} else {
				keyName = key;
			}
			
			String value = sortedMap.get(key);

			addNodeTo(value, keyName);
		}
	}

	private Hashtable<String, String> readFileToHash(String fileName) {
		Hashtable<String, String> hash = new Hashtable<>();
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(fileName));
			String currentLine = null;
			while ((currentLine = br.readLine()) != null) {
				String[] tList = currentLine.split(iXVariables.VALUE_SEPERATOR);
				if (tList.length > 1) {
					String key = tList[0];
					String value = tList[1];
					hash.put(key, value);
				}
			}
		} catch (IOException e) {
			System.out.println("iXPAD : File reading problem.\n" + e.getMessage());
		} catch (Exception e) {
			System.out.println("iXPAD : " + e.getMessage());
		}

		return hash;
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

	public void setComparator(Comparator<String> comp) {
		compare = comp;
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
		((DefaultTreeModel) this.getModel()).setRoot(new DefaultMutableTreeNode("No " + className));
		treeStructureMap.clear();
	}

	public boolean isEmpty() {
		return isTreeEmpty;
	}
}
