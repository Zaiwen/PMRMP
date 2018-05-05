package nl.tue.tm.is.epc;

import org.utils.JsonUtil;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.util.*;

public class EPC extends Node {

	Map<String, Function> functions;
	Map<String, Event> events;
	Map<String, Connector> connectors;
	Set<Arc> arcs;
	Map<Node, Set<Arc>> arcsBySource;
	Map<Node, Set<Arc>> arcsByTarget;

	public EPC() {
		functions = new HashMap<String, Function>();
		events = new HashMap<String, Event>();
		connectors = new HashMap<String, Connector>();
		arcs = new HashSet<Arc>();
		arcsBySource = new HashMap<Node, Set<Arc>>();
		arcsByTarget = new HashMap<Node, Set<Arc>>();
	}

	public String Epc2Epml(EPC epc){
		String epml = "";
		epml += "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n";
		epml += "<epml>\n";
		epml += "<epc epcId = \"" + epc.getId() + "\" name = \"" + epc.getName() + "\" xmlns:fo=\"http://www.w3.org/1999/XSL/Format\"> \n";
		for(Event e : epc.getEvents()){
			epml += "	<event id = \"" + e.getId() + "\">\n";
			epml += "		<name>" + e.getName() + "</name>\n";
			epml += "	</event>\n";
		}
		    
		for(Connector c : epc.getConnectors()){
			epml += "	<" + c.getName() + " id = \"" + c.getId() + "\"/>\n";
		}
		
		for(Function f : epc.getFunctions()){
			epml += "	<function id = \"" + f.getId() + "\" >\n";
			epml += "		<name>" + f.getName() + "</name>\n";
			epml += "	</function>\n";
		}
		
		for(Arc a : epc.getArcs()){
			epml += "	<arc id = \"" + a.getId()+ "\">\n";
			epml += "		<flow source = \"" + a.getSource().getId() + "\" target = \"" + a.getTarget().getId() + "\"/> \n";
			epml += "	</arc>\n";
		}
		
		epml += "</epc>\n";
		epml += "</epml>";
		
		
		return epml;
	}
	
	public static EPC loadEPML(String fileLocation) {
		EPC result = new EPC();

		DefaultHandler handler = new EPCParser(result);
		SAXParserFactory factory = SAXParserFactory.newInstance();

		try {
			SAXParser saxParser = factory.newSAXParser();
			saxParser.parse(new File(fileLocation), handler);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	public Node getNodeById(long id){
		String s = String.valueOf(id);
		Node n = functions.get(s);
		if(n != null) return n;
		n = events.get(s);
		if(n != null) return n;
		n = connectors.get(s);
		if(n != null) return n;
		return null;
	}
	
	public void addFunction(Function f) {
		functions.put(f.getId(), f);
	}

	public void addEvent(Event e) {
		events.put(e.getId(), e);
	}

	public void addConnector(Connector c) {
		connectors.put(c.getId(), c);
	}

	public void addArc(Arc a) {
		arcs.add(a);
		Set<Arc> arcsFromSource = arcsBySource.get(a.getSource());
		if (arcsFromSource == null) {
			arcsFromSource = new HashSet<Arc>();
			arcsFromSource.add(a);
			arcsBySource.put(a.getSource(), arcsFromSource);
		} else {
			arcsFromSource.add(a);
		}
		Set<Arc> arcsToTarget = arcsByTarget.get(a.getTarget());
		if (arcsToTarget == null) {
			arcsToTarget = new HashSet<Arc>();
			arcsToTarget.add(a);
			arcsByTarget.put(a.getTarget(), arcsToTarget);
		} else {
			arcsToTarget.add(a);
		}
	}

	public Function findFunction(String id) {
		return functions.get(id);
	}

	public Event findEvent(String id) {
		return events.get(id);
	}

	public Connector findConnector(String id) {
		return connectors.get(id);
	}

	public Node findNode(String id) {
		Node result = events.get(id);
		if (result != null) {
			return result;
		}
		result = functions.get(id);
		if (result != null) {
			return result;
		}
		result = connectors.get(id);
		return result;
	}

	public Set<Node> getPre(Node n) {
		Set<Node> result = new HashSet<Node>();
		Set<Arc> incoming = arcsByTarget.get(n);
		if (incoming != null) {
			for (Iterator<Arc> i = incoming.iterator(); i.hasNext();) {
				Arc a = i.next();
				result.add(a.getSource());
			}
		}
		return result;
	}

	public Set<Node> getPost(Node n) {
		Set<Node> result = new HashSet<Node>();
		Set<Arc> outgoing = arcsBySource.get(n);
		if (outgoing != null) {
			for (Iterator<Arc> i = outgoing.iterator(); i.hasNext();) {
				Arc a = i.next();
				result.add(a.getTarget());
			}
		}
		return result;
	}

	public Set<Function> getFunctions() {
		return new HashSet<Function>(functions.values());
	}

	public Set<Event> getEvents() {
		return new HashSet<Event>(events.values());
	}

	public Set<Connector> getConnectors() {
		return new HashSet<Connector>(connectors.values());
	}

	public Set<Arc> getArcs() {
		return new HashSet<Arc>(arcs);
	}

	public Set<Node> getNodes() {
		Set<Node> result = new HashSet<Node>();
		result.addAll(functions.values());
		result.addAll(events.values());
		result.addAll(connectors.values());
		return result;
	}

	@Override
	public String toString() {
		return JsonUtil.toJson(this);
	}

	public void addArcByid(Arc a) {
		
		Node src = this.findNode(String.valueOf(a.getSrcid()));
		Node tgt = this.findNode(String.valueOf(a.getTgtid()));
		if(src == null) System.out.println("AddArcById src is wrong");
		if(tgt == null) System.out.println("AddArcByid tgt is Wrong");
		a.setSource(src);
		a.setTarget(tgt);
		this.addArc(a);
		
	}

}
