package org.gw.inputParser;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Features {
	public List<String> allFeaturesList = new ArrayList<String>();
	public List<String> subFeatureList = new ArrayList<String>();
	public List<Features.BinningVars> binningVars = new ArrayList<Features.BinningVars>();

	private class BinningVars {
		public double minValue;
		public double delta;

		public BinningVars(double d, double m) {
			delta = d;
			minValue = m;
		}
	}

	@Override
	public String toString() {
		int i=0;
		String features = "Features [";
		for (String feature : subFeatureList) {
			features += i+"="+feature + " ";
			i++;
		}
		features += "]";
		return features;
	}

	public void setFeatures(List<Integer> indexes){
		binningVars.clear();
		subFeatureList.clear();
		for (Integer index : indexes) {
			subFeatureList.add(allFeaturesList.get(index));
			binningVars.add(null);
		}
		
	}
	public Features(String feature_file) {
		try {
			BufferedReader bf = new BufferedReader(new FileReader(feature_file));
			String feature = bf.readLine();
			while (feature != null) {
				allFeaturesList.add(feature);
				feature = bf.readLine();
				binningVars.add(null);
			}
			this.subFeatureList = new ArrayList<String>(this.allFeaturesList);
		} catch (FileNotFoundException e) {
			System.out.println("file name error");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("error reading line");
			e.printStackTrace();
		}
	}

	public void addBinningVar(int index, double delta, double minValue) {
		binningVars.set(index, new BinningVars(delta, minValue));
	}

	public String getAllFeatures() {
		int i=0;
		String features = "Features [";
		for (String feature : allFeaturesList) {
			features += i+"="+feature + " ";
			i++;
		}
		features += "]";
		return features;
	}

}
