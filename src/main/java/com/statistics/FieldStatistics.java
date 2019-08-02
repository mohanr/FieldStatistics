package com.statistics;

import java.util.concurrent.TimeUnit;

import org.HdrHistogram.Histogram;

public class FieldStatistics {

	private static final Histogram HDR_HISTOGRAM =
		    new Histogram(TimeUnit.MINUTES.toNanos(1), 2);
		  
		  private static void observe(int i, long time) {
		    HDR_HISTOGRAM.recordValue(time);
		  }
		  private static void report() {
		    System.out.printf("#%d,%d,%d,%d,%d,%d,%d\n",
		                      HDR_HISTOGRAM.getMinValue(), 
		                      HDR_HISTOGRAM.getMaxValue());
		    HDR_HISTOGRAM.reset();
		  }
}
