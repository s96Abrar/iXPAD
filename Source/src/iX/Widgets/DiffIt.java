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
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

import iX.Duplicity.DiffMatch;
import iX.Duplicity.DiffMatch.Diff;
import iX.Duplicity.DiffMatch.Operation;
import iX.Utilities.iXUtility;

/**
 * @author abrar
 *
 */
public class DiffIt extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JTextPane editor;
	private JTextArea lbl;

	public DiffIt() {
		// Set frame properties
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.setMinimumSize(new Dimension(600, 600));
		this.setTitle("iXPAD - Duplicity");
		this.setLocationRelativeTo(null); // Place the frame to the center of the parent.
		// ====================

		editor = new JTextPane();
		JScrollPane scroll = new JScrollPane();
		scroll.setViewportView(editor);
		lbl = new JTextArea();
		lbl.setEditable(false);
		add(lbl, BorderLayout.PAGE_END);
		add(scroll, BorderLayout.CENTER);

		iXUtility isp = new iXUtility();

//		String text1 = isp.openFromFile("F:\\iXPAD\\Source\\src\\iX\\Widgets\\iXPinTree.java");
//		String text2 = isp.openFromFile("F:\\iXPAD\\Source\\src\\iX\\Widgets\\iXTree.java");
		String text1 = isp
				.openFromFile("/home/abrar/Desktop/Varsity_project/Main/iXPAD/Source/src/iX/Widgets/iXPinTree.java");
		String text2 = isp
				.openFromFile("/home/abrar/Desktop/Varsity_project/Main/iXPAD/Source/src/iX/Widgets/iXTree.java");

		DiffMatch dmp = new DiffMatch();
		int i = 0;
		int j = 0;
		int k = 0;
		
		Color color = null;
		StyleContext sc = StyleContext.getDefaultStyleContext();

		int length = 0;
		for (Diff d : dmp.diff_main(text1, text2)) {;
			if (d.operation == Operation.EQUAL) {
				i += d.text.length();
				color = editor.getBackground();
			} else if (d.operation == Operation.INSERT) {
				j += d.text.length();
				color = Color.GREEN;
			} else if (d.operation == Operation.DELETE) {
				k += d.text.length();
				color = Color.RED;
			}

			AttributeSet attr = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Background, color);
			try {
				editor.getDocument().insertString(length, d.text, attr);
			} catch (BadLocationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			length += d.text.length();
		}

		lbl.setText("Count of document modifications(old, new) :\nCharacter deleted : " + k + "\nCharacter inserted : "
				+ j + "\nCharacter unchanged : " + i);
	}

	public static void main(String[] args) {
		DiffIt jk = new DiffIt();
		jk.setVisible(true);
	}
}
