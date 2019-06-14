package iX.Widgets;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;
import javax.swing.text.Document;
import javax.swing.text.Highlighter;
import javax.swing.text.JTextComponent;

import iX.Duplicity.DiffMatch;
import iX.Duplicity.DiffMatch.Diff;
import iX.Duplicity.DiffMatch.Operation;
import iX.Utilities.iXUtility;

public class DiffIt extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JTextPane editor;
	private JTextArea lbl;

	public DiffIt() {
		// Set frame properties
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.setMinimumSize(new Dimension(600, 600));
		this.setTitle("iXPAD - Duplicity");
		this.setLocationRelativeTo(null); // Place the frame to the center of the parent.
		// ====================

		editor = new JTextPane();
		JScrollPane scroll = new JScrollPane();
		scroll.setViewportView(editor);
		lbl = new JTextArea();
		lbl.setEditable(false);
		add(lbl, BorderLayout.PAGE_END);
		add(scroll, BorderLayout.CENTER);

		iXUtility isp = new iXUtility();

//		String text1 = isp.openFromFile("F:\\iXPAD\\Source\\src\\iX\\Widgets\\iXPinTree.java");
//		String text2 = isp.openFromFile("F:\\iXPAD\\Source\\src\\iX\\Widgets\\iXTree.java");
		String text1 = isp
				.openFromFile("/home/abrar/Desktop/Varsity_project/Main/iXPAD/Source/src/iX/Widgets/iXPinTree.java");
		String text2 = isp
				.openFromFile("/home/abrar/Desktop/Varsity_project/Main/iXPAD/Source/src/iX/Widgets/iXTree.java");

		DiffMatch dmp = new DiffMatch();
		int i = 0;
		int j = 0;
		int k = 0;
		
		Color color = null;
//		StyledDocument styledDoc = editor.getStyledDocument();
		StyleContext sc = StyleContext.getDefaultStyleContext();

		for (Diff d : dmp.diff_main(text1, text2)) {
//			System.out.println(d.operation + d.text);
			if (d.operation == Operation.EQUAL) {

//				System.out.println("Equal " + i + " " + (i+d.text.length()));
//				DefaultHighlightPainter p = new DefaultHighlightPainter(Color.WHITE);
//				try {
//					editor.getHighlighter().addHighlight(i, i+d.text.length()-1, p);
//				} catch (BadLocationException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}

//				highlight(editor, d.text, new MyHighlightPainter(Color.RED));
//				doHighlight(editor, d.operation, i, d.text.length());
				i += d.text.length();
				
				color = editor.getBackground();
			} else if (d.operation == Operation.INSERT) {

//				System.out.println("Insert " + i + " " + (i+d.text.length()));
//				editor.setText(editor.getText() + d.text);

//				DefaultHighlightPainter p = new DefaultHighlightPainter(Color.GREEN);
//				try {
//					editor.getHighlighter().addHighlight(i, i+d.text.length()-1, p);
//				} catch (BadLocationException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}

//				highlight(editor, d.text, new MyHighlightPainter(Color.GREEN));
//				doHighlight(editor, d.operation, i, d.text.length());
				j += d.text.length();
				
				color = Color.GREEN;
			} else if (d.operation == Operation.DELETE) {
//
//
//				System.out.println("Delete " + i + " " + (i+d.text.length()));
//				editor.setText(editor.getText() + d.text);

//				DefaultHighlightPainter p = new DefaultHighlightPainter(Color.RED);
//				try {
//					editor.getHighlighter().addHighlight(i, i+d.text.length()-1, p);
//				} catch (BadLocationException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}

//				highlight(editor, d.text, new MyHighlightPainter(Color.RED));
//				doHighlight(editor, d.operation, i, d.text.length());
				k += d.text.length();
				
				color = Color.RED;
			} else {
//				System.out.println(d.operation + d.text);
			}

			AttributeSet attr = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Background, color);
			try {
				editor.getDocument().insertString(editor.getText().length(), d.text, attr);
			} catch (BadLocationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

//			editor.setText(editor.getText() + "<" + d.operation + ">" + d.text + "</" + d.operation + ">");
		}

		lbl.setText("Count of document modifications(old, new) :\nCharacter deleted : " + k + "\nCharacter inserted : "
				+ j + "\nCharacter unchanged : " + i);
	}

	// A private subclass of the default highlight painter
	class MyHighlightPainter extends DefaultHighlighter.DefaultHighlightPainter {
		public MyHighlightPainter(Color color) {
			super(color);
		}
	}

	public void highlight(JTextComponent textComp, String pattern, MyHighlightPainter myHighlightPainter) {
		// First remove all old highlights
//	    removeHighlights(textComp);

		try {
			Highlighter hilite = textComp.getHighlighter();
			Document doc = textComp.getDocument();
			String text = doc.getText(0, doc.getLength());
			int pos = 0;

			// Search for pattern
			// see I have updated now its not case sensitive
			while ((pos = text.toUpperCase().indexOf(pattern.toUpperCase(), pos)) >= 0) {
				// Create highlighter using private painter and apply around pattern
				hilite.addHighlight(pos, pos + pattern.length(), myHighlightPainter);
				pos += pattern.length();
			}
		} catch (BadLocationException e) {
		}
	}

	public void doHighlight(JTextPane t, Operation op, int offset, int length) {
		StyledDocument styledDoc = t.getStyledDocument();
		StyleContext sc = StyleContext.getDefaultStyleContext();

//		SimpleAttributeSet sas = new SimpleAttributeSet();

		AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, Color.GREEN);
		AttributeSet aset2 = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, Color.RED);
		AttributeSet aset3 = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, Color.BLACK);
		AttributeSet asetF = null;
		AttributeSet asetF2 = null;

		switch (op) {
		case INSERT:
			asetF = sc.addAttribute(aset, StyleConstants.Bold, false);
			break;
		case DELETE:
			asetF = sc.addAttribute(aset2, StyleConstants.Bold, false);
			break;
		default:
			asetF = sc.addAttribute(aset3, StyleConstants.Bold, false);
			break;
		}
		if (asetF != null)
			styledDoc.setCharacterAttributes(offset, length, asetF, true);
		else
			styledDoc.setCharacterAttributes(offset, length, aset, true);

//		try {
//			System.out.println(offset + " " + length + " " + op + " " + t.getText(offset, length));
//		} catch (BadLocationException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

//	    styledDoc.setCharacterAttributes(offset, length, asetF, false);

	}

	public static void main(String[] args) {
		DiffIt jk = new DiffIt();
		jk.setVisible(true);
	}
}
