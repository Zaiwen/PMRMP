package change4epml;
import nl.tue.tm.is.epc.*;
import ontology.IdWorker;
import org.sklse.processRegister.db.dto.*;
import org.sklse.processRegister.db.dto.JoinDependencyDTO.JoinType;
import org.sklse.processRegister.db.dto.SplitDependencyDTO.SplitType;
import org.sklse.processRegister.processGraph.*;

import java.util.Set;

public class EpmlMapping {
	
	private static int count = 0;


		public static ProcessGraphTemp mapping(EPC epc) throws Exception{
			ProcessGraphTemp pgt = new ProcessGraphTemp();
			
			//检查EPC语法是否合理
			CheckEpcRule(epc);
			
			//Map arc into Edge
			MapArc(epc,pgt);
			
			//Map Function into ProcessNode
			MapFunction(pgt,epc);

			//Map Connectors into Spilt/JoinNode
			MapConnector(pgt,epc);
			
			//Map Events into EventNode/SequenceDependency/Spilt/JoinOptionNode
			MapEvent(pgt,epc);
			
			System.out.println("Mapping Num : " + count);
			
			//  NullNode 与 OptionNode 的处理 唯一ID的分配，边的管理,Event信息的还原
			IdWorker.getNextId();
			
			//维护     node - Null - node 边的关系
			System.out.println("maintain Node - Null - Node relation  -------------------------");
			ProcessGraphTemp pgt1 = maintainNullNode(pgt);
			
			
			//make optionNode and edge,and put them in new Graph
			System.out.println("make optionNode and edge  -------------------------");
			ProcessGraphTemp pgt2 = MakeOptionNode(pgt1);
			
			//change old node ID ,and put it into new Graph
			System.out.println("assing uniqueID  -------------------------");
			ProcessGraphTemp pgt3 = assingUinqueId(pgt2);
		
			//restore eventinfo in Join/SplitDependencyNode
			System.out.println("restore eventinfo in Join/SplitDependencyNode  -------------------------");
			convertEventInfoInConnector(pgt3);
			
			//Condition String 2 Object
			pgt3.Condition2Object();
			
			//ParamObject 2 String
			pgt3.ParamObject2String();
			return pgt3;
		}
		
		private static boolean CheckEpcRule(EPC epc) throws Exception {
			// TODO Auto-generated method stub
			for(Connector c : epc.getConnectors()){
				checkConnectorRule(c,epc);
			}
			for(Event e : epc.getEvents()){
				checkEventRule(e,epc) ;
			}
			return true;
		}

		private static boolean checkConnectorRule(Connector c, EPC epc) throws Exception {
			if(epc.getPre(c).size() > 1 && epc.getPost(c).size() > 1){
				throw new Exception("Error Scheme : Connector output > 1 and input > 1 ");
			}
				
				
			if(epc.getPre(c).size() == 1 && epc.getPost(c).size() == 1) {
				throw new Exception("Error Scheme : Connector output = 1 and input = 1 ");
				
			}
			return true;
				
			
		}

		private static void MapArc(EPC epc, ProcessGraphTemp pgt) {
			
			Set<Arc> arcs = epc.getArcs();
			for(Arc a : arcs){
				Edge ed = new Edge();
				ed.setSrcid((long)Integer.valueOf(a.getSource().getId()));
				//System.out.println("srcid : " + a.getSource().getJoinid());
				ed.setTgtid((long)Integer.valueOf(a.getTarget().getId()));
				//System.out.println("tgtid : " + a.getTarget().getJoinid());
				//System.out.println("-----------");
				ed.setId(Integer.valueOf(a.getId()));
				ed.setOriginalId(Long.parseLong(a.getId()));
				pgt.addEdge(ed);
			}
			
		}

