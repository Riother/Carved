package lab4;

import java.util.List;

import lab4.edu.neumont.ui.Picture;

public class Driver {

	public static void main(String[] args) {
//		testTopologicalSort();
		testSeamCarving();
	}
	
	public static void testTopologicalSort() {
		Graph g = new Graph(8);
		TopologicalSort ts = new TopologicalSort();
		
		g.addEdge(0, 3, 0);
		g.addEdge(0, 4, 0);
		
		g.addEdge(1, 3, 0);
		
		g.addEdge(2, 4, 0);
		g.addEdge(2, 7, 0);
		
		g.addEdge(3, 5, 0);
		g.addEdge(3, 6, 0);
		g.addEdge(3, 7, 0);
		
		g.addEdge(4, 6, 0);
		
		
		List<Integer> sortedValues = ts.sort(g);
		if(sortedValues != null) {
			System.out.print("Sorted: ");
			for(int i = 0; i < sortedValues.size(); i++) {
				System.out.print(sortedValues.get(i) + " ");
			}
		}
	}
	
	public static void testSeamCarving() {
		Picture before = new Picture("modified.png");
		
		SeamCarver sc = new SeamCarver(before);
		
		for(int i = 0; i < 20; i++) {
			int[] indices = sc.findVerticalSeam();
			sc.removeVerticalSeam(indices);
		}
		System.out.println("Done with Vertical carving");
		
		for(int i = 0; i < 20; i++) {
			int[] indices = sc.findHorizontalSeam();
			sc.removeHorizontalSeam(indices);
		}
		System.out.println("Done with Horizontal carving");
		
		
	}
}
