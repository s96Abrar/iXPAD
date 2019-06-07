/*
 * iXPAD is a simple text editor with bookmark, syntax highlighting, recent activity, spell check
 * 
 * Copyright (C) 2019  Abrar
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.

 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.

 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package iX.TextEditor;

import java.awt.Color;

import java.awt.Component;
import java.awt.FileDialog;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.JTextPane;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.Element;
import javax.swing.text.Highlighter.HighlightPainter;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;
import javax.swing.undo.UndoManager;

import iX.Utilities.iXUtility;
import iX.Windows.iXPAD;
import iX.Windows.iXSearch;
import iX.SpellCheck.WaveHighlighter;

/**
 * @author abrar
 *
 */
public class iXEditor extends JTextPane implements DocumentListener {

	/**
	 * Default serial version id. Used for removing warning.
	 */
	private static final long serialVersionUID = 1L;

	// Declaring variables
	private iXUtility ixUtil;
	private iXEditorAbstractAction editorAction;
	private UndoManager ixEditorUndoManage;
	private iXEditorUndoableListener ixUndoListener;

	private String workFilePath;

	private iXEditor textEditor;
	private iXPAD ixPAD;

	private String act;

	// TODO Move pattern to new class
	String commentPattern = "//.*";
	String comment2Pattern = "///.*";
	String defaultPattern = ".*";
	String stringPattern = "\"[^\"\\\\]*(\\\\(.|\\n)[^\"\\\\]*)*\"|'[^'\\\\]*(\\\\(.|\\n)[^'\\\\]*)*'";
	String multiLineCommentPattern = "/\\*(.|[\\r\\n])*?\\*/";
	String className = "\\b[A-Z][a-z]*([A-Z][a-z]*)*\\b";
//	String numbers = "\b[0-9]+\\.?[0-9]*\b";
	String numbers = "\\b\\d+[\\.]?\\d*([eE]\\-?\\d+)?[lLdDfF]?\\b|\\b0x[a-fA-F\\d]+\\b";
	String functionName = "(\\w+\\()+([^\\)]*\\)+)";
	String preprocessor = ".*#[^\n]*";
	String keywords = "";

	HighlightPainter painter;

	// ===================

	public iXEditor(Component parentParent) {
		// Initialize variables
		textEditor = this;
		if (parentParent != null)
			ixPAD = (iXPAD) parentParent;
		ixUtil = new iXUtility();
		ixEditorUndoManage = new UndoManager();

		act = "Change";
		// ====================

		// Initialize UI
		setupUI();

		painter = new WaveHighlighter(Color.RED);
		// TODO Remove unused code
//		getStyledDocument().putProperty(DefaultEditorKit.EndOfLineStringProperty, "\n");
//		setEditorKit(new StyledEditorKit());		
//		iXEditorLineWrapEditorKit e = new iXEditorLineWrapEditorKit();
//		e.setWrap(true);
//		setEditorKit(e);
//		this.setEditorKit(new WrapEditorKit());
	}

	public String getWorkFilePath() {
		return workFilePath;
	}

	private void setupUI() {
		getDocument().addDocumentListener(this);
		ixUndoListener = new iXEditorUndoableListener();
		getDocument().addUndoableEditListener(ixUndoListener);

		// Add shortcut key
		addKeyboardShortcut();

		// Get C++ keywords
		String[] st = { "char", "class", "const", "double", "enum", "explicit", "extern", "float", "friend", "inline",
				"int", "long", "namespace", "operator", "private", "protected", "public", "short", "signals", "signed",
				"slots", "static", "struct", "template", "typedef", "typename", "union", "using", "unsigned", "virtual",
				"void", "volatile", "true", "false", "bool", "and", "or", "not", "for", "while", "switch", "case",
				"continue", "break", "if", "else", "return", "exit" };

		for (String s : st) {
			String.join("|", keywords, "\\b" + s + "\\b");
		}

		System.out.println("Why" + keywords);

	}

