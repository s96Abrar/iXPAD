package iX.Widgets;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

import iX.Utilities.iXUtility;

public class iXPinIt extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final String pinFile = System.getProperty("user.home") + "/iXPADPin.txt";
	public static final String pinSectionFile = System.getProperty("user.home") + "/iXPADPinSection.txt";
	
	public iXPinIt(String filePath, Component parent) {
		
		this.filePath = filePath;
		setupUI(parent);
	}
	
	private JCheckBox chkOldSection;
	private JComboBox<String> sectionList; 
	private JCheckBox chkNewSection;
	private JTextField tfNewSection;
	private JLabel lblFileName;
	private JButton btnOK;
	private String filePath;
	
	private void setupUI(Component parent) {
		// Set frame properties
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.setMinimumSize(new Dimension(400, 200));
		this.setTitle("iXPAD - Pin It");
		this.setLocationRelativeTo(parent); // Place the frame to the center of the parent.
		// ====================
		
		chkOldSection = new JCheckBox("Add to existing section");
		sectionList = new JComboBox<>(); 
		
		Vector<String> ll = new Vector<>();
		ArrayList<String> list = new ArrayList<>();
		BufferedReader br;
		try {
			int i = 0;
			br = new BufferedReader(new FileReader(pinSectionFile));
			String currentLine = "";
			while ((currentLine = br.readLine()) != null) {
				System.out.println("Line " + currentLine);
				ll.add(currentLine);
			}
		} catch (IOException e) {
			System.out.println("iXPAD : File reading problem.\n" + e.getMessage());
		} catch (Exception e) {
			System.out.println("iXPAD : " + e.getMessage());
		}
		System.out.println("Sr " + list.toString());
		
		String[] jk = null; jk = ll.toArray(jk);
//		jk = (String[]) list.toArray();
		if (jk != null) {
			DefaultComboBoxModel<String> d = new DefaultComboBoxModel<>(jk);
			sectionList.setModel(d);
		}
		chkNewSection = new JCheckBox("Add to new section ");
		tfNewSection = new JTextField();
		lblFileName = new JLabel(filePath);
		btnOK = new JButton("OK");
		
		iXUtility ixUtil = new iXUtility();
		
		btnOK.addActionListener( (l) -> {
			if (chkNewSection.isSelected()) {
				ixUtil.saveToFile(tfNewSection.getText() + "\t\t\t" + lblFileName.getText() + "\n", pinFile, true);
				ixUtil.saveToFile(tfNewSection.getText() + "\n", pinSectionFile, true);
			} else if (chkOldSection.isSelected()) {
				ixUtil.saveToFile(sectionList.getSelectedItem() + "\t\t\t" + lblFileName.getText() + "\n", pinFile, true);
				ixUtil.saveToFile((String)sectionList.getSelectedItem() + "\n", pinSectionFile, true);
			}
		});
				
		GridBagLayout layout = new GridBagLayout();
		
		setLayout(layout);
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		add(chkOldSection, gbc);
		gbc.gridx = 1;
//		gbc.ipadx = 60;
		add(sectionList, gbc);
		gbc.gridy = 1;
		add(tfNewSection, gbc);
		gbc.gridx = 0;
		add(chkNewSection, gbc);
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.gridwidth = 2;
//		gbc.ipady = 40;
//		gbc.ipadx = 0;
		add(lblFileName, gbc);
		gbc.gridx = 1;
		gbc.gridy = 3;
//		gbc.ipady = 0;
		add(btnOK, gbc);
				
	}
	
	
	
	public static void main(String[] args) {
		iXPinIt pin = new iXPinIt("/home/abrar/", null);
		pin.setVisible(true);
	}
}

