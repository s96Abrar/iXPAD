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
import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;

import iX.Utilities.iXVariables;
import iX.Widgets.iXPinTree;

public class iXPin extends JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
//	public static final String pinFile = System.getProperty("user.home") + "/iXPADPin.txt";
//	public static final String pinSectionFile = System.getProperty("user.home") + "/iXPADPinSection.txt";

	private Component parent;
	private JPanel buttonPanel;
	private JButton btnClearActivity;
	private iXPinTree treePin;

	public iXPin(Component parent) {
		if (parent == null) {
			System.out.println("iXPAD : PinView parent cannot be null");
		}

		this.parent = parent;
		setModal(true);
		setupUI();
	}

	private void setupUI() {
		// Set frame properties
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.setMinimumSize(new Dimension(600, 600));
		this.setTitle("iXPAD - Pin View");
		this.setLocationRelativeTo(parent); // Place the frame to the center of the parent.
		// ====================

		btnClearActivity = new JButton("Clear Pins");
		buttonPanel = new JPanel();
		treePin = new iXPinTree(iXVariables.iXPADPinFile, "PinView");

		BoxLayout buttonPanelLayout = new BoxLayout(buttonPanel, BoxLayout.X_AXIS);
		BorderLayout mainLayout = new BorderLayout();

		buttonPanel.setLayout(buttonPanelLayout);
		buttonPanel.add(Box.createHorizontalGlue());
		buttonPanel.add(btnClearActivity);

		this.setLayout(mainLayout);
		this.add(buttonPanel, BorderLayout.PAGE_START);
		this.add(treePin, BorderLayout.CENTER);
	}

	public static void main(String[] args) {
		iXPin test = new iXPin(null);
		test.setVisible(true);
	}
}
