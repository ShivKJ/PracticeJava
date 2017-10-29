package algo.graphs.traversal.mst;

import java.util.Collection;

import algo.graphs.Edge;
import algo.graphs.traversal.TraversalVertex;

public class Prim<V extends TraversalVertex<?>, W extends Edge<? extends V>> extends MSTGraph<V, W> {

	public Prim(Collection<V> vertices, Collection<W> edges) {
		
		super(vertices, edges);
	}

}
