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

package iX.TextEditor;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class iXEditorPanel extends JPanel {

	/**
	 * Default serial version id.
	 * Used for removing warning. 
	 */
	private static final long serialVersionUID = 1L;

	// Declaring UI components
	private Component editorPanelParent;
	private JScrollPane ixEditorScrollPane;
	private iXEditor ixEditor;
	private iXEditorLineNumberArea ixEditorLineNumberArea;
	// =======================
	
	public iXEditorPanel(Component parent) {
		editorPanelParent = parent;
		
		// Initializing UI
		setupUI();
	}

	private void setupUI() {
		BorderLayout defaultLayout = new BorderLayout();
		
		ixEditor = new iXEditor(editorPanelParent);
		ixEditorLineNumberArea = new iXEditorLineNumberArea(ixEditor.getDocument(), ixEditor.getForeground(), ixEditor.getBackground());
		
		ixEditorScrollPane = new JScrollPane();
		ixEditorScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		ixEditorScrollPane.setViewportView(ixEditor);
		ixEditorScrollPane.setRowHeaderView(ixEditorLineNumberArea);
		
		setLayout(defaultLayout);
		add(ixEditorScrollPane);		
	}
	
	// Get the text editor
	public iXEditor getiXTextEditor() {
		return ixEditor;
	}
}
