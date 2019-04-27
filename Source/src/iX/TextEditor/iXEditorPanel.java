package iX.TextEditor;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class iXEditorPanel extends JPanel {

	/**
	 * Default serial version id.
	 * Used for removing warning. 
	 */
	private static final long serialVersionUID = 1L;

	// Declaring UI components
	private Component editorPanelParent;
	private JScrollPane ixEditorScrollPane;
	private iXEditor ixEditor;
	private iXEditorLineNumberArea ixEditorLineNumberArea;
	// =======================
	
	public iXEditorPanel(Component parent) {
		editorPanelParent = parent;
		
		// Initializing UI
		setupUI();
	}

	private void setupUI() {
		BorderLayout defaultLayout = new BorderLayout();
		
		ixEditor = new iXEditor(editorPanelParent);
		ixEditor.appendString("ABRAR");
		ixEditorLineNumberArea = new iXEditorLineNumberArea(ixEditor.getDocument(), ixEditor.getForeground(), ixEditor.getBackground());
		
		ixEditorScrollPane = new JScrollPane();
		ixEditorScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		ixEditorScrollPane.setViewportView(ixEditor);
		ixEditorScrollPane.setRowHeaderView(ixEditorLineNumberArea);
		
		setLayout(defaultLayout);
		add(ixEditorScrollPane);		
	}
	
	// Get the text editor
	public iXEditor getiXTextEditor() {
		return ixEditor;
	}
}
