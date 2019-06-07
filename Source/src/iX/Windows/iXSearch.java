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

import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;

public class iXSearch extends JDialog implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Component parent;
	
	int startIndex = 0;
	int select_start = -1;
	
	private JLabel lblFind;
	private JLabel lblReplace;
	private JTextField txtFind;
	private JTextField txtReplace;
	private JButton btnFind;
	private JButton btnFindNext;
	private JButton btnReplace;
	private JButton btnReplaceAll;
	private JButton btnCancel;
	
	private JTextPane editor;

	public iXSearch(JTextPane textEditor) {
		this.editor = textEditor;
		setupUI();
	}

	private void setupUI() {
		// Set frame properties
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.setMinimumSize(new Dimension(450, 120));
		this.setTitle("iXPAD - Find/Replace");
		this.setLocationRelativeTo(parent);
		// ====================
		
		GridLayout mainLayout = new GridLayout(3, 4, 2, 2);
		
		lblFind = new JLabel("Find");
		lblReplace = new JLabel("Replace");
		txtFind = new JTextField();
		txtReplace = new JTextField();
		btnFind = new JButton("Find");
		btnFindNext = new JButton("Find Next");
		btnReplace = new JButton("Replace");
		btnReplaceAll = new JButton("Replace All");
		btnCancel = new JButton("Cancel");
		
		this.setLayout(mainLayout);
		
		this.add(lblFind, "Center", 0);
		this.add(txtFind, 1);
		this.add(btnFind, 2);
		this.add(btnFindNext, 3);
		this.add(lblReplace, 4);
		this.add(txtReplace, 5);
		this.add(btnReplace, 6);
		this.add(btnReplaceAll, 7);
		this.add(new JLabel(""), 8);
		this.add(new JLabel(""), 9);
		this.add(new JLabel(""), 10);
		this.add(btnCancel, 11);
		
	}
	
	public void find() {
		editor.requestFocus();
		select_start = editor.getText().toLowerCase().indexOf(txtFind.getText().toLowerCase());
		if (select_start == -1) {
			startIndex = 0;
			JOptionPane.showMessageDialog(null, "Could not find \"" + txtFind.getText() + "\"!");
			return;
		}
		if (select_start == editor.getText().toLowerCase().lastIndexOf(txtFind.getText().toLowerCase())) {
			startIndex = 0;
		}
		int select_end = select_start + txtFind.getText().length();
		editor.select(select_start, select_end);
		editor.requestFocus();
	}

	public void findNext() {
		String selection = editor.getSelectedText();
		try {
			selection.equals("");
		} catch (NullPointerException e) {
			selection = txtFind.getText();
			try {
				selection.equals("");
			} catch (NullPointerException e2) {
				selection = JOptionPane.showInputDialog("Find:");
				txtFind.setText(selection);
			}
		}
		try {
			int select_start = editor.getText().toLowerCase().indexOf(selection.toLowerCase(), startIndex);
			int select_end = select_start + selection.length();
			editor.select(select_start, select_end);
			startIndex = select_end + 1;

			if (select_start == editor.getText().toLowerCase().lastIndexOf(selection.toLowerCase())) {
				startIndex = 0;
			}
		} catch (NullPointerException e) {
		}
	}

	public void replace() {
		try {
			find();
			if (select_start != -1)
				editor.replaceSelection(txtReplace.getText());
		} catch (NullPointerException e) {
			System.out.print("Null Pointer Exception: " + e);
		}
	}

	public void replaceAll() {
		editor.setText(editor.getText().toLowerCase().replaceAll(txtFind.getText().toLowerCase(), txtReplace.getText()));
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnFind) {
			find();
		} else if (e.getSource() == btnFindNext) {
			findNext();
		} else if (e.getSource() == btnReplace) {
			replace();
		} else if (e.getSource() == btnReplaceAll) {
			replaceAll();
		} else if (e.getSource() == btnCancel) {
			this.setVisible(false);
		}
	}
	
	public static void main(String[] args) {
		iXSearch s = new iXSearch(new JTextPane());
		s.setVisible(true);
	}

}