package org.gw.decisionTree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;

import org.gw.graphicalDt.SelectableTree;
import org.gw.inputParser.Classification;
import org.gw.inputParser.Sample;
import org.gw.inputParser.SampleCollection;

public class DecisionTree {

	public DecisionTreeNode rootNode;
	private Queue<DecisionTreeNode> levelOrderdt = new LinkedBlockingQueue<DecisionTreeNode>();
	private List<Integer> indexes;
	static Scanner scan = new Scanner(System.in);

	public List<Integer> getIndexes() {
		return indexes;
	}

	public DecisionTree(SampleCollection sample_collection) {
		selectFeatures(sample_collection);
		System.out.println(sample_collection.FEATURES);
		new Discretizer(sample_collection);
		// System.out.println("discrete"+dSC);
		rootNode = ID3(sample_collection.FEATURES.subFeatureList,
				sample_collection.CLASS, sample_collection.sampleList);
		// System.out.println(rootNode);
		new SelectableTree(this);
	}

	@Override
	public String toString() {
		String tree = levelOrderTraverse(rootNode);
		// StringBuilder tree = new StringBuilder("");
		// getTreeStructure(rootNode, tree, 0);
		return tree;
	}

	private void selectFeatures(SampleCollection sample_collection) {
		System.out.println(sample_collection.FEATURES.getAllFeatures());
		System.out.println("Enter 1:Pre selected Features 2: Manual Selection");
		switch (scan.nextInt()) {
		case 1:
			auto_selection(sample_collection);
			break;
		case 2:
			manualSelection();
			break;
		}
		// System.out.println("indexes " + indexes);
		sample_collection.FEATURES.setFeatures(indexes);
		for (Sample sample : sample_collection.sampleList) {
			sample.setFeatures(indexes);
		}
	}

	private void auto_selection(SampleCollection sample_collection) {

		int i = 1, option;

		System.out.println("Select: ");
		for (List<Integer> indexes : sample_collection.dt.getIndexes()) {
			String features = "";
			int j = 0;
			for (Integer index : indexes) {

				features += sample_collection.FEATURES.allFeaturesList
						.get(index);
				if (j < indexes.size())
					features += ", ";
				j++;
			}
			System.out.println(i + ": " + features);
			i++;
		}
		System.out.println(i + ": All");
		option = scan.nextInt();
		if (option == i) {
			indexes = new ArrayList<Integer>();
			for (i = 0; i < sample_collection.FEATURES.allFeaturesList
					.size(); i++) {
				indexes.add(i);
			}
			return;
		}
		indexes = sample_collection.dt.getIndexes().get(option - 1);
	}

	private void manualSelection() {
		System.out
				.println("Select Features from the list: (Enter a charcter to stop)");
		indexes = new ArrayList<Integer>();
		while (scan.hasNextInt()) {
			indexes.add(scan.nextInt());
		}
		scan.next();
	}

	private String levelOrderTraverse(DecisionTreeNode rootNode2) {
		levelOrderdt.add(rootNode2);
		String returnString = "\n";
		while (!levelOrderdt.isEmpty()) {
			DecisionTreeNode node = levelOrderdt.poll();
			if (node.isLeaf()) {
				returnString += "-> " + node.getNodeclass();
			} else {
				returnString += node.getFeatureName();
				HashMap<Double, DecisionTreeNode> children = node.getChildren();
				String child = "";
				for (Double val : children.keySet()) {
					levelOrderdt.add(children.get(val));
					child += "(" + val + ","
							+ children.get(val).getFeatureName() + ") ";
				}
				returnString += "\n" + child;
			}
		}
		return returnString;
	}

	private String getProminentClass(List<Sample> samples) {
		int pos = 0, neg = 0;
		for (Sample sample : samples) {
			if (SampleCollection.CLASS.equals(sample.getSampleclass())) {
				pos++;
			} else {
				neg++;
			}
		}
		return pos > neg ? SampleCollection.CLASS.POSITIVECLASS
				: SampleCollection.CLASS.NEGATIVECLASS;
	}

