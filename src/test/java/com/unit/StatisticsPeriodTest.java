package com.unit;

import com.statistics.FieldStatistics;
import org.HdrHistogram.DoubleHistogram;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import static org.junit.Assert.assertEquals;

public class StatisticsPeriodTest {

    static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm'Z'", Locale.ENGLISH);

    Logger logger = LoggerFactory.getLogger("com.statistics.test");


    /**
     * More test values are required to understand the true nature of the
     * precision aspects.
     */
    @Test
    public void histogramBasic(){
        DoubleHistogram histo = new DoubleHistogram(3);
        histo.recordValue(6.0);
        histo.recordValue(1.0);
        histo.recordValue(5.0);
        histo.recordValue(8.0);
        histo.recordValue(3.0);
        histo.recordValue(7.0);
        logger.debug( "Min {} Max. {} Mean {}", histo.getMinValue(), histo.getMaxValue() , histo.getMean());
        assertEquals(1.0, histo.getMinValue(), 0.01);
        assertEquals(8.0, histo.getMaxValue(), 0.01);
    }

    /**
     * More test values are required to understand the true nature of the
     * precision aspects.
     */
    @Test
    public void histogramBasic1(){
        DoubleHistogram histo = new DoubleHistogram(3);
        histo.recordValue(0.82);
        histo.recordValue(0.55);
        histo.recordValue(0.63);
        histo.recordValue(0.12);
        histo.recordValue(0.22);
        histo.recordValue(0.62);
        logger.debug( "Min {} Max. {} Mean {}", histo.getMinValue(), histo.getMaxValue() , histo.getMean());
        assertEquals(0.12, histo.getMinValue(), 0.01);
        assertEquals(0.82, histo.getMaxValue(), 0.01);

    }

    @Test
    public void fieldStatisticsBasic(){
        FieldStatistics histo = new FieldStatistics();
        LocalDate date = LocalDate.parse("2019-04-23T08:50Z", dateTimeFormatter);
        histo.record(0.82,date.toEpochDay());
        histo.record(0.55,date.toEpochDay());
        histo.record(0.63,date.toEpochDay());
        histo.record(0.12,date.toEpochDay());
        histo.record(0.22,date.toEpochDay());
        histo.record(0.62,date.toEpochDay());
        double[] values =  histo.summarize();
        assertEquals(0.12, values[0], 0.01);
        assertEquals(0.82, values[1], 0.01);

    }
}
