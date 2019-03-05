package iX;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class MainFrame extends JFrame implements ActionListener{
	private JButton btnOpen;
	JButton btnSave;
	JTextArea textEditor;
	private Font f;

	MainFrame() {
		initComponents();
	}

	public void initComponents() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setBounds(150,50,1060,640);
		this.setTitle("iXPAD");
		this.setLayout(null);
		
		Container frameContainer = this.getContentPane();
		frameContainer.setLayout(null);
		f = new Font("Arial",Font.BOLD,18);
				
		// App name label
		JLabel appName = new JLabel("iXPAD");
		appName.setBounds(5,0,100,50);
		
		frameContainer.add(appName);
		
		// Control button panel
		JPanel buttonPanel = new JPanel();
		buttonPanel.setBounds(0,45,250,640);
		buttonPanel.setBackground(Color.LIGHT_GRAY);
		
		// Control button panel layout
		BoxLayout buttonPanelBoxLayout = new BoxLayout(buttonPanel, BoxLayout.Y_AXIS);
		
		buttonPanel.setLayout(buttonPanelBoxLayout);

		frameContainer.add(buttonPanel);
		
		// All Control Buttons
		btnOpen = new JButton("Open");
		JButton btnNewPage = new JButton("New Page");
		btnSave = new JButton("Save");
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
		
		JPanel textEditorPanel = new JPanel();
		textEditorPanel.setLayout(null);
		textEditorPanel.setBounds(260,0,1060,640);
		textEditorPanel.setBackground(Color.lightGray);
		frameContainer.add(textEditorPanel);
	    
	    textEditor = new JTextArea();	    
	    textEditor.setLineWrap(true);
	    textEditor.setWrapStyleWord(true); 
	
	    JScrollPane textEditorScroll = new JScrollPane(textEditor);
		textEditorScroll.setBounds(0,0,1060,640);
		textEditorPanel.add(textEditorScroll);
		
		btnOpen.addActionListener(this);
		btnNewPage.addActionListener(this);
		btnSave.addActionListener(this);
		btnSaveAs.addActionListener(this);
		btnCopy.addActionListener(this);
		btnPaste.addActionListener(this);
		btnCut.addActionListener(this);
		btnUndo.addActionListener(this);
		btnRedo.addActionListener(this);
		btnSearch.addActionListener(this);
		btnBookmarkIt.addActionListener(this);
		
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnOpen) {
			JFileChooser fileChooser = new JFileChooser();
			int exec = fileChooser.showOpenDialog(this);
			
			if (exec == JFileChooser.APPROVE_OPTION) {
				File file = fileChooser.getSelectedFile();
				String filePath = file.getPath();
				
				try {
					BufferedReader br = new BufferedReader(new FileReader(filePath));
					String str1 = "", str2 = "";
					while ((str1 = br.readLine()) != null) {
						str2 += str1 + "\n";
					}
					
					textEditor.setText(str2);
					br.close();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}
		
		if (e.getSource() == btnSave) {
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.showSaveDialog(this);
			File file = fileChooser.getSelectedFile();
			
			try {
				FileWriter fw = new FileWriter(file);
				String text = textEditor.getText();
				fw.write(text);
				fw.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}		
	}
}