	/**
	 *
	 * @param featureList
	 * @param samples
	 * @return best feature to perform the next split based on the samples and available features
	 */
	private int getBestFeature(List<String> featureList, List<Sample> samples) {
		double maxInformationGain = Double.MIN_VALUE;
		int maxGainIndex = 0;
		for (int i = 0; i < featureList.size(); i++) {
			double infoGain = getInformationGain(samples, i);
			// System.out.println(maxInformationGain+" "+infoGain);
			if (Double.compare(infoGain, maxInformationGain) > 0) {
				maxInformationGain = infoGain;
				maxGainIndex = i;

			}
		}
		return maxGainIndex;

	}

	/**
	 *
	 * @param featureList
	 * @param decisionclass
	 * @param samples
	 * @return return the root of a decision tree computed with ID3 algorithm
	 */
	private DecisionTreeNode ID3(List<String> featureList,
			Classification decisionclass, List<Sample> samples) {
		DecisionTreeNode tempNode;
		if (samples.size() == 0) {
			System.out.println("Failure: sample list empty");
			System.exit(1);
		}
		if (featureList.isEmpty()) {
			tempNode = new DecisionTreeNode(true, getProminentClass(samples));
			return tempNode;
		}
		int bestFeature = getBestFeature(featureList, samples);
		Set<Double> bestAttrVals = getUniqueVals(bestFeature, samples);
		tempNode = new DecisionTreeNode(featureList.get(bestFeature),
				bestAttrVals.size());
		List<String> subFeatures = new ArrayList<String>(featureList);
		subFeatures.remove(bestFeature);

		for (Double attrVal : bestAttrVals) {
			List<Sample> subset = getSubset(samples, bestFeature, attrVal);
			// if(subset.size()==0)
			// System.out.println("there exist");
			DecisionTreeNode childNode = ID3(
					new ArrayList<String>(subFeatures), decisionclass, subset);
			tempNode.setChildNode(attrVal, childNode);
		}
		return tempNode;
	}

	private List<Sample> getSubset(List<Sample> samples, int bestFeature,
			Double attrVal) {
		List<Sample> subset = new ArrayList<Sample>();
		for (Sample sample : samples) {
			if (sample.getDescreteValue(bestFeature).equals(attrVal)) {
				Sample newSample = new Sample(sample);
				newSample.removeVals(bestFeature);
				subset.add(newSample);
			}
		}
		return subset;
	}

	private Set<Double> getUniqueVals(int bestFeature, List<Sample> samples) {
		Set<Double> uniqvals = new HashSet<Double>();
		for (Sample sample : samples) {
			uniqvals.add(sample.getDescreteValue(bestFeature));
		}
		return uniqvals;
	}

	public double getEntropy(List<Sample> samples) {
		double pos = 0, neg = 0;

		for (Sample sample : samples) {
			if (SampleCollection.CLASS.equals(sample.getSampleclass()))
				pos++;
			else
				neg++;
		}
		return getEntropy(pos, neg, samples.size());
	}

	/**
	 *
	 * @param pos
	 * @param neg
	 * @param size
	 * @return Entropy of the split, computed using the positive and negative sample probability
	 */
	public double getEntropy(double pos, double neg, int size) {
		double ppos, pneg, ent;
		ppos = pos / size;
		pneg = neg / size;
		if (Double.compare(ppos, 0) == 0)
			ppos = 1;
		if (Double.compare(pneg, 0) == 0)
			pneg = 1;
		ent = -(ppos * (Math.log(ppos) / Math.log(2)))
				- (pneg * (Math.log(pneg) / Math.log(2)));
		// System.out.println("entropy is "+ent);
		return Math.abs(ent);
	}

	/**
	 *
	 * @param samples
	 * @param featureIndex
	 * @return Information gain associated with the feature
	 */
	public double getInformationGain(List<Sample> samples, int featureIndex) {
		double entropy = 0.0;
		Set<Double> uniquevals = getUniqueVals(featureIndex, samples);
		for (Double val : uniquevals) {
			double pos = 0, neg = 0;
			for (Sample sample : samples) {
				if (sample.getDescreteValue(featureIndex).equals(val)) {
					if (SampleCollection.CLASS.equals(sample.getSampleclass())) {
						pos++;
					} else {
						neg++;
					}
				}
			}
			// System.out.println(pos+" "+neg+" "+getEntropy(pos, neg, (int)
			// (pos + neg)));
			entropy += ((pos + neg) / samples.size())
					* getEntropy(pos, neg, (int) (pos + neg));
		}
		entropy = getEntropy(samples) - entropy;
		return entropy;
	}
}
