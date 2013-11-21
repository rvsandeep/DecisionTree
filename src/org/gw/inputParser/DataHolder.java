package org.gw.inputParser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DataHolder{

	private String TRAINING_SAMPLES_FILE_NAME;
	private String TESTING_SAMPLES_FILE_NAME;
	private String ATTRIBUTES_FILE_NAME;
	private String POSITIVE_CLASS;
	private String NEGATIVE_CLASS;
	private List<List<Integer>> indexes=new ArrayList<List<Integer>>();
	public DataHolder(int option) {
		switch (option) {
		case 2:
			TRAINING_SAMPLES_FILE_NAME = "Training Data/Horse/horse.train";
			TESTING_SAMPLES_FILE_NAME = "Training Data/Horse/horse.test";
			ATTRIBUTES_FILE_NAME = "Training Data/Horse/horse.attribute";
			POSITIVE_CLASS = "healthy.";
			NEGATIVE_CLASS = "colic.";
			setHorseIndexes();
			break;

		case 3:
			TRAINING_SAMPLES_FILE_NAME = "Training Data/Whine/whine.train";
			TESTING_SAMPLES_FILE_NAME = "Training Data/Whine/whine.test";
			ATTRIBUTES_FILE_NAME = "Training Data/Whine/whine.attribute";
			POSITIVE_CLASS = "excellent.";
			NEGATIVE_CLASS = "poor.";
			setWineIndexes();

			break;
		default:
		case 1:
			TRAINING_SAMPLES_FILE_NAME = "Training Data/Water/water.train";
			TESTING_SAMPLES_FILE_NAME = "Training Data/Water/water.test";
			ATTRIBUTES_FILE_NAME = "Training Data/Water/water.attribute";
			POSITIVE_CLASS = "potable.";
			NEGATIVE_CLASS = "not potable.";
			setWaterIndexes();

		}
	}

	private void setWineIndexes() {
		List<Integer> f1 = Arrays.asList(8,9,10);
		List<Integer> f2 = Arrays.asList(7,8,9,10);
		List<Integer> f3 = Arrays.asList(5,6,8,9,10);
		this.indexes.add(f1);
		this.indexes.add(f2);
		this.indexes.add(f3);
	}

	private void setWaterIndexes() {
		List<Integer> f1 = Arrays.asList(12,13,14);
		List<Integer> f2 = Arrays.asList(1,3,7,10,12,13);
		List<Integer> f3 = Arrays.asList(0,1,2,3,5,6);
		List<Integer> f4 = Arrays.asList(6,7,12,13,14);
		List<Integer> f5 = Arrays.asList(3,6,9,10,12,13);
		List<Integer> f6 = Arrays.asList(0,1,3,6,8,10,12,14);
		List<Integer> f7 = Arrays.asList(3,6,8,9,10,13,14);
		this.indexes.add(f1);
		this.indexes.add(f2);
		this.indexes.add(f3);
		this.indexes.add(f4);
		this.indexes.add(f5);
		this.indexes.add(f6);
		this.indexes.add(f7);

	}

	private void setHorseIndexes() {
		List<Integer> f1 = Arrays.asList(10,11,15);
		List<Integer> f2 = Arrays.asList(4,10,11,14,15);
		List<Integer> f3 = Arrays.asList(3,9,10,12,15);
		List<Integer> f4 = Arrays.asList(0,5,9,12,13,14);
		List<Integer> f5 = Arrays.asList(10,15);
		List<Integer> f6 = Arrays.asList(0,1,2,3,5);
		List<Integer> f7 = Arrays.asList(0,11,12,13,14,15);
		List<Integer> f8 = Arrays.asList(2,4,8,12,13,14,15);
		this.indexes.add(f1);
		this.indexes.add(f2);
		this.indexes.add(f3);
		this.indexes.add(f4);
		this.indexes.add(f5);
		this.indexes.add(f6);
		this.indexes.add(f7);
		this.indexes.add(f8);
	}

	public String getTrainingSamplesFileName() {
		return TRAINING_SAMPLES_FILE_NAME;
	}

	public String getTestingSamplesFileName() {
		return TESTING_SAMPLES_FILE_NAME;
	}

	public String getAttributesFileName() {
		return ATTRIBUTES_FILE_NAME;
	}

	public String getPositiveClass() {
		return POSITIVE_CLASS;
	}

	public String getNegativeClass() {
		return NEGATIVE_CLASS;
	}
	
	public List<List<Integer>> getIndexes(){
		return indexes;
	}
}