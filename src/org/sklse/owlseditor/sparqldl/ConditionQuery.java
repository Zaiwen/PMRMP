package org.sklse.owlseditor.sparqldl;

/**Created by Liang Chen****/

import java.util.ArrayList;
import java.util.HashSet;

import org.junit.Test;

import edu.sussex.nlp.jws.JWS;
import edu.sussex.nlp.jws.Lin;

public class ConditionQuery {
	//private static String dir =ConditionQuery.class.getClassLoader().getResource("struts.xml").getPath().split("BPEP")[0]+"BPEP/wordnet2.1";//wordnet的路径
	private static String dir ="E://JavaProject//BPEP//wordnet2.1";

	private static JWS ws = new JWS(dir, "2.1");
	//private static JWS ws = new BPEPJWS(dir, "2.1");
	private static Lin lin = ws.getLin();

    public  static double SIMARITY_POINT=0.84;

	// 计算precondition相似度请调用该方法
	public static boolean findSimilarity(String src, String dest)
			throws Exception {
		if (findSimilarityResult(src,dest) >= SIMARITY_POINT) {
			return true;
		} else {
			return false;
		}
	}
	public static double findSimilarityResult1(String src,String dest){
		if(src.equalsIgnoreCase(dest)){
    		return 1.0;
    	}
        // 1.截取src字符串为集合
        ArrayList<String> srcArray = splitString(src);
        int srcLength = srcArray.size();
        // 2.将dest字符串截取为集合
        ArrayList<String> destArray = splitString(dest);
        int destLength = destArray.size();
        double sum = 0.0;
		String value1,value2;
		double result1=0.0,result2=0.0;
		if(srcLength<=destLength){
			for (int m = 0; m < srcArray.size(); ++m) {
	        	double temp=-1.0;
	        	value1=srcArray.get(m);
	            for (int n = 0; n < destArray.size(); ++n) {
					value2=destArray.get(n);
					result1=SemanticSimilarity(value1,value2);
					result2=SyntacticSimilarity(value1,value2);
	                temp=Math.max(temp,result1*0.5+result2*0.5);
	            }
	            System.out.println(temp);
	            sum+=temp;
	        }
		}else{
			for (int m = 0; m < destArray.size(); ++m) {
	        	double temp=-1.0;
	        	value1=destArray.get(m);
	            for (int n = 0; n < srcArray.size(); ++n) {
					value2=srcArray.get(n);
					result1=SemanticSimilarity(value1,value2);
					result2=SyntacticSimilarity(value1,value2);
	                temp=Math.max(temp,result1*0.5+result2*0.5);
	            }
	            System.out.println(temp);
	            sum+=temp;
	        }
		}
        
       sum = sum / Math.min(srcLength,destLength);
        // 4.按照公式计算相似度
        //double result=0.0;
        //result=((1.0 * 2 * retainLength) + (0.75 * sum * 2)) / (srcLength + destLength + 0.0);
        return (((int) ((sum + 0.005) * 100)) / 100.0);
	}
    @SuppressWarnings("unchecked")
	public static double findSimilarityResult(String src, String dest)
            throws Exception {
    	if(src.equalsIgnoreCase(dest)){
    		return 1.0;
    	}
        // 1.截取src字符串为集合
        ArrayList<String> srcArray = splitString(src);
        ArrayList<String> tempArray1 = (ArrayList<String>) srcArray.clone();
        int srcLength = srcArray.size();
        // 2.将dest字符串截取为集合
        ArrayList<String> destArray = splitString(dest);
        ArrayList<String> tempArray2 = (ArrayList<String>) destArray.clone();
        int destLength = destArray.size();
        // 3.去掉相同字符串
        destArray.removeAll(srcArray);
        srcArray.removeAll(tempArray2);
        tempArray1.retainAll(tempArray2);
        int retainLength = tempArray1.size();
        double sum = 0.0;
		String value1,value2;
		double result1=0.0,result2=0.0;
        for (int m = 0; m < destArray.size(); ++m) {
        	double temp=-1.0;
        	value1=destArray.get(m);
            for (int n = 0; n < srcArray.size(); ++n) {
				value2=srcArray.get(n);
				result1=SemanticSimilarity(value1,value2);
				result2=SyntacticSimilarity(value1,value2);
                temp=Math.max(temp,Math.max(result1,result2));
            }
            System.out.println(temp);
            sum+=temp;
        }
       // sum = sum / (srcLength + destLength + 0.0);
        // 4.按照公式计算相似度
        double result=0.0;
        result=((1.0 * 2 * retainLength) + (0.75 * sum * 2)) / (srcLength + destLength + 0.0);
        return (((int) ((result + 0.005) * 100)) / 100.0);
}
    
