1. A prototype implementation of Generalized Guided Sampling Algorithm. (For Clustering Coefficent Property)
2. The entry point is TestClass.java
3. The input files are
	a. DFS_Graph.txt (adjacency list of G such that neighbors of each node are sorted in descending order of degrees)
	b. ***_Edges.txt (Edge list of the same graph)
4. GraphCalcR.java uses R routine to calculate weight of edges. Paths and file names are hard coded 
in this file so need to be changed accordingly.
5. In FirstPhase.java, we perform DFS and extract a sample with extra edges.
6. In SecondPhase.java, we trim the sample with the help of edge weights.
7. The R files are used for calculating edge weight and we have a separate R file for each property.
8. Changing the R file called in GraphCalc.R, we can use this implementation for other properties. 
9. For testing Path length, please change the condition on line 53 of SecondPhase.java to
if(currentMetV == 0 ){
	continue;
}