		private static void MapEvent(ProcessGraphTemp pgt, EPC epc) throws Exception {
			Set<Event> evt = epc.getEvents();
			for(Event e : evt){
				if(e.getId().equalsIgnoreCase("1")) System.out.println("1111");
				Node pre,post;
				if(epc.getPre(e).size() != 1) pre = null;
				else  pre = epc.getPre(e).iterator().next() ;
				if(epc.getPost(e).size() != 1) post = null;
				else  post = epc.getPost(e).iterator().next() ;
				if(pre instanceof Function && post instanceof Function){//func - event - func
					count ++;
					System.out.println("----Sequence Mapping -----");
					((ProcessNode)pgt.getNode(pre.getId())).dto().setPostconditions("#" + e.getName() + "#");
					((ProcessNode)pgt.getNode(post.getId())).dto().setPreconditions("#" + e.getName() + "#");
					SequenceDependencyDTO seqdto = new SequenceDependencyDTO();
					seqdto.setId((long)Integer.valueOf(e.getId()));
					seqdto.setFollowing((long)Integer.valueOf(post.getId()));
					seqdto.setPreceding((long)Integer.valueOf(pre.getId()));
					//seqdto.setName(e.getName());
					SequenceDependencyNode seqn = new SequenceDependencyNode(seqdto);
					seqn.setOriginalId(Long.parseLong(e.getId()));
					pgt.addNode(seqn);
					
				}
				else if(pre == null && post instanceof Function){// null - event - func
					count ++;
					System.out.println("----Event Mapping -----");
					((ProcessNode)pgt.getNode(post.getId())).dto().setPreconditions("#" + e.getName() + "#");
					EventDTO edto = new EventDTO();
					edto.setId((long)Integer.valueOf(e.getId()));
					edto.setName(e.getName());
					EventNode en = new EventNode(edto);
					en.setOriginalId(Long.parseLong(e.getId()));
					//System.out.println("EventID: " + en.getJoinid());
					pgt.addNode(en);
					
				}
				
				else if(pre instanceof Function && post == null ){//  func - event - null
					count ++;
					System.out.println("----Event Mapping -----");
					((ProcessNode)pgt.getNode(pre.getId())).dto().setPostconditions("#" + e.getName() + "#");
					EventDTO edto = new EventDTO();
					edto.setId((long)Integer.valueOf(e.getId()));
					edto.setName(e.getName());
					EventNode en = new EventNode(edto);
					en.setOriginalId(Long.parseLong(e.getId()));
					pgt.addNode(en);
					
				}
				
				else if(pre instanceof Connector && !(post instanceof Connector)){//Split connector - event 
					if(pgt.getNode(pre.getId()) instanceof SplitDependencyNode){
						if(post == null){ //Split - event -null
							//此处SplitDependencyOption未配套，在分配ID时再补上
							//这里这样做是为了避免由于增添元素导致边发生变化，省去维护成本
							System.out.println("----Split - event - Null  -----");
							count ++;
							EventDTO edto = new EventDTO();
							edto.setId(Long.parseLong(e.getId()));
							edto.setName(e.getName());
							EventNode en = new EventNode(edto);
							en.setOriginalId(Long.parseLong(e.getId()));
							pgt.addNode(en);
							//to do 改进 ： 把EventInfo统一放在Connector里面，然后回溯拾起沿路的EventInfo与Boolean ，存到最近的ProcessNode;
							setPostconditionBeforeSplit(pre.getId(),pgt,e.getName());
							
						}else if(post instanceof Function){
							System.out.println("----Split - event - Func  -----");
							count ++;
							setPostconditionBeforeSplit(pre.getId(),pgt,e.getName());
							((ProcessNode)pgt.getNode(post.getId())).dto().setPreconditions("#" + e.getName() + "#");
							SplitDependencyOptionDTO sdodto = new SplitDependencyOptionDTO();
							sdodto.setSplitid(Long.parseLong(pre.getId()));
							sdodto.setId(Long.parseLong(e.getId()));
							sdodto.setCondition(e.getName());
							sdodto.setFollowing(Long.parseLong(post.getId()));
							SplitDependencyOptionNode sdon = new SplitDependencyOptionNode(sdodto);
							sdon.setOriginalId(Long.parseLong(e.getId()));
							pgt.addNode(sdon);
							
						}
						
					}else if(pgt.getNode(pre.getId()) instanceof JoinDependencyNode){ //Join - event 
					
						if(post == null){
							System.out.println("----Join - event - Null  -----");
							count++;
							EventDTO edto = new EventDTO();
							edto.setId(Long.parseLong(e.getId()));
							edto.setName(e.getName());
							EventNode en = new EventNode(edto);
							en.setOriginalId(Long.parseLong(e.getId()));
							pgt.addNode(en);
							((JoinDependencyNode)pgt.getNode(pre.getId())).dto().setParam(e.getName());//event信息保存在JoinDependency
						}else if(post instanceof Function){//Join - event -Func
							count++;
							System.out.println("----Join - event - Func  -----");
							NullNode nn = new NullNode();
							nn.setPreid(Long.parseLong(pre.getId()));
							nn.setPostid(Long.parseLong(post.getId()));
							nn.setId(Long.parseLong(e.getId()));
							pgt.addNode(nn);
							((ProcessNode)pgt.getNode(post.getId())).dto().setPreconditions("#" + e.getName() + "#");
							((JoinDependencyNode)pgt.getNode(pre.getId())).dto().setParam(e.getName());//event信息保存在JoinDependency
						}
						
					}
				}else if(post instanceof Connector && !(pre instanceof Connector)){// event - Join connector
					if(pgt.getNode(post.getId()) instanceof JoinDependencyNode){
						if(pre == null){//null - Event - Join
							System.out.println("----Null - event - Join  -----");
							count++;
							//TODO 考虑后面的连接符
							setPreconditionAfterJoin(post.getId(),pgt,e.getName());
							EventDTO edto = new EventDTO();
							edto.setId(Long.parseLong(e.getId()));
							edto.setName(e.getName());
							EventNode en = new EventNode(edto);
							en.setOriginalId(Long.parseLong(e.getId()));
							pgt.addNode(en);
						
						}else if(pre instanceof Function){//Func - event - Join
							System.out.println("----Func - event - Join  -----");
							((ProcessNode)pgt.getNode(pre.getId())).dto().setPostconditions("#" + e.getName() + "#");
							setPreconditionAfterJoin(post.getId(),pgt,e.getName());
							count ++;
							JoinDependencyOptionDTO jdodto = new JoinDependencyOptionDTO();
							jdodto.setJoinid(Long.parseLong(post.getId()));
						    jdodto.setPreceding(Long.parseLong(pre.getId()));
							jdodto.setId(Long.parseLong(e.getId()));
							jdodto.setCondition(e.getName());
							JoinDependencyOptionNode jdon = new JoinDependencyOptionNode(jdodto);
							jdon.setOriginalId(Long.parseLong(e.getId()));
							pgt.addNode(jdon);
						}
					}else if(pgt.getNode(post.getId()) instanceof SplitDependencyNode){//Event - Split 
						if(pre == null){//Null - Event - Split 
							System.out.println("----Null - event - Split  -----");
							count ++;
							EventDTO edto = new EventDTO();
							edto.setName(e.getName());
							edto.setId(Long.parseLong(e.getId()));
							EventNode en = new EventNode(edto);
							en.setOriginalId(Long.parseLong(e.getId()));
							pgt.addNode(en);
							((SplitDependencyNode)pgt.getNode(post.getId())).dto().setParam(e.getName());//event信息保存在SplitDependency
						}else if(pre instanceof Function){
							count ++;
							System.out.println("----Func - event - Split  -----");
							((ProcessNode)pgt.getNode(pre.getId())).dto().setPostconditions("#" + e.getName() + "#");
							NullNode ne = new NullNode();
							ne.setPreid(Long.parseLong(pre.getId()));
							ne.setPostid(Long.parseLong(post.getId()));
							ne.setId(Long.parseLong(e.getId()));
							pgt.addNode(ne);
							((SplitDependencyNode)pgt.getNode(post.getId())).dto().setParam(e.getName());//event信息保存在SplitDependency
						}
					}
				}else if(pre instanceof Connector && post instanceof Connector){
					if(pgt.getNode(pre.getId()) instanceof SplitDependencyNode){
						if(pgt.getNode(post.getId())instanceof JoinDependencyNode){// Split - event - Join
							SplitDependencyOptionNode sdon = new SplitDependencyOptionNode();
							sdon.setDTOId(Long.parseLong(e.getId()));
							sdon.dto().setCondition(e.getName());
							sdon.setOriginalId(Long.parseLong(e.getId()));
							pgt.addNode(sdon);
							setPreconditionAfterJoin(post.getId(),pgt,e.getName());
							setPostconditionBeforeSplit(pre.getId(),pgt,e.getName());
						}else if(pgt.getNode(post.getId()) instanceof SplitDependencyNode){// Split - event - Split
							System.out.println("  Split - Event - Split");
							SplitDependencyOptionNode sdon = new SplitDependencyOptionNode();
							sdon.setDTOId(Long.parseLong(e.getId()));
							sdon.dto().setCondition(e.getName());
							sdon.setOriginalId(Long.parseLong(e.getId()));
							pgt.addNode(sdon);
							setPostconditionBeforeSplit(pre.getId(),pgt,e.getName());
							setPreconditionAfterSplit(pgt.getNode(post.getId()),e.getName(),pgt);
						}
					}else if(pgt.getNode(pre.getId()) instanceof JoinDependencyNode){
						if(pgt.getNode(post.getId()) instanceof SplitDependencyNode){//Join - event - Split
							System.out.println("Join - Event - Split");
							NullNode nn = new NullNode();
							nn.setDTOId(Long.parseLong(e.getId()));
							nn.setId(Long.parseLong(e.getId()));
							nn.setPostid(Long.parseLong(post.getId()));
							nn.setPreid(Long.parseLong(pre.getId()));
							pgt.addNode(nn);
							setPreconditionAfterSplit(pgt.getNode(post.getId()), e.getName(), pgt);
							setPostconditionBeforeJoin(pgt.getNode(pre.getId()),e.getName(),pgt);
						}else if(pgt.getNode(post.getId()) instanceof JoinDependencyNode){//Join - event - Join
							JoinDependencyOptionNode jdon = new JoinDependencyOptionNode();
							if (e.getId() == "1") System.out.println("Find 1 here");
							jdon.setDTOId(Long.parseLong(e.getId()));
							jdon.dto().setCondition(e.getName());
							jdon.setOriginalId(Long.parseLong(e.getId()));
							pgt.addNode(jdon);
							setPostconditionBeforeJoin(pgt.getNode(pre.getId()),e.getName(),pgt);
							setPreconditionAfterJoin(post.getId(),pgt,e.getName());
						}
					}
				}
					
			}
			
		}

		private static boolean checkEventRule(Event e, EPC epc) throws Exception {
			// TODO Auto-generated method stub
			
			Node pre = null;
			Node post = null;
			Node prepre = null;
			Node postpost = null;
			if(!epc.getPre(e).isEmpty()){
				pre = epc.getPre(e).iterator().next();
			}
			if(!epc.getPost(e).isEmpty()){
				post = epc.getPost(e).iterator().next();
			}
			
			if(pre instanceof Event || post instanceof Event){
				throw new Exception("Error Scheme : Event - Event");
				
				
			}
			
			if(pre instanceof Connector) {
				for(Node n : epc.getPre(pre)){
					if(n instanceof Event) {
						throw new Exception("Error Scheme : Event - Connetor ------ Event");
						
						
					}
				}
			}
			
			if(post instanceof Connector ){
				for(Node n : epc.getPost(post)){
					if(n instanceof Event) {
						throw new Exception("Error Shceme : Event ------ Connector - Event");
						
					}
					
				}
			}
			return true;
			
		}

		private static void MapConnector(ProcessGraphTemp pgt, EPC epc) {
			Set<Connector> con = epc.getConnectors();
			for(Connector c : con){
				
				if(epc.getPre(c).size() > 1){//JoinNode
					count ++;
					System.out.println("----Join Mapping -----");
					String follow = epc.getPost(c).iterator().next().getId();
					long f =  Long.parseLong(follow);
					JoinDependencyDTO jdto = new JoinDependencyDTO();//sync=====?
					jdto.setId((long)Integer.valueOf(c.getId()));
					jdto.setType(String2EnumJ(c.getName()));
					jdto.setFollowing(f);
					JoinDependencyNode jn = new JoinDependencyNode(jdto);
					jn.setOriginalId(Long.parseLong(c.getId()));
					pgt.addNode(jn);
					
				}else if(epc.getPost(c).size() > 1){//SpiltNode
					count ++;
					System.out.println("----Split Mapping -----");
					String pre = epc.getPre(c).iterator().next().getId();
					long p = Long.parseLong(pre);
					SplitDependencyDTO sdto = new SplitDependencyDTO();
					sdto.setId(Long.parseLong(c.getId()));
					sdto.setType(String2EnumS(c.getName()));
					sdto.setPreceding(p);
					SplitDependencyNode sn = new SplitDependencyNode(sdto);
					sn.setOriginalId(Long.parseLong(c.getId()));
					pgt.addNode(sn);
					
				}
			}
			
		}

