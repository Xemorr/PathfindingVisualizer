package me.xemor.pathfindingvisualizer.Algorithms.NoHeuristic;

import java.util.List;
import java.util.stream.Collectors;

import me.xemor.pathfindingvisualizer.Algorithms.Node;

public class NoWeightingNode {

    private Node node;
    /**
     * The previously visited node that was used to discover this node.
     */
    private NoWeightingNode parentNode;

    public NoWeightingNode(Node node) {
        this.node = node;
    }

    /**
     * Gets horizontal/vertical neighbours of the DepthFirstNode. Internally calls Node#getNeighbours
     * @param noWeightingGrid
     * @return List<DepthFirstNode>
     */
    public List<NoWeightingNode> getNeighbours(NoWeightingGrid noWeightingGrid) {
        return this.getNode().getNeighbours(noWeightingGrid.getGrid()).stream().map(noWeightingGrid::getDepthFirstNode).collect(Collectors.toList());
    }

    /**
     * Sets the parent node of the depth first node.
     * @param parentNode
     */
    public void setParentNode(NoWeightingNode parentNode) {
        this.parentNode = parentNode;
    }

    public Node getNode() {
        return node;
    }

    public NoWeightingNode getParentNode() {
        return parentNode;
    }
}
