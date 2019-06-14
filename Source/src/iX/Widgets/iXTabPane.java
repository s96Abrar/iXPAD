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

package iX.Widgets;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.plaf.basic.BasicButtonUI;

import iX.TextEditor.iXEditorPanel;

/**
 * @author abrar
 *
 */
public class iXTabPane extends JTabbedPane {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public iXTabPane() {
		// super();
	}

	public void addiXEditorPanel(iXEditorPanel editorPanel, String title) {
		add(title, editorPanel);
		setTabLayoutPolicy(JTabbedPane.WRAP_TAB_LAYOUT);
		setTabComponentAt(this.getTabCount() - 1, new iXTabButtonComponent(this));
	}

	public iXEditorPanel getEditorPanel() {
		if (getComponentAt(getSelectedIndex()) instanceof iXEditorPanel) {
			System.out.println("Editor panel found");
			return (iXEditorPanel) getComponentAt(getSelectedIndex());
		}

		System.out.println("Editor panel not found");
		return null;
	}

	private class iXTabButtonComponent extends JPanel {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private iXTabPane ixTabPane;

		public iXTabButtonComponent(iXTabPane ixTabP) {
			super(new FlowLayout(FlowLayout.LEFT, 0, 0));

			if (ixTabP == null) {
				throw new NullPointerException("iXTabPane is null");
			}

			ixTabPane = ixTabP;
			setOpaque(false);

			// Fetch tab label
			JLabel title = new JLabel() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				public String getText() {
					int i = ixTabPane.getSelectedIndex();// indexOfComponent(iXTabButtonComponent.this);
					if (i != -1) {
						return ixTabPane.getTitleAt(i);
					}

					return null;
				}
			};
			title.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 5));

			JButton btnClose = new iXTabCloseButton();

			add(title);
			add(btnClose);

			setBorder(BorderFactory.createEmptyBorder(2, 0, 0, 0));
		}

		private class iXTabCloseButton extends JButton {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public iXTabCloseButton() {
				int size = 17;

				setPreferredSize(new Dimension(size, size));

				// Make the button looks the same for all Laf's
				setUI(new BasicButtonUI());
				// Make transparent
				setContentAreaFilled(false);
				setBorder(BorderFactory.createEtchedBorder());
				setBorderPainted(false);
				// Making nice roll over effect
				addMouseListener(new iXButtonMouseListener());
				setRolloverEnabled(true);
				// Close the proper tab by clicking the button
				addActionListener(
						// iXTabCloseButtonActionListener
						(e) -> {
							int i = ixTabPane.getSelectedIndex();// indexOfComponent(iXTabButtonComponent.this);
							if (i != -1) {
								ixTabPane.remove(i);
							}
						});
			}

			public void updateUI() {
				// Empty no need to update the UI
			}

			protected void paintComponent(Graphics g) {
				super.paintComponent(g);

				Graphics2D g2d = (Graphics2D) g.create();

				// Image shift for pressed button
				if (getModel().isPressed()) {
					g2d.translate(1, 1);
				}

				g2d.setStroke(new BasicStroke(2));
				g2d.setColor(Color.RED);

				if (getModel().isRollover()) {
					// TODO Change color to hex
					g2d.setColor(Color.MAGENTA);
				}

				int delta = 6;
				g2d.drawLine(delta, delta, getWidth() - delta - 1, getHeight() - delta - 1);
				g2d.drawLine(getWidth() - delta - 1, delta, delta, getHeight() - delta - 1);
				g2d.dispose();
			}

			private class iXButtonMouseListener extends MouseAdapter {
				public void mouseEntered(MouseEvent e) {
					Component component = e.getComponent();
					if (component instanceof AbstractButton) {
						AbstractButton btn = (AbstractButton) component;
						btn.setBorderPainted(true);
					}
				}

				public void mouseExited(MouseEvent e) {
					Component component = e.getComponent();
					if (component instanceof AbstractButton) {
						AbstractButton btn = (AbstractButton) component;
						btn.setBorderPainted(false);
					}
				}
			}
		}

	}
}