	private void addKeyboardShortcut() {
		String keyText = null;
		int keyEvent = 0;

		for (int i = 0; i < 3; i++) {
			if (i == 0) {
				keyText = "UpperCase";
				keyEvent = KeyEvent.VK_U;
			} else if (i == 1) {
				keyText = "LowerCase";
				keyEvent = KeyEvent.VK_L;
			} else if (i == 2) {
				keyText = "TitleCase";
				keyEvent = KeyEvent.VK_T;
			}

			editorAction = new iXEditorAbstractAction(keyText);
			ixUtil.addKeyShortcut(this, keyText, KeyStroke.getKeyStroke(keyEvent, ActionEvent.CTRL_MASK), editorAction);
		}
	}

	public void appendString(String str) {
		try {
			Document doc = getDocument();
			doc.insertString(doc.getLength(), str, null);
		} catch (BadLocationException ex) {
			ex.printStackTrace();
		}
	}

	public void undo() {
		try {
			ixEditorUndoManage.undo();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		ixPAD.updateUndoRedo(canUndo(), canRedo());
	}

	public void redo() {
		try {
			ixEditorUndoManage.redo();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		ixPAD.updateUndoRedo(canUndo(), canRedo());
	}

	public void search() {
		new iXSearch(this);
	}

	private boolean canUndo() {
		return ixEditorUndoManage.canUndo();
	}

	private boolean canRedo() {
		return ixEditorUndoManage.canRedo();
	}

	public void openFile() {
		String filePath = openFileDialog();
		File f = new File(filePath);
		if (f.exists()) {
			setText(ixUtil.openFromFile(filePath));
		} else {
			System.out.println("iXPAD : File not exists " + f.getAbsolutePath());
		}
	}

	public void saveFile() {
		// TODO: check the work file path before saving it and move it to safe
		String filePath = workFilePath;

		if (filePath == null) {
			filePath = saveFileDialog();
		} else {
			File f = new File(filePath);
			if (f.exists() == false) {
				System.out.println("iXPAD : File not exists " + f.getAbsolutePath());
			}
			ixUtil.saveToFile(getText(), filePath);
		}
	}

	public void saveAsFile() {
		String filePath = saveFileDialog();
		if (filePath != null) {
			ixUtil.saveToFile(getText(), filePath);
		} else {
			System.out.println("iXPAD : Null file path at save as.");
		}
	}

	private String openFileDialog() {
		return fileDialog("Open File", FileDialog.LOAD);
	}

	private String saveFileDialog() {
		return fileDialog("Save File", FileDialog.SAVE);
	}

	private String fileDialog(String title, int mode) {
		String filePath = null;

		FileDialog fd = new FileDialog(new JFrame(), title, mode);
		// TODO Move to variables class
		fd.setDirectory(System.getProperty("user.home"));
		fd.setFilenameFilter((dir, name) -> name.endsWith(".txt"));
		fd.setVisible(true);
		filePath = fd.getDirectory() + fd.getFile();

		return filePath;
	}

	private class iXEditorAbstractAction extends AbstractAction {

		/**
		 * Default serial version id. Used for removing warning.
		 */
		private static final long serialVersionUID = 1L;

		private String actionText;

		public iXEditorAbstractAction(String actText) {
			actionText = actText;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if (actionText == "UpperCase") {
				textEditor.replaceSelection(toStrUpperCase(textEditor.getSelectedText()));
			} else if (actionText == "LowerCase") {
				textEditor.replaceSelection(toStrLowerCase(textEditor.getSelectedText()));
			} else if (actionText == "TitleCase") {
				textEditor.replaceSelection(toStrTitleCase(textEditor.getSelectedText()));
			}
		}

	}

	private class iXEditorUndoableListener implements UndoableEditListener {

		@Override
		public void undoableEditHappened(UndoableEditEvent e) {
			if (act == "Change") {
				ixEditorUndoManage.addEdit(e.getEdit());
				ixPAD.updateUndoRedo(canUndo(), canRedo());
			}
		}

	}

	private class iXEditorDocumentListener implements DocumentListener {

		// TODO Remove Unused
		@Override
		public void changedUpdate(DocumentEvent e) {
			// Event never called
			// See JTextComponent documentation
		}

		@Override
		public void insertUpdate(DocumentEvent e) {

		}

		@Override
		public void removeUpdate(DocumentEvent e) {
			// TODO Auto-generated method stub

		}

	}

	// TODO Move to Utility class
	public String toStrUpperCase(String str) {
		if (str == null) {
			return null;
		}

		return str.toUpperCase();
	}

	// TODO Move to Utility class
	public String toStrLowerCase(String str) {
		if (str == null) {
			return null;
		}

		return str.toLowerCase();
	}

	// TODO Move to Utility class
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
					replaceStr.append(s.substring(0, 1).toUpperCase()).append(s.substring(1).toLowerCase());
				} else {
					replaceStr.append(s.toUpperCase());
				}

				replaceStr.append(" ");
			}
			replaceStr.append("\n");
		}

		return new String(replaceStr).trim();
	}

	public void changedUpdate(DocumentEvent e) {
		// Event never called
		// See JTextComponent documentation
	}

	public void insertUpdate(DocumentEvent e) {
		syntaxHighlight();
	}

	public void removeUpdate(DocumentEvent e) {
		syntaxHighlight();
	}

	private int getCurrentLineNumber(Element root) {
		return (root.getElementIndex(getCaretPosition()) + 1);
	}

	private Point getCurrentLineStartEndOffset(Element root) {
		int start, end;
		Element lineElement = root.getElement(getCurrentLineNumber(root) - 1);
		start = lineElement.getStartOffset();
		end = lineElement.getEndOffset();

		return new Point(start, end);
	}

	private String getCurrentLineBlock(Element root) {
		String textStr = null;

		Point p = getCurrentLineStartEndOffset(root);
		int start = p.x;
		int end = p.y;

		try {
			textStr = getText(start, end - start);
		} catch (BadLocationException e) {
			e.printStackTrace();
		}

		System.out.println("Text " + textStr);

		return textStr;
	}

	private void updateSyntaxColor(int offset, int length, Color c, int style, String Operation) {
//		getDocument().removeUndoableEditListener(ixUndoListener);
		System.out.println("Offset " + offset + " length " + length);

		act = "color";
		System.out.println(Operation + " " + offset + " " + length);
		StyledDocument styledDoc = getStyledDocument();
		StyleContext sc = StyleContext.getDefaultStyleContext();

		AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, c);
		AttributeSet asetF = null;
		AttributeSet asetF2 = null;

		switch (style) {
		case Font.BOLD:
			asetF = sc.addAttribute(aset, StyleConstants.Bold, true);
			break;
		case Font.ITALIC:
			asetF = sc.addAttribute(aset, StyleConstants.Italic, true);
			break;
		case 3:
			asetF = sc.addAttribute(aset, StyleConstants.Bold, true);
			asetF2 = sc.addAttribute(asetF, StyleConstants.Italic, true);
			break;
		default:
//			asetF = sc.addAttribute(aset, StyleConstants., true);
		}

		if (asetF2 != null)
			styledDoc.setCharacterAttributes(offset, length, asetF2, true);
		else if (asetF != null)
			styledDoc.setCharacterAttributes(offset, length, asetF, true);
		else
			styledDoc.setCharacterAttributes(offset, length, aset, true);

		act = "Change";
