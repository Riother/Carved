package lab4;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class TopologicalSort {

	public TopologicalSort() {
	}
	
	public List<Integer> sort(Graph g) {
		
		List<Integer> sortedValues = new ArrayList<Integer>();
		Set<Integer> startValues = new HashSet<Integer>();
		for(int i = 0; i < g.vertexCount(); i++) {
			if(!g.hasIncomingEdges(i))
				startValues.add(i);
		}
		
		while(!startValues.isEmpty()) {
			Iterator<Integer> iterator = startValues.iterator();
			int tempValue = iterator.next();
			startValues.remove(tempValue);
			sortedValues.add(tempValue);
			for(int i = 0; i < g.vertexCount(); i++) {
				if(g.isEdge(tempValue, i)) {
					g.removeEdge(tempValue, i);
					if(!g.hasIncomingEdges(i))
						startValues.add(i);
				}
			}
		}
		
		if(g.edgeCount() > 0) {
			System.out.println("Unable to perform Topological Sort on graph!");
			return null;
		}
		else			
			return sortedValues;
	}
}
