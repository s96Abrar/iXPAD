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

import java.awt.BorderLayout;
import java.awt.Container;
import java.io.BufferedReader;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class iXActivity extends JFrame implements ListSelectionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final String activityFile = System.getProperty("user.home") + "/iXPADActivity.txt";

	Container frameContainer;
	JList<String> recentList;
	
	public iXActivity() {
		// Load the last activity
		System.out.println("Recent Activity Initializing...");
		
		setupUi();
		loadActivity();
	}
	
	private void setupUi() {
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setBounds(150,50,1060,640);
		this.setTitle("iXPAD - Recent Activity");
		
		// center the frame on the monitor
        this.setLocationRelativeTo(null);
		
		frameContainer = this.getContentPane();
		frameContainer.setLayout(new BorderLayout(2, 2));
		
		recentList = new JList<String>();
		frameContainer.add(recentList, BorderLayout.CENTER);
		recentList.addListSelectionListener(this);
	}
	
	private void loadActivity() {
		ArrayList<Date> dateTime = new ArrayList<Date>();
		ArrayList<String> fileName = new ArrayList<String>();
		
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(activityFile));
			String currentLine = "";
			while ((currentLine = br.readLine()) != null) {
				String[] tList = currentLine.split("\t\t\t");
				if (tList.length > 1) {
					//System.out.println(new SimpleDateFormat("HH.mm.ss.SSS dd.MM.yyyy").parse(tList[0]));
					//System.out.println(tList[1]);
					try {
						dateTime.add( new SimpleDateFormat("HH.mm.ss.SSS dd.MM.yyyy").parse(tList[0]) );
						fileName.add(tList[1]);
					} catch (Exception ex){
						System.out.println(tList[0]);
					}					
				}
			}		
			br.close();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		ArrayList<Date> tdt = new ArrayList<Date>( dateTime );
		
		// Sorting on date time by descending order
		Collections.sort(dateTime, new Comparator<Date>() {

			@Override
			public int compare(Date obj1, Date obj2) {
				if (obj1 == null || obj2 == null)
			        return 0;
				
			    return obj2.compareTo(obj1);
			}
		});
		
		ArrayList<String> list = new ArrayList<String>();
		for (Date d : dateTime) {
			int index = tdt.indexOf(d);
			 list.add(fileName.get(index));
		}
		
		String[] ts = new String[list.size()];
		ts = list.toArray(ts);
		
		recentList.setListData(ts);
	}
	
	public static void saveActivity(String lastFilePath) {
		String str = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH.mm.ss.SSS dd.MM.yyyy")) + "\t\t\t" + lastFilePath;
		Utilities.appendStrToFile(str + "\n", activityFile);
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		MainFrame f = new MainFrame();
		f.setVisible(true);
		f.openFile(recentList.getSelectedValue());
	}
}
