package GS_ClusCoeff;

import java.io.BufferedReader;
import java.io.FileReader;


public class TestClass{
	  // The dataset names are in the same order as the target values in GetReading.java file
	  public static String[] DS_Name = {  "Gowalla",	"CiteSeer",	"DBLP",	"FourSquare",	"Digg",  "YouTube",	"Lastfm",	"Hyves",	"Flixster",	"Facebook","Twitter","Skitter"};
		  	  
	  public static String currentDS;
	  
	   public static void main(String[]args) throws java.io.IOException{		   
		    ReadGraph X = new ReadGraph();
		    GetReading GR = new GetReading(1);
		    			
			for(int i=0; i<12; i++){
				currentDS = DS_Name[i];
				System.out.println("=========Reading Undirected Original Graph======" + currentDS);	
				readOriginalData(X, currentDS);
				GR.processData(X, i, i);				
				System.out.println("Finished Processing--------"+currentDS);
				X.Vertices.clear();
				X.Edges.clear();
			}		
		   
		    System.out.println("Sampling Done...");
		   
	   }

	   public static void readOriginalData(ReadGraph X, String DS){
		   
		   OutputFile out = new OutputFile();
		   //DFS_Graph file contains adjacency list such that the neighbors of a node
		   // are sorted in descending order of degree.
		   try (BufferedReader br = new BufferedReader(new FileReader(out.inPutPath+"DFS_Graph.txt"))) {			
			    String line;
			    String delimit = "\\s+";			   
			    while ((line = br.readLine()) != null) {			
			      String tokens[] =  line.split(delimit);
			      if(line!=null) X.addVertex(tokens);
			    }
			} catch (Exception e) {	e.printStackTrace();} 	
		   
		   //_Edges file is read for speeding up induction step in FirstPhase.java
		   try (BufferedReader br = new BufferedReader(new FileReader(out.inPutPath+DS+"_Edges.txt"))) {			
			    String line;
			    String delimit = "\\s+";			   
			  
			    while ((line = br.readLine()) != null) {
			      String tokens[] =  line.split(delimit);
			      if(line!=null) X.addEdges(tokens);
			    }
			} catch (Exception e) {	e.printStackTrace();} 	
		   
		    X.DisplayGraph();
 
	   }
}