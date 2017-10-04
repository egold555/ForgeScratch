package org.golde.scratchforge.installer.helpers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class JavaHelpers {

	public static void downloadZip(String urlSite, File file) throws Exception{
		URL url = new URL(urlSite);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("GET");
		InputStream in = connection.getInputStream();
		FileOutputStream out = new FileOutputStream(file);
		copyFile(in, out, 1024);
		out.close();
	}

	public static void copyFile(InputStream input, OutputStream output, int bufferSize) throws IOException {
		byte[] buf = new byte[bufferSize];
		int n = input.read(buf);
		while (n >= 0) {
			output.write(buf, 0, n);
			n = input.read(buf);
		}
		output.flush();
	}

	public static void unZipIt(File zipFile, String outputFolder) throws Exception {

		byte[] buffer = new byte[1024];



		//create output directory is not exists
		File folder = new File(outputFolder);
		if(!folder.exists()){
			folder.mkdir();
		}

		//get the zip file content
		ZipInputStream zis =
				new ZipInputStream(new FileInputStream(zipFile));
		//get the zipped file list entry
		ZipEntry ze = zis.getNextEntry();

		while(ze!=null){

			String fileName = ze.getName();
			File newFile = new File(outputFolder + File.separator + fileName);

			//create all non exists folders
			//else you will hit FileNotFoundException for compressed folder
			new File(newFile.getParent()).mkdirs();

			FileOutputStream fos = new FileOutputStream(newFile);

			int len;
			while ((len = zis.read(buffer)) > 0) {
				fos.write(buffer, 0, len);
			}

			fos.close();
			ze = zis.getNextEntry();
		}

		zis.closeEntry();
		zis.close();
	}

}
