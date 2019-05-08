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
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.AbstractButton;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.plaf.basic.BasicButtonUI;

import iX.Utilities.iXUtility;
import iX.Listener.iXButtonActionListener;
import iX.Listener.iXWindowListener;
import iX.TextEditor.iXEditor;
import iX.TextEditor.iXEditorPanel;

/**
 * @author abrar
 *
 */

// TODO Move all the string variables to a new class

public class iXPAD extends JFrame {

	/**
	 * Default serial version id.
	 * Used for removing warning. 
	 */
	private static final long serialVersionUID = 1L;
	
	// Declaring the UI components
	private Container mainContainer;
	private JLabel appName;
	private JPanel buttonPanel;
	private JButton btnOpen;
	private JButton btnNewPage;
	private JButton btnSave;
	private JButton btnSaveAs;
	private JButton btnCopy;
	private JButton btnPaste;
	private JButton btnCut;
	private JButton btnUndo;
	private JButton btnRedo;
	private JButton btnSearch;
	private JButton btnActivity;
	private JButton btnPinIt;
	private JButton btnPinView;
	private JButton btnSettings;
	private JButton btnAbout;
	private JButton[] buttons;
	
	private iXTabPane ixTabPane;	
	// ========================
	
	// Declaring Variables	
	iXUtility ixUtil;
	int iXTabCount;
	// ===================

	public iXPAD() {	
		// Initializing variables
		ixUtil = new iXUtility();
		// ======================
		
		// Initializing UI
		setupUI();
		// ==============
		
	}
	
	private void setupUI() {
		// Initialize variables
		iXTabCount = 0;
		
		// Set frame properties
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.setMinimumSize(new Dimension(800, 600));
		this.setTitle("iXPAD");		 
		this.setLocationRelativeTo(null); // Place the frame to the center of the monitor.
		this.addWindowListener(new iXWindowListener(this));
		// ====================
		
		// Initialize all layout
		BorderLayout mainLayout = new BorderLayout(2, 2);
		// =====================
		
		// Initialize main container
		mainContainer = getContentPane();
		mainContainer.setLayout(mainLayout);
		// =========================
		
		// Initialize application name
		appName = new JLabel("iXPAD");
		appName.setFont(new Font("Arial", Font.BOLD, 28));
		//====================
		
		// Initialize the controls
		
		// buttons
		btnOpen = new JButton("Open");
		// TODO Remove
//		btnOpen.setIcon(ixUtil.getImageResource("btnOpen.png"));
		
		btnNewPage = new JButton("New Page");		
		btnSave = new JButton("Save");
		btnSaveAs = new JButton("Save As");
		btnCopy = new JButton("Copy");
		btnPaste = new JButton("Paste");		
		btnCut = new JButton("Cut");		
		btnUndo = new JButton("Undo");		
		btnRedo = new JButton("Redo");		
		btnSearch = new JButton("Search");
		btnActivity = new JButton("Activity");
		btnPinIt = new JButton("Pin It");
		btnPinView = new JButton("Pin View");
		btnSettings = new JButton("Settings");
		btnAbout = new JButton("About");
		
		buttons = new JButton[] {
									btnOpen,
									btnNewPage,
									btnSave,
									btnSaveAs, 
									btnCopy,
									btnPaste,
									btnCut,
									btnUndo,
									btnRedo,
									btnSearch,
									btnActivity,
									btnPinIt,
									btnPinView,
									btnSettings,
									btnAbout
								};		

		for (JButton btn : buttons) {
			if (btn == null) {
				continue;
			}
			
//			btn.setUI(new BasicButtonUI());
//			btn.setContentAreaFilled(false);
//			btn.setRolloverEnabled(true);	
			
			String btnText = btn.getText().replaceAll("\\b \\b", "");
			btn.setIcon(ixUtil.getImageResource("btn" + btnText + ".png"));
			btn.setHorizontalAlignment(JButton.LEFT);			
			
			KeyStroke actionKey = getButtonKeyStroke(btn.getText());
			if (actionKey != null) {
				ixUtil.addKeyShortcut(btn, btnText, actionKey, new iXAbstractButtonAction());
			} else {
				System.out.println("iXPAD : Action key not found for " + btn.getText()); 
			}
		}
		
		btnUndo.setEnabled(false);
		btnRedo.setEnabled(false);
		// ==============================
		
		// Initialize panel's
		
		// buttonPanel
		buttonPanel = new JPanel();
//		buttonPanel.setBackground(Color.LIGHT_GRAY);
		
		GridBagLayout bg = new GridBagLayout();
		buttonPanel.setLayout(bg);
		
		GridBagConstraints bgc = new GridBagConstraints();
		
		bgc.gridx = 0;
		bgc.gridy = 0;
		bgc.weighty = 0.1;
		bgc.fill = GridBagConstraints.CENTER;		
		buttonPanel.add(appName, bgc);
		
		bgc.weighty = 0.01;
		bgc.fill = GridBagConstraints.HORIZONTAL;
		int i = 0;
		for (JButton btn : buttons) {
			bgc.gridy = ++i;
			buttonPanel.add(btn, bgc);
		}
		
		bgc.gridy = ++i;
		bgc.weighty = 0.9;
		buttonPanel.add(Box.createVerticalGlue(), bgc);
		// ===================
		// iXTabPane
		ixTabPane = new iXTabPane();
		createTab();		
		// ==================
		
		// Add all component in container
		mainContainer.add(buttonPanel, BorderLayout.LINE_START);
		mainContainer.add(ixTabPane, BorderLayout.CENTER);
		// ======================
		
		// Initialize button action listener
		iXButtonActionListener act = new iXButtonActionListener(this);
		for (JButton btn : buttons) {
			btn.addActionListener(act);
		}
		// ===================

		pack();
		getiXEditor().requestFocusInWindow();
		
	}

