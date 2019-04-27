package iX.Listener;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JOptionPane;

import iX.Widgets.iXPAD;

public class iXWindowListener implements WindowListener {
	
	private iXPAD ixPAD; 
	
	public iXWindowListener(iXPAD ixpad) {
		ixPAD = ixpad;
	}

	// TODO Remove 
	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	// TODO Remove 
	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosing(WindowEvent e) {
		// TODO Add icon for the message
		// ImageIcon icon = new ImageIcon(ixPAD.getIconImage())
		int reply = JOptionPane.showOptionDialog(ixPAD, "Are you sure to close this application?", "Exit Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
		if (reply == JOptionPane.YES_OPTION) {
			
			// TODO Check current working file path and add to recent activity
//			if (!workFilePath.isEmpty()) {
//            	RecentActivity.saveActivity(workFilePath);
//            }
			
			System.exit(0);
		}
	}

	// TODO Remove 
	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	// TODO Remove 
	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	// TODO Remove 
	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	// TODO Remove 
	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

}
