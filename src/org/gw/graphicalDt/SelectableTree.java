package org.gw.graphicalDt;

import java.awt.BorderLayout;
import java.awt.Container;
import java.util.HashMap;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.MutableTreeNode;

import org.gw.decisionTree.DecisionTree;
import org.gw.decisionTree.DecisionTreeNode;

public class SelectableTree extends JFrame implements TreeSelectionListener {

	private JTree tree;
	private JTextField currentSelectionField;
	private Queue<DecisionTreeNode> levelOrderdt = new LinkedBlockingQueue<DecisionTreeNode>();

	public SelectableTree(DecisionTree dt) {
		super("Decision Tree");
		WindowUtilities.setNativeLookAndFeel();
		addWindowListener(new ExitListener());
		Container content = getContentPane();

		MutableTreeNode gTreeRoot = new DefaultMutableTreeNode("");

		makeGraphicalTree(gTreeRoot,dt.rootNode);
		
		tree = new JTree(gTreeRoot);
		tree.addTreeSelectionListener(this);
		content.add(new JScrollPane(tree), BorderLayout.CENTER);
		currentSelectionField = new JTextField("Current Selection: NONE");
		content.add(currentSelectionField, BorderLayout.SOUTH);
		setSize(500, 575);
		setVisible(true);
	}

	private void makeGraphicalTree(MutableTreeNode gTreeRoot,
			DecisionTreeNode rootNode) {
//		System.out.println( rootNode+" "+rootNode.getNumChild());
		if (rootNode.isLeaf()) {
			((DefaultMutableTreeNode) gTreeRoot).add(new DefaultMutableTreeNode(rootNode));
			return;
		}
		HashMap<Double, DecisionTreeNode> children = rootNode.getChildren();
		int i=0;
		for (Double val : children.keySet()) {
			((DefaultMutableTreeNode) gTreeRoot).add(new DefaultMutableTreeNode(rootNode.getFeatureName()+"="+val));
			makeGraphicalTree((MutableTreeNode) gTreeRoot.getChildAt(i++), children.get(val));
		}
	}

	public void valueChanged(TreeSelectionEvent event) {
		if(tree.getLastSelectedPathComponent()!=null)
		currentSelectionField.setText("Current Selection: "
				+ tree.getLastSelectedPathComponent().toString());
	}
}
