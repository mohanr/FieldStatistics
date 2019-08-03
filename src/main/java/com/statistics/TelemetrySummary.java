package com.statistics;

public class TelemetrySummary {

    private final double min;
    private final double max;
    private final double mean;

    public TelemetrySummary(double min, double max, double mean) {
        this.mean = mean;
        this.max = max;
        this.min = min;
    }

    public double getMin() {
        return min;
    }

    public double getMax() {
        return max;
    }

    public double getMean() {
        return mean;
    }

    public String toString(){
        StringBuffer sb = new StringBuffer();
        sb.append( "Max. " + max).append( " Min. " + min ).append( " Mean " + mean );
        return sb.toString();
    }
}

