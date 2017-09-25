package org.golde.java.scratchforge.windows;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import org.golde.java.scratchforge.Main;
import org.golde.java.scratchforge.helpers.JavaHelper;
import org.golde.java.scratchforge.helpers.PLog;
import org.golde.java.scratchforge.mod.Mod;

public class WindowToggleMods extends JFrame{

	private static final long serialVersionUID = 6722641269135618502L;

	private Main main;


	public WindowToggleMods(Main main) {
		this.main = main;
		this.setResizable(false);
		setTitle("Mod Manager");

		JTree tree = new JTree(populateTreeView());
		tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		tree.setCellRenderer(new MyTreeCellRenderer());
		tree.addMouseListener(ma);
		JScrollPane treeView = new JScrollPane(tree);
		treeView.setBounds(12, 39, 420, 213);

		getContentPane().add(treeView);

		setLocationRelativeTo(null);
		pack();
		setVisible(true);
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(450, 345);
	}

	private DefaultMutableTreeNode populateTreeView() {
		DefaultMutableTreeNode top = new DefaultMutableTreeNode("Mods");
		for(Mod mod: main.modManager.allMods()) {
			DefaultMutableTreeNode modNode = new DefaultMutableTreeNode(mod.getDisplayName());
			modNode.setUserObject(mod);
			top.add(modNode);
		}

		return top;
	}

	MouseAdapter ma = new MouseAdapter() {
		private void myPopupEvent(MouseEvent e) {
			int x = e.getX();
			int y = e.getY();
			JTree tree = (JTree)e.getSource();
			TreePath path = tree.getPathForLocation(x, y);
			if (path == null)
				return; 
			
			tree.setSelectionPath(path);

			Object obj = ((DefaultMutableTreeNode) path.getLastPathComponent()).getUserObject();
			
			if(obj instanceof Mod) {
				Mod mod = (Mod) obj;
				JPopupMenu popup = new JPopupMenu();

				String[] lables = {"Enable", "Disable", "E̶x̶p̶o̶r̶t̶", "Delete"};
				
				ActionListener menuListener = new ActionListener() {
					public void actionPerformed(ActionEvent event) {
						String action = event.getActionCommand();
						if(action.equals("Enable")) {
							mod.setEnabled(true);
						}
						else if(action.equals("Disable")) {
							mod.setEnabled(false);
						}
						else if(action.equals("Delete")) {
							mod.delete();
							JavaHelper.deleteSelectedItems(tree);
						}
						PLog.info("Popup menu item ["+ event.getActionCommand() + "] was pressed.");
					}
				};

				for(String label:lables) {
					JMenuItem item = new JMenuItem(label);
					item.addActionListener(menuListener);
					popup.add(item);
				}

				popup.show(tree, x, y);
				
				

			}

		}
		public void mousePressed(MouseEvent e) {
			if (e.isPopupTrigger()) myPopupEvent(e);
		}
		public void mouseReleased(MouseEvent e) {
			if (e.isPopupTrigger()) myPopupEvent(e);
		}
		
	};
	

	class MyTreeCellRenderer extends DefaultTreeCellRenderer {

		private static final long serialVersionUID = -3533994315548188299L;

		@Override
		public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean exp, boolean leaf, int row, boolean hasFocus) {
			super.getTreeCellRendererComponent(tree, value, sel, exp, leaf, row, hasFocus);

			Object obj = ((DefaultMutableTreeNode) value).getUserObject();
			if(obj instanceof Mod) {
				Mod mod = (Mod) obj;
				
				setText(mod.getDisplayName()); //Does the ✘ and ✔
				
				if (leaf && mod.getDisplayName().startsWith("✔")) {
					
					setForeground(new Color(39,174,96)); //Green
				}
				else if (leaf && mod.getDisplayName().startsWith("✘")) {
					setForeground(new Color(231,76,60)); //Red
				}
			}

			return this;
		}
	}

}
