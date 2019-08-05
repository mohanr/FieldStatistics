## Spring Boot with [HdrHistogram](https://hdrhistogram.github.io/HdrHistogram/)

Thie repository contains Spring Boot code that uses _DoubleHistograms_ to ensure that space and time are constant
when recording and aggregating the minimum and maximum values.

It maintains one such _Histograms_ for the past 30 days but several such _Histograms_ for different periods can be
stored effectively as the storage required is very less.

Once we set the log location `file>C:/FieldStatistics/statistics.log</file>` in `\src\main\resources`,
`mvn install` will execute all tests.
