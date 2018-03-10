package org.gw.decisionTree;

import java.util.ArrayList;
import java.util.List;

import org.gw.inputParser.Features;
import org.gw.inputParser.Sample;
import org.gw.inputParser.SampleCollection;

public class Discretizer {
	public static int NUMBER_OF_BINS = 6;
	private double minValue = Double.MAX_VALUE;
	SampleCollection sc;

	public Discretizer(SampleCollection samples) {
		this.sc = samples;
		this.discretizeEqualBinner(samples, NUMBER_OF_BINS);
	}

	@Override
	public String toString() {
		List<Sample> samples = sc.sampleList;
		String discreteVals = "";
		for (Sample sample : samples) {
			discreteVals += "[ Sample , " + sample.subDiscreteVals + " ]\n";
		}
		return discreteVals;
	}


	/**
	 *
	 * @param samples
	 * @param binSize
	 * discretizes the values of a certain feature
	 * of a collection of samples. After discretization, the values can be any
	 * integer between 0 and binSize (inclusive) featureIndex specifies the
	 * index of the feature in the featureList array of the samples collection
	 */
	public void discretizeEqualBinner(SampleCollection samples, int binSize) {
		Features features = samples.FEATURES;
		List<String> featureList = features.subFeatureList;

		for (int i = 0; i < featureList.size(); i++) {
			double delta = computeBinWidth(samples, i, binSize);

			// System.out.println("Delta = " + delta);
			features.addBinningVar(i, delta, minValue);

			discretizeSamples(samples, i, delta);

		}
	}

	/**
	 *
	 * @param samples
	 * @param featureIndex
	 * @param binSize
	 * @return Computes delta = (max(feature) - min(feature)) / binSize
	 */
	private double computeBinWidth(SampleCollection samples, int featureIndex,
			int binSize) {

		minValue = Double.MAX_VALUE;
		double max = Double.MIN_VALUE;

		for (Sample sample : samples.sampleList) {
			Double feature = sample.getValue(featureIndex);
			if (feature < minValue)
				minValue = feature;
			if (feature > max)
				max = feature;
		}

		double delta = (max - minValue) / binSize;
		return delta;
	}

	/**
	 * Using the "Equal Width Interval Binning" algorithm for discretization.
	 * 
	 * See the paper for more information -
	 * http://robotics.stanford.edu/users/sahami/papers-dir/disc.pdf
	 */
	private void discretizeSamples(SampleCollection samples, int featureIndex,
			double delta) {
		List<Sample> samplesList = samples.sampleList;

		for (Sample sample : samplesList) {
			/*
			 * double newValue =
			 * (int)((sample.getFeature(featureName).getValue() - minValue) /
			 * delta); sample.setFeature(new Feature(featureName, newValue));
			 */
			discretizeSample(sample, featureIndex, delta, minValue);
		}
	}

	/**
	 * A public method to discretize the feature - featureName of sample based
	 * on delta and min
	 */
	public void discretizeSample(Sample sample, int featureIndex, double delta,
			double min) {
		double newValue = (int) ((sample.getValue(featureIndex) - min) / delta);

		// Check for extraneous values
		if (newValue < 0)
			newValue = 0;
		if (newValue > NUMBER_OF_BINS)
			newValue = NUMBER_OF_BINS;
		sample.addDescreteVal(featureIndex, newValue);
	}

}