		private static void MapFunction(ProcessGraphTemp pgt, EPC epc) {
			Set<Function> fun = epc.getFunctions();
			for(Function f : fun){
				count ++;
				System.out.println("----Process Mapping -----");
				long epc_id = (long)Integer.valueOf(f.getId());
				ProcessDTO pdt = new ProcessDTO();
				pdt.setId(epc_id);
				pdt.setName(f.getName());
				ProcessNode pn = new ProcessNode(pdt);
				pn.setOriginalId(Long.parseLong(f.getId()));
				pgt.addNode(pn);
			}
		}

		private static void convertEventInfoInConnector(
				ProcessGraphTemp pgt) {
			
			for(GraphNode gn : pgt.getNodes().values()){
				
				if(gn instanceof JoinDependencyNode){//Ruturn EventInfo
					String eventinfo = ((JoinDependencyNode) gn).dto().getParam();
					if (eventinfo != null){
						setPostconditionBeforeJoin(gn,eventinfo,pgt);
					}
				}else if(gn instanceof SplitDependencyNode){//Ruturn EventInfo
					String eventinfo = ((SplitDependencyNode) gn).dto().getParam();
					if (eventinfo != null){
						setPreconditionAfterSplit(gn,eventinfo,pgt);
					}
				}
			}
			
		}

