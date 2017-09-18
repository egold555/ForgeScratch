package org.golde.java.scratchforge.windows;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JCheckBox;
import javax.swing.JTextField;

import org.golde.java.scratchforge.Config;
import org.golde.java.scratchforge.Config.ConfigProperty;
import org.golde.java.scratchforge.Main;
import org.golde.java.scratchforge.helpers.JavaHelper;
import org.golde.java.scratchforge.helpers.PLog;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.awt.event.ActionEvent;

public class WindowProgramOptions extends JFrame{
	
	private static final long serialVersionUID = 2808329917591294729L;
	
	enum RAM{
		mb512("512 MB", 512),
		mb768("768 MB", 768),
		gb1("1 GB", 1000),
		gb1_5("1.5 GB", 1500),
		gb2("2 GB", 2000),
		gb2_5("2.5 GB", 2500),
		gb3("3 GB", 3000),
		gb3_5("3.5 GB", 3500),
		gb4("4 GB", 4000),
		gb5("5 GB", 5000),
		gb6("6 GB", 6000),
		gb7("7 GB", 7000),
		gb8("8 GB", 8000),
		gb9("9 GB", 9000),
		gb10("10 GB", 10000),
		gb11("11 GB", 11000),
		gb12("12 GB", 12000),
		gb13("13 GB", 13000),
		gb14("14 GB", 14000),
		gb15("15 GB", 15000)
		;
		
		public final String display;
		public final String jargs;
		RAM(String display, int jargs){
			this.display = display;
			this.jargs = "-Xmx" + jargs + "M";
		}
		
		public static String[] valuesDisplay() {
			int length = RAM.values().length;
			String[] result = new String[length];
			for(int i = 0; i < length; i++) {
				result[i] = RAM.values()[i].display;
			}
			return result;
		}
		
		public static RAM valueOfDisplay(String display) {
			for(RAM r:RAM.values()) {
				if(r.display.equals(display)) {
					return r;
				}
			}
			return null;
		}
	}
	
	private Config config;
	private JComboBox<String> comboBoxRam = new JComboBox<String>();
	private JCheckBox chckbxMultiplayerEnabled;
	private JCheckBox chckbxMultiplayerLimited;
	private JTextField textField;
	
	public WindowProgramOptions(Main main, Config config) {
		this.config = config;
		JPanel box1 = new JPanel();
		box1.setBounds(12, 13, 191, 144);
		box1.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), "Minecraft"));
		getContentPane().add(box1);
		box1.setLayout(null);
		
		JPanel box2 = new JPanel();
		box2.setBounds(215, 13, 255, 78);
		box2.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), "Java"));
		getContentPane().add(box2);
		box2.setLayout(null);
		
		textField = new JTextField();
		textField.setEnabled(false);
		textField.setBounds(12, 42, 231, 22);
		box2.add(textField);
		textField.setColumns(10);
		
		JLabel lblExtraJavaArgs = new JLabel("Extra Java Args");
		lblExtraJavaArgs.setBounds(66, 13, 102, 16);
		lblExtraJavaArgs.setEnabled(false);
		box2.add(lblExtraJavaArgs);
		box2.setEnabled(false);
		
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		getContentPane().setLayout(null);
		
		
		comboBoxRam.setModel(new DefaultComboBoxModel<String>(RAM.valuesDisplay()));
		comboBoxRam.setSelectedIndex(8);
		comboBoxRam.setBounds(48, 27, 85, 22);
		box1.add(comboBoxRam);
		
		JLabel lblRam = new JLabel("Ram:");
		lblRam.setBounds(12, 30, 39, 16);
		box1.add(lblRam);
		
		chckbxMultiplayerEnabled = new JCheckBox("MultiPlayer Enabled");
		chckbxMultiplayerEnabled.setBounds(22, 58, 149, 25);
		box1.add(chckbxMultiplayerEnabled);
		
		chckbxMultiplayerLimited = new JCheckBox("MultiPlayer Limited");
		chckbxMultiplayerLimited.setBounds(22, 92, 149, 25);
		box1.add(chckbxMultiplayerLimited);
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), "Quick Fixes"));
		panel.setBounds(215, 104, 255, 78);
		getContentPane().add(panel);
		
		JButton btnCleanProject = new JButton("Clean Project");
		btnCleanProject.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					JavaHelper.runCMD(main.forge_folder, "gradlew clean", false);
				} catch (IOException e) {
					PLog.error(e, "Failed to clean project!");
				}
			}
		});
		btnCleanProject.setBounds(12, 29, 124, 25);
		panel.add(btnCleanProject);
		
		/*JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), "Other"));
		panel.setBounds(12, 219, 191, 144);
		getContentPane().add(panel);*/
		
		/*JPanel panel_1 = new JPanel();
		panel_1.setLayout(null);
		panel_1.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), "Other"));
		panel_1.setBounds(279, 219, 191, 144);
		getContentPane().add(panel_1);*/
		
		this.setResizable(false);
		
		addWindowListener(new java.awt.event.WindowAdapter() {
		    @Override
		    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
		        saveAndCloseSettingsMenu();
		    }
		});
		
		
	}
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(500, 423);
	}
	
	public void showSettingsMenu() {
		try {
			comboBoxRam.setSelectedIndex(config.getInt(ConfigProperty.MCRAM));
			chckbxMultiplayerEnabled.setSelected(config.getBoolean(ConfigProperty.CLIENT_MULTIPLAYER_ENABLED));
			chckbxMultiplayerLimited.setSelected(config.getBoolean(ConfigProperty.CLIENT_MULTIPLAYER_LIMITED));
		}
		catch(Exception e) {
			PLog.error(e, "Failed to load config file. I Don't think they have saved any settings yet");
		}
		pack();
		setVisible(true);
	}
	
	public void saveAndCloseSettingsMenu() {
		config.setInt(ConfigProperty.MCRAM, comboBoxRam.getSelectedIndex());
		config.setBoolean(ConfigProperty.CLIENT_MULTIPLAYER_ENABLED, chckbxMultiplayerEnabled.isSelected());
		config.setBoolean(ConfigProperty.CLIENT_MULTIPLAYER_LIMITED, chckbxMultiplayerLimited.isSelected());
		setVisible(false);
	}
	
	public RAM getSelectedRam() {
		return RAM.valueOfDisplay((String)comboBoxRam.getSelectedItem());
	}
}
