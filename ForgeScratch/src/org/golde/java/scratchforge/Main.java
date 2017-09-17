package org.golde.java.scratchforge;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.ButtonGroup;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.golde.java.scratchforge.Config.ConfigProperty;
import org.golde.java.scratchforge.helpers.JavaHelper;
import org.golde.java.scratchforge.helpers.PLog;
import org.golde.java.scratchforge.mod.ModManager;
import org.golde.java.scratchforge.windows.WindowEditTexture;
import org.golde.java.scratchforge.windows.WindowProgramOptions;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.layout.StackPane;
import javafx.scene.web.PromptData;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.util.Callback;
import netscape.javascript.JSObject;

/**
 * Main program class
 * @author Eric
 *
 */
public class Main implements ActionListener{

	public final String VERSION = "1.0 alpha";

	//File for JSObject window to communicate functions to
	public JSFunctions jsFunctions; 

	//Config manager. 
	//Makes a properties file and simple saving and loading settings
	public Config config = new Config();

	WindowProgramOptions windowProgramOptions = new WindowProgramOptions(config);

	//Forge directory
	public File forge_folder = new File("forge");

	//Main JFame
	private static JFrame frame = new JFrame("Forge Scratch"); 

	//Menu bar for JFrame
	private JMenuBar menuBar = new JMenuBar(); 

	//Every thing to put under the "File" button in the menu bar
	private JMenuItem mFileNew = new JMenuItem("New");
	private JMenuItem mFileOpen = new JMenuItem("Open");
	private JMenuItem mFileSaveItem = new JMenuItem("Save");
	private JMenuItem mFileSaveAs = new JMenuItem("Save As");
	private JMenuItem mFileExit = new JMenuItem("Exit");

	//Every thing to put under the "Options - Program" button in the menu bar
	private JMenuItem mOptionsProgram = new JMenuItem("Program Options");

	//Every thing to put under the "Options - Mod" button in the menu bar
	private JMenuItem mOptionsModTextures = new JMenuItem("Textures");
	private JMenuItem mOptionsModEnabled = new JMenuItem("Enabled Mods");
	private JMenuItem mOptionsModExport = new JMenuItem("Export Mod");

	//Every thing to put under the "Help" button in the menu bar
	private JMenuItem mHelpAbout = new JMenuItem("About");

	//File name for wither opening or closing. Set by "Open" or "Save As"
	private String filename = null;  

	//Simple final arguments for the file extension so I do not miss type it.
	private final String FILE_EXTENTION = "blockmod";
	private final String FILE_EXTENTION_DESCRIPTION = "Mod Save File";

	//The magical thing that communicates with the javascript portion
	public JSObject window; 

	//Default mod name, gets overwritten on new project creation
	public String MOD_NAME = "If you see this, something bad happened"; 

	public ModManager modManager;

	public static void main(String[] args) {
		//Run things after everything, also non static :)
		SwingUtilities.invokeLater(new Main()::initAndShowGUI); 
	}

	//Start creation of everything
	void initAndShowGUI() {
		// This has to be called after "forge_folder" is initialized.
		modManager = new ModManager(this);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		final JFXPanel fxPanel = new JFXPanel(){

			private static final long serialVersionUID = 5346914097448163759L;

			@Override
			public Dimension getPreferredSize() {
				return new Dimension(640, 480);
			}
		};

		frame.add(fxPanel, BorderLayout.CENTER);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

		//Make the menu button "File" and add elements to it
		JMenu fileMenu = new JMenu("File");
		fileMenu.add(mFileNew);
		fileMenu.add(mFileOpen);
		fileMenu.add(mFileSaveItem);
		fileMenu.add(mFileSaveAs);
		fileMenu.add(mFileExit);
		mFileNew.addActionListener(this);
		mFileOpen.addActionListener(this);
		mFileSaveItem.addActionListener(this);
		mFileSaveAs.addActionListener(this);
		mFileExit.addActionListener(this);
		menuBar.add(fileMenu);

		JMenu mOptionsMenu = new JMenu("Options");

		//Make the menu button "Mod Options" and add elements to it
		JMenu mOptionsMod = new JMenu("Mod Options");
		mOptionsMod.add(mOptionsModTextures);
		mOptionsMod.add(mOptionsModEnabled);
		mOptionsMod.add(mOptionsModExport);
		mOptionsModTextures.addActionListener(this);
		mOptionsModTextures.setEnabled(true);
		mOptionsModEnabled.addActionListener(this);
		mOptionsModExport.addActionListener(this);
		mOptionsModExport.setEnabled(false);
		mOptionsMenu.add(mOptionsMod);

		//Make the menu button "Program Options" and add elements to it
		mOptionsMenu.add(mOptionsProgram);
		mOptionsProgram.addActionListener(this);
		mOptionsProgram.setEnabled(true);

		//Add Options menu to menu bar
		menuBar.add(mOptionsMenu);

		//Make the menu button "Help" and add elements to it
		JMenu helpMenu = new JMenu("Help");
		helpMenu.add(mHelpAbout);
		mHelpAbout.addActionListener(this);
		//mHelpAbout.setEnabled(false);
		menuBar.add(helpMenu);

		frame.setJMenuBar(menuBar);

		Platform.runLater(() -> {
			fxPanel.setScene(createScene());
		});

		startupDialog();
	}

