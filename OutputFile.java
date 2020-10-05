package GS_ClusCoeff;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;


class OutputFile{
	
	 BufferedWriter outPutFile = null;

	 String inPutPath;
	 String outPutPath;
	
	
	public OutputFile(){
		
		 inPutPath =  "//home//userN//JavaData//GGS//" + TestClass.currentDS+"//";
		 outPutPath = "//home//userN//JavaData//GGS//" + TestClass.currentDS+"//";
		 
	}
	
	public void openFile(String name){
		
		
		  try {
				outPutFile = new BufferedWriter(new FileWriter(name));
			  } catch (IOException e) {			
				e.printStackTrace();
			  }
	  }
	 
	  public void createSampleFile(ArrayList <Vertex> Vertices){
		  Object obj=null;	
		  try{						
			  for(int x=0; x<Vertices.size(); x++){				
					for(int y=0; y<Vertices.get(x).NeighborList.size(); y++){						
						obj = Vertices.get(x).vertexID+" ";
						outPutFile.append(obj.toString());	
						obj = Vertices.get(x).NeighborList.get(y).neighborID;
						outPutFile.append(obj.toString());
						outPutFile.newLine();
					}
				} 		
			 }catch(IOException e){
				 System.out.println("Can not Write to File:");
				 e.printStackTrace();
			 }
	  }
	  
	  public void createSampleFileEL(ArrayList <Edge> Edges){
		  Object obj=null;	
		  try{						
			  for(int x=0; x<Edges.size(); x++){				
						obj = Edges.get(x).from+" "+Edges.get(x).to;
						outPutFile.append(obj.toString());							
						outPutFile.newLine();
					}		
			 }catch(IOException e){
				 System.out.println("Can not Write to File:");
				 e.printStackTrace();
			 }
	  }
	  
	  public void saveCurrentVals(ArrayList <Edge> Edges){
		  Object obj=null;	
		  try{						
			  for(int x=0; x<Edges.size(); x++){				
						obj = Edges.get(x).metW;
						outPutFile.append(obj.toString());							
						outPutFile.newLine();
					}		
			 }catch(IOException e){
				 System.out.println("Can not Write to File:");
				 e.printStackTrace();
			 }
	  }	    
	  
	  public void closeFile(){
		  
		  try {
				outPutFile.close();
			} catch (IOException e) {			
				e.printStackTrace();
			} 
		  
	  }
}
