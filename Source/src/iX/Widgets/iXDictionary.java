package iX.Widgets;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Hashtable;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextArea;

import iX.Utilities.iXUtility;

public class iXDictionary extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String word;
	private String dictFile = "/dict/dictfile.txt";
	Hashtable<String, String> di;

	public iXDictionary(String word) {
		// Set frame properties
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.setMinimumSize(new Dimension(400, 300));
		this.setTitle("iXPAD - Dictionary");
		this.setLocationRelativeTo(null); // Place the frame to the center of the parent.
		// ====================
		this.word = word;

		di = loadDict();

		JLabel lbl = new JLabel();
		JTextArea txt = new JTextArea();
		add(lbl, BorderLayout.PAGE_START);
		add(txt, BorderLayout.CENTER);

		lbl.setText(word);
		txt.setEditable(false);
		txt.setLineWrap(true);
		txt.setText(di.get(word));
	}

	private Hashtable<String, String> loadDict() {
		Hashtable<String, String> hash = new Hashtable<>();
		BufferedReader br;
		try {
			br = new BufferedReader(new InputStreamReader(iXUtility.class.getResourceAsStream(dictFile)));
			String currentLine = null;
			while ((currentLine = br.readLine()) != null) {
				String[] tList = currentLine.split(">>>>>");
				if (tList.length > 1) {
					String key = (tList[0]);
					String value = tList[1];
					hash.put(key, value);
				}
			}
		} catch (IOException e) {
			System.out.println("iXPAD : File reading problem.\n" + e.getMessage());
		} catch (Exception e) {
			System.out.println("iXPAD : " + e.getMessage());
		}

		return hash;
	}

	public static void main(String[] args) {
		iXDictionary id = new iXDictionary("ACCESS");
		id.setVisible(true);
	}
}