	//Setup the web scene and JSObject
	Scene createScene() {
		StackPane root = new StackPane();
		Scene scene = new Scene(root);
		WebView  webView = new WebView();
		webView.setContextMenuEnabled(false);
		WebEngine webEngine = webView.getEngine();
		File f = new File("html\\index.html");
		
		webEngine.setPromptHandler(new Callback<PromptData, String>() {
			
			@Override
			public String call(PromptData param) {
				return JOptionPane.showInputDialog(frame, param.getMessage());
			}
		});
		
		webEngine.getLoadWorker().stateProperty().addListener(
				new ChangeListener<Worker.State>() {
					public void changed(ObservableValue<? extends Worker.State> ov, Worker.State oldState, Worker.State newState) {
						if (newState == Worker.State.SUCCEEDED) {
							// web page is loaded.
							window = (JSObject) webEngine.executeScript("window");
							jsFunctions = new JSFunctions(Main.this);
							window.setMember("java_app", jsFunctions);
							
							//TODO: Better Dark Mode
							if(Boolean.valueOf(config.getString(ConfigProperty.DARK_MODE))) {
								webEngine.executeScript("var newSS,styles=\"* { background: #1A2424 ! important; color: cyan !important } :link, :link * { color: #66FF99 !important } :visited, :visited * { color: #9966CC !important }\";document.createStyleSheet?document.createStyleSheet(\"styles\"):((newSS=document.createElement(\"link\")).rel=\"stylesheet\",newSS.href=\"data:text/css,\"+escape(styles),document.getElementsByTagName(\"head\")[0].appendChild(newSS));");
							}
						}
					}
				});


		webEngine.load(f.toURI().toString());
		root.getChildren().add(webView);
		return scene;
	}

	// Handle menu events
	@Override
	public void actionPerformed(ActionEvent e) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				Object source = e.getSource();
				//File
				if (source == mFileNew) {
					//new mod
					createMod(false);
				}
				else if (source == mFileOpen) {
					loadFile(false);
				}
				else if (source == mFileSaveItem) {
					saveFile(filename);
				}
				else if (source == mFileSaveAs) {
					saveFile(null);
				}
				else if (source == mFileExit) {
					System.exit(0);
				}

