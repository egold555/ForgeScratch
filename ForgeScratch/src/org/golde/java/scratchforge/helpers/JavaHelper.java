package org.golde.java.scratchforge.helpers;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Formatter;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import org.golde.java.scratchforge.Main;
import org.golde.java.scratchforge.JSFunctions.EnumToast;

/**
 * A class to do functions that Java should have built in
 * @author Eric
 *
 */
public class JavaHelper {

	public static final Random RANDOM = new Random();

	//Opens up a cmd prompt and executes commands. 
	public static void runCMD(File dir, String cmd) throws Exception { //TODO: MAC
		Process p = Runtime.getRuntime().exec("cmd.exe /c cd \"" + dir.getAbsolutePath() + "\" & start \"Console\" /wait cmd.exe /c \"" + cmd + "\"");
		//Main.getInstance().jsFunctions.showToast(EnumToast.INFO, "Minecraft is now running. Please close Minecraft to continue.");
		p.waitFor();
		if(doesFileContainString(new File(Main.getInstance().forge_folder, ".gradle\\gradle.log"), "FAILURE: Build failed with an exception.")) {
			Main.getInstance().jsFunctions.showToast(EnumToast.ERROR_BLOCKS, "Minecraft failed to build. Please double check all of your functions!");
		}
		PLog.info("Exit Code: " + p.waitFor());
	}	


	//Write file to system
	public static void writeFile(File file, String text) throws FileNotFoundException, UnsupportedEncodingException {
		Formatter out = new Formatter(file, "UTF-8");  // might fail
		out.format("%s", text);
		out.close();
	}

	//Read file from system
	public static String readFile(File file) throws FileNotFoundException, UnsupportedEncodingException {
		Scanner in = new Scanner(file, "UTF-8");
		String result = "";
		while (in.hasNext()) {
			result = result + in.nextLine() + "\n";
		}
		in.close();
		return result;
	}

	static List<File> files;
	//List all files in folder. This works for nested files
	public static List<File> listFilesForFolder(final File folder) {
		files = new ArrayList<File>();
		File[] listOfFiles = folder.listFiles();
		if(listOfFiles == null) {
			PLog.error("Directory " + folder.getAbsolutePath() + " is missing!!! Making folder and trying again...");
			folder.mkdir();
			return listFilesForFolder(folder);
		}
		for (final File fileEntry : listOfFiles) {
			if (fileEntry.isDirectory()) {
				listFilesForFolder(fileEntry);
			} else {
				files.add(fileEntry);
			}
		}
		return files;
	}

	public static List<File> listFoldersInFolder(final File folder){
		files = new ArrayList<File>();
		File[] listOfFiles = folder.listFiles();
		if(listOfFiles == null) {
			PLog.error("Directory " + folder.getAbsolutePath() + " is missing!!! Making folder and trying again...");
			folder.mkdir();
			return listFilesForFolder(folder);
		}
		for (final File fileEntry : listOfFiles) {
			if (fileEntry.isDirectory()) {
				files.add(fileEntry);
			}
		}
		return files;
	}

	public static boolean isStringEmpty(String s) {

		if(s == null) {return true;}
		if(s.equalsIgnoreCase("")) {return true;}
		if(s.equalsIgnoreCase(" ")) {return true;}

		return false;
	}

	public static void editFileWithDefaultProgram(File file) {
		try {
			Desktop.getDesktop().edit(file);
		} catch (Exception e) {
			PLog.error(e, "Failed to edit file!");
		}
	}

	/*public static String base64EncodeImage(BufferedImage image) {
		String imageString = null;
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			ImageIO.write(image, "png", bos);
			byte[] imageBytes = bos.toByteArray();

			imageString = base64Encode(imageBytes);

			bos.close();
		} catch (IOException e) {
			PLog.error(e, "Failed to convert image to base64");
		}
		return imageString;
	}*/

	public static String base64EncodeFile(File file) {
		String imageString = null;
		try {
			imageString = base64Encode(Files.readAllBytes(file.toPath()));
		} catch (IOException e) {
			PLog.error(e, "Failed to convert image to base64");
		}
		return imageString;
	}


	public static String base64Encode(byte[] toEncode) {
		return Base64.getEncoder().encodeToString(toEncode);
	}

	public static void base64DecodeFile(String data, File file)
	{
		try {
			Files.write(file.toPath(), base64Decode(data), StandardOpenOption.CREATE);
		} catch (IOException e) {
			PLog.error(e, "Failed to write file");
		}
	}

