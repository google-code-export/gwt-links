package com.orange.links.client.save;

import java.util.Set;

import com.google.gwt.regexp.shared.RegExp;
import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.NodeList;
import com.google.gwt.xml.client.XMLParser;


public class DiagramTranslationService {

	
	public static DiagramRepresentation importDiagram(String xml){
		Document doc = XMLParser.parse(xml);
		Element root = doc.getDocumentElement();

		// DiagramRepresentation
		DiagramRepresentation diagramRepresentation = new DiagramRepresentation();
		
		NodeList nodeList = root.getElementsByTagName("function");
		for(int i=0;i<nodeList.getLength();i++){
			Element functionElement = (Element) nodeList.item(i);
			FunctionRepresentation function = new FunctionRepresentation();
			function.id = functionElement.getAttribute("id");
			function.content = (functionElement.getFirstChild() != null) 
					? functionElement.getFirstChild().toString() : null;
			function.identifier = functionElement.getAttribute("identifier");
			function.left = Integer.parseInt(functionElement.getAttribute("left"));
			function.top = Integer.parseInt(functionElement.getAttribute("top"));
			diagramRepresentation.addFunction(function);
		}

		nodeList = root.getElementsByTagName("link");
		for(int i=0;i<nodeList.getLength();i++){
			Element linkElement = (Element) nodeList.item(i);
			LinkRepresentation link = new LinkRepresentation();
			link.startId = linkElement.getAttribute("startid");
			link.endId = linkElement.getAttribute("endid");
			
			// Get decoration if exists
			if(linkElement.getElementsByTagName("decoration").getLength() > 0){
				Element decoration = (Element) linkElement.getElementsByTagName("decoration").item(0);
				DecorationRepresentation decorationRepresentation = new DecorationRepresentation();
				decorationRepresentation.content = decoration.getFirstChild().toString().trim();
				decorationRepresentation.identifier = decoration.getAttribute("identifier");
				link.decoration = decorationRepresentation;
			}
			
			link.type = linkElement.getAttribute("type");
			// Contruct point list
			NodeList pointNodeList = linkElement.getElementsByTagName("point");
			int[][] pointList = new int[pointNodeList.getLength()][2];
			for(int j=0;j<pointNodeList.getLength();j++){
				Element pointElement = (Element) pointNodeList.item(j);
				int[] point = {Integer.decode(pointElement.getAttribute("x")),
						Integer.decode(pointElement.getAttribute("y"))};
				pointList[j] = point;
			}
			link.pointList = pointList;
			diagramRepresentation.addLink(link);
		}

		return diagramRepresentation;
	}
	
	public static String exportDiagram(DiagramRepresentation diagramRepresentation){

		Document doc = XMLParser.createDocument();
		
		// Diagram Properties
		Element diagramRoot = doc.createElement("diagram");
		diagramRoot.setAttribute("width", diagramRepresentation.getWidth() + "");
		diagramRoot.setAttribute("heigth", diagramRepresentation.getHeight() + "");
		diagramRoot.setAttribute("grid", diagramRepresentation.isHasGrid() + "");
		doc.appendChild(diagramRoot);
		
		// Function computation
		Set<FunctionRepresentation> functionSet = diagramRepresentation.getFunctionRepresentationSet();
		for(FunctionRepresentation function : functionSet){
			Element functionElement = doc.createElement("function");
			functionElement.setAttribute("left", function.left + "");
			functionElement.setAttribute("top",function.top + "");
			functionElement.setAttribute("id",function.id);
			functionElement.setAttribute("identifier",function.identifier);
			if(function.content != null && !function.content.trim().equals(""))
				functionElement.appendChild(doc.createTextNode(function.content));
			diagramRoot.appendChild(functionElement);
		}
		
		// Link computation
		Set<LinkRepresentation> linkSet 
		= diagramRepresentation.getLinkRepresentationSet();
		for(LinkRepresentation link : linkSet){
			Element linkElement = doc.createElement("link");
			linkElement.setAttribute("startid", link.startId + "");
			linkElement.setAttribute("endid", link.endId + "");
			if(link.decoration != null){
				Element decoration = doc.createElement("decoration");
				decoration.setAttribute("identifier", link.decoration.identifier);
				decoration.appendChild(doc.createTextNode(link.decoration.content));
				linkElement.appendChild(decoration);
			}
			// Add the movable points
			for(int[] p : link.pointList){
				Element pointElement = doc.createElement("point");
				pointElement.setAttribute("x", p[0] +"");
				pointElement.setAttribute("y", p[1] + "");
				linkElement.appendChild(pointElement);
			}
			diagramRoot.appendChild(linkElement);
		}
		
		String out = doc.toString();
		out = format(out);
		out = createXmlHeader(out);
		return out;
	}
	
	private static String createXmlHeader(String xml){
		return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n\n" + xml;
	}
	
	private static String format(String xml){
		String output = "";
		RegExp r = RegExp.compile("(<[^<]*>|<[^<]*/>|</[^<]*>)","g");
		output = r.replace(xml, "$1\n");
		return output;
	}
	
}
