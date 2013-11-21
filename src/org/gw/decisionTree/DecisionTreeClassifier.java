package org.gw.decisionTree;

import java.util.HashMap;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

import org.gw.inputParser.Sample;
import org.gw.inputParser.SampleCollection;

public class DecisionTreeClassifier {
	private Discretizer dSC;
	private double accuracy;

	public double getAccuracy() {
		return accuracy;
	}

	private DecisionTree dt;

	public DecisionTreeClassifier(DecisionTree dt, SampleCollection testSamples) {
		this.dt = dt;
		setSamplesAndFeatures(testSamples);
		dSC = new Discretizer(testSamples);
//		System.out.println(SampleCollection.FEATURES.subFeatureList);
		classifyAndSetAccuracy(testSamples.sampleList,
				SampleCollection.FEATURES.subFeatureList);
	}

	private void setSamplesAndFeatures(SampleCollection testSamples) {
		testSamples.FEATURES.setFeatures(this.dt.getIndexes());
		for (Sample sample : testSamples.sampleList) {
			sample.setFeatures(this.dt.getIndexes());
		}		
	}

	private void classifyAndSetAccuracy(List<Sample> samples,
			List<String> features) {
		double correct = 0, wrongs = 0;
		for (Sample sample : samples) {
			String classifiedAs = classify(sample, features);
//			System.out.println("classifiedAs " + classifiedAs);
			if (classifiedAs.equals(sample.getSampleclass()))
				correct++;
			else
				wrongs++;
		}
		accuracy = (correct / (correct + wrongs)) * 100;
	}

	private String classify(Sample sample, List<String> features) {
		DecisionTreeNode root = dt.rootNode;
//		System.out.println("classifying  " + sample);
		while (true) {
			if (!root.isLeaf()) {
				String featureName = root.getFeatureName();
//				System.out.println(featureName);
				int featureindex = features.indexOf(featureName);
				Double featureVal = sample.getDescreteValue(featureindex);
				if (root.getChildren().keySet().contains(featureVal)) {
					root = root.getChildren().get(featureVal);
				} else {
//					System.out.println("getting prominent");
					return prominentClass(root);
				}
			} else
				return root.getNodeclass();
		}
	}

	private String prominentClass(DecisionTreeNode root) {
		int pos = 0, neg = 0;
		Queue<DecisionTreeNode> curr = new LinkedBlockingQueue<DecisionTreeNode>();
		curr.add(root);
		while (!curr.isEmpty()) {
			DecisionTreeNode node = curr.poll();
			if (node.isLeaf()) {
				if (SampleCollection.CLASS.equals(node.getNodeclass())) {
					pos++;
				} else {
					neg++;
				}
			} else {
				HashMap<Double, DecisionTreeNode> children = node.getChildren();
				for (Double vals : children.keySet()) {
//					System.out.println("children " + children.get(vals));
					curr.add(children.get(vals));
				}
			}
			
		}
//		System.out.println("result " + pos + " " + neg);
		return pos > neg ? SampleCollection.CLASS.POSITIVECLASS
				: SampleCollection.CLASS.NEGATIVECLASS;
	}
}
