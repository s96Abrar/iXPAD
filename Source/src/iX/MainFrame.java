package iX;

import java.awt.Color;
import java.awt.Container;
import java.awt.FileDialog;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
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
	private Container frameContainer;
	private JLabel appName;
	private JPanel buttonPanel, textEditorPanel;
	private JButton btnOpen, btnNewPage, btnSave, btnSaveAs, btnCopy, btnPaste, btnCut, btnUndo, btnRedo, btnSearch, btnBookmarkIt;
	private JTextArea textEditor;
	private Font f;
	
	private String workFilePath;

	MainFrame() {
		initComponents();
	}

	public void initComponents() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setBounds(150,50,1060,640);
		this.setTitle("iXPAD");
		this.setLayout(null);
		
		frameContainer = this.getContentPane();
		frameContainer.setLayout(null);
		f = new Font("Arial",Font.BOLD,18);
				
		// App name label
		appName = new JLabel("iXPAD");
		appName.setBounds(5,0,100,50);
		
		frameContainer.add(appName);
		
		// Control button panel
		buttonPanel = new JPanel();
		buttonPanel.setBounds(0,45,250,640);
		buttonPanel.setBackground(Color.LIGHT_GRAY);
		
		// Control button panel layout
		BoxLayout buttonPanelBoxLayout = new BoxLayout(buttonPanel, BoxLayout.Y_AXIS);
		
		buttonPanel.setLayout(buttonPanelBoxLayout);

		frameContainer.add(buttonPanel);
		
		// All Control Buttons
		btnOpen = new JButton("Open");
		btnNewPage = new JButton("New Page");
		btnSave = new JButton("Save");
		btnSaveAs = new JButton("Save As");
		btnCopy = new JButton("Copy");
		btnPaste = new JButton("Paste");
		btnCut = new JButton("Cut");
		btnUndo = new JButton("Undo");
		btnRedo = new JButton("Redo");
		btnSearch = new JButton("Search");
		btnBookmarkIt = new JButton("Bookmark It");
		
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
		
		textEditorPanel = new JPanel();
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
				String filePath = file.toString();
				
				try {
					BufferedReader br = new BufferedReader(new FileReader(filePath));
					String currentLine = "";
					while ((currentLine = br.readLine()) != null) {
						textEditor.append(currentLine + "\n");
					}
					
					br.close();
					
					workFilePath = filePath;
					setTitle(workFilePath + " - iXPAD");
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}
		
		if (e.getSource() == btnSave) {
			String fileName = workFilePath;
			File file = new File(fileName);
			if (file.exists()) {
				if (saveFile(fileName)) {
					textEditor.requestFocus();
				}
			} else {
				saveFileDialog();
			}
		}
		
		if (e.getSource() == btnSaveAs) {
			if (saveFileDialog()) {
				textEditor.requestFocus();
			}
		}
		
		if (e.getSource() == btnCopy) {
			textEditor.copy();
		}
		
		if (e.getSource() == btnCut) {
			textEditor.cut();
		}
		
		if (e.getSource() == btnPaste) {
			textEditor.paste();
		}
	}
	
	private boolean saveFileDialog() {
		FileDialog fd = new FileDialog(new JFrame(), "Save File", FileDialog.SAVE);
        fd.show();
        if (fd.getFile() != null) {
            String fileName = fd.getDirectory() + fd.getFile();

            if (saveFile(fileName)) {
                workFilePath = fileName;
                setTitle(fileName.substring(fileName.lastIndexOf("\\") + 1) + "iXPAD");
                
                return true;
            }
        }
        
        return false;
	}
	
	private boolean saveFile(String filePath) {
		try {
			DataOutputStream d = new DataOutputStream(new FileOutputStream(filePath));
            String line = textEditor.getText();
            d.writeBytes(line);
            d.close();
		} catch (Exception ex) {
            System.out.println("File not found");
			return false;
		}
		
		return true;
	}
}
