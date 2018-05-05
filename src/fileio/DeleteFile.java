package fileio;

import java.io.*;

public class DeleteFile {
	public static boolean deletefile(String delpath){
		try {
			File file = new File(delpath);
			if (!file.isDirectory()) {
				file.delete();
			} else if (file.isDirectory()) {
				String[] filelist = file.list();
				for (int i = 0; i < filelist.length; i++) {
					File delfile = new File(delpath + "//" + filelist[i]);
					if (!delfile.isDirectory()) {
						delfile.delete();
					} else if (delfile.isDirectory()) {
						deletefile(delpath + "//" + filelist[i]);
					}
				}
				file.delete();
			}

		} catch (Exception e) {
			return false;
		}
		return true;
	}
}
