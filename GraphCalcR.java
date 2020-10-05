package GS_ClusCoeff;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;


public class GraphCalcR{
	
	ArrayList<Double> metW;
	
	GraphCalcR(){
		metW = new ArrayList<Double>();
	}
	//R routines are used for calculating weights for efficiency
	//it writes the SampleGraph_ACC_P1_R*****.txt file again and again after removing edge
	//it is not efficient but this code is just a prototype
	//to check other properties, e.g., Assortativity, we call appropriate file accordingly
	public ArrayList<Double> callR4Weight(ArrayList<Integer> com, int read, double frac){
		
		OutputFile out   = new OutputFile();
		//change the path as per your directory hierarchy
		String Rexe      = "//usr//lib//R//bin//Rscript"; 
		String Rfile     = "//home//userN//JavaData//GGS//RFiles//ClusCoeff_W.R";
		String sample    = out.outPutPath + "SampleGraph_ACC_P1_R"+read+"_S"+frac+".txt";
		String str       = Rexe+" "+Rfile+" "+sample;
		
		Process p = null;
		try {		
			p = Runtime.getRuntime().exec(str);			
		} catch (IOException e) {
			e.printStackTrace();
		}
		InputStream stdout    = p.getInputStream();
	    BufferedReader reader = new BufferedReader(new InputStreamReader(stdout,StandardCharsets.UTF_8));
	    String line;
	    String tokens[];
	    try{
	       while((line = reader.readLine()) != null){
	    	   tokens = line.split(" ");
	    	   metW.add(Double.parseDouble(tokens[0]));
	    	   com.add(Integer.parseInt(tokens[3]));
	       }
	    }catch(IOException e){
	       System.out.println("Exception in reading output"+ e.toString());
	    }
	    
	    return metW;
	}
	

	public double callR4GS(int read, double frac, int index){
		OutputFile out   = new OutputFile();
		//change the path as per your directory hierarchy
		String Rexe      = "//usr//lib//R//bin//Rscript"; 
		String Rfile     = "//home//userN//JavaData//GGS//RFiles//ClusCoeff_GS.R";
		String sample    = out.outPutPath + "SampleGraph_ACC_P2_R"+read+"_S"+frac+".txt";
		String str       = Rexe+" "+Rfile+" "+sample+" "+index;
		
		double val = 0;
		
		Process p = null;
		try {		
			p = Runtime.getRuntime().exec(str);			
		} catch (IOException e) {
			e.printStackTrace();
		}
		InputStream stdout    = p.getInputStream();
	    BufferedReader reader = new BufferedReader(new InputStreamReader(stdout,StandardCharsets.UTF_8));
	    String line;
	    try{
	       while((line = reader.readLine()) != null){
	            val = Double.parseDouble(line); 
	        }
	    }catch(IOException e){
	          System.out.println("Exception in reading output"+ e.toString());
	    }
		return val;		
	}	
}