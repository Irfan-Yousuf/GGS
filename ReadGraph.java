package GS_ClusCoeff;
import java.util.ArrayList;


public class ReadGraph implements Cloneable{
	
	public ArrayList <Vertex> Vertices;
	public ArrayList <Edge> Edges;
		
	public ReadGraph(){
		Vertices = new ArrayList<Vertex>();
		Edges    = new ArrayList<Edge>();
		
	}
	
	public Object clone(){
		 ReadGraph copy = new ReadGraph();
		 Vertex v;
		 Edge e;
		 Neighbor nr;
		 for(int i=0; i<this.Vertices.size(); i++){
			 v = new Vertex(this.Vertices.get(i).vertexID);
			 copy.Vertices.add(v);
			 for(int j=0; j<this.Vertices.get(i).NeighborList.size(); j++){
				 nr = new Neighbor(this.Vertices.get(i).NeighborList.get(j).neighborID);
				 copy.Vertices.get(i).NeighborList.add(nr);
			 }
		 }
		 for(int i=0; i<this.Edges.size(); i++){
			 e = new Edge(this.Edges.get(i).from, this.Edges.get(i).to);
			 copy.Edges.add(e);
		 }
		 return copy;
		
	}
		
	public void addVertex(String tokens[]){
		
		Vertex temp;
		Neighbor Nr;
		
		
		temp = new Vertex(Integer.parseInt(tokens[0]));
		this.Vertices.add(temp);
		
		for(int i=1; i<tokens.length; i++){
			Nr = new Neighbor(Integer.parseInt(tokens[i]));
			this.Vertices.get(this.Vertices.size()-1).NeighborList.add(Nr);			
		}		
	}
		
	public void addEdges(String tokens[]){
		
		Edge e;	   
		int from=0,to=0;		
		
		from = Integer.parseInt(tokens[0]);
		to = Integer.parseInt(tokens[1]);
		
		e = new Edge(from,to);
		Edges.add(e);
	}
	
	
	
	public void DisplayGraph(){
		
		System.out.println("Total Vertices= "+this.Vertices.size());
		System.out.println("Total    Edges= "+this.Edges.size());

	}
	
}