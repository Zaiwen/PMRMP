package DataStructure;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import SimilarityCalculation.Similarity;

public class EPCTest {

	public static void main(String[] args) throws Exception{
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();  
        DocumentBuilder builder = factory.newDocumentBuilder();  
        Document document = builder.parse(new File("C:\\JAVAProject\\BPEP\\WebContent\\processManage\\epc\\AllEPCs.epml"));  
        Element element = document.getDocumentElement();  
        NodeList list1=element.getChildNodes();//epc elements
        List<BPG> bpg=new ArrayList<BPG>();
        for(int i=0;i<list1.getLength();i++){
        	List<Vertice> lv=new ArrayList<Vertice>();
        	List<Edge> le=new ArrayList<Edge>();
        	Node node1=list1.item(i);
        	if(node1.getNodeType()==Node.ELEMENT_NODE&&"epc".equals(node1.getNodeName())){
        		NodeList list2=node1.getChildNodes();
        		for(int j=0;j<list2.getLength();j++){
	        		Node node2=list2.item(j);
		        	if(node2.getNodeType()==Node.ELEMENT_NODE){
		        		String _id=node2.getAttributes().getNamedItem("id").getNodeValue();
		        		int id=Integer.parseInt(_id);
		        		String name=node2.getNodeName();
		        		if("event".equals(name)){
		        			lv.add(new Vertice(id,Type.Event,node2.getTextContent()));
		        		}else if("function".equals(name)){
		        			lv.add(new Vertice(id,Type.Function,node2.getTextContent()));
		        		}else if("and".equals(name)){
		        			lv.add(new Vertice(id,Type.And,"and"));
		        		}else if("or".equals(name)){
		        			lv.add(new Vertice(id,Type.Or,"or"));
		        		}else if("xor".equals(name)){
		        			lv.add(new Vertice(id,Type.Xor,"xor"));
		        		}else if("arc".equals(name)){
		        			NodeList list3=node2.getChildNodes();
		        			for(int k=0;k<list3.getLength();k++){
		        				Node node3=list3.item(k);
		        				if(node3.getNodeType()==Node.ELEMENT_NODE&&"flow".equals(node3.getNodeName())){
									String _source=node3.getAttributes().getNamedItem("source").getNodeValue();
									String _target=node3.getAttributes().getNamedItem("target").getNodeValue();
									int source=Integer.parseInt(_source);
									int target=Integer.parseInt(_target);
									le.add(new Edge(target,source,Type.Arc,"arc"));
									break;
		        				}
		        			}
						}
	        			
					}
		       	}
	        	bpg.add(new BPG(le,lv));
        	}
        }
        int sum=0;
        for(int i=0;i<bpg.size();i++){
        	for(int j=i+1;j<bpg.size();j++){
        		double s1=Similarity.StructuralSimilarity(bpg.get(i), bpg.get(j));
        		double s2=Similarity.BehavioralSimilarity(bpg.get(i), bpg.get(j));
        		double s3=Similarity.NodeMatchingSimilarity(bpg.get(i), bpg.get(j));
        	
        			sum++;
        			System.out.println(sum+"\t"+i+"-"+j+"\t"+s1+"\t"+s2+"\t"+s3);

        	}
        }
        System.out.println("Sum:"+sum);
        
	}

}
