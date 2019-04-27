package iX.TextEditor;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;
import javax.swing.text.Element;

/**
 * @author abrar
 *
 */
public class iXEditorLineNumberArea extends JTextArea {

	/**
	 * Default serial version id.
	 * Used for removing warning. 
	 */
	private static final long serialVersionUID = 1L;
	
	// Declaring variables
	private Document iXEditorDoc;
	private Color foregroundColor;
	private Color backgroundColor;
	private int previousLine;
	
	public iXEditorLineNumberArea(Document editorDoc, Color foreground, Color background) {
		iXEditorDoc = editorDoc;
		foregroundColor = foreground;
		backgroundColor = background;
		
		previousLine = 1;
		
		// Initialize UI
		setupUI();
	}
	
	private void setupUI() {
		iXEditorLineNumberAreaDocumentListener iXDocListener = new iXEditorLineNumberAreaDocumentListener();
		setForeground(foregroundColor);
		setBackground(backgroundColor);
		// TODO Need Border color for line area
	    setBorder(BorderFactory.createMatteBorder(0, 0, 0, 2, Color.DARK_GRAY));
	    setEditable(false);
	    setText(iXDocListener.getNumbersString(previousLine));
	    
		iXEditorDoc.addDocumentListener(iXDocListener);
	}

	
    /**
     * @author abrar
     * 
     * Document listener for updating the line number area.
     */
    private class iXEditorLineNumberAreaDocumentListener implements DocumentListener {

		@Override
		public void changedUpdate(DocumentEvent e) {
			// Never called
			// See JTextComponent documentation
		}

		@Override
		public void insertUpdate(DocumentEvent e) {
			updateLineNumbers(getNumberOfLines(e.getDocument()));
		}

		@Override
		public void removeUpdate(DocumentEvent e) {
			updateLineNumbers(getNumberOfLines(e.getDocument()));
		}
		
		private void updateLineNumbers(int line) {
	        if(line != previousLine) {
	            setText(getNumbersString(line));
	            previousLine = line;
	        }
	    }
		
	    private String getNumbersString(int lineNumber) {
	        StringBuilder lineNumbersText = new StringBuilder();
			String format = " %" + Math.max(Integer.toString(lineNumber).length(), 2) + "d ";
			for (int i = 1; i <= lineNumber; i++)
				lineNumbersText.append(String.format(format, i)).append(System.lineSeparator());
			return lineNumbersText.toString();
		}
	    
	    private int getNumberOfLines(Document doc) {
	        int textLength = doc.getLength();
	        Element root = doc.getDefaultRootElement();
	        if(textLength > 0)
	            return root.getElementCount();
	        else
	            return 1;
	    }
	    
    }
}