	public void createTab() {
		if (iXTabCount == 0) {
			ixTabPane.addiXEditorPanel(new iXEditorPanel(this), "Untitled" + iXTabCount++);
		} else {
			ixTabPane.addiXEditorPanel(new iXEditorPanel(this), "Untitled" + iXTabCount++);
		}
	}
	
	public void showPinView() { 
		iXPin ixPin = new iXPin(this);
		ixPin.setVisible(true);
	}
	

	public void showActivity() {
		iXActivity activity = new iXActivity(this);
		activity.setVisible(true);;
	}

	public void pinIt() {
		// TODO Auto-generated method stub
		
	}
	
	public void showSettings() {
		
	}
	
	public void showAbout() {
		
	}
	
	public iXEditor getiXEditor() {
//		return editorPanel.getiXTextEditor();
		if (ixTabPane.getEditorPanel() == null) {
			System.out.println("Null editor");
			return null;
		}
		
		System.out.println("Editor found");
		return ixTabPane.getEditorPanel().getiXTextEditor();
	}
	
//	private boolean getiXEditorManageCanUndo() {
//		return getiXEditor().canUndo();
//	}
//	
//	private boolean getiXEditorManageCanRedo() {
//		return getiXEditor().canRedo();
//	}
	
	private KeyStroke getButtonKeyStroke(String buttonText) {
		int key = 0;
		int modifier = ActionEvent.CTRL_MASK;
		
		// TODO Store all buttons to hash map as string, jbutton 
		if (buttonText == "Open") {
			key = KeyEvent.VK_O;
		} else if (buttonText == "New Page") {
			key = KeyEvent.VK_N;
		} else if (buttonText == "Save") {
			key = KeyEvent.VK_S;
		} else if (buttonText == "Save As") {
			key = KeyEvent.VK_S;
			modifier |= ActionEvent.SHIFT_MASK;
		} else if (buttonText == "Copy") {
			key = KeyEvent.VK_C;
		} else if (buttonText == "Paste") {
			key = KeyEvent.VK_V;
		} else if (buttonText == "Cut") {
			key = KeyEvent.VK_X;
		} else if (buttonText == "Undo") {
			key = KeyEvent.VK_Z;
		} else if (buttonText == "Redo") {
			key = KeyEvent.VK_Y;
		} else if (buttonText == "Search") {
			key = KeyEvent.VK_F;
		} else if (buttonText == "Activity") {
			key = KeyEvent.VK_A;
			modifier |= ActionEvent.SHIFT_MASK;			
		} else if (buttonText == "Pin It") {
			key = KeyEvent.VK_B;
		} else if (buttonText == "Pin View") {
			key = KeyEvent.VK_B;
			modifier |= ActionEvent.SHIFT_MASK;
		} else if (buttonText == "Settings") {
			key = KeyEvent.VK_S;
			modifier |= ActionEvent.ALT_MASK;
		} else if (buttonText == "About") {
			key = KeyEvent.VK_A;
			modifier |= ActionEvent.ALT_MASK;
		} else {
			return null;
		}
		
		return KeyStroke.getKeyStroke(key, modifier);
	}
	
	public void updateUndoRedo(boolean canUndo, boolean canRedo) {
		System.out.println("From "  + canUndo + " " + canRedo);
		btnUndo.setEnabled(canUndo);
		btnRedo.setEnabled(canRedo);
	}
	

	private class iXAbstractButtonAction extends AbstractAction {

		/**
		 * Default serial version id.
		 * Used for removing warning. 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			((AbstractButton) e.getSource()).doClick();
		}
		
	}


}
