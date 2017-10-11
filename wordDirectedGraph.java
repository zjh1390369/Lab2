package lab1;

import java.util.Vector;
import java.util.LinkedList;
import java.util.Random;

public class wordDirectedGraph {
	class linkNode {
		public Integer wordID;
		public Integer weight;
		
		public linkNode(Integer word, Integer wei) {
			wordID = word;
			weight = wei;
		}
	} 
	
	class edge {
		public String src;
		public String des;
		public Integer weight;
		
		public edge(String s, String d, Integer w) {
			src = s;
			des = d;
			weight = w;
		}
	}
	
	private Vector<String> wordIDTable = new Vector<String>();
	private Vector<LinkedList<linkNode>> graphicTable = new Vector<LinkedList<linkNode>>();
	
	public void clear() {
		wordIDTable.clear();
		graphicTable.clear();
	}
	
	public void addWord(String w) {
		if (wordIDTable.indexOf(w) == -1) {
			wordIDTable.add(w);
			graphicTable.add(new LinkedList<linkNode>());
		}
	}
	
	public void addEdge(String src, String des) {
		addWord(src);
		addWord(des);
		Integer srcID = wordIDTable.indexOf(src);
		Integer desID = wordIDTable.indexOf(des);
		
		for (linkNode node : graphicTable.get(srcID)) {
			if (node.wordID == desID) {
				node.weight = node.weight + 1;
				return;
			}
		}
		
		graphicTable.get(srcID).add(new linkNode(desID, 1));
	}
	
	public Vector<String> getWordArr() {
		return wordIDTable;
	}
	
	public Vector<edge> getEdgeArr() {
		Vector<edge> res = new Vector<edge>();
		for (int srcID = 0; srcID < graphicTable.size(); srcID++) {
			for (linkNode des : graphicTable.get(srcID)) {
				int desID = des.wordID;
				int weight = des.weight;
				res.add(new edge(wordIDTable.get(srcID), wordIDTable.get(desID), weight));
			}
		}
		return res;
	}
	
	public Vector<edge> getEdges(String word) {
		Vector<edge> res = new Vector<edge>();
		Integer wordID = wordIDTable.indexOf(word);
		
		if (wordID != -1) {
			for (linkNode node : graphicTable.get(wordID)) {
				res.add(new edge(word, wordIDTable.get(node.wordID), node.weight));
			}
		}
		
		return res;
	}

	public Vector<String> getBridgeEdge(String src, String des) {
		Vector<String> res = new Vector<String>();
		Integer srcID = wordIDTable.indexOf(src);
		Integer desID = wordIDTable.indexOf(des);
		
		if (srcID == -1 || desID == -1) 
			return res;
		
		for (linkNode bridge : graphicTable.get(srcID)) {
			for (linkNode node : graphicTable.get(bridge.wordID)) {
				if (node.wordID == desID)
					res.add(wordIDTable.get(bridge.wordID));
			}
		}
		
		return res;
	}
	
	public Vector<String> shortestPath(String src, String des) {
		Vector<String> res = new Vector<String>();
		Integer srcID = wordIDTable.indexOf(src);
		Integer desID = wordIDTable.indexOf(des);
		
		if (srcID == -1 || desID == -1)
			return res;
		
		Vector<Integer> distances = new Vector<Integer>();
		Vector<Integer> choicedNodes = new Vector<Integer>();
		Vector<Integer> middleWord = new Vector<Integer>();
		for (int i=0; i<wordIDTable.size(); i++) {
			distances.add(Integer.MAX_VALUE);
			choicedNodes.add(0);
			middleWord.add(-1);
		}
		distances.set(srcID, 0);
		for (int i=0; i<wordIDTable.size(); i++) {
			Integer minIndex = indexOfMin(distances, choicedNodes);
			if (minIndex == -1 || minIndex == desID)
				break;
			choicedNodes.set(minIndex, 1);
			for (linkNode node : graphicTable.get(minIndex)) {
				if (distances.get(minIndex) + node.weight < distances.get(node.wordID)) {
					middleWord.set(node.wordID, minIndex);
					distances.set(node.wordID, distances.get(minIndex) + node.weight);
				}
			}
		}
		if (middleWord.get(desID) == -1)
			return res;
		Integer node = desID;
		while (node != srcID) {
			res.add(wordIDTable.get(node));
			node = middleWord.get(node);
		}
		res.add(src);
		for (int i=0; i<res.size()/2; i++) {
			String temp = res.get(i);
			res.set(i, res.get(res.size()-1-i));
			res.set(res.size()-1-i, temp);
		}
		return res;
	}
	
	private Integer indexOfMin(Vector<Integer> d, Vector<Integer> n) {
		Integer distance = Integer.MAX_VALUE;
		Integer index = -1;
		for (int i=0; i<d.size(); i++) {
			if (n.get(i) == 0 && d.get(i) < distance) {
				distance = d.get(i);
				index = i;
			}
		}
		return index;
	}
	
	public Vector<String> randomWalk() {
		Vector<String> res = new Vector<String>();
		Vector<LinkedList<linkNode>> usedEdge = new Vector<LinkedList<linkNode>>();
		for (int i=0; i<wordIDTable.size(); i++)
			usedEdge.add(new LinkedList<linkNode>());
		Random rdm = new Random();
		if (wordIDTable.size() == 0)
			return res;
		Integer thisID = rdm.nextInt(wordIDTable.size());
		
		while (true) {
			res.add(wordIDTable.get(thisID));
			Vector<Integer> items = new Vector<Integer>();
			for (linkNode node : graphicTable.get(thisID)) {
				Integer edgeUsed = 0;
				for (linkNode node1 : usedEdge.get(thisID)) {
					if (node1.wordID == node.wordID)
						edgeUsed = 1;
				}
				if (edgeUsed == 0)
					items.add(node.wordID);
			}
			if (items.size() == 0)
				break;
			Integer nextID = items.get(rdm.nextInt(items.size()));
			usedEdge.get(thisID).add(new linkNode(nextID, 1));
			thisID = nextID;
		}
		return res;
	}
	
	void print() {
		for (int i=0; i<wordIDTable.size(); i++) {
			System.out.print(i);
			System.out.print(" : ");
			System.out.print(wordIDTable.get(i));
			System.out.print("\n");
		}
		int i = 0;
		for (LinkedList<linkNode> lista : graphicTable) {
			System.out.print(i);
			System.out.print(" : ");
			for (int k=0; k<lista.size(); k++) {
				System.out.print(((linkNode)lista.get(k)).wordID);
				System.out.print(" ");
			}
			System.out.print("\n");
			i++;
		}
	}
	
	public static void main(String args[]) { 
		System.out.println("Hello World!"); 
		wordDirectedGraph test = new wordDirectedGraph();
		test.addEdge("to", "explore");
		test.addEdge("explore", "strange");
		test.addEdge("strange", "new");
		test.addEdge("new", "worlds");
		test.addEdge("worlds", "to");
		test.addEdge("to", "seek");
		test.addEdge("seek", "out");
		test.addEdge("out", "new");
		test.addEdge("new", "life");
		test.addEdge("life", "and");
		test.addEdge("and", "new");
		test.addEdge("new", "civilizations");
		test.print();
		for (String s : test.getBridgeEdge("explore", "new"))
			System.out.println(s);
		for (String s : test.getBridgeEdge("new", "and"))
			System.out.println(s);
		for (String s : test.shortestPath("strange", "worlds"))
			System.out.println(s);
		for (String s : test.randomWalk())
			System.out.println(s);
	} 
}
