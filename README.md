## Spring Boot with [HdrHistogram](https://hdrhistogram.github.io/HdrHistogram/)

Thie repository contains Spring Boot code that uses _DoubleHistograms_ to ensure that space and time are constant
when recording and aggregating the minimum and maximum values.

It maintains one such _Histogram_ for the past 30 days but several such _Histograms_ for different periods can be
stored effectively as the storage required is very less.

As of now the _timestamps_ are not invidually persisted as I am still researching the approach.

Once we set the log location `file>C:/FieldStatistics/statistics.log</file>` in `\src\main\resources`,
`mvn install` will execute all tests.

## Tests

All tests should pass but they can be improved.

#REST endpoint tests

`com.application.StreamingStatisticsApplicationTest` has many tests to exercise the endpoints. These tests
do not `assert` the result. This is again pending now.

# Unit tests

`com.unit.StatisticsPeriodTest` has tests that exercise the _DoubleHistograms_. This is crucial as the foundation
is tested to prove that we get back the aggregated values we need.

# Concurrency

Here I list some concurrency mechanisms used by _HdrHistogram_. A more advanced description is  

1. Writers do not block. Writers record observed values.
2. Readers only block for other readers. Readers work with a stable copy of the underlying data for data analysis.

Further exploration of  [concurrency](http://hdrhistogram.github.io/HdrHistogram/JavaDoc/index.html?org/HdrHistogram/Histogram.html) is planned.

The other concurrency feature the code uses is a _java.util.concurrent.ConcurrentHashMap_ to store a _Histogram_
datastructure associated with a _timestamp_. When a new _Timestamp_ is received so that difference between the
stored _timestamp_ and the new one is more than 30 days, the old _Histogram_ is removed from the Map and the new
one stored. We wait till 30 days pass and repeat the same procedure.



