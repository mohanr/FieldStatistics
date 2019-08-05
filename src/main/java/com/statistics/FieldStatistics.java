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

	 DoubleHistogram  histogram;

	/**
	 * Record in Histogram if the timestamps are within a period of 30 days.
	 * Remove the old Histogram becauae we are maintainiing only field values for the past 30 days.
	 * A more detailed library to insert the Histogram into a Time-series database  is needed
	 * to make this more versatile.
	 * @param value
	 * @param timeStamp
	 */
	public  void record( double value, long timeStamp ) {


		reset(  timeStamp );
		histogram.recordValue(value);

	}

	private void reset( long timeStamp ){
		histogramMap.computeIfAbsent(timeStamp, k -> store( k ) );
		for (Map.Entry<Long, DoubleHistogram> entry : histogramMap.entrySet()) {
			Long key = entry.getKey();
			long difference = reconstructDateTime(key).until( reconstructDateTime(timeStamp), ChronoUnit.DAYS);
			if( difference > 30 ){
				logger.info("Difference between dates is {}", difference );
				histogramMap.remove( key);
				histogramMap.computeIfAbsent(timeStamp, k -> store( k ) );
			}
		}
	}

	 /**
	  * Reconstruct LocalDateTime to check the difference. There may be a direct way
	  * to do this.
	  * TODO The difference should be 30 days and 1 second ( if that is the precision )
	  * TODO so that we are sure that we aggregate for 30 days exactly.
	  * @param timeStamp
	  * @return reconstructed LocalDateTimeDateTime
	  */
	 private LocalDateTime reconstructDateTime( long timeStamp ){

		 LocalDateTime reconstructedDateTime =
				 LocalDateTime.ofInstant(Instant.ofEpochMilli(  timeStamp),
						 ZoneId.of("UTC"));
		 return reconstructedDateTime;
	 }

	private DoubleHistogram store( long timeStamp ){
		histogram =  new DoubleHistogram ( 3);
		return histogram;
	}

	public double[]  summarize() {
		double[] keyValues = new double[] {histogram.getMinValue(), histogram.getMaxValue(), histogram.getMean() };
		//histogram.reset();
		return keyValues;
	}
}
