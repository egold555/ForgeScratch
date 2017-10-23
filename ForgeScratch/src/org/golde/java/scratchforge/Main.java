package org.golde.java.scratchforge;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
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
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.golde.java.scratchforge.JSFunctions.EnumToast;
import org.golde.java.scratchforge.helpers.JavaHelper;
import org.golde.java.scratchforge.helpers.PLog;
import org.golde.java.scratchforge.mod.ModManager;
import org.golde.java.scratchforge.windows.WindowEditTexture;
import org.golde.java.scratchforge.windows.WindowProgramOptions;
import org.golde.java.scratchforge.windows.WindowToggleMods;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.web.PopupFeatures;
import javafx.scene.web.PromptData;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import netscape.javascript.JSObject;

/**
 * Main program class
 * @author Eric
 *
 */
public class Main implements ActionListener, KeyListener{

	public final String VERSION = "1.0";

	private static Main INSTANCE;

	//File for JSObject window to communicate functions to
	public JSFunctions jsFunctions; 

	//Config manager. 
	//Makes a properties file and simple saving and loading settings
	public Config config = new Config();

	private WindowProgramOptions windowProgramOptions = new WindowProgramOptions();

	//Forge directory
	public File forge_folder = new File("forge");

	//Main JFame
	private JFrame frame = new JFrame("ScratchForge"); 

	//Menu bar for JFrame
	private JMenuBar menuBar = new JMenuBar(); 

	//Everything to put under the "File" button in the menu bar
	private JMenuItem mFileNew = new JMenuItem("New");
	private JMenuItem mFileOpen = new JMenuItem("Open");
	private JMenuItem mFileSaveItem = new JMenuItem("Save");
	private JMenuItem mFileSaveAs = new JMenuItem("Save As");
	private JMenuItem mFileExit = new JMenuItem("Exit");

	//Everything to put under the "Options - Program" button in the menu bar
	private JMenuItem mOptionsProgram = new JMenuItem("Program Options");

	//Everything to put under the "Options - Mod" button in the menu bar
	private JMenuItem mOptionsModTextures = new JMenuItem("Textures");
	private JMenuItem mOptionsModManager = new JMenuItem("Mod Manager");

	//Everything to put under the "Help" button in the menu bar
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

	public boolean offlineMode = false;

	private WebEngine webEngine;

	public static void main(String[] args) {
		//Run things after everything, also non static :)
		SwingUtilities.invokeLater(new Main()::initAndShowGUI); 
	}

	//Start creation of everything
	void initAndShowGUI() {
		INSTANCE = this;
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			//UIManager.setLookAndFeel("com.jtattoo.plaf.acryl.AcrylLookAndFeel");
		} catch (Exception e) {
			PLog.error(e, "Failed to set look and feel");;
		}

		//Offline mode to prevent gradlew from erroring
		offlineMode = !JavaHelper.isConnectedToTheInternet();
		if(offlineMode) {PLog.info("Offline mode detected.");}



		// This has to be called after "forge_folder" is initialized.
		modManager = new ModManager();

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
		fxPanel.addKeyListener(this);

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

		//Make the menu button "Examples" and add elements to it
		JMenu exampleMenu = new JMenu("Examples");
		File examplesDir = new File("examples");
		for(File f:JavaHelper.listFoldersInFolder(examplesDir)) {
			JMenuItem category = new JMenu(f.getName());
			for(File sub:JavaHelper.listFilesForFolder(f)) {
				JMenuItem item = new JMenuItem(sub.getName());
				item.addActionListener(this);
				category.add(item);
			}
			category.addActionListener(this);
			exampleMenu.add(category);
		}



		menuBar.add(exampleMenu); //TODO Make examples
		 
		//Make the menu button "Options" and add elements to it
		JMenu mOptionsMenu = new JMenu("Options");

		//Make the menu button "Mod Options" and add elements to it
		JMenu mOptionsMod = new JMenu("Mod Options");
		mOptionsMod.add(mOptionsModTextures);
		mOptionsMod.add(mOptionsModManager);
		mOptionsModTextures.addActionListener(this);
		mOptionsModTextures.setEnabled(true);
		mOptionsModManager.addActionListener(this);
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

