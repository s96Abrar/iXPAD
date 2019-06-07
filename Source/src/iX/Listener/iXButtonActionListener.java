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

package iX.Listener;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import iX.TextEditor.iXEditor;
import iX.Windows.iXPAD;

public class iXButtonActionListener implements ActionListener{
	
	private iXPAD ixPAD;
	private iXEditor ixEditor;
	
	public iXButtonActionListener(Component parent) {
		if (parent instanceof iXPAD) {
			ixPAD = (iXPAD) parent;
			ixEditor = ixPAD.getiXEditor();
		} else {
			System.out.println("iXPAD : Parent not found...");
		}
	}

	public void actionPerformed(ActionEvent e) {		
		if (e.getActionCommand() == "Open") {
			ixEditor.openFile();
		} else if (e.getActionCommand() == "New Page") {
			ixPAD.createTab();
		} else if (e.getActionCommand() == "Save") {
			ixEditor.saveFile();
		} else if (e.getActionCommand() == "Save As") {
			ixEditor.saveAsFile();
		} else if (e.getActionCommand() == "Copy") {
			ixEditor.copy();
		} else if (e.getActionCommand() == "Paste") {
			ixEditor.paste();
		} else if (e.getActionCommand() == "Cut") {
			ixEditor.cut();
		} else if (e.getActionCommand() == "Undo") {
			ixEditor.undo();
		} else if (e.getActionCommand() == "Redo") {
			ixEditor.redo();
		} else if (e.getActionCommand() == "Search") {
			ixEditor.search();
		} else if (e.getActionCommand() == "Activity") {
			ixPAD.showActivity();
		} else if (e.getActionCommand() == "Pin It") {
			ixPAD.pinIt();
		} else if (e.getActionCommand() == "Pin View") {
			ixPAD.showPinView();
		} else if (e.getActionCommand() == "Settings") {
			ixPAD.showSettings();
		} else if (e.getActionCommand() == "About") {
			ixPAD.showAbout();
		} else {
			System.out.println("iXPAD : No action found for \"" + e.getActionCommand() + "\"");
		}
	}
	
	
}
