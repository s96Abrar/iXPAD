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
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;

public class iXSearch extends JDialog implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	int startIndex = 0;
	int select_start = -1;
	
	private JLabel lblFind;
	private JLabel lblReplace;
	private JTextField txtFind;
	private JTextField txtReplace;
	private JCheckBox chkMatchCase;
	private JButton btnFind;
	private JButton btnFindNext;
	private JButton btnReplace;
	private JButton btnReplaceAll;
	private JButton btnCancel;
	
	private JTextPane editor;

	public iXSearch(JTextPane textEditor) {
		
		this.editor = textEditor;
		
		setupUI();
		setVisible(true);
	}

	private void setupUI() {
		// Set frame properties
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.setMinimumSize(new Dimension(450, 120));
		this.setTitle("iXPAD - Find/Replace");
		this.setLocationRelativeTo(editor);
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
		chkMatchCase = new JCheckBox("Match Case");
		btnCancel = new JButton("Cancel");
		
		btnFind.addActionListener(this);
		btnFindNext.addActionListener(this);
		btnReplace.addActionListener(this);
		btnReplaceAll.addActionListener(this);
		btnCancel.addActionListener(this);
		
		this.setLayout(mainLayout);
		
		this.add(lblFind, 0);
		this.add(txtFind, 1);
		this.add(btnFind, 2);
		this.add(btnFindNext, 3);
		this.add(lblReplace, 4);
		this.add(txtReplace, 5);
		this.add(btnReplace, 6);
		this.add(btnReplaceAll, 7);
		this.add(new JLabel(""), 8);
		this.add(new JLabel(""), 9);
		this.add(chkMatchCase, 10);
		this.add(btnCancel, 11);		
	}
	
	private void findString(String str, int pos) {
		if (str.isEmpty()) {
			return;
		}
		
		if (pos == -1) {
			pos = 0;
		}
		
		String from = editor.getText();
		int startPos = -1;
		if (chkMatchCase.isSelected()) {
			startPos = from.indexOf(str, pos);
		} else {
			startPos = from.toLowerCase().indexOf(str.toLowerCase(), pos);
		}
		
		if (startPos == -1) {
			JOptionPane.showMessageDialog(null, "Could not find \"" + str + "\"!");
			return;
		}
		int endPos = startPos + str.length();
		
		editor.select(startPos, endPos);
	}
	
	public void find() {
		editor.requestFocus();
		
		int currPos = editor.getCaretPosition();
		
		String selStr = editor.getSelectedText();
		if (selStr == null || selStr == "") {
			if (currPos == editor.getText().length()) {
				currPos = 0;
			}
		} else {
			currPos -= selStr.length();
		}
		
		findString(txtFind.getText(), currPos);
		
		editor.requestFocus();
	}

	public void findNext() {
		editor.requestFocus();
		
		int currSel = editor.getCaretPosition();	
		if (currSel == editor.getText().length()) {
			currSel = 0;
		}
		
		findString(txtFind.getText(), currSel);
		
		editor.requestFocus();
	}

	public void replace() {
		try {
			find();
			String selStr = editor.getSelectedText();
			if (selStr != null || selStr != "") {
				editor.replaceSelection(txtReplace.getText());
			}
		} catch (NullPointerException e) {
			System.out.print("Null Pointer Exception: " + e);
		}
	}

	public void replaceAll() {
		if (chkMatchCase.isSelected()) {
			editor.setText(editor.getText().replaceAll(txtFind.getText(), txtReplace.getText()));
		} else {
			editor.setText(editor.getText().toLowerCase().replaceAll(txtFind.getText().toLowerCase(), txtReplace.getText()));
		}
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
			this.dispose();
		}
	}
	
	public static void main(String[] args) {
		iXSearch s = new iXSearch(new JTextPane());
		s.setVisible(true);
	}

}