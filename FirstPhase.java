/*******************************************************************************
 * Perform DFS and get Sample Graph in first Phase
 **************************************************************************************/

package GS_ClusCoeff;

import java.util.ArrayList;
import java.util.Random;


public class FirstPhase{
	
	
public int Sampling(ReadGraph OriginalGraph, double sFrac, double degree, int reading, double metV){
		
	ReadGraph localOriginalGraph       = (ReadGraph) OriginalGraph.clone();	
	ArrayList <Vertex> sampleGraph     = new ArrayList <Vertex>();
	ArrayList <Edge> sampleEdges       = new ArrayList<Edge>();
	ArrayList <Edge> ccEdges           = new ArrayList<Edge>();

	
	double sampleFrac                  = sFrac;
	Random rnd                         = new Random(System.currentTimeMillis());
	ArrayList <Integer> lifo           = new ArrayList <Integer>();	
	int currentSampledNodes            = 0;
	int maxSampledNodes                = (int) (sampleFrac*localOriginalGraph.Vertices.size());
	int index=0;
	Vertex temp;
	Neighbor Nr;		
	boolean alreadyExist = false, rmv = false;
	long startTime, elapsedTime;
	
	
	System.out.println("First Phase (Performing High Degree DFS)");
    System.out.println("Required Nodes in Sample= "+maxSampledNodes);
    
    startTime = System.currentTimeMillis();
	while(currentSampledNodes < maxSampledNodes){
		if(currentSampledNodes >= maxSampledNodes) break;		
	 while(lifo.size()==0){
			index = rnd.nextInt(localOriginalGraph.Vertices.size());	
			if(localOriginalGraph.Vertices.get(index).Sampled==false){
				lifo.add(localOriginalGraph.Vertices.get(index).vertexID);
				localOriginalGraph.Vertices.get(index).Sampled=true;
				makeAllInstancesTrue(localOriginalGraph,localOriginalGraph.Vertices.get(index).vertexID);
				currentSampledNodes++;
			}
		}   
		temp = new Vertex(lifo.get(lifo.size()-1)); 
		//check if the node is already in the Sample Graph
		alreadyExist = false;
		for(int k=0; k<sampleGraph.size(); k++){
			if(sampleGraph.get(k).vertexID == temp.vertexID){
				alreadyExist = true;
				break;
			}
		}
		if(alreadyExist == false) {
			sampleGraph.add(temp); 
			currentSampledNodes++; 
			if(currentSampledNodes >= maxSampledNodes) break;
		}
	
		//find node in Original Graph
		for(int h=0; h<localOriginalGraph.Vertices.size(); h++){
			if(OriginalGraph.Vertices.get(h).vertexID == temp.vertexID){
				index=h;
				break;
			}
		}
		//remove the node from LIFO if all neighbors have been visited
		rmv = true;
		for(int j=0; j<localOriginalGraph.Vertices.get(index).NeighborList.size(); j++){
		   if(localOriginalGraph.Vertices.get(index).NeighborList.get(j).Sampled == false){
			   rmv = false;
			   break;
		   }
		}
		if(rmv == true) { 
		   lifo.remove(lifo.size()-1); 	//remove the last node	
		   continue;
		}
		//perform DFS
		for(int i=0; i<localOriginalGraph.Vertices.get(index).NeighborList.size(); i++){
			Nr = new Neighbor(localOriginalGraph.Vertices.get(index).NeighborList.get(i).neighborID);
			if(localOriginalGraph.Vertices.get(index).NeighborList.get(i).Sampled==false) {					
				lifo.add(Nr.neighborID); //add to lifo only unvisited nodes
				localOriginalGraph.Vertices.get(index).NeighborList.get(i).Sampled=true;	
				makeAllInstancesTrue(localOriginalGraph,localOriginalGraph.Vertices.get(index).NeighborList.get(i).neighborID);	
				break;					
			}			
		}					
   }				
   while(lifo.size()>0){
	   temp = new Vertex(lifo.get(lifo.size()-1)); lifo.remove(lifo.size()-1); 
	   //check before adding if already in Sample Graph 
	    alreadyExist = false;
		for(int k=0; k<sampleGraph.size(); k++){
			if(sampleGraph.get(k).vertexID == temp.vertexID){
				alreadyExist = true;
				break;
			}
		}
		if(alreadyExist == false) {
			sampleGraph.add(temp);
		}
	}//END DFS
   
		performInduction(localOriginalGraph.Edges, sampleGraph , sampleEdges);
		System.out.println("DFS Completed......");
		System.out.println("Total Edges in Sample= "+sampleEdges.size());
		System.out.println("Unique Nodes in Sample= "+sampleGraph.size());	
				
		
		int expectedEdges      = (int) (degree * maxSampledNodes *0.5);
		int extraEdges         = (int) (sampleEdges.size() - expectedEdges);
		System.out.println("Expected Edges= "+expectedEdges);
		System.out.println("Extra Edges= "+extraEdges);
		
		if(extraEdges < 1 ) return 0; //Avoid unguided readings
		
		OutputFile out = new OutputFile();
		out.openFile(out.outPutPath+"SampleGraph_ACC_P1_R"+reading+"_S"+sFrac+".txt");
		out.createSampleFileEL(sampleEdges);
		out.closeFile();
		
		System.out.println("Second Phase......Average Clustering Coefficient  Weight");
	
		
		CalcWeight wet    = new CalcWeight();
		ccEdges           = wet.calcweights(sampleEdges, metV, reading, sFrac);
		
		
		SecondPhase sp = new SecondPhase();		
		sp.removeExtraEdges(ccEdges, extraEdges, metV, reading, sFrac);
		elapsedTime = System.currentTimeMillis() - startTime;
		
		System.out.println("Time taken in sec: "+elapsedTime/1000);
				
		System.out.println("After Guidance  .......");
		System.out.println("Total Edges in Sample= "+sampleEdges.size());
		System.out.println("Unique Nodes in Sample= "+sampleGraph.size());			
				
		return 1;
   }

