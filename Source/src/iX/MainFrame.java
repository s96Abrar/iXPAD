package projectdemo;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;


public class MainFrame extends JFrame{
	private JPanel buttonpanel, textareapanel;
	private Container c;
	private JTextArea ta;
	private JScrollPane scroll;
	private JLabel appName;
	private Font f;

	MainFrame() {
		initcomponents();
	}

	public void initcomponents() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setBounds(150,50,1060,640);
		this.setTitle("iXPAD");
		this.setLayout(null);
		
		c = this.getContentPane();
		c.setLayout(null);
		f = new Font("Arial",Font.BOLD,18);
				
		// App name label
		 appName = new JLabel("iXPAD");
		appName.setBounds(5,0,100,50);
		
		c.add(appName);
		
		// Control button panel
		 buttonpanel = new JPanel();
		buttonpanel.setBounds(0,45,250,640);
		buttonpanel.setBackground(Color.LIGHT_GRAY);
		// Control button panel layout
		BoxLayout buttonPanelBoxLayout = new BoxLayout(buttonpanel, BoxLayout.Y_AXIS);
		
		buttonpanel.setLayout(buttonPanelBoxLayout);

		c.add(buttonpanel);
		
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
		
		buttonpanel.add(btnOpen);
		buttonpanel.add(btnNewPage);
		buttonpanel.add(btnSave);
		buttonpanel.add(btnSaveAs);
		buttonpanel.add(btnCopy);
		buttonpanel.add(btnPaste);
		buttonpanel.add(btnCut);
		buttonpanel.add(btnUndo);
		buttonpanel.add(btnRedo);
		buttonpanel.add(btnSearch);
		buttonpanel.add(btnBookmarkIt);
		
		textareapanel = new JPanel();
		textareapanel.setLayout(null);
		textareapanel.setBounds(260,0,1060,640);
		textareapanel.setBackground(Color.lightGray);
	    c.add(textareapanel);
	
	ta = new JTextArea();
	ta.setBackground(Color.LIGHT_GRAY);
	ta.setLineWrap(true);
	ta.setWrapStyleWord(true);     
	ta.setFont(f ); 
	
	scroll = new JScrollPane(ta);
	scroll.setBounds(0,0,1060,640);
	textareapanel.add(scroll);  

		
	}
		
		
	
	
	public static void main(String[] args) {
		MainFrame m = new MainFrame();
		m.setVisible(true);
	}
}
