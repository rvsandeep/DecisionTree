package org.gw.inputParser;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SampleCollection {

	public static Features FEATURES;
	public List<Sample> sampleList = new ArrayList<Sample>();
	public static Classification CLASS;
	public static DataHolder dt;

	public SampleCollection(String sample_file, DataHolder dt) {

		this.dt = dt;
		initFeatures(dt.getAttributesFileName());

		initSamples(sample_file, sampleList);

		initClass();

	}

	@Override
	public String toString() {
		return FEATURES + "\n" + sampleList + "\n" + CLASS;
	}

	private static void initFeatures(String attr_file) {
		FEATURES = new Features(attr_file);
	}

	private static void initSamples(String samples_file, List<Sample> sampleList) {
		try {
			BufferedReader bf = new BufferedReader(new FileReader(samples_file));
			String sample = bf.readLine();
			while (sample != null) {
				// System.out.println(new Sample(sample));
				sampleList.add(new Sample(sample));
				sample = bf.readLine();
			}
		} catch (FileNotFoundException e) {
			System.out.println("file name error");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("error reading line");
			e.printStackTrace();
		}
	}

	private static void initClass() {
		CLASS = new Classification(dt.getPositiveClass(), dt.getNegativeClass());
	}
}
