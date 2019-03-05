package iX;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;

import javax.swing.AbstractAction;
import javax.swing.AbstractButton;
import javax.swing.BoxLayout;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.undo.UndoManager;

public class MainFrame extends JFrame implements ActionListener{
	private Container frameContainer;
	private JLabel appName;
	private JPanel buttonPanel, textEditorPanel;
	private JButton btnOpen, btnNewPage, btnSave, btnSaveAs, btnCopy, btnPaste, btnCut, btnUndo, btnRedo, btnSearch, btnBookmarkIt;
	private JTextArea textEditor;
	private UndoManager undoManager = new UndoManager();
	private Font f;
	
	private String workFilePath;

	MainFrame() {
		initComponents();
	}

	public void initComponents() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setBounds(150,50,1060,640);
		this.setTitle("iXPAD");
//		this.setLayout(null);
		
		// center the frame on the monitor
        this.setLocationRelativeTo(null);
		
		frameContainer = this.getContentPane();
//		frameContainer.setLayout(null);
		frameContainer.setLayout(new BorderLayout(2, 2));
		f = new Font("Arial",Font.BOLD,28);
				
		// App name label
		appName = new JLabel(" iXPAD");
//		appName.setBounds(5,0,100,50);
		appName.setFont(f);
		
		// Control button panel
		buttonPanel = new JPanel();
		
//		buttonPanel.setBounds(0,45,250,640);
		buttonPanel.setBackground(Color.LIGHT_GRAY);
		
		// Control button panel layout
		//BoxLayout buttonPanelBoxLayout = new BoxLayout(buttonPanel, BoxLayout.Y_AXIS);

		buttonPanel.setLayout(new GridLayout(12, 1, 0, 5));;

		frameContainer.add(buttonPanel, BorderLayout.LINE_START);
		
		buttonPanel.add(appName);
		// All Control Buttons
		btnOpen = new JButton("Open");
		addKeyShortcut(btnOpen, "Open", KeyStroke.getKeyStroke(KeyEvent.VK_O,ActionEvent.CTRL_MASK));
		
		btnNewPage = new JButton("New Page");
		addKeyShortcut(btnNewPage, "New Page", KeyStroke.getKeyStroke(KeyEvent.VK_N,ActionEvent.CTRL_MASK));
				
		btnSave = new JButton("Save");
		addKeyShortcut(btnSave, "Save", KeyStroke.getKeyStroke(KeyEvent.VK_S,ActionEvent.CTRL_MASK));
		
		btnSaveAs = new JButton("Save As");
		addKeyShortcut(btnSaveAs, "Save As", KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK | ActionEvent.SHIFT_MASK));
		
		btnCopy = new JButton("Copy");
		addKeyShortcut(btnCopy, "Copy", KeyStroke.getKeyStroke(KeyEvent.VK_C,ActionEvent.CTRL_MASK));
		
		btnPaste = new JButton("Paste");
		addKeyShortcut(btnPaste, "Paste", KeyStroke.getKeyStroke(KeyEvent.VK_V,ActionEvent.CTRL_MASK));
		
		btnCut = new JButton("Cut");
		addKeyShortcut(btnCut, "Cut", KeyStroke.getKeyStroke(KeyEvent.VK_X,ActionEvent.CTRL_MASK));
		
		btnUndo = new JButton("Undo");
		addKeyShortcut(btnUndo, "Undo", KeyStroke.getKeyStroke(KeyEvent.VK_Z,ActionEvent.CTRL_MASK));
		
		btnRedo = new JButton("Redo");
		addKeyShortcut(btnRedo, "Redo", KeyStroke.getKeyStroke(KeyEvent.VK_Y,ActionEvent.CTRL_MASK));
		
		btnSearch = new JButton("Search");
		addKeyShortcut(btnSearch, "Search", KeyStroke.getKeyStroke(KeyEvent.VK_F,ActionEvent.CTRL_MASK));
		
		btnBookmarkIt = new JButton("Bookmark It");
		addKeyShortcut(btnBookmarkIt, "Bookmark It", KeyStroke.getKeyStroke(KeyEvent.VK_B,ActionEvent.CTRL_MASK));
		
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
		
		textEditorPanel = new JPanel(new BorderLayout());
//		textEditorPanel.setLayout(null);
//		textEditorPanel.setBounds(260,0,1060,640);
		textEditorPanel.setBackground(Color.lightGray);
	    
	    textEditor = new JTextArea();	    
	    textEditor.setLineWrap(true);
	    textEditor.setWrapStyleWord(true); 
	    textEditor.getDocument().addUndoableEditListener(new UndoableEditListener(){

            @Override
            public void undoableEditHappened(UndoableEditEvent e) {
            undoManager.addEdit(e.getEdit());
            updateUndoRedo();
            }    
        });
        
	
	    JScrollPane textEditorScroll = new JScrollPane(textEditor);
		//textEditorScroll.setBounds(0,0,1060,640);
	    textEditorScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
	    
	    
		textEditorPanel.add(textEditorScroll);
		
		frameContainer.add(textEditorPanel, BorderLayout.CENTER);
		
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
		
		undoManager.setLimit(1000);
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
		
		if (e.getSource() == btnUndo) {
			undoFile();
		}
		
		if (e.getSource() == btnRedo) {
			redoFile();
		}
		
		if (e.getSource() == btnSearch) {
			new Find(textEditor);
		}
	}
	
	private void addKeyShortcut(JButton button, String actionText, KeyStroke ks) {
		InputMap inputMap = button.getInputMap(JButton.WHEN_IN_FOCUSED_WINDOW);
		inputMap.put(ks, actionText);
		button.getActionMap().put(actionText, new AbstractAction()
	    {
	        @Override
	        public void actionPerformed( ActionEvent e )
	        {
	            ((AbstractButton) e.getSource()).doClick();
	        }
	    } );
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
	
	public void undoFile(){
        try{
            undoManager.undo();
        }catch(Exception ex){
            ex.printStackTrace();
        }
        updateUndoRedo();
    }
    
    public void redoFile(){
        try{
            undoManager.redo();
        }catch(Exception ex){
        	ex.printStackTrace();
        }
        updateUndoRedo();
    }
	
	public void updateUndoRedo(){
        btnUndo.setEnabled(undoManager.canUndo());
        btnRedo.setEnabled(undoManager.canRedo());
    }
}
