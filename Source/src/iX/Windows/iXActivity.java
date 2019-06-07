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

package iX.Windows;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.Date;
import java.util.Hashtable;
import java.util.TreeMap;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;

import iX.Utilities.iXUtility;
import iX.Utilities.iXVariables;
import iX.Widgets.iXTree;

public class iXActivity extends JDialog implements ListSelectionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Component parent;
	private JPanel buttonPanel;
	private JButton btnClearActivity;
	private iXTree treeActivity;

	iXUtility ixUtil;
	Container frameContainer;
	JList<String> recentList;
	
	public iXActivity(Component parent) {
		if (parent == null) {
			System.out.println("iXPAD : Recent activity parent cannot be null");
		}
		
		this.parent = parent;
		setModal(true);
		setupUI();
		
//		loadActivity();
	}
	
	private void setupUI() {
		// Set frame properties
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.setMinimumSize(new Dimension(600, 600));
		this.setTitle("iXPAD - Activity");
		this.setLocationRelativeTo(parent); // Place the frame to the center of the parent.
		// ====================
        
		ixUtil = new iXUtility();
		
        btnClearActivity = new JButton("Clear Activity");  
        btnClearActivity.addActionListener( (l) -> {
        	clearActivity();
        });
        buttonPanel = new JPanel();
        treeActivity = new iXTree(iXVariables.iXPADActivityFile, "Activity");
        treeActivity.expandNode(treeActivity.getRootName());
        
        JScrollPane treeScroll = new JScrollPane();
        treeScroll.setViewportView(treeActivity);
        
        // TODO tree.putClientProperty("JTree.lineStyle", "Horizontal");
        
		BoxLayout buttonPanelLayout = new BoxLayout(buttonPanel, BoxLayout.X_AXIS);
		BorderLayout mainLayout = new BorderLayout();		
        
        buttonPanel.setLayout(buttonPanelLayout);
        buttonPanel.add(Box.createHorizontalGlue());
        buttonPanel.add(btnClearActivity);        
        
        this.setLayout(mainLayout);
        this.add(buttonPanel, BorderLayout.PAGE_START);
        this.add(treeScroll, BorderLayout.CENTER);
	}
	
	private void loadActivity() {

		Hashtable<Date, String> act = new Hashtable<>();
		TreeMap<Date, String> sorted = new TreeMap<>( 	// Sorting on date time by descending order
													new Comparator<Date>() {
														public int compare(Date obj1, Date obj2) {
															if (obj1 == null || obj2 == null)
														        return 0;
															
															return obj2.compareTo(obj1);// Sort in descending order
														}
													});
		
		BufferedReader bbr;
		try {
			bbr = new BufferedReader(new FileReader(iXVariables.iXPADActivityFile));
			String currentLine = null;
			while ((currentLine = bbr.readLine()) != null) {
				String[] tList = currentLine.split("\t\t\t");
				if (tList.length > 1) {
					try {
						Date key = new SimpleDateFormat("HH.mm.ss.SSS dd.MM.yyyy").parse(tList[0]);
						String value = (tList[1]);
						act.put(key, value);
					} catch (Exception e) {
						
					}
				}
			}
		} catch (Exception e) {
			
		}
		
		
//		ArrayList<Date> dateTime = new ArrayList<Date>();
//		ArrayList<String> fileName = new ArrayList<String>();
//		
//		BufferedReader br;
//		try {
//			br = new BufferedReader(new FileReader(activityFile));
//			String currentLine = "";
//			while ((currentLine = br.readLine()) != null) {
//				String[] tList = currentLine.split("\t\t\t");
//				if (tList.length > 1) {
//					//System.out.println(new SimpleDateFormat("HH.mm.ss.SSS dd.MM.yyyy").parse(tList[0]));
//					//System.out.println(tList[1]);
//					try {
//						dateTime.add( new SimpleDateFormat("HH.mm.ss.SSS dd.MM.yyyy").parse(tList[0]) );
//						fileName.add(tList[1]);
//					} catch (Exception ex){
//						System.out.println(tList[0]);
//					}					
//				}
//			}		
//			br.close();
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//		ArrayList<Date> tdt = new ArrayList<Date>( dateTime );
//		
//		// Sorting on date time by descending order
//		Collections.sort(datetime, new Comparator<Date>() {
//
//			@Override
//			public int compare(Date obj1, Date obj2) {
//				if (obj1 == null || obj2 == null)
//			        return 0;
//				
//			    return obj2.compareTo(obj1);
//			}
//		});
//		
//		ArrayList<String> list = new ArrayList<String>();
//		for (Date d : dateTime) {
//			int index = tdt.indexOf(d);
//			 list.add(fileName.get(index));
//		}
//		
//		String[] ts = new String[list.size()];
//		ts = list.toArray(ts);
		
		sorted.putAll(act);
		act.putAll(sorted);
		
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("root");
		for (Date key : act.keySet()) {
			String[] dateTime = (new SimpleDateFormat("HH.mm.ss.SSS dd.MM.yyyy").format(key)).toString().split(" ");
			DefaultMutableTreeNode date = new DefaultMutableTreeNode(dateTime[1]);
			DefaultMutableTreeNode time = new DefaultMutableTreeNode(dateTime[0]);
			date.add(time);
			root.add(date);
			
		}
//		treeActivity.getModel().;
		sorted.clear();
//		treeActivity = new JTree(act);
	}
	
	public void saveActivity(String lastFilePath) {
		String str = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH.mm.ss.SSS dd.MM.yyyy")) + "\t\t\t" + lastFilePath;
		ixUtil.saveToFile(str + "\n", iXVariables.iXPADActivityFile, true);
//		Utilities.appendStrToFile(str + "\n", activityFile);
	}

	private void clearActivity() {
		if (treeActivity.isEmpty() == false) {
			ixUtil.saveToFile("", iXVariables.iXPADActivityFile);
			treeActivity.clearAll();
		}
	}
	
	@Override
	public void valueChanged(ListSelectionEvent e) {
//		MainFrame f = new MainFrame();
//		f.setVisible(true);
//		f.openFile(recentList.getSelectedValue());
	}
	
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
		} catch(Exception e) {
			
		}
		iXActivity test = new iXActivity(null);
		test.setVisible(true);
	}
}