	private void makeAllInstancesTrue(ReadGraph localOriginalGraph,int ID){
	
		   for(int i=0; i<localOriginalGraph.Vertices.size(); i++){
			   if(localOriginalGraph.Vertices.get(i).vertexID==ID) localOriginalGraph.Vertices.get(i).Sampled=true;
			   for(int j=0; j<localOriginalGraph.Vertices.get(i).NeighborList.size(); j++){
				   if(localOriginalGraph.Vertices.get(i).NeighborList.get(j).neighborID == ID){
					   localOriginalGraph.Vertices.get(i).NeighborList.get(j).Sampled = true;
				   }
			   }
		   }
	}
	
public void performInduction(ArrayList <Edge> originalEdgeList, ArrayList<Vertex> sampleGraph , ArrayList<Edge> sampleEdges){
		
		int VS1, VS2;
		int index1=0,index2=0;
		boolean VS1_exist=false, VS2_exist=false, edgeExist=true;
		Neighbor temp1,temp2;		
		int ind=0;
		int from, to;
		
		for(int i=0; i<originalEdgeList.size(); i++){
			edgeExist=true;
			
			VS1 = originalEdgeList.get(i).from;
			VS2 = originalEdgeList.get(i).to;
			if(VS1==VS2) {continue;} //avoid self-references
			VS1_exist = false; VS2_exist=false; 
			for(int j=0; j<sampleGraph.size(); j++){
				if(sampleGraph.get(j).vertexID == VS1) VS1_exist=true;
				if(sampleGraph.get(j).vertexID == VS2) VS2_exist=true;
				if(VS1_exist == true && VS2_exist==true) break;
			}
			//check if edge already induced	
			if(VS1_exist == true && VS2_exist == true){
				edgeExist = false;
				index1=-1; index2=-1;
				for(int h=0; h<sampleGraph.size(); h++){
					if(sampleGraph.get(h).vertexID == VS1) index1=h; //from
					if(sampleGraph.get(h).vertexID == VS2) index2=h; //to
					
					if(index1>=0 && index2>=0) break;
				}
				
				for(int k=0; k<sampleGraph.get(index1).NeighborList.size(); k++){
					if(VS2 == sampleGraph.get(index1).NeighborList.get(k).neighborID){
						edgeExist=true;
						break;
					}
				}
				
				for(int k=0; k<sampleGraph.get(index2).NeighborList.size(); k++){
					if(VS1 == sampleGraph.get(index2).NeighborList.get(k).neighborID){
						edgeExist=true;
						break;
					}
				}
			}
			//Add if edge is not induced
			
			if(edgeExist == false){				
				 temp1 = new Neighbor(VS2);
				 sampleGraph.get(index1).NeighborList.add(temp1);
				 temp2 = new Neighbor(VS1);
				 sampleGraph.get(index2).NeighborList.add(temp2);	
				 
				 from = sampleGraph.get(index1).vertexID;
				 to   = sampleGraph.get(index2).vertexID; //temp1.neighborID;
				 sampleEdges.add(new Edge(from,to));
				 ind++;
			}			
		}
		System.out.println("Edges Induced= "+ind+" sample Edges= "+sampleEdges.size());
	}
}