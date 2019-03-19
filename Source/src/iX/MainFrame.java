package iX;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FileDialog;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import javax.swing.AbstractAction;
import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.text.Element;
import javax.swing.undo.UndoManager;

public class MainFrame extends JFrame implements ActionListener {
	private Container frameContainer;
	private JLabel appName;
	private JPanel buttonPanel, textEditorPanel;
	private JButton btnOpen, btnNewPage, btnSave, btnSaveAs, btnCopy, btnPaste, btnCut, btnUndo, btnRedo, btnSearch, btnBookmarkIt;
	private JTextArea textEditor, lines;
	private UndoManager undoManager = new UndoManager();
	private Font f;
	
	int caretpos;
	
	private String workFilePath = "";

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
		btnOpen.setIcon(new ImageIcon("images/btnOpen.png"));		
		addKeyShortcut(btnOpen, "Open", KeyStroke.getKeyStroke(KeyEvent.VK_O,ActionEvent.CTRL_MASK));
		
		btnNewPage = new JButton("New Page");
		btnNewPage.setIcon(new ImageIcon("images/btnNewPage.png"));	
		addKeyShortcut(btnNewPage, "New Page", KeyStroke.getKeyStroke(KeyEvent.VK_N,ActionEvent.CTRL_MASK));
				
		btnSave = new JButton("Save");
		btnSave.setIcon(new ImageIcon("images/btnSave.png"));
		addKeyShortcut(btnSave, "Save", KeyStroke.getKeyStroke(KeyEvent.VK_S,ActionEvent.CTRL_MASK));
		
