package iX.Widgets;

import javax.swing.JDialog;

import iX.TextEditor.iXEditor;
import iX.Utilities.diff_match_patch;
import iX.Utilities.diff_match_patch.Diff;


public class DiffIt extends JDialog {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private iXEditor editor;
	public DiffIt() {
		
		editor = new iXEditor(this);
		add(editor);
		
		String text1 = "";
		String text2 = "";
		
		diff_match_patch dmp = new diff_match_patch();
		for (Diff d : dmp.diff_main(text1, text2)) {
			System.out.println(d.operation + d.text);
		}
	}
}