		private static void setPreconditionAfterSplit(GraphNode gn,
				String eventinfo, ProcessGraphTemp pgt) {
			
			System.out.println("SetPreconditionAfterSplit");
			GraphNode postnode = gn; 
			GraphNode originalnode = postnode;//用来保存Connector符号，此处没有什么用处
			//for(GraphNode postnode : pgt.getPostNode(gn.getDTOId())){
				while(!(postnode instanceof ProcessNode)){
					//TODO
					
					/*
					 * TODO : 改进设置condition算法
					 * 
					 * //再次遇到SPlit 则递归调用
					if(postnode instanceof SplitDependencyNode) {
						setPreconditionAfterSplit(postnode,eventinfo,pgt);
						break;
					}
					//再次遇到Join，调用外部方法
					if(postnode instanceof JoinDependencyNode){
						setPreconditionAfterJoin(String.valueOf(postnode.getDTOId()),pgt,eventinfo);
						break;
					} 
					 */
					if(postnode instanceof EventNode) return ;
					Set<GraphNode> postNode2 = pgt.getPostNode(postnode.getDTOId());
					if(postNode2.isEmpty()) return ;
					if(postNode2.size() > 1) {
						for(GraphNode postnodeaftersplit : postNode2){
							if(postnodeaftersplit == null) continue;
							setPreconditionAfterSplit(postnodeaftersplit, eventinfo, pgt);
						}
						return ;
					}
					postnode = postNode2.iterator().next();
				}
				
				String conditionrelation = "XOR";
				//if(originalnode instanceof JoinDependencyNode){
				//	conditionrelation = ((JoinDependencyNode)originalnode).dto().getType().toString();
				//}
				if(((ProcessNode)postnode).dto().getPreconditions().equalsIgnoreCase("")){
					((ProcessNode)postnode).dto().setPreconditions("#" + eventinfo + "#");	
				}else{
					String oldpre = "(" + ((ProcessNode)postnode).dto().getPreconditions() + ")";
					((ProcessNode)postnode).dto().setPreconditions("#" + oldpre + "#" + conditionrelation + "#" + eventinfo + "#");	
				}
				
			//}
				return ;
		
		}

		private static void setPostconditionBeforeJoin(GraphNode gn,
				String eventinfo, ProcessGraphTemp pgt) {
			
			System.out.println("SetPostConditionBeforJoin");
			GraphNode prenode = gn; 
			GraphNode originalnode = null;//暂时没有什么用处
			while(!(prenode instanceof ProcessNode)){
				if(prenode instanceof EventNode) return;
				originalnode = prenode;
				Set<GraphNode> preNode2 = pgt.getPreNode(prenode.getDTOId());
				if(preNode2.isEmpty()) return ;
				if(preNode2.size() > 1){
					for(GraphNode nodebeforejoin : preNode2){
						if(nodebeforejoin == null) continue;
						setPostconditionBeforeJoin(nodebeforejoin,eventinfo,pgt);
					}
					return ;
				}
				prenode = preNode2.iterator().next();
				
			}
			String conditionrelation = "XOR";
			//if(originalnode instanceof JoinDependencyNode){
			//	conditionrelation = ((JoinDependencyNode)originalnode).dto().getType().toString();
			//}
			if(((ProcessNode)prenode).dto().getPostconditions().equalsIgnoreCase("")){
				((ProcessNode)prenode).dto().setPostconditions("#" + eventinfo + "#");
			}else{
				String oldpost = "(" + ((ProcessNode)prenode).dto().getPostconditions() + ")";
				((ProcessNode)prenode).dto().setPostconditions("#" + oldpost + "#" + conditionrelation + "#" + eventinfo + "#");
			}
			
				
		}