		btnSaveAs = new JButton("Save As");
		btnSaveAs.setIcon(new ImageIcon("images/btnSaveAs.png"));
		addKeyShortcut(btnSaveAs, "Save As", KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK | ActionEvent.SHIFT_MASK));
		
		btnCopy = new JButton("Copy");
		btnCopy.setIcon(new ImageIcon("images/btnCopy.png"));
		addKeyShortcut(btnCopy, "Copy", KeyStroke.getKeyStroke(KeyEvent.VK_C,ActionEvent.CTRL_MASK));
		
		btnPaste = new JButton("Paste");
		btnPaste.setIcon(new ImageIcon("images/btnPaste.png"));
		addKeyShortcut(btnPaste, "Paste", KeyStroke.getKeyStroke(KeyEvent.VK_V,ActionEvent.CTRL_MASK));
		
		btnCut = new JButton("Cut");
		btnCut.setIcon(new ImageIcon("images/btnCut.png"));
		addKeyShortcut(btnCut, "Cut", KeyStroke.getKeyStroke(KeyEvent.VK_X,ActionEvent.CTRL_MASK));
		
		btnUndo = new JButton("Undo");
		btnUndo.setIcon(new ImageIcon("images/btnUndo.png"));
		btnUndo.setEnabled(false);
		addKeyShortcut(btnUndo, "Undo", KeyStroke.getKeyStroke(KeyEvent.VK_Z,ActionEvent.CTRL_MASK));
		
		btnRedo = new JButton("Redo");
		btnRedo.setIcon(new ImageIcon("images/btnRedo.png"));
		btnRedo.setEnabled(false);
		addKeyShortcut(btnRedo, "Redo", KeyStroke.getKeyStroke(KeyEvent.VK_Y,ActionEvent.CTRL_MASK));
		
		btnSearch = new JButton("Search");
		btnSearch.setIcon(new ImageIcon("images/btnSearch.png"));
		addKeyShortcut(btnSearch, "Search", KeyStroke.getKeyStroke(KeyEvent.VK_F,ActionEvent.CTRL_MASK));
		
		btnBookmarkIt = new JButton("Bookmark It");
		btnBookmarkIt.setIcon(new ImageIcon("images/btnBookmarkIt.png"));
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
	    
	    textEditor.addCaretListener(new CaretListener(){
            @Override
            public void caretUpdate(CaretEvent e){
                JTextArea t = (JTextArea)e.getSource();
                int line=1, col=1;
                
                try {
                    caretpos = t.getCaretPosition();
                    line = t.getLineOfOffset(caretpos);
                    col = caretpos - t.getLineStartOffset(line);
                    line++;
                    col++;
                } catch (Exception ex) { }
                
            }
            
        });
	    
	    lines = new JTextArea("1       "); 
        lines.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 2, Color.DARK_GRAY));
        lines.setEditable(false);
        lines.setBackground(Color.LIGHT_GRAY);
        textEditor.getDocument().addDocumentListener(new DocumentListener(){
            
            public String getText(){
                int pos=textEditor.getDocument().getLength();
                Element root=textEditor.getDocument().getDefaultRootElement();
                String t = "1       " + System.getProperty("line.separator");
                
                for(int i=2;i<root.getElementIndex(pos)+2;i++)
                    t+= i + System.getProperty("line.separator");
                return t;
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                lines.setText(getText());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                lines.setText(getText());
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                lines.setText(getText());
            }
        });
        
	    textEditor.getDocument().addUndoableEditListener(new UndoableEditListener(){
            @Override
            public void undoableEditHappened(UndoableEditEvent e) {
            undoManager.addEdit(e.getEdit());
            updateUndoRedo();
            }    
        });
	    
	    // To upper case shortcut
	    String key = "UpperCase";
	    textEditor.getInputMap(JTextArea.WHEN_IN_FOCUSED_WINDOW).put(
	            KeyStroke.getKeyStroke(KeyEvent.VK_U, ActionEvent.CTRL_MASK), key);
	     
	    textEditor.getActionMap().put(key, new AbstractAction(key) {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				textEditor.replaceSelection(toStrUpperCase(textEditor.getSelectedText()));
			}
		});
	    
	    // To lower case shortcut
	    key = "LowerCase";
	    textEditor.getInputMap(JTextArea.WHEN_IN_FOCUSED_WINDOW).put(
	            KeyStroke.getKeyStroke(KeyEvent.VK_L, ActionEvent.CTRL_MASK), key);
	     
	    textEditor.getActionMap().put(key, new AbstractAction(key) {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				textEditor.replaceSelection(toStrLowerCase(textEditor.getSelectedText()));
			}
		});

	    // To title case shortcut
	    key = "TitleCase";
	    textEditor.getInputMap(JTextArea.WHEN_IN_FOCUSED_WINDOW).put(
	            KeyStroke.getKeyStroke(KeyEvent.VK_T, ActionEvent.CTRL_MASK), key);
	     
	    textEditor.getActionMap().put(key, new AbstractAction(key) {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				textEditor.replaceSelection(toStrTitleCase(textEditor.getSelectedText()));
			}
		});
	    
	    JScrollPane textEditorScroll = new JScrollPane(textEditor);
	    textEditorScroll.setRowHeaderView(lines);
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
		
		MainFrame p = this;
        WindowListener exitListener = new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                int confirm = JOptionPane.showOptionDialog(p,
                        "Are You Sure to Close this Application?",
                        "Exit Confirmation", JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE, null, null, null);
                if (confirm == JOptionPane.YES_OPTION) {
                    System.out.println(System.getProperty("user.home"));
                    if (!workFilePath.isEmpty()) {
                    	RecentActivity.saveActivity(workFilePath);
                    }
                    System.exit(1);
                }
            }
        };
        this.addWindowListener(exitListener);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnOpen) {
			JFileChooser fileChooser = new JFileChooser();
			int exec = fileChooser.showOpenDialog(this);
			
			if (exec == JFileChooser.APPROVE_OPTION) {
				File file = fileChooser.getSelectedFile();
				String filePath = file.toString();
				
				try {
					textEditor.setText("");
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
			// TODO: check the work file path before saving it and move it to safe
			String fileName = workFilePath;
			File file = new File(fileName);
			if (file.exists()) {
				if (Utilities.saveFile(textEditor.getText(), fileName)) {
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
			System.out.println("Test ");
			test();
			//textEditor.paste();
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
	
	private void test() {
		String str = textEditor.getSelectedText();
		textEditor.replaceSelection(toStrTitleCase(str));
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

            if (Utilities.saveFile(textEditor.getText(), fileName)) {
                workFilePath = fileName;
                setTitle(fileName.substring(fileName.lastIndexOf("\\") + 1) + "iXPAD");
                
                return true;
            }
        }
        
        return false;
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
	
	// Move to Editor class
	public String toStrUpperCase(String str) {
		if (str.isEmpty()) {
			return null;
		}
		
		return str.toUpperCase();
	}
	
	public String toStrLowerCase(String str) {
		if (str.isEmpty()) {
			return null;
		}
		
		return str.toLowerCase();
	}
	
	public String toStrTitleCase(String str) {
		if (str.isEmpty()) {
            return "";
        }
 
        if (str.length() == 1) {
            return str.toUpperCase();
        }
 
        StringBuffer replaceStr = new StringBuffer(str.length());
 
        for (String line : str.split("\n")) {
	        String[] tStr = line.split(" ");
	        for (String s : tStr) {
	        	if (s.length() > 1) {
	        		replaceStr.append(s.substring(0, 1).toUpperCase())
	        				  .append(s.substring(1).toLowerCase());
	        	} else {
	        		replaceStr.append(s.toUpperCase());
	        	}
	        	
	        	replaceStr.append(" ");
	        }
	        replaceStr.append("\n");
        }
        
        return new String(replaceStr).trim();
    }
}
