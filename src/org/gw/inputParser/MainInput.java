package org.gw.inputParser;

import java.util.Scanner;

import org.gw.decisionTree.DecisionTree;
import org.gw.decisionTree.DecisionTreeClassifier;
import org.gw.graphicalDt.SelectableTree;

public class MainInput {

	public static void main(String[] args) {
		System.out.println("Enter training Set: 1: Water 2:Horse 3:Wine");
		Scanner scan = new Scanner(System.in);
		DataHolder dt = new DataHolder(scan.nextInt());
		SampleCollection trainSamples = new SampleCollection(
				dt.getTrainingSamplesFileName(), dt);
		// Discretizer dsct = new Discretizer(trainSamples);

		// System.out.println(dsct);
		// System.out.println(trainSamples);

		while (true) {
			DecisionTree st = new DecisionTree(trainSamples);
			// System.out.println(trainSamples.SAMPLELIST.get(0));
			SampleCollection testSamples = new SampleCollection(
					dt.getTestingSamplesFileName(), dt);

			DecisionTreeClassifier dtCtrain = new DecisionTreeClassifier(st,
					trainSamples);
			System.out.println("Training Set Accuracy: "
					+ dtCtrain.getAccuracy());
			DecisionTreeClassifier dtCtest = new DecisionTreeClassifier(st,
					testSamples);
			System.out.println("Test Set Accuracy: " + dtCtest.getAccuracy());
			System.out.println("Enter zero to stop: ");
			if (scan.nextInt() == 0) {
				break;
			}
		}
		System.out.println("stopped");
	}

}
