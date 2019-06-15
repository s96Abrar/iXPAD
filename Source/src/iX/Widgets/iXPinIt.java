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

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

import iX.Utilities.iXUtility;
import iX.Utilities.iXVariables;

/**
 * @author abrar
 *
 */
public class iXPinIt extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JCheckBox chkOldSection;
	private JComboBox<String> sectionList;
	private JCheckBox chkNewSection;
	private JTextField tfNewSection;
	private JLabel lblFileName;
	private JButton btnOK;
	private String filePath;

	public iXPinIt(String filePath, Component parent) {
		this.filePath = filePath;
		setupUI(parent);
	}

	private void setupUI(Component parent) {
		// Set frame properties
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.setMinimumSize(new Dimension(400, 200));
		this.setTitle("iXPAD - Pin It");
		this.setLocationRelativeTo(parent); // Place the frame to the center of the parent.
		// ====================

		chkOldSection = new JCheckBox("Add to existing section");
		sectionList = new JComboBox<>();

		ArrayList<String> list = new ArrayList<>();
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(iXVariables.iXPADPinSectionFile));
			String currentLine = "";
			while ((currentLine = br.readLine()) != null) {
				System.out.println("Line " + currentLine);
				list.add(currentLine);
			}
		} catch (IOException e) {
			System.out.println("iXPAD : File reading problem.\n" + e.getMessage());
		} catch (Exception e) {
			System.out.println("iXPAD : " + e.getMessage());
		}
		System.out.println("Sr " + list.toString());

		for (String s : list) {
			sectionList.addItem(s);
		}

		chkNewSection = new JCheckBox("Add to new section ");
		tfNewSection = new JTextField();
		lblFileName = new JLabel(filePath);
		btnOK = new JButton("OK");

		iXUtility ixUtil = new iXUtility();

		btnOK.addActionListener((l) -> {
			if (chkNewSection.isSelected()) {
				ixUtil.saveToFile(tfNewSection.getText() + "\t\t\t" + lblFileName.getText() + "\n",
						iXVariables.iXPADPinFile, true);
				ixUtil.saveToFile(tfNewSection.getText() + "\n", iXVariables.iXPADPinSectionFile, true);
			} else if (chkOldSection.isSelected()) {
				ixUtil.saveToFile(sectionList.getSelectedItem() + "\t\t\t" + lblFileName.getText() + "\n",
						iXVariables.iXPADPinFile, true);
				ixUtil.saveToFile((String) sectionList.getSelectedItem() + "\n", iXVariables.iXPADPinSectionFile, true);
			}
		});

		GridBagLayout layout = new GridBagLayout();

		setLayout(layout);

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		add(chkOldSection, gbc);
		gbc.gridx = 1;
//		gbc.ipadx = 60;
		add(sectionList, gbc);
		gbc.gridy = 1;
		add(tfNewSection, gbc);
		gbc.gridx = 0;
		add(chkNewSection, gbc);
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.gridwidth = 2;
//		gbc.ipady = 40;
//		gbc.ipadx = 0;
		add(lblFileName, gbc);
		gbc.gridx = 1;
		gbc.gridy = 3;
//		gbc.ipady = 0;
		add(btnOK, gbc);

	}

	public static void main(String[] args) {
		iXPinIt pin = new iXPinIt("/home/abrar/", null);
		pin.setVisible(true);
	}
}