//		getDocument().addUndoableEditListener(ixUndoListener);
	}

	class SyntaxRule {
		String rule;
		String startRule;
		String endRule;
		Color color;
		int font;

		SyntaxRule(String r, String sr, String er, Color c, int f) {
			rule = r;
			startRule = sr;
			endRule = er;
			color = c;
			font = f;
		}
	}

	SyntaxRule[] syntax_rules = { new SyntaxRule(null, "/\\*", "\\*/", Color.red, Font.ITALIC),
			new SyntaxRule(commentPattern, null, null, Color.green, Font.ITALIC),
			new SyntaxRule(comment2Pattern, null, null, Color.blue, Font.BOLD + Font.ITALIC) };

	private void syntaxHighlight() {

		String[] syntaxs = { defaultPattern, commentPattern, /* comment2Pattern, */ multiLineCommentPattern,
				stringPattern, className, numbers, functionName, preprocessor, keywords };

		Runnable syntaxHighlight = new Runnable() {
			public void run() {
				Element root = getDocument().getDefaultRootElement();
				int start = getCurrentLineStartEndOffset(root).x;
				String textStr = getCurrentLineBlock(root);

//            	int start = 0;
//            	int splitactive = Pattern.compile("/\\*").matcher(getText()).find() ? 0 : -1;
//            	if (splitactive > syntax_rules.length - 1) {
//            		splitactive = -1;
//            	}
//            	System.out.println("Split + " + splitactive + " start " + start + " text " + getCaretPosition());
//            	
//            	while(start >= 0 && (getCaretPosition() - start) <= textStr.length() - 1) {
//            		if (splitactive >= 0) {
//            			Pattern p = Pattern.compile(syntax_rules[0].endRule);
//            			Matcher m = p.matcher(textStr);
//            			if (m.find() == false) {
//            				System.out.println("No end rule" + start + " length " + (textStr.length()-(getCaretPosition() - start) + 1));
//            				if (start > 0) {
//            					updateSyntaxColor(start - 1, textStr.length()-(getCaretPosition() - start) + 1, syntax_rules[0].color, syntax_rules[0].font, "Multi");
//            				} else {
//            					updateSyntaxColor(start, textStr.length()-start, syntax_rules[0].color, syntax_rules[0].font, "Multi");
//            				}
//            				
//            				break;
//            			} else {
//                			int end = m.end();
//            				int len = end - start + (end - m.start());
//            				System.out.println("end rule" + start + " length " + len + " end " + end);
//
//            				if (start > 0) { start--; len++;}
//            				updateSyntaxColor(start, len, syntax_rules[0].color, syntax_rules[0].font, "multiSame");
//            				start += len;
//            				splitactive = -1;
//            			}
////            			System.out.println("End " + end);
//            		}
//            		for (int i = 0; i < syntax_rules.length && splitactive < 0; i++) {
//            			if (syntax_rules[i].startRule == null) {continue;}
//            			Pattern p = Pattern.compile(syntax_rules[i].startRule);
//            			Matcher m = p.matcher(textStr);
//            			if (m.find() == false) {continue;}
//            			int newStart = m.start();
//            			System.out.println("Other pattern " + newStart);
//            			if (newStart >= start) {
//            				splitactive = i;
//                            start = newStart+1;
//                            if (start >= textStr.length()-1) {
//                            	updateSyntaxColor(start - 1, textStr.length()-start + 1, syntax_rules[i].color, syntax_rules[i].font, "Multi");
//                            }
//            			}
//            		}
//            		
//            		if (splitactive < 0) {
//            			break;
//            		}
//            	}
//            	
//            	for (int i = 0; i < syntax_rules.length; i++){
//                    if (syntax_rules[i].rule == null){ continue; }
//                    Pattern p = Pattern.compile(syntax_rules[i].rule);
//                    Matcher m = p.matcher(textStr);
//                    
//                    if (m.find() == false) {continue;}
//                    int index = m.start();
//                    System.out.println("Other rules " + index);
//                    //if(splitactive>=0 || index<start){ continue; } //skip this one - falls within a multi-line pattern above
//
//                    while (index > -1) {
//                    	updateSyntaxColor(m.start(), m.end() - m.start(), syntax_rules[i].color, syntax_rules[i].font, "O");
//                    	if (m.find()) {
//                    		index = m.start();
//                    	} else {
//                    		index = -1;
//                    	}
//                    }
//                    while(index>=0){
//                        int len = Pattern.compile(syntax_rules[i].rule).matcher(textStr).end() + s;
//                        if(format(index)==currentBlock().charFormat())
//                        {
//                        	updateSyntaxColor(index, len, syntax_rules[i].color, syntax_rules[i].font, "o");
//                        } //only apply highlighting if not within a section already
//                        index = patt.indexIn(text, index+len); //go to the next match
//                    }
//                }

				/*
				 * int start = 0; int splitactive = previousBlockState();
				 * if(splitactive>syntax.rules.length()-1){ splitactive = -1; } //just in case
				 * while(start>=0 && start<=text.length()-1){ if(splitactive>=0){ //Find the end
				 * of the current rule int end =
				 * syntax.rules[splitactive].endPattern.indexIn(text, start); if(end==-1){
				 * //rule did not finish - apply to all if(start>0){ setFormat(start-1,
				 * text.length()-start+1, syntax.rules[splitactive].format); } else{
				 * setFormat(start, text.length()-start, syntax.rules[splitactive].format); }
				 * break; //stop looking for more multi-line patterns } else { //Found end point
				 * within the same line int len =
				 * end-start+syntax.rules[splitactive].endPattern.matchedLength(); if(start>0){
				 * start--; len++; } //need to include the first character as well
				 * setFormat(start, len , syntax.rules[splitactive].format); start+=len; //move
				 * pointer to the end of handled range splitactive = -1; //done with this rule }
				 * } //end check for end match //Look for the start of any new split rules
				 * for(int i=0; i<syntax.rules.length() && splitactive<0; i++){
				 * if(syntax.rules[i].startPattern.isEmpty()){ continue; } int newstart =
				 * syntax.rules[i].startPattern.indexIn(text,start); if(newstart>=start){
				 * splitactive = i; start = newstart+1; if(start>=text.length()-1){ //Need to
				 * apply highlighting to this section too - start matches the end of the line
				 * setFormat(start-1, text.length()-start+1, syntax.rules[splitactive].format);
				 * } } } if(splitactive<0){ break; } //no other rules found - go ahead and exit
				 * the loop } //end scan over line length and multi-line formats
				 * 
				 * qDebug() << text; setCurrentBlockState(splitactive); //Do all the single-line
				 * patterns for(int i=0; i<syntax.rules.length(); i++){
				 * if(syntax.rules[i].pattern.isEmpty()){ continue; } //not a single-line rule
				 * QRegExp patt(syntax.rules[i].pattern); //need a copy of the rule's pattern
				 * (will be changing it below) int index = patt.indexIn(text); if(splitactive>=0
				 * || index<start){ continue; } //skip this one - falls within a multi-line
				 * pattern above while(index>=0){ int len = patt.matchedLength();
				 * if(format(index)==currentBlock().charFormat()){ setFormat(index, len,
				 * syntax.rules[i].format); } //only apply highlighting if not within a section
				 * already index = patt.indexIn(text, index+len); //go to the next match }
				 * }//end loop over normal (single-line) patterns
				 * 
				 */

//            	{ // Default string
//	        		//Pattern pattern = Pattern.compile(defaultPattern);				
//					//Matcher match = pattern.matcher(getText());
//					
//					int mStart = -1;
//					int mEnd = -1;
//					//if (match.find()) {
//						mStart = 0;//match.start();
//						mEnd = getText().length()/* - 1*/;//match.end();
//					//}
//					
//					if (mStart > -1) {
//						updateSyntaxColor(mStart/*+start*/, mEnd/*-mStart*/, Color.BLACK, Font.PLAIN, "DefaultString");
//					}
//            	}
//            	
//            	{ // Comment "//"
//	            	Pattern pattern = Pattern.compile(commentPattern);				
//					Matcher match = pattern.matcher(textStr);
//					
//					int mStart = -1;
//					int mEnd = -1;
//					if (match.find()) {
//						mStart = match.start();
//						mEnd = match.end();
//					}
//					
//					if (mStart > -1) {
//						updateSyntaxColor(mStart+start, mEnd-mStart, Color.green, Font.ITALIC, "Comment//");
//					}
//            	}
//            	
//            	{ // Comment "///"
//            		Pattern pattern = Pattern.compile(comment2Pattern);				
//    				Matcher match = pattern.matcher(textStr);
//    				
//    				int mStart = -1;
//    				int mEnd = -1;
//    				if (match.find()) {
//    					mStart = match.start();
//    					mEnd = match.end();
//    				}
//    				
//    				if (mStart > -1) {
//    					updateSyntaxColor(mStart+start, mEnd-mStart, Color.green, Font.BOLD + Font.ITALIC, "Comment///");
//    				}
//            	}
//            	
//            	{ // Multiline Comment
//            		Pattern pattern = Pattern.compile(multiLineCommentPattern);
////            		System.out.println(textStr.matches("/\\*"));
//            		//if (textStr.matches("/\\*") || textStr.matches("\\*/")) {
//            			// Need to check whole text string for multiline comment
//        				Matcher match = pattern.matcher(getText());
//        				
//        				int mStart = -1;
//        				int mEnd = -1;
//        				if (match.find()) {
//        					mStart = match.start();
//        					mEnd = match.end();
//        				}
//        				
//        				if (mStart > -1) {
//        					updateSyntaxColor(mStart, mEnd-mStart, Color.green, Font.ITALIC, "Multiline");
//        				}
//            		//}
//            	}

				int i = 0;
				for (String s : syntaxs) {
					Pattern pattern = Pattern.compile(s);
					Matcher match = pattern.matcher(getText());

//					int mStart = -1;
//					int mEnd = -1;
//					if (match.find()) {
//						mStart = match.start();
//						mEnd = match.end();
//					}

					while (match.find()) {
						updateSyntaxColor(match.start(), match.end() - match.start(), colors[i], Font.PLAIN, "Random");
					}
//					
					i++;
//					if (mStart > -1) {
//						updateSyntaxColor(mStart+start, mEnd-mStart, Color.green, Font.ITALIC, "Comment//");
//					}
				}

			}
		};
		SwingUtilities.invokeLater(syntaxHighlight);
	}

	Color[] colors = { Color.BLACK, Color.decode("#008000"), Color.decode("#008000"), Color.decode("#008000"),
			Color.decode("#800080"), Color.decode("#808080"), Color.decode("#008080"), Color.decode("#808000"),
			Color.decode("#0000ff") };

}

