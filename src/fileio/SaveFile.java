package fileio;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import finalvariable.BasicPathVariable;

public class SaveFile{
	private String fileroot;
	private String keyword;
	private String content;
	private String author;
	public void save(String filename,String fileauthor,String filevalue){
	try{
		   keyword=filename;
		   author=fileauthor;
		   fileroot=BasicPathVariable.processPath+author;
		   File file=new File(fileroot);
		   if(!file.exists()){
			   file.mkdir();
		   }
		   fileroot=fileroot+"//"+keyword;
		   content=filevalue;
		   BufferedOutputStream fout=new BufferedOutputStream(new FileOutputStream(fileroot+".owl"));
		   byte bb[]=content.getBytes();
		   fout.write(bb);
		   fout.close();
	}catch(IOException ioe){
		System.err.println(ioe);
	}
	}

}
