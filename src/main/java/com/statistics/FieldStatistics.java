package com.statistics;

import org.HdrHistogram.Histogram;

public class FieldStatistics {


	static Histogram histogram = new Histogram(10, 3);

	public  void record( long value ) {
		  histogram.recordValue(value);
	  }

	public double[]  summarize() {
		return new double[] {histogram.getMinValue(), histogram.getMaxValue(), histogram.getMean() };
	  }
}