		private static ProcessGraphTemp assingUinqueId(ProcessGraphTemp pgt) throws Exception {
			ProcessGraphTemp pgt2 = new ProcessGraphTemp();
			for(GraphNode gn : pgt.getNodes().values()){
				//if(gn.getDTOId() == 1) System.out.println("Find 1 here");
				//存储原来的ID 
				//gn.setOriginalId(gn.getDTOId());
				long uniqueid = IdWorker.getNextId();
				System.out.println("UniqueId : " + uniqueid);
				for(Edge e : pgt.getEdgesBySrc(gn.getDTOId())){
					e.setSrcid(uniqueid);
				}
				for(Edge e : pgt.getEdgesByTgt(gn.getDTOId())){
					e.setTgtid(uniqueid);
				}
				gn.setDTOId(uniqueid);
				pgt2.addNode(gn);
			}
			
			pgt2.setEdges(pgt.getEdges());
			
			for(GraphNode gn : pgt2.getNodes().values()){
				if(gn instanceof SequenceDependencyNode){
					System.out.println("Sequence");
					long following = pgt2.getPostNode(gn.getDTOId()).iterator().next().getDTOId();
					((SequenceDependencyNode) gn).setFollowing(following);
					long preceding = pgt2.getPreNode(gn.getDTOId()).iterator().next().getDTOId();
					((SequenceDependencyNode) gn).setPreceding(preceding);
				}
				if(gn instanceof JoinDependencyOptionNode){
					System.out.println("JoinDependencyOption");
					//if(((JoinDependencyOptionNode) gn).getDTOId() == 1) System.out.println("Assign ID  find 1");
					long joinid = pgt2.getPostNode(gn.getDTOId()).iterator().next().getDTOId();
					((JoinDependencyOptionNode) gn).dto().setJoinid(joinid);
					
					if(pgt2.getPreNode(gn.getDTOId()).isEmpty()){
						
					}else{
						long preceding = pgt2.getPreNode(gn.getDTOId()).iterator().next().getDTOId();
						((JoinDependencyOptionNode) gn).dto().setPreceding(preceding);
					}
					
				}
				if(gn instanceof SplitDependencyOptionNode){
					System.out.println("SplitDependencyOption");
					long following = pgt2.getPreNode(gn.getDTOId()).iterator().next().getDTOId();
					((SplitDependencyOptionNode) gn).dto().setFollowing(following);
					if(pgt2.getPostNode(gn.getDTOId()).isEmpty()){
						
					}else{
						long splitid = pgt2.getPostNode(gn.getDTOId()).iterator().next().getDTOId();
						((SplitDependencyOptionNode) gn).dto().setSplitid(splitid);
					}
					
				}
				
				if(gn instanceof JoinDependencyNode){
					System.out.println("JoinDependency");
					System.out.println(gn.getDTOId());
					if(pgt2.getPostNode(gn.getDTOId()).isEmpty()){
						System.out.println("Join Following Is Empty");
					}else {
						long following = pgt2.getPostNode(gn.getDTOId()).iterator().next().getDTOId();
						((JoinDependencyNode) gn).dto().setFollowing(following);
					}
					
				}
				if(gn instanceof SplitDependencyNode){
					System.out.println("SplitDependency");
					if(pgt2.getPreNode(gn.getDTOId()).isEmpty()){
						System.out.println("Split Preceding is Empty");
					}else {
						long preceding = pgt2.getPreNode(gn.getDTOId()).iterator().next().getDTOId();
						((SplitDependencyNode) gn).dto().setPreceding(preceding);
					}
					
				}
				
				//TODO JoinDependency  SplitDependency 
				
				
			}
			
			return pgt2;
		}