    public static double findSimilarityResult2(String value1,String value2){
    	if(value1 == null || value2 == null || value1.equals("") || value2.equals(""))
			return 0.0;
		if(value1.equalsIgnoreCase(value2)){
			return 1.0;
		}
		// 1.截取src字符串为集合
        ArrayList<String> srcArray = splitString(value1);
        ArrayList<String> tempArray1 = (ArrayList<String>) srcArray.clone();
        // 2.将dest字符串截取为集合
        ArrayList<String> destArray = splitString(value2);
        ArrayList<String> tempArray2 = (ArrayList<String>) destArray.clone();
        //destArray.removeAll(srcArray);
        //srcArray.removeAll(tempArray2);
        tempArray1.retainAll(tempArray2);
        int srcLength = srcArray.size();
        int destLength = destArray.size();
        int retainLength = tempArray1.size();
        HashSet<String> srcWords=Jwnl.getWords(srcArray);
        int sim12=getSimilarity(srcWords,destArray);
        HashSet<String> destWords=Jwnl.getWords(destArray);
        int sim21=getSimilarity(destWords,srcArray);
        double result=0.0;
        result=((1.0 *2 * retainLength) + (0.75 * (sim12+sim21))) / (srcLength + destLength + 0.0);
        return (((int) ((result + 0.005) * 100)) / 100.0);
    }
    
    
	// 计算两个字符串的语义相似度
	public static double SemanticSimilarity(String value1, String value2) {
		if(value1 == null || value2 == null || value1.equals("") || value2.equals(""))
			return 0.0;
		if(value1.equalsIgnoreCase(value2)){
			return 1.0;
		}
		double sc = getLin().max(value1, value2, "n");
		double temp=0.0;
		if (!(Math.abs(sc) >= 0.5)) {
			temp=getLin().max(value1, value2, "v");
			if(sc<temp){
				sc=temp;
			}
		}
		return (((int) ((sc + 0.005) * 100)) / 100.0);
	}
	
	
	// 计算两个字符串的语法相似度
	public static double SyntacticSimilarity(String value1, String value2) {
		if(value1 == null || value2 == null || value1.equals("") || value2.equals(""))
			return 0.0;
		if(value1.equalsIgnoreCase(value2)){
			return 1.0;
		}
		//矩阵D保存单词之间的距离
		int[][] D = new int[value1.length()+1][value2.length()+1];
		int i, j, d;
		//初始化
		for(i = 0; i <= value1.length(); i++)
			D[i][0] = i;
		for(j = 0; j <= value2.length(); j++)
			D[0][j] = j;
		//利用Floyd算法计算编辑距离
		for(i = 1; i<= value1.length(); i++){
			for(j = 1; j<=value2.length(); j++){
				if(value1.charAt(i - 1) == value2.charAt(j - 1))
					d = 0;
				else
					d = 1;
				
				D[i][j] = min(D[i-1][j-1] + d, D[i][j-1] + 1, D[i-1][j] + 1);
			}
		}
		//此时D[value1.length()][value2.length()]保存的就是value1与value2的编辑距离ed(value1,value2)
		int Max=0;
		if(value1.length() > value2.length())
			Max = value1.length();
		else
			Max = value2.length();
		//max保存两个字符串的长度的最大值
		//利用公式计算语法相似度
		double result=(1- (double)D[value1.length()][value2.length()]/Max);
		return (((int) ((result + 0.005) * 100)) / 100.0);
	}
	
	
	// 将字符串转化为集合,便于操作
	private static ArrayList<String> splitString(String str) {
		str = str.trim();
		String[] result = str.split("\\s+");
		ArrayList<String> temp = new ArrayList<String>();
		for (int i = 0; i < result.length; ++i) {
			temp.add(result[i]);
		}
		return temp;
	}
	
	//三个整数求最小值
	private static int min(int a, int b, int c)
	{
		int Min = a;
		if(b < Min)
			Min = b;
		if(c < Min)
			Min = c;
		
		return Min;
	}
	
	//获取set集合中在array集合中出现的字符串的个数
	private static int getSimilarity(HashSet<String> sets,ArrayList<String> arrays){
		int count=0;
		double result1=0.0,result2=0.0;
		double result=0.0;
		for(String array:arrays){
			result=0.0;
			for(String set:sets){
					result1=SemanticSimilarity(array,set);
					result2=SyntacticSimilarity(array,set);
					result=Math.max(result,Math.max(result1,result2));
					//result=Math.max(result,result1*0.5+result2*0.5);
					//result=Math.max(result,result1);
			}
			if(result>=0.84){
				++count;
			}
			//System.out.println(result);
		}
		System.out.println(count);
		return count;
	}
	private static Lin getLin() {
		return lin;
	}
	

	
	@Test
	public void test() throws Exception{
		System.out.println(SemanticSimilarity("item", "good"));
		System.out.println(SyntacticSimilarity("items", "goods"));
		System.out.println(SyntacticSimilarity("created", "generated"));
		//System.out.println(findSimilarityResult("client opens goods were created","customer open items were generated"));
		//System.out.println(findSimilarityResult2("client processing","customer process"));
		//System.out.println(findSimilarityResult2("client inquiries query processing","customer inquiry process"));
	}
}