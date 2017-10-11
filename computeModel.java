package lab1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;
import java.util.Vector;

public class computeModel {
	public static wordDirectedGraph graph = new wordDirectedGraph();
	
	public String getText(String file) throws IOException{
		StringBuilder strBuilder = new StringBuilder();
		BufferedReader reader = new BufferedReader(new FileReader(file));
	    String text = null;

	    while ((text = reader.readLine()) != null) {
	      strBuilder.append(" ");
	      strBuilder.append(text);
	    }
	    reader.close();
	    
	    return strBuilder.toString().toLowerCase();
	}
	
	public Vector<String> getWords(String text) {
		Vector<String> res = new Vector<String>();
		for(String str : text.replaceAll("[^a-zA-Z]", " ").split(" ")) {
			if (str.length() != 0)
				res.add(str);
		}
		return res; 
	}
	
	public wordDirectedGraph getGraph(String file) throws IOException {
		graph.clear();
		Vector<String> wordArr = getWords(getText(file));
		if (wordArr.size() == 0)
			return graph;
		graph.addWord(wordArr.get(0));
		for (int i=1; i<wordArr.size(); i++) {
			graph.addEdge(wordArr.get(i-1), wordArr.get(i));
		}
		
		return graph;
	}
	
	public Vector<String> getBridgeWord(String src, String des) {
		return graph.getBridgeEdge(src, des);
	}
	
	public String generateNewText(String text) {
		Random rdm = new Random();
		String oldText = text.toLowerCase();
		Vector<String> wordArr = getWords(oldText);
		if (wordArr.size() > 0) {
			int index = 1;
			while (index < wordArr.size()) {
				Vector<String> bridgeWordArr = 
						graph.getBridgeEdge(wordArr.get(index-1), wordArr.get(index));
				if (bridgeWordArr.size() > 0) {
					wordArr.add(index, bridgeWordArr.get(rdm.nextInt(bridgeWordArr.size())));
					index = index + 2;
				} else
					index = index + 1;
			}
		}
		return wordArr.toString().replaceAll("\\pP", "");
	}
	
	public Vector<String> getShortestPath(String src, String des) {
		return graph.shortestPath(src, des);
	}
	
	public Vector<String> getRandomWalkText() {
		return graph.randomWalk();
	}
	
	public static void main(String[] args) throws IOException{
		computeModel test = new computeModel();
		test.getGraph("/Users/ligang/Downloads/1.txt").print();
		System.out.println(test.generateNewText("Seek to explore new and exciting synergies"));
    }
}
//hello my