//class WrapEditorKit extends StyledEditorKit {
//    ViewFactory defaultFactory=new WrapColumnFactory();
//    public ViewFactory getViewFactory() {
//        return defaultFactory;
//    }
//
//    public MutableAttributeSet getInputAttributes() {
//        MutableAttributeSet mAttrs=super.getInputAttributes();
////        mAttrs.removeAttribute(WrapApp.LINE_BREAK_ATTRIBUTE_NAME);
//        return mAttrs;
//    }
//}
//
//class WrapColumnFactory implements ViewFactory {
//    public View create(Element elem) {
//        String kind = elem.getName();
//        if (kind != null) {
//            if (kind.equals(AbstractDocument.ContentElementName)) {
//                return new iXWrapLabelView(elem);
////            } else if (kind.equals(AbstractDocument.ParagraphElementName)) {
////                return new NoWrapParagraphView(elem);
//            } else if (kind.equals(AbstractDocument.SectionElementName)) {
//                return new BoxView(elem, View.Y_AXIS);
//            } else if (kind.equals(StyleConstants.ComponentElementName)) {
//                return new ComponentView(elem);
//            } else if (kind.equals(StyleConstants.IconElementName)) {
//                return new IconView(elem);
//            }
//        }
//
//        // default to text display
//        return new LabelView(elem);
//    }
//}
//