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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import iX.Utilities.iXUtility;
import iX.Utilities.iXVariables;
import iX.Widgets.iXActivityTree;

/**
 * @author abrar
 *
 */
public class iXActivity extends JDialog implements ListSelectionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Component parent;
	private JPanel buttonPanel;
	private JButton btnClearActivity;
	private iXActivityTree treeActivity;

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
		btnClearActivity.addActionListener((l) -> {
			clearActivity();
		});
		buttonPanel = new JPanel();
		treeActivity = new iXActivityTree(iXVariables.iXPADActivityFile, "Activity");
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

	public void saveActivity(String lastFilePath) {
		String str = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH.mm.ss.SSS dd.MM.yyyy")) + "\t\t\t"
				+ lastFilePath;
		ixUtil.saveToFile(str + "\n", iXVariables.iXPADActivityFile, true);
//		Utilities.appendStrToFile(str + "\n", activityFile);
	}

	private void clearActivity() {
		if (treeActivity.isEmpty() == false) {
			ixUtil.saveToFile("", iXVariables.iXPADActivityFile);
			treeActivity.clearAll();
		}
	}

	public void valueChanged(ListSelectionEvent e) {
//		MainFrame f = new MainFrame();
//		f.setVisible(true);
//		f.openFile(recentList.getSelectedValue());
	}

	public static void main(String[] args) {		
		iXActivity test = new iXActivity(null);
		test.setVisible(true);
	}
}