		private static ProcessGraphTemp MakeOptionNode(ProcessGraphTemp pgt) throws Exception {
			ProcessGraphTemp pgt1 = new ProcessGraphTemp();
			
			for(GraphNode gn : pgt.getNodes().values()){
				pgt1.addNode(gn);
				if(gn instanceof JoinDependencyNode){
					for(GraphNode gn2 : pgt.getPreNode(gn.getDTOId())){
						if( !(gn2 instanceof JoinDependencyOptionNode)  && gn2 != null ){//no option befor join 
							
							JoinDependencyOptionDTO sdod = new JoinDependencyOptionDTO();
							sdod.setId(IdWorker.getNextId());
							sdod.setJoinid(gn.getDTOId());
							sdod.setPreceding(gn2.getDTOId());
							pgt.newAddNode ++ ;
							JoinDependencyOptionNode jdon = new JoinDependencyOptionNode(sdod);
							jdon.setOriginalId(0-pgt.newAddNode);;
							pgt1.addNode(jdon);
							pgt.newAddEdge ++;
							pgt.newAddEdge ++;
							Edge e1 = new Edge();
							e1.setOriginalId(0 - pgt.newAddEdge);
							e1.setId(IdWorker.getNextId());
							e1.setSrcid(gn2.getDTOId());
							e1.setTgtid(sdod.getId());
							Edge e2 = new Edge();
							e2.setOriginalId(0 - pgt.newAddEdge);
							e2.setId(IdWorker.getNextId());
							e2.setSrcid(sdod.getId());
							e2.setTgtid(gn.getDTOId());
							pgt.addEdge(e1);
							pgt.addEdge(e2);
							pgt.removeEdgeByTwoNode(gn2.getDTOId(), gn.getDTOId());
						}
					}
				}
				
				if(gn instanceof SplitDependencyNode){
					for(GraphNode gn2 : pgt.getPostNode(gn.getDTOId())){
						if(!(gn2 instanceof SplitDependencyOptionNode) && gn2 != null){
							//if(gn2 == null) System.out.println("Null in MakeOptionNode");
							if(gn2 instanceof NullNode) System.out.println("NullNode in MakeOptionNode");
							SplitDependencyOptionDTO jdod = new SplitDependencyOptionDTO();
							jdod.setId(IdWorker.getNextId());
							jdod.setSplitid(gn.getDTOId());
							jdod.setFollowing(gn2.getDTOId());
							SplitDependencyOptionNode jdon = new SplitDependencyOptionNode(jdod);
							pgt.newAddNode++;
							jdon.setOriginalId(0 - pgt.newAddNode);
							pgt1.addNode(jdon);
							pgt.newAddEdge++;
							pgt.newAddEdge++;
							Edge e1 = new Edge();
							e1.setOriginalId(0 - pgt.newAddEdge);
							e1.setId(IdWorker.getNextId());
							e1.setTgtid(gn2.getDTOId());
							e1.setSrcid(jdod.getId());
							Edge e2 = new Edge();
							e2.setOriginalId(0 - pgt.newAddEdge);
							e2.setId(IdWorker.getNextId());
							e2.setTgtid(jdod.getId());
							e2.setSrcid(gn.getDTOId());
							pgt.addEdge(e1);
							pgt.addEdge(e2);
							pgt.removeEdgeByTwoNode(gn.getDTOId(), gn2.getDTOId());
						}
					}
				}
			}
			pgt1.setEdges(pgt.getEdges());
			return pgt1;
		}

		private static ProcessGraphTemp maintainNullNode(ProcessGraphTemp pgt) throws Exception {
			
			ProcessGraphTemp pgt1 = new ProcessGraphTemp();
			pgt1.setEdges(pgt.getEdges());
			
			for(GraphNode gn : pgt.getNodes().values()){
				if(gn instanceof NullNode){
					long pre = ((NullNode)gn).getPreid();
					long post = ((NullNode)gn).getPostid();
					System.out.println("NullNode ID : " + ((NullNode)gn).getId());
					//Delete null - node and node - null
					pgt1.removeEdgeBySource(((NullNode) gn).getId());
					pgt1.removeEdgeByTarget(((NullNode) gn).getId());
					Edge e = new Edge();
					pgt.newAddEdge++;
					e.setOriginalId(0 - pgt.newAddEdge);
					//Add node - node in node - Null - node model
					e.setId(IdWorker.getNextId());
					e.setSrcid(pre);
					e.setTgtid(post);
					pgt1.addEdge(e);
				}
			}
			//delete null node
			for(GraphNode gn : pgt.getNodes().values()){
				if(gn instanceof NullNode) continue;
				pgt1.addNode(gn);
			}
			
			
			return pgt1;
		}

