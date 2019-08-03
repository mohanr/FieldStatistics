package com.statistics;

import org.HdrHistogram.Histogram;

import java.util.concurrent.TimeUnit;

public class FieldStatistics {


	static Histogram histogram = new Histogram(TimeUnit.SECONDS.toNanos(10), 3);

	public  void record( long value ) {
		  histogram.recordValue(value);
	  }

	public double[]  summarize() {
		//histogram.reset();
		histogram.outputPercentileDistribution(System.out, 1000.0);
		return new double[] {histogram.getMinValue(), histogram.getMaxValue(), histogram.getMean() };
	  }
}
