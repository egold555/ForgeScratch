package org.golde.java.scratchforge.windows;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.SwingConstants;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeSelectionModel;

import org.golde.java.scratchforge.Main;
import org.golde.java.scratchforge.helpers.JavaHelper;
import org.golde.java.scratchforge.helpers.PLog;
import org.golde.java.scratchforge.mod.Mod;
import org.golde.java.scratchforge.mod.Mod.Texture;

public class WindowEditTexture extends JFrame {

	private static final long serialVersionUID = -8302074072215275295L;
	private Main main;

	private final String[] texturesToIgnore = {"spawn_egg", "spawn_egg_overlay"};

	public WindowEditTexture(Main main) {
		this.main = main;
		File forgeModsIn = new File(main.forge_folder, "src\\main\\java\\org\\golde\\forge\\scratchforge\\mods");

		this.setResizable(false);
		getContentPane().setLayout(null);

		JTree tree = new JTree(populateTreeView(forgeModsIn));
		tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		tree.setCellRenderer(new MyTreeCellRenderer());

		tree.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2 && e.getButton() == MouseEvent.BUTTON1) {
					DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();

					if (node == null) {
						return;
					}

					Object nodeInfo = node.getUserObject();
					if(nodeInfo instanceof Texture) {
						Texture texture = (Texture)nodeInfo;
						if(!texture.hasBeenCreated()) {
							try {
								texture.createTexture();
							} catch (IOException e1) {
								PLog.error(e1, "Failed to create texture");
							}
						} 
						JavaHelper.openFileWithDefaultProgram(texture.getFile());
					}
				}
			}
		});

		tree.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {}

			@Override
			public void keyReleased(KeyEvent e) {}

			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_DELETE) {
					DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();

					if (node == null) {
						return;
					}

					Object nodeInfo = node.getUserObject();
					if(nodeInfo instanceof Texture) {
						Texture texture = (Texture)nodeInfo;
						if(texture.delete()) {
							//successfully deleted
							JavaHelper.deleteSelectedItems(tree);
						}
					}
				}
			}
			
		});


		JScrollPane treeView = new JScrollPane(tree);
		treeView.setBounds(12, 39, 420, 213);

		getContentPane().add(treeView);

		setLocationRelativeTo(null);

		pack();
		setVisible(true);


		JLabel lblDoubleClickImage = new JLabel("Double-Click an image to edit it");
		lblDoubleClickImage.setHorizontalAlignment(SwingConstants.CENTER);
		lblDoubleClickImage.setBounds(12, 13, 420, 16);
		getContentPane().add(lblDoubleClickImage);

	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(450, 345);
	}

	private DefaultMutableTreeNode populateTreeView(File forgeModsIn) {
		DefaultMutableTreeNode top = new DefaultMutableTreeNode("Mods");
		for(Mod mod: main.modManager.allMods()) {
			DefaultMutableTreeNode modNode = new DefaultMutableTreeNode(mod.getModName());
			modNode.setUserObject(mod);
			//PLog.info("Textures: " + mod.getTextures().length);
			for(Texture texture:mod.getTextures()) {
				if(Arrays.asList(texturesToIgnore).contains(texture.getTextureName())) {
					continue;
				}
				DefaultMutableTreeNode textureNode = new DefaultMutableTreeNode(texture.getDisplayName());
				textureNode.setUserObject(texture);
				modNode.add(textureNode);
			}
			top.add(modNode);
		}

		return top;
	}

	class MyTreeCellRenderer extends DefaultTreeCellRenderer {

		private static final long serialVersionUID = -3533994315548188299L;

		@Override
		public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean exp, boolean leaf, int row, boolean hasFocus) {
			super.getTreeCellRendererComponent(tree, value, sel, exp, leaf, row, hasFocus);

			Object obj = ((DefaultMutableTreeNode) value).getUserObject();
			//TODO: change icons?

			return this;
		}
	}
}
