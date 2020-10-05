/*******************************************************************************
 * calculate the Weight of each edge
 **************************************************************************************/

package GS_ClusCoeff;

import java.util.ArrayList;


public class CalcWeight{
	

public ArrayList<Edge> calcweights(ArrayList <Edge> sampleEdges, double orgMetV, int read, double frac){	
	
	
	ArrayList<Double> weights;
	ArrayList<Integer> comp = new ArrayList<Integer>();
	GraphCalcR gc     = new GraphCalcR();
	weights           = gc.callR4Weight(comp, read, frac);
	
	System.out.println("Assigning weights.... ");
	for(int i=0; i<sampleEdges.size(); i++){		
		sampleEdges.get(i).metW = weights.get(i).doubleValue();
	}
	SelectionSort(sampleEdges); 
	return sampleEdges;
}

public static void SelectionSort(ArrayList<Edge> edgeList){ /*Ascending Order */
	int from,to;
	double weight;
	int comp;

	Edge temp;
	int index;
	for(int i=0; i<edgeList.size()-1; i++){
		index = i;
		for(int j=i+1; j<edgeList.size(); j++){    			    			
			if(edgeList.get(j).metW < edgeList.get(index).metW){// < is Ascending
				index = j;    				
			}
		}
		
		from = edgeList.get(index).from;
		to = edgeList.get(index).to;
		weight = edgeList.get(index).metW;
		comp = edgeList.get(index).comp;
		
		
		temp = new Edge(edgeList.get(i).from,edgeList.get(i).to,edgeList.get(i).metW, edgeList.get(i).comp);		
		edgeList.set(index, temp);
		
		temp = new Edge(from,to,weight, comp);		
		edgeList.set(i, temp);
	}
}


}