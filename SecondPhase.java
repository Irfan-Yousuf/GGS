/*******************************************************************************
 * In Second Phase, we remove extra edges
 **************************************************************************************/

package GS_ClusCoeff;

import java.util.ArrayList;
import java.util.Random;


public class SecondPhase{

	
	public void removeExtraEdges(ArrayList<Edge> sampleEdges, int maxExtraEdges, double orgMetV,  int read, double frac){
	
	double currentMetV=0, slope=0, expMetV=0, initMetV=0;
	int index=0,  median=1;
    int extraEdgesRemoved = 0;
    int countA=0, countB=0;
	Random rnd = new Random();
	GraphCalcR gc = new GraphCalcR();
	OutputFile out = new OutputFile();
	//we save file so that R routine can read it 
	out.openFile(out.outPutPath+"SampleGraph_ACC_P2_R"+read+"_S"+frac+".txt");
	out.createSampleFileEL(sampleEdges);
	out.closeFile();
	
	System.out.println("Performing Guided Induction for Average Clustering Coefficient ... ");
	currentMetV = gc.callR4GS(read, frac, 1);	
	initMetV = currentMetV;
	System.out.println("CC Before Guidance= "+currentMetV+" Target= "+orgMetV);
	
	//edges are sorted in Ascending order of (weights difference = fullSample - OneEdgeremoved)
	//+ve weight (index=last) means OneEdgeRemoved decreases value
	//-ve weight (index=0) means OneEdgeremoved increases weight
	// 0----median means removing will increase value
	//median---last means removing will decrease value
	median  = (int) (sampleEdges.size()*0.5);
	slope   = (1-(currentMetV/orgMetV)) / maxExtraEdges;
	expMetV = initMetV + (slope * extraEdgesRemoved * orgMetV);
	System.out.println("SampleEdges = "+sampleEdges.size()+" Median= "+median+" Slope= "+slope);
	while (extraEdgesRemoved < maxExtraEdges){
		if( currentMetV < expMetV) {//Remove to increase 
			index = rnd.nextInt(median); //between 1-median	
			countA++;
		}
		else{ // Remove to decrease
			index = rnd.nextInt(median) + median - 1;
			countB++;
		}
		
		currentMetV = gc.callR4GS(read, frac, index+1);	
		if(currentMetV > 2 ){
			continue;
		}
		
		sampleEdges.remove(index);
		extraEdgesRemoved++; 
		//save the updated edge list after removing an edge
		//NOT efficient implementation but OKish for a prototype
		out.openFile(out.outPutPath+"SampleGraph_ACC_P2_R"+read+"_S"+frac+".txt");
		out.createSampleFileEL(sampleEdges);
		out.closeFile();
		
		expMetV = initMetV + (slope * extraEdgesRemoved * orgMetV);
		
		median = (int) (sampleEdges.size()*0.5);
	}

	System.out.println("CC After Guidance= "+currentMetV);
	System.out.println("Count L:  "+countA+" count R: "+countB);
}
}