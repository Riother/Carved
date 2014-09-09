package lab4;

public class Graph {
	
	private int[][] vertex;
	private int[] visitedVertices;
	private int numVertices;
	private int numEdges;
	
	public Graph(int numVertices){
		vertex = new int[numVertices][numVertices];
		visitedVertices = new int[numVertices];
		for(int i = 0; i <numVertices; i++) {
			visitedVertices[i] = 0;
			for(int j = 0; j < numVertices; j++) {
				vertex[i][j] = 0;
			}
		}
		
		this.numVertices = numVertices;
		numEdges = 0;
	}
	
	public int vertexCount() {
		return numVertices;
	}
	
	public int edgeCount() {
		return numEdges;
	}
	
	public int getFirst(int vertexIndex) {
		for(int i = 0; i < numVertices; i++) {
			 if(vertex[vertexIndex][i] != 0)
				  return i;
		}
		return vertexCount();
	}
	
	public int getNext(int vertexIndex, int neighborIndex) {
		if(neighborIndex + 1 > numVertices) 
			return vertexCount();

		for(int i = neighborIndex + 1; i < numVertices; i++) {
				if(vertex[vertexIndex][i] != 0)
					return i;
		}
		
		return vertexCount();
	}
	
	public void addEdge(int vertexIndex, int neighborIndex, int weight) {
		if(vertexIndex != neighborIndex && !isEdge(vertexIndex, neighborIndex)) {
			vertex[vertexIndex][neighborIndex] = 1;
			numEdges++;
		}
	}
	
	public void removeEdge(int vertexIndex, int neighborIndex) {
		if(vertexIndex != neighborIndex && isEdge(vertexIndex, neighborIndex)) {
			vertex[vertexIndex][neighborIndex] = 0;
			numEdges--;
		}
	}
	
	public boolean isEdge(int vertexIndex, int neighborIndex) {
		return (vertex[vertexIndex][neighborIndex] != 0) ? true : false;
	}
	
	public boolean hasIncomingEdges(int vertexIndex) {
		for(int i = 0; i < numVertices; i++) {
			if(isEdge(i, vertexIndex))
				return true;
		}
		
		return false;
	}
	
	public int degree(int vertexIndex) {
		int value = 0;
		
		for(int i = 0; i < numVertices; i++) {
			if(vertex[vertexIndex][i] != 0)
				value++;
		}
		
		
		return value;
	}
	
	public int getMark(int vertexIndex) {
		return visitedVertices[vertexIndex];
	}
	
	public void setMark(int vertexIndex, int color) {
		visitedVertices[vertexIndex] = color;
	}
	
	public boolean anyMarked(int mark) {
		boolean marked = false;
		
		for(int i = 0; i < numVertices && !marked; i++) {
			if(visitedVertices[i] != mark)
				marked = true;
		}
		
		return marked;
	}
}
