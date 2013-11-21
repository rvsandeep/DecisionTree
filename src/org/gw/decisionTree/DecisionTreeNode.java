package org.gw.decisionTree;

import java.util.HashMap;

public class DecisionTreeNode {

	private boolean isLeaf;
	private String nodeclass;
	private String featureName;

	private HashMap<Double, DecisionTreeNode> children;

	public HashMap<Double, DecisionTreeNode> getChildren() {
		return children;
	}

	@Override
	public String toString() {
		if (isLeaf)
			return nodeclass;
		return featureName;
	}

	public void setChildNode(Double featureValue, DecisionTreeNode childNode) {
		children.put(featureValue, childNode);
	}

	public int getNumChild() {
		if(this.isLeaf)
			return 0;
		return this.children.size();
	}

	DecisionTreeNode(boolean isLeaf, String nodeclass) {
		setLeaf(isLeaf);
		setNodeclass(nodeclass);
	}

	DecisionTreeNode(String featureName, int numArcs) {
		setFeatureName(featureName);
		setNumChildren(numArcs);
		setLeaf(false);
	}

	private void setNumChildren(int numArcs) {
		children = new HashMap<Double, DecisionTreeNode>(numArcs);
	}

	public boolean isLeaf() {
		return isLeaf;
	}

	public void setLeaf(boolean isLeaf) {
		this.isLeaf = isLeaf;
	}

	public String getNodeclass() {
		return nodeclass;
	}

	public void setNodeclass(String nodeclass) {
		this.nodeclass = nodeclass;
	}

	public String getFeatureName() {
		return featureName;
	}

	public void setFeatureName(String featureName) {
		this.featureName = featureName;
	}

}
