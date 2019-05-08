package iX.Widgets;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JDialog;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.DefaultHighlighter.DefaultHighlightPainter;

import iX.TextEditor.iXEditor;
import iX.Utilities.diff_match_patch;
import iX.Utilities.diff_match_patch.Diff;
import iX.Utilities.diff_match_patch.Operation;
import iX.Utilities.iXUtility;


public class DiffIt extends JDialog {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JTextPane editor;
	public DiffIt() {
		// Set frame properties
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.setMinimumSize(new Dimension(600, 600));
		this.setTitle("iXPAD - Duplicity");
		this.setLocationRelativeTo(null); // Place the frame to the center of the parent.
		// ====================
		        
		editor = new JTextPane();
		add(editor);
		
		iXUtility isp = new iXUtility();
		
		String text1 = isp.openFromFile("F:\\iXPAD\\Source\\src\\iX\\Widgets\\iXPinTree.java");
		String text2 = isp.openFromFile("F:\\iXPAD\\Source\\src\\iX\\Widgets\\iXTree.java");
		
		diff_match_patch dmp = new diff_match_patch();
		int i = 0;
		for (Diff d : dmp.diff_main(text1, text2)) {
			System.out.println(d.operation + d.text);
			if (d.operation == Operation.EQUAL) {
				DefaultHighlightPainter p = new DefaultHighlightPainter(Color.WHITE);
				try {
					editor.getHighlighter().addHighlight(i, i+d.text.length(), p);
				} catch (BadLocationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				editor.setText(d.text);
				i += d.text.length();
			} else if (d.operation == Operation.INSERT) {
				DefaultHighlightPainter p = new DefaultHighlightPainter(Color.GREEN);
				try {
					editor.getHighlighter().addHighlight(i, i+d.text.length(), p);
				} catch (BadLocationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				editor.setText(d.text);
				i += d.text.length();
			} else if (d.operation == Operation.DELETE) {
				DefaultHighlightPainter p = new DefaultHighlightPainter(Color.RED);
				try {
					editor.getHighlighter().addHighlight(i, i+d.text.length(), p);
				} catch (BadLocationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				editor.setText(d.text);
				i += d.text.length();
			}
		}
	}
	
	public static void main(String[] args) {
		DiffIt jk = new DiffIt();
		jk.setVisible(true);
	}
}