		//frame.addKeyListener(new KeyboardListener());

		Platform.runLater(() -> {
			fxPanel.setScene(createScene());
		});

		frame.setIconImage(Toolkit.getDefaultToolkit().getImage("icon.png"));

		startupDialog();
	}

	//Setup the web scene and JSObject
	Scene createScene() {
		StackPane root = new StackPane();
		Scene scene = new Scene(root);
		WebView  webView = new WebView();
		webView.setContextMenuEnabled(false);
		webEngine = webView.getEngine();
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
							jsFunctions = new JSFunctions();
							window.setMember("java_app", jsFunctions);
							Platform.runLater(() -> {
								PLog.info("Checking updates...");
								checkUpdate();
							});
						}


					}
				});


		// Open new windows in a new WebView.
		webEngine.setCreatePopupHandler(new Callback<PopupFeatures, WebEngine>() {

			@Override
			public WebEngine call(PopupFeatures p) {
				Stage stage = new Stage(StageStyle.UTILITY);
				WebView wv2 = new WebView();
				stage.setScene(new Scene(wv2));
				stage.show();
				return wv2.getEngine();
			}
		});

		webEngine.load(f.toURI().toString());
		root.getChildren().add(webView);
		return scene;
	}

	// Handle menu events
	@Override
	public void actionPerformed(ActionEvent e) {
		Platform.runLater(() -> {
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
			else if(source == mOptionsModManager) {
				//jsFunctions.showEnabledMods(frame);
				new WindowToggleMods();
			}
			else if(source == mOptionsProgram) {
				windowProgramOptions.showSettingsMenu();
			}
			else if(source == mOptionsModTextures) {

				new WindowEditTexture();
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

			if(source instanceof JMenuItem) {
				String name = ((JMenuItem)source).getText();
				if(name.endsWith(".blockmod")) {
					//Example
					PLog.info("Name: " + name);
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

			Platform.runLater(() -> {
				try {
					jsFunctions.load(XML);
					jsFunctions.showToast(EnumToast.SUCCESS, "Successfully loaded mod!");
				} catch (Exception e) {
					jsFunctions.showToast(EnumToast.ERROR_PROGRAM, "Failed to load mod!");
					PLog.error(e, "Failed to load blockMod!");
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
			Platform.runLater(() -> {
				webEngine.executeScript("Code.discard()");
			});


		}else {
			if(isStarting) {
				startupDialog();
			}
			return;
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {}

	@Override
	public void keyReleased(KeyEvent e) {
		Platform.runLater(() -> {
			if(e.isControlDown()) {
				int kc = e.getKeyCode();
				/*if(kc == KeyEvent.VK_R) { //TODO: Debug
					webEngine.reload();
				}*/
				if(kc == KeyEvent.VK_S) {
					saveFile(filename);
				}
				else if(kc == KeyEvent.VK_O) {
					loadFile(false);
				}
				else if(kc == KeyEvent.VK_N) {
					createMod(false);
				}
			}

		});
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	public static Main getInstance() {
		return INSTANCE;
	}

	private String hasUpdate = null;
	public void checkUpdate() {
		try {
			String officialVersion = JavaHelper.readResponceFromUrl("http://scratchforge.golde.org/version.php");
			String officialVersionDownload = JavaHelper.readResponceFromUrl("http://scratchforge.golde.org/version.php?dl=1");
			if(JavaHelper.isStringEmpty(officialVersion) || !officialVersion.contains(".")) {return;} //Should not ever happen but you never know
			if(!Main.getInstance().VERSION.equals(officialVersion)) {
				//Update avaiable
				hasUpdate = officialVersionDownload;
				jsFunctions.showToast(EnumToast.UPDATE, "Update " + officialVersion + " is avaiable to download. Click me to download and install the update.");
			}
		} 
		catch (Exception e) {
			PLog.error(e, "Failed to check for updates!");
			jsFunctions.showToast(EnumToast.ERROR_PROGRAM, "Failed to check for updates! Error code: " + e.getMessage());
		}
	}

	public void downloadUpdate() { 
		if(hasUpdate != null) {

		}
	}

}
