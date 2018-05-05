package org.sklse.owlseditor.sparqldl;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import net.didion.jwnl.JWNL;
import net.didion.jwnl.JWNLException;
import net.didion.jwnl.data.IndexWord;
import net.didion.jwnl.data.Synset;
import net.didion.jwnl.data.Word;
import net.didion.jwnl.dictionary.Dictionary;

import org.junit.Test;
public class Jwnl {
	private static final String file="E:\\JavaProject\\BPEP\\jwnl\\jwnl14-rc2\\config\\file_properties.xml";
	static{
		try {
			JWNL.initialize(new FileInputStream(file));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (JWNLException e) {
			e.printStackTrace();
		}
	}
	public static HashSet<String> getWords(ArrayList<String> array){
	try{
		HashSet<String> result=new HashSet<String>();
		Dictionary dict = Dictionary.getInstance();
		HashSet<Synset> synsets = new HashSet<Synset>();
		for(String str : array){
			IndexWord[] indexWords=dict.lookupAllIndexWords(str).getIndexWordArray();
			for (IndexWord iw : indexWords){
				synsets.addAll(Arrays.asList(iw.getSenses()));
			}
		}
		
		for(Synset synset : synsets){
			Word[] words=synset.getWords();
			for(Word word:words){
				result.add(word.getLemma());
			}
		}
		for(String word : result){
			System.out.println(word);
		}
		return result;
	}catch(JWNLException e){
		e.printStackTrace();
	}
	return null;
	}
	@Test
	public void test() throws JWNLException{
		ArrayList<String> array=new ArrayList<String>();
		array.add("goods");
		//array.add("girl");
		HashSet<String> words=getWords(array);
//		for(String word : words){
//			System.out.println(word);
//		}
	}
}
