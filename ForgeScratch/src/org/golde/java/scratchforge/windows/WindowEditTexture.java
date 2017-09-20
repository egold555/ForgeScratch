package org.golde.java.scratchforge.windows;

import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.SwingConstants;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;

import org.golde.java.scratchforge.Main;
import org.golde.java.scratchforge.helpers.JavaHelper;
import org.golde.java.scratchforge.helpers.PLog;
import org.golde.java.scratchforge.mod.Mod;
import org.golde.java.scratchforge.mod.Mod.Texture;

public class WindowEditTexture extends JFrame {

	private static final long serialVersionUID = -8302074072215275295L;
	
	private JTree tree;
	private Main main;
	
	public WindowEditTexture(Main main) {
		this.main = main;
		File forgeModsIn = new File(main.forge_folder, "src\\main\\java\\org\\golde\\forge\\scratchforge\\mods");
		
		this.setResizable(false);
		getContentPane().setLayout(null);
		
		tree = new JTree(populateTreeView(forgeModsIn));
		tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		
		tree.addMouseListener(new MouseAdapter() {
	        public void mouseClicked(MouseEvent e) {
	            if (e.getClickCount() == 2) {
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
		
		JScrollPane treeView = new JScrollPane(tree);
		treeView.setBounds(12, 39, 420, 213);
		
		getContentPane().add(treeView);
		
		
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
			PLog.info("Textures: " + mod.getTextures().length);
			for(Texture texture:mod.getTextures()) {
				DefaultMutableTreeNode textureNode = new DefaultMutableTreeNode(texture.getDisplayName());
				textureNode.setUserObject(texture);
				modNode.add(textureNode);
			}
			top.add(modNode);
		}
		
		return top;
	}

}
