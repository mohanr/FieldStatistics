package com.statistics;

import org.HdrHistogram.Histogram;

public class FieldStatistics {


	static Histogram histogram = new Histogram(10, 3);
		  
		  private static void record( long value ) {
			  histogram.recordValue(value);
		  }

		  private double[]  getValues() {
		    return new double[] {histogram.getMinValue(), histogram.getMaxValue(), histogram.getMean() };
		  }
}
