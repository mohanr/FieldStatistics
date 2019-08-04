package com.statistics;

import org.HdrHistogram.DoubleHistogram;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

 /** A more detailed library to insert the Histogram into a Time-series database  is needed
  * to make this more versatile.
  */

public class FieldStatistics {

	 private Map<Long, DoubleHistogram> histogramMap = new ConcurrentHashMap<Long,DoubleHistogram >();

	 private Logger logger = LoggerFactory.getLogger("com.statistics");

	 DoubleHistogram  histogram = new DoubleHistogram ( 3);

	/**
	 * Record in Histogram if the timestamps are within a period of 30 days.
	 * Remove the old Histogram becauae we are maintainiing only field values for the past 30 days.
	 * A more detailed library to insert the Histogram into a Time-series database  is needed
	 * to make this more versatile.
	 * @param value
	 * @param timeStamp
	 */
	public  void record( double value, long timeStamp ) {


		histogram.recordValue(value);

	}

	private void reset( long timeStamp ){
		histogramMap.computeIfAbsent(timeStamp, k -> store( k ) );
		for (Map.Entry<Long, DoubleHistogram> entry : histogramMap.entrySet()) {
			Long key = entry.getKey();
			LocalDateTime newDate =
					LocalDateTime.ofInstant(Instant.ofEpochMilli(key), ZoneId.of("UTC"));
			LocalDateTime oldDate =
					LocalDateTime.ofInstant(Instant.ofEpochMilli(timeStamp), ZoneId.of("UTC"));
			long different = ChronoUnit.DAYS.between(newDate, oldDate);
			//logger.info("Difference between dates is {}", different );
		}
	}

	private DoubleHistogram store( long timeStamp ){
		logger.info("histogram.reset()" );
		//histogram.reset();
		return histogram;
	}

	public double[]  summarize() {
		double[] keyValues = new double[] {histogram.getMinValue(), histogram.getMaxValue(), histogram.getMean() };
		//histogram.reset();
		return keyValues;
	}
}
