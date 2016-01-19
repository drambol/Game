package utility.file;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.*;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XmlParser {
	
	protected Document doc;
	protected File file;
	
	public XmlParser(String str) {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		file = new File(System.getProperty("user.dir") + "\\src\\xml\\" + str);
		if (!file.exists())
			file = new File(System.getProperty("user.dir") + "\\src\\" + str);
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			this.doc = builder.parse(file);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public List<String> getNodeValues(String nodeName) {
		NodeList nodeList = doc.getElementsByTagName(nodeName);
		List<String> args = new ArrayList<String>();
		try{
			for (int i = 0; i < nodeList.getLength(); i++) {
				args.add(nodeList.item(i).getTextContent());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return args;
	}
	
	public List<String> getNonNullValues(String nodeName) {
		NodeList nodeList = doc.getElementsByTagName(nodeName);
		List<String> args = new ArrayList<String>();
		try{
			for (int i = 0; i < nodeList.getLength(); i++) {
				if (!"".equalsIgnoreCase(nodeList.item(i).getTextContent()))
					args.add(nodeList.item(i).getTextContent());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return args;
	}
	
	public String getNodeValue(String str) {
		String nodeValue = null;
		try{
			nodeValue = doc.getElementsByTagName(str).item(0).getTextContent();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return nodeValue;
	}
	
	public String getNodeValue(String str, int i) {
		String nodeValue = null;
		try{
			nodeValue = doc.getElementsByTagName(str).item(i).getTextContent();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return nodeValue;
	}
	
	public String getNodeValue(Node node, String nodeName) {
		String nodeValue = null;
		int childNodeCount = node.getChildNodes().getLength();
		for (int i = 0; i < childNodeCount; i++) {
			if (nodeName == node.getChildNodes().item(i).getNodeName()) {
				nodeValue = node.getChildNodes().item(i).getTextContent();
				break;
			}
		}
		return nodeValue;
	}

	public Node getNodeByName(String nodeName) {
		Node node = null;
		try{
			node = doc.getElementsByTagName(nodeName).item(0);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return node;
	}
	
	public Node getNodeByName(String nodeName, int i) {
		Node node = null;
		try{
			node = doc.getElementsByTagName(nodeName).item(i);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return node;
	}
	
	public NodeList getNodesByName(String nodeName) {
		NodeList nodes = null;
		try{
			nodes = doc.getElementsByTagName(nodeName);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return nodes;
	}
	
	public Node getNodeByValue(String nodeName, String nodeValue) {
		Node node = null;
		try {
			NodeList nodes = doc.getElementsByTagName(nodeName);
			for (int i = 0; i < nodes.getLength(); i++) {
				if (nodes.item(i).getTextContent().equals(nodeValue)) {
					return nodes.item(i);
				}
			}
		} catch (Exception e) {
			System.out.println("No such node found!");
		}
		return node;
	}
	
	public Node getSubNodeByValue(String nodeName, String nodeValue) {
		Node node = null;
		try {
			node = doc.getElementsByTagName(nodeName).item(0);
			NodeList nodes = node.getChildNodes();
			for (int i = 0; i < nodes.getLength(); i++) {
				if (nodes.item(i).getTextContent().contains(nodeValue)) {
					return nodes.item(i);
				}
			}
		} catch (Exception e) {
			System.out.println("No such node found!");
		}
		return node;
	}
	
	public Node getChildNode(Node node, String childNodeName) {
		try {
			NodeList nodes = node.getChildNodes();
			for (int i = 0; i < nodes.getLength(); i++) {
				if (nodes.item(i).getNodeName().equals(childNodeName)) {
					return nodes.item(i);
				}
			}
		} catch (Exception e) {
			System.out.println("No such node found!");
		}
		return null;
	}
	
	public List<Node> getChildNodes(Node node, String childNodeName) {
		List<Node> chileNodes = new ArrayList<Node>();
		try {
			NodeList nodes = node.getChildNodes();
			
			for (int i = 0; i < nodes.getLength(); i++) {
				if (nodes.item(i).getNodeName().equals(childNodeName)) {
					chileNodes.add(nodes.item(i));
				}
			}
		} catch (Exception e) {
			System.out.println("No such node found!");
		}
		return chileNodes;
	}
	
	public void save() {
		try {
			TransformerFactory tFactory = TransformerFactory.newInstance();
			Transformer transformer = tFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(file);
			transformer.transform(source, result);
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
	
}
