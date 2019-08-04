package com.statistics;

import org.HdrHistogram.Histogram;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

 /** A more detailed library to insert the Histogram into a Time-series database  is needed
  * to make this more versatile.
  */

public class FieldStatistics {

	Map<Long, Histogram> histogramMap = new ConcurrentHashMap<Long,Histogram>();

	static Histogram histogram = new Histogram(TimeUnit.SECONDS.toNanos(10), 3);

	Logger logger = LoggerFactory.getLogger("com.statistics");

	/**
	 * Record in Histogram if the timestamps are within a period of 30 days.
	 * Remove the old Histogram becauae we are maintainiing only field values for the past 30 days.
	 * A more detailed library to insert the Histogram into a Time-series database  is needed
	 * to make this more versatile.
	 * @param value
	 * @param timeStamp
	 */
	public  void record( long value, long timeStamp ) {

		histogramMap.computeIfAbsent(timeStamp, k -> store( k ) );
		for (Map.Entry<Long, Histogram> entry : histogramMap.entrySet()) {
			Long key = entry.getKey();
			LocalDateTime newDate =
					LocalDateTime.ofInstant(Instant.ofEpochMilli(key), ZoneId.of("UTC"));
			LocalDateTime oldDate =
					LocalDateTime.ofInstant(Instant.ofEpochMilli(timeStamp), ZoneId.of("UTC"));
			long different = ChronoUnit.DAYS.between(newDate, oldDate);
			logger.debug("Difference between dates is {}", different );
		}
		histogram.recordValue(value);

	}

	private Histogram store( long timeStamp ){
		histogram.reset();
		return histogram;
	}

	public double[]  summarize() {
		//histogram.reset();
		histogram.outputPercentileDistribution(System.out, 1000.0);
		return new double[] {histogram.getMinValue(), histogram.getMaxValue(), histogram.getMean() };
	  }
}
