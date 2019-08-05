package com.unit;

import com.statistics.FieldStatistics;
import org.HdrHistogram.DoubleHistogram;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Locale;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class StatisticsPeriodTest {

    static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm'Z'", Locale.ENGLISH);

    Logger logger = LoggerFactory.getLogger("com.statistics.test");


    /**
     * More test values are required to understand the true nature of the
     * precision aspects.
     */
    @Test
    public void histogramBasic() {
        DoubleHistogram histo = new DoubleHistogram(3);
        histo.recordValue(6.0);
        histo.recordValue(1.0);
        histo.recordValue(5.0);
        histo.recordValue(8.0);
        histo.recordValue(3.0);
        histo.recordValue(7.0);
        logger.debug("Min {} Max. {} Mean {}", histo.getMinValue(), histo.getMaxValue(), histo.getMean());
        assertEquals(1.0, histo.getMinValue(), 0.01);
        assertEquals(8.0, histo.getMaxValue(), 0.01);
    }

    /**
     * More test values are required to understand the true nature of the
     * precision aspects.
     */
    @Test
    public void histogramBasic1() {
        DoubleHistogram histo = new DoubleHistogram(3);
        histo.recordValue(0.82);
        histo.recordValue(0.55);
        histo.recordValue(0.63);
        histo.recordValue(0.12);
        histo.recordValue(0.22);
        histo.recordValue(0.62);
        logger.debug("Min {} Max. {} Mean {}", histo.getMinValue(), histo.getMaxValue(), histo.getMean());
        assertEquals(0.12, histo.getMinValue(), 0.01);
        assertEquals(0.82, histo.getMaxValue(), 0.01);

    }

    @Test
    public void fieldStatisticsBasic() {
        FieldStatistics histo = new FieldStatistics();
        LocalDate date = LocalDate.parse("2019-04-23T08:50Z", dateTimeFormatter);
        histo.record(0.82, date.toEpochDay());
        histo.record(0.55, date.toEpochDay());
        histo.record(0.63, date.toEpochDay());
        histo.record(0.12, date.toEpochDay());
        histo.record(0.22, date.toEpochDay());
        histo.record(0.62, date.toEpochDay());
        double[] values = histo.summarize();
        assertEquals(0.12, values[0], 0.01);
        assertEquals(0.82, values[1], 0.01);

    }

	  /** Reconstruct LocalDateTime to check the difference. There may be a direct way
	  * to do this.
       */
    @Test
    public void dateDifference(){

        LocalDateTime oldDate = LocalDateTime.parse("2019-04-23T08:50Z", dateTimeFormatter);
        LocalDateTime newDate = oldDate.plusDays(30);

        LocalDateTime oldDateTime =
                LocalDateTime.ofInstant(oldDate.atZone(ZoneId.of("UTC")).toInstant(),ZoneId.of("UTC") );
        ZonedDateTime ozdt = oldDateTime.atZone(ZoneId.of("UTC"));

        LocalDateTime newDateTime =
                LocalDateTime.ofInstant(newDate.atZone(ZoneId.of("UTC")).toInstant(),ZoneId.of("UTC") );
        ZonedDateTime nzdt = oldDateTime.atZone(ZoneId.of("UTC"));

        LocalDateTime reconstructOldDateTime =
                LocalDateTime.ofInstant(Instant.ofEpochMilli(ozdt.toInstant().toEpochMilli()),
                        ZoneId.of("UTC"));

        LocalDateTime reconstructNewDateTime =
                LocalDateTime.ofInstant(Instant.ofEpochMilli(nzdt.toInstant().toEpochMilli()),
                        ZoneId.of("UTC"));

        long difference = oldDateTime.until( newDateTime, ChronoUnit.DAYS);
        System.out.println( difference );
        assertTrue( "Difference in days is greater than 30", difference == 30 );
    }

    @Test
    public void fieldStatistics30DayPeriod() {

        FieldStatistics histo = new FieldStatistics();

        LocalDateTime oldDateTime = LocalDateTime.parse("2019-04-23T08:50Z", dateTimeFormatter);
        ZonedDateTime ozdt = oldDateTime.atZone(ZoneId.of("UTC"));
        long oldTimeStamp = ozdt.toInstant().toEpochMilli();

        histo.record(0.82, oldTimeStamp);
        histo.record(0.55, oldTimeStamp);
        histo.record(0.63, oldTimeStamp);
        histo.record(0.12, oldTimeStamp);
        histo.record(0.22, oldTimeStamp);
        histo.record(0.62, oldTimeStamp);
        double[] oldValues = histo.summarize();
        assertEquals(0.12, oldValues[0], 0.01);
        assertEquals(0.82, oldValues[1], 0.01);

        LocalDateTime newDateTime = oldDateTime.plusDays(30);
        ZonedDateTime nzdt = newDateTime.atZone(ZoneId.of("UTC"));
        long newTimeStamp = nzdt.toInstant().toEpochMilli();


        histo.record(6.0, newTimeStamp);
        histo.record(1.0, newTimeStamp);
        histo.record(5.0, newTimeStamp);
        histo.record(8.0, newTimeStamp);
        histo.record(3.0, newTimeStamp);
        histo.record(7.0, newTimeStamp);
        double[] newValues = histo.summarize();
        assertEquals(1.0, newValues[0], 0.01);
        assertEquals(8.0, newValues[1], 0.01);


    }
}