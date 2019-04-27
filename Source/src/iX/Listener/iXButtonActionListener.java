package iX.Listener;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import iX.TextEditor.iXEditor;
import iX.Widgets.iXPAD;

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
		}
	}
	
	
}