				//Mod Options
				else if(source == mOptionsModEnabled) {
					jsFunctions.showEnabledMods(frame);
				}
				else if(source == mOptionsProgram) {
					windowProgramOptions.showSettingsMenu();
				}
				else if(source == mOptionsModTextures) {

					new WindowEditTexture(Main.this);
				}
				else if(source == mHelpAbout) {
					String[] aboutTextList = {
							"ScratchForge v" + VERSION,
							" ",
							"Created By Eric Golde",
							" ",
							"Special thanks to:",
							"  Peter Golde",
							"  Google - Blockly",
							"  Sri Harsha Chilakapati - Image Tool",
							"  Forge Mod Developers",
							"  Mojang"
					};
					String aboutText = JavaHelper.joinStrings(aboutTextList, "\n", 0);
					JOptionPane.showMessageDialog(null, aboutText, "About", JOptionPane.INFORMATION_MESSAGE);
				}


			}
		});

	}

	// Prompt user to enter filename and load file.  Allow user to cancel.
	// If file is not found, pop up an error and leave screen contents
	// and filename unchanged.
	private void loadFile(boolean isStarting) {
		JFileChooser fc = new JFileChooser();
		fc.setDialogType(JFileChooser.OPEN_DIALOG);
		fc.addChoosableFileFilter(new FileNameExtensionFilter(FILE_EXTENTION_DESCRIPTION, FILE_EXTENTION));
		fc.setAcceptAllFileFilterUsed(false);
		fc.setMultiSelectionEnabled(false);
		String name = null;
		if (fc.showOpenDialog(null) != JFileChooser.CANCEL_OPTION) {
			name = fc.getSelectedFile().getAbsolutePath();
		}
		else {
			if(isStarting) {
				startupDialog();
			}
			return;  // user cancelled

		}
		try {
			String XML = JavaHelper.readFile(new File(name));
			
			Platform.runLater(new Runnable() {
		        @Override
		        public void run() {
		        	jsFunctions.load(XML);
		        }
		   });
			
		}
		catch (Exception e) {
			PLog.error(e, "File not found: " + name);
			JOptionPane.showMessageDialog(frame, "File not found: " + name, "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	// Save named file.  If name is null, prompt user and assign to filename.
	// Allow user to cancel, leaving filename null.  Tell user if save is
	// successful.
	private void saveFile(String name) {
		if (name == null) {  // get filename from user
			JFileChooser fc = new JFileChooser();
			fc.setDialogType(JFileChooser.SAVE_DIALOG);
			fc.addChoosableFileFilter(new FileNameExtensionFilter(FILE_EXTENTION_DESCRIPTION, FILE_EXTENTION));
			fc.setAcceptAllFileFilterUsed(false);
			if (fc.showSaveDialog(null) != JFileChooser.CANCEL_OPTION) {
				name = fc.getSelectedFile().getAbsolutePath();
			}
		}
		if (name != null) {  // else user cancelled
			try {

				if(!name.endsWith("." + FILE_EXTENTION)) { //make sure users dont mess file extenton up
					name += "." + FILE_EXTENTION;
				}

				File file = new File(name);

				if (file.exists()) {
					int response = JOptionPane.showConfirmDialog(null, "Do you want to replace the existing file?", "Confirm", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
					if (response != JOptionPane.YES_OPTION) {
						return;
					} 
				}


				JavaHelper.writeFile(file, jsFunctions.saveXML());
				filename = name;

				JOptionPane.showMessageDialog(frame, "Saved to " + filename, "Save File", JOptionPane.PLAIN_MESSAGE);
			}
			catch (Exception e) {
				PLog.error(e, "Cannot write to file: " + name);
				JOptionPane.showMessageDialog(frame, "Cannot write to file: " + name, "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	/*private void showProgramArgsMenu() {
        JTextField field1 = new JTextField("-Xincgc -Xmx4G -Xms4G");
        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Arguments:"));
        panel.add(field1);
        int result = JOptionPane.showConfirmDialog(null, panel, "Program Argument Options", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {

        }
	}*/

	//Main dialog on startup
	public void startupDialog() {
		JRadioButton newProject = new JRadioButton("New");
		JRadioButton openProject = new JRadioButton("Open");

		newProject.setSelected(true);

		ButtonGroup bttnGroup = new ButtonGroup();
		bttnGroup.add(newProject);
		bttnGroup.add(openProject);

		JPanel panel = new JPanel(new GridLayout(0, 1));
		panel.add(newProject);
		panel.add(openProject);

		int result; 
		do  {
			result = JOptionPane.showConfirmDialog(frame, panel, "What would you like to do?", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
		}
		while(result == JOptionPane.CLOSED_OPTION); 

		if(newProject.isSelected()) {
			createMod(true);
		}else {
			loadFile(true);
		}
	}

	//Prompt screen to make a mod
	private void createMod(boolean isStarting) {
		JTextField field1 = new JTextField("");
		JPanel panel = new JPanel(new GridLayout(0, 1));
		panel.add(new JLabel("Mod Name: "));
		panel.add(field1);
		int result = JOptionPane.showConfirmDialog(frame, panel, "New Mod", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
		if (result == JOptionPane.OK_OPTION && !JavaHelper.isStringEmpty(field1.getText())) {
			MOD_NAME = field1.getText();
		}else {
			if(isStarting) {
				startupDialog();
			}
			return;
		}
	}

}