	/*public static BufferedImage decodeImage(String data) {
		 BufferedImage image = null;
	        byte[] imageByte;
	        try {
	            imageByte = base64Decode(data);
	            ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
	            image = ImageIO.read(bis);
	            bis.close();
	        } catch (Exception e) {
	        	PLog.error(e, "Failed to read Base64 image!");
	        }
	        return image;
	}*/

	public static byte[] base64Decode(String data) {
		return Base64.getDecoder().decode(data);
	}



	public static String makeJavaId(String name) {
		String result = "";
		for (int i = 0; i < name.length(); ++i) {
			char c = name.charAt(i);
			if (isJavaId(c)) {
				result = result + c;
			}
			else {
				result = result + "_";
			}
		}

		return result.toLowerCase();
	}

	public static boolean isJavaId(char c) {
		if (c >= 'A' && c <= 'Z')
			return true;
		else if (c >= 'a' && c <= 'z') 
			return true;
		else if (c >= '0' && c <= '9') 
			return true;

		return false;
	}

	public static String joinStrings(String[] aboutTextList, String conjunction, int iequals)
	{
		StringBuilder sb = new StringBuilder();
		boolean first = true;
		for(int i = iequals; i < aboutTextList.length; ++i)
		{
			String item = aboutTextList[i];

			if (first)
				first = false;
			else
				sb.append(conjunction);
			sb.append(item);
		}
		return sb.toString();
	}

	public static void copyEverythingInAFolder(File source, File destination)
	{
		if (source.isDirectory())
		{
			if (!destination.exists())
			{
				destination.mkdirs();
			}

			String files[] = source.list();

			for (String file : files)
			{
				File srcFile = new File(source, file);
				File destFile = new File(destination, file);

				copyEverythingInAFolder(srcFile, destFile);
			}
		}
		else
		{
			InputStream in = null;
			OutputStream out = null;

			try
			{
				in = new FileInputStream(source);
				out = new FileOutputStream(destination);

				byte[] buffer = new byte[1024];

				int length;
				while ((length = in.read(buffer)) > 0)
				{
					out.write(buffer, 0, length);
				}

				in.close();
				out.close();

			}
			catch (Exception e)
			{
				PLog.error(e, "Failed to copy folder!");
			}
		}
	}

	public static boolean deleteDirectory(File dir) { 
		if (dir.isDirectory()) { 
			File[] children = dir.listFiles(); 
			for (int i = 0; i < children.length; i++) { 
				boolean success = deleteDirectory(children[i]); 
				if (!success) {
					return false;
				}
			} 
		} // either file or an empty directory 
		return dir.delete(); 
	}

	public static void deleteSelectedItems(JTree tree) {
		DefaultMutableTreeNode node;
		DefaultTreeModel model = (DefaultTreeModel) (tree.getModel());
		TreePath[] paths = tree.getSelectionPaths();
		for (int i = 0; i < paths.length; i++) {
			node = (DefaultMutableTreeNode) (paths[i].getLastPathComponent());
			model.removeNodeFromParent(node);
		}
	}

	public static boolean isConnectedToTheInternet() {
		try {
			URL obj = new URL("http://google.com/");
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			con.setRequestMethod("GET");
			con.setRequestProperty("User-Agent", "Mozilla/5.0");

			BufferedReader in = new BufferedReader(
					new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();

			return true;

		}
		catch(Exception e) {
			return false;
		}
	}

	public static InputStream stringToInputStream(String s) throws UnsupportedEncodingException {
		return new ByteArrayInputStream(s.getBytes(StandardCharsets.UTF_8.name()));
	}

	public static boolean doesFileContainString(File file, String toFind) throws FileNotFoundException {
		Scanner scanner = new Scanner(file);
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			if(line.contains(toFind)) { 
				scanner.close();
				return true;
			}
		}
		scanner.close();
		return false;
	}

	public static String readResponceFromUrl(String url) throws Exception {
		URL website = new URL(url);
		URLConnection connection = website.openConnection();
		BufferedReader in = new BufferedReader(
				new InputStreamReader(
						connection.getInputStream()));

		StringBuilder response = new StringBuilder();
		String inputLine;

		while ((inputLine = in.readLine()) != null) 
			response.append(inputLine);

		in.close();

		return response.toString();
	}
}
