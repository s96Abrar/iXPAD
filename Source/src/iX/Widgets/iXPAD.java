package iX.Widgets;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

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
	private JButton btnPinIt;	
	private JButton[] buttons;
	
	private iXEditorPanel editorPanel;
	// ========================
	
	// Declaring Variables	
	iXUtility ixUtil;
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
		// Set frame properties
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setSize(800, 600);
		setTitle("iXPAD");		 
		setLocationRelativeTo(null); // Place the frame to the center of the monitor.
		addWindowListener(new iXWindowListener(this));
		// ====================
		
		// Initialize all layout
		GridLayout buttonPanelLayout = new GridLayout(12, 1, 0, 5);
		BorderLayout mainLayout = new BorderLayout(2, 2);
		// =====================
		
		// Initialize main container
		mainContainer = getContentPane();
		mainContainer.setLayout(mainLayout);
		// =========================
		
		// Initialize application name
		appName = new JLabel("iXPAD");
		appName.setAlignmentY(RIGHT_ALIGNMENT);
		appName.setFont(new Font("Arial", Font.BOLD, 28));
		//====================
		
		// Initialize the controls
		
		// buttons
		btnOpen = new JButton("Open");
		// TODO Remove
//		btnOpen.setIcon(ixUtil.getImageResource("btnOpen.png"));
		
		btnNewPage = new JButton("New Page");
		// TODO Remove
//		btnNewPage.setIcon(ixUtil.getImageResource("btnNewPage.png"));
		
		btnSave = new JButton("Save");
		// TODO Remove
//		btnSave.setIcon(ixUtil.getImageResource("btnSave.png"));
		
		btnSaveAs = new JButton("Save As");
		// TODO Remove
//		btnSaveAs.setIcon(ixUtil.getImageResource("btnSaveAs.png"));
		
		btnCopy = new JButton("Copy");
		
		btnPaste = new JButton("Paste");
		
		btnCut = new JButton("Cut");
		
		btnUndo = new JButton("Undo");
		
		btnRedo = new JButton("Redo");
		
		btnSearch = new JButton("Search");
		
		btnPinIt = new JButton("Pin It");
				
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
									btnPinIt
								};		
		
		// FIXME Difference between button is too big
		for (JButton btn : buttons) {
			if (btn == null) {
				continue;
			}
			
			String btnText = btn.getText().replaceAll("\\b \\b", "");
			btn.setIcon(ixUtil.getImageResource("btn" + btnText + ".png"));
			btn.setHorizontalAlignment(JButton.LEFT);
			
			KeyStroke actionKey = getButtonKeyStroke(btn.getText());
			if (actionKey != null) {
				ixUtil.addKeyShortcut(btn, btnText, actionKey, new iXAbstractButtonAction());
			} else {
				System.out.println("iXPAD : Action key not found for " + btn.getText()); 
			}
			
			// Removes border from button
//			btn.setBorder(BorderFactory.createEmptyBorder(2,2,2,2));
//			btn.setHorizontalAlignment(JButton.LEADING); 
//			btn.setBorderPainted(false);
//			btn.setContentAreaFilled(false);

			// TODO Remove not working
//			btn.setMaximumSize(new Dimension(20, 22));
		}
		
		// ==============================
		
		// Initialize panel's
		
		// buttonPanel
		buttonPanel = new JPanel();
		buttonPanel.setBackground(Color.LIGHT_GRAY);
//		buttonPanel.setLayout(new BorderLayout());
		GridBagLayout bg = new GridBagLayout();
		buttonPanel.setLayout(bg);
		
		// Add control buttons to button panel
		// TODO Remove
//		buttonPanel.add(btnOpen);
//		buttonPanel.add(btnNewPage);
//		buttonPanel.add(btnSave);
//		buttonPanel.add(btnSaveAs);
		
		// TODO Make the layout with GridBagLayout
		GridBagConstraints bgc = new GridBagConstraints();
		bgc.gridx = 0;
		bgc.gridy = 0;
		bgc.weighty = 4;
		bgc.fill = GridBagConstraints.CENTER;
		
		buttonPanel.add(appName, bgc);
		bgc.weighty = 1;
		bgc.fill = GridBagConstraints.HORIZONTAL;
		int i = 0;
		for (JButton btn : buttons) {
			bgc.gridy = ++i;
			buttonPanel.add(btn, bgc);
		}
		
		// editorPanel
		editorPanel = new iXEditorPanel(this);
		
		// ==================
		
		// Add all component in container
		mainContainer.add(buttonPanel, BorderLayout.LINE_START);
		mainContainer.add(editorPanel, BorderLayout.CENTER);
		// ======================
		
		// Initialize listener
		iXButtonActionListener act = new iXButtonActionListener(this);
		for (JButton btn : buttons) {
			btn.addActionListener(act);
		}
		
		System.out.println(getComponentCount());
	}
	
	public iXEditor getiXEditor() {
		return editorPanel.getiXTextEditor();
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
		} else if (buttonText == "Bookmark It") {
			key = KeyEvent.VK_B;
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
