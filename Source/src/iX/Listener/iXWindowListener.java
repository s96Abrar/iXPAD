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

package iX.Listener;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JOptionPane;

import iX.Windows.iXPAD;

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
		int reply = JOptionPane.showOptionDialog(ixPAD, "Are you sure to close this application?", "Exit Confirmation",
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
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
