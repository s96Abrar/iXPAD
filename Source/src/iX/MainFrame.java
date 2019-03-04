package iX;

import java.awt.Container;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

@SuppressWarnings("serial")
public class MainFrame extends JFrame{

	MainFrame() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(800, 600);
		this.setTitle("iXPAD");
		this.setLayout(null);
		
		Container frameContainer = this.getContentPane();
		frameContainer.setLayout(null);
				
		// App name label
		JLabel appName = new JLabel("iXPAD");
		appName.setBounds(5, 0, 100, 50);
		
		frameContainer.add(appName);
		
		// Control button panel
		JPanel buttonPanel = new JPanel();
		buttonPanel.setBounds(0, 45, 250, 640);
		
		// Control button panel layout
		BoxLayout buttonPanelBoxLayout = new BoxLayout(buttonPanel, BoxLayout.Y_AXIS);
		
		buttonPanel.setLayout(buttonPanelBoxLayout);

		frameContainer.add(buttonPanel);
		
		// All Control Buttons
		JButton btnOpen = new JButton("Open");
		JButton btnNewPage = new JButton("New Page");
		JButton btnSave = new JButton("Save");
		JButton btnSaveAs = new JButton("Save As");
		JButton btnCopy = new JButton("Copy");
		JButton btnPaste = new JButton("Paste");
		JButton btnCut = new JButton("Cut");
		JButton btnUndo = new JButton("Undo");
		JButton btnRedo = new JButton("Redo");
		JButton btnSearch = new JButton("Search");
		JButton btnBookmarkIt = new JButton("Bookmark It");
		
		buttonPanel.add(btnOpen);
		buttonPanel.add(btnNewPage);
		buttonPanel.add(btnSave);
		buttonPanel.add(btnSaveAs);
		buttonPanel.add(btnCopy);
		buttonPanel.add(btnPaste);
		buttonPanel.add(btnCut);
		buttonPanel.add(btnUndo);
		buttonPanel.add(btnRedo);
		buttonPanel.add(btnSearch);
		buttonPanel.add(btnBookmarkIt);
		
		// Text editor
		JTextArea textEditor = new JTextArea();

		// Scroll area for text editor panel
		JScrollPane textEditorScroll = new JScrollPane(textEditor);
		textEditorScroll.add(textEditor);
		textEditorScroll.setBounds(0, 0, 800, 600);
		
		// Text editor panel
		JPanel textEditorPanel = new JPanel();
		textEditorPanel.setBounds(280, 45, 800, 600);
		
		frameContainer.add(textEditorPanel);
		
		textEditorPanel.add(textEditorScroll);		
		textEditorPanel.add(textEditor);
		
	}
}