		private static void setPreconditionAfterJoin(String id, ProcessGraphTemp pgt,
				String name) throws Exception {
			System.out.println("setPreconditionAfterJoin---------");
			System.out.println("Condition : " + name);
			GraphNode postnode = pgt.getNode(id);
			if(postnode == null) return;
			GraphNode originalnode = postnode; //保留遇到的第一个connector，即join的Type
			while(!(postnode instanceof ProcessNode)){
				//originalnode = postnode;
				if(postnode instanceof EventNode) return;
				
				Set<GraphNode> postNode2 = pgt.getPostNode(postnode.getDTOId());
				if(postNode2.isEmpty()) return ;
				if(postNode2.size() > 1) {
					for(GraphNode postn : postNode2){
						if(postn == null) continue;
						setPreconditionAfterJoin(String.valueOf(postn.getDTOId()),pgt,name);
					}
					return ;
				}
				postnode = postNode2.iterator().next();
				/*
				 * TODO : 改进设置调用，普遍适应一切情况
				 * if(postnode instanceof JoinDependencyNode){
					setPreconditionAfterJoin(postnode);
				}
				
				 * if(postnode instanceof SplitDependencyNode){
					setPreconditionAfterSplit(postnode,name,pgt);//后面遇到SplitDependencyNode调用另外的方法
					return ;
				} 
				 */
				
			}
			String conditionrelation = "XOR";
			if(originalnode instanceof JoinDependencyNode){
				conditionrelation = ((JoinDependencyNode)originalnode).dto().getType().toString();
			}
			if(((ProcessNode)postnode).dto().getPreconditions().equalsIgnoreCase("")){
				((ProcessNode)postnode).dto().setPreconditions("#" + name + "#");
			}else{
				String oldpre = "(" + ((ProcessNode)postnode).dto().getPreconditions() + ")";
				((ProcessNode)postnode).dto().setPreconditions("#" + oldpre + "#" + conditionrelation + "#" + name + "#");
			}
			
			//System.out.println("Precondition : " + ((ProcessNode)postnode).dto().getPreconditons());
			
		}

		private static void setPostconditionBeforeSplit(String preid, ProcessGraphTemp pgt,String condition) {
			System.out.println("SetPostConditionBeforSPlit");
			GraphNode prenode = pgt.getNode(preid);
			if(prenode == null) return;
			GraphNode originalnode = prenode;//关于bool运算符的改动
			while(!(prenode instanceof ProcessNode)){
				if(prenode instanceof EventNode) return;
				//if(prenode instanceof JoinDependencyNode) setPostconditionBeforeJoin(prenode, condition, pgt);
				Set<GraphNode> preNode2 = pgt.getPreNode(prenode.getDTOId());
				if(preNode2.isEmpty()) return ;
				if(preNode2.size() > 1){
					for(GraphNode pren : preNode2){
						if(pren == null) continue;
						setPostconditionBeforeSplit(String.valueOf(pren.getDTOId()),pgt,condition);	
					}
					return ;
				}
				
				prenode = preNode2.iterator().next();//preNode变为上一个NOde
			}
			String conditionrelation = "XOR";
			if(originalnode instanceof SplitDependencyNode){
				conditionrelation = ((SplitDependencyNode)originalnode).dto().getType().toString();
			}
			if(((ProcessNode)prenode).dto().getPostconditions() == ""){
				((ProcessNode)prenode).dto().setPostconditions("#" + condition + "#");
			}else{
				String oldpost = "(" + ((ProcessNode)prenode).dto().getPostconditions() + ")";
				((ProcessNode)prenode).dto().setPostconditions("#" + oldpost + "#" + conditionrelation + "#" + condition + "#");
			}
			
			
		}

		private static JoinType String2EnumJ(String type){
			if(type.equalsIgnoreCase("AND"))
				return JoinType.AND;
			else if(type.equalsIgnoreCase("OR"))
				return JoinType.OR;
			return JoinType.XOR;
		}
		
		private static SplitType String2EnumS(String type){
			if(type.equalsIgnoreCase("AND"))
				return SplitType.AND;
			else if(type.equalsIgnoreCase("OR"))
				return SplitType.OR;
			return SplitType.XOR;
		}


}
