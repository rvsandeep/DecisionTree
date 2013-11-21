package org.gw.inputParser;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Sample {

	private ArrayList<Double> allFeatureVals = new ArrayList<Double>();
	private ArrayList<Double> subFeatureVals = new ArrayList<Double>();
	public ArrayList<Double> subDiscreteVals;
	private String sampleclass;

	public Sample(Sample sample) {
		this.subFeatureVals = new ArrayList<Double>(sample.subFeatureVals);
		this.subDiscreteVals = new ArrayList<Double>(sample.subDiscreteVals);
		this.sampleclass = new String(sample.getSampleclass());
	}

	public void setFeatures(List<Integer> indexes) {
		this.subFeatureVals.clear();
		for (Integer index : indexes)
			this.subFeatureVals.add(this.allFeatureVals.get(index));
		subDiscreteVals = new ArrayList<Double>(subFeatureVals.size());
	}

	public Sample(String sample) {
		StringTokenizer st = new StringTokenizer(sample, ",");
		int numTokens = st.countTokens();
		while (--numTokens != 0) {
			this.allFeatureVals.add(Double.parseDouble(st.nextToken()));
		}
		this.sampleclass = st.nextToken();
		this.subFeatureVals = new ArrayList<Double>(allFeatureVals);
		this.subDiscreteVals = new ArrayList<Double>(subFeatureVals.size());
	}

	public String getSampleclass() {
		return sampleclass;
	}

	public void addDescreteVal(int index, Double descreteVal) {
		subDiscreteVals.add(index, descreteVal);
	}

	public Double getDescreteValue(int index) {
		return subDiscreteVals.get(index);
	}

	public Double getValue(int index) {
		return subFeatureVals.get(index);
	}

	public void removeVals(int index) {
		this.subDiscreteVals.remove(index);
		this.subFeatureVals.remove(index);
	}

	@Override
	public String toString() {
		int i = 0;
		String sample = "Sample [";
		for (Double feature : subFeatureVals) {
			if (i < SampleCollection.FEATURES.subFeatureList.size())
				sample += SampleCollection.FEATURES.subFeatureList.get(i) + "="
						+ feature + " ";
			i++;
		}
		sample += " ] " + sampleclass;
		return sample;
	}
}
