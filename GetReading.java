package GS_ClusCoeff;

public class GetReading{
	
	private int readings;
	private double[] sampFrac  = {0.001,0.002, 0.003, 0.004, 0.005};
	//values in the same order as the dataset names in TestClass.java
	//These are only Average CC values, change them to other values, e.g., Assortativity for
	//test other properties.
	private double degValues[] = { 9.6681,	7.1629,	6.6221,	10.0623,	15.3273,	5.265,	7.584,	3.9602,	6.2763,	14.27,4.09, 13.08,};
	private double metValues[]  ={ 0.3163,	0.7673,	0.7321,	0.2209,	    0.162,	    0.1723,	0.1378,	0.1035,	0.2058,	0.2094,0.019, 0.258,}; //Average CC
	
	
	public GetReading(int r){
		readings = r;
	}
		
	public void processData(ReadGraph OriginalGraph, int d, int c){
		
		 int skip=-1;
		 double degree=0,metV=0;
		 FirstPhase fp = new FirstPhase();
						
         for(int i=0; i<sampFrac.length; i++){
        	 degree = degValues[d];
        	 metV   = metValues[d];
			for(int j=0; j<readings; j++){				
				   System.out.println("=======================Sample Size= "+sampFrac[i]+" Reading= "+j+" Degree= "+degree+" MetV= "+metV);								  								 								 
				   skip = fp.Sampling(OriginalGraph, sampFrac[i], degree, j, metV);
				   if(skip==0){
					   System.out.println("No Extra Edges.....Trying again");
					   j--;
					   continue;
				   }
			}		
		}			
		System.out.println("Data Written Successfully^~^");
	}
}