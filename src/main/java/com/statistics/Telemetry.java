package com.statistics;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

public class Telemetry {

        @Min(0)
        @Max(100)
        private float value;

        private String timeStamp;

        @JsonCreator
        public Telemetry( @JsonProperty("vegetation") float value,@JsonProperty("occurrenceAt")String timeStamp){
                this.value = value;
                this.timeStamp = timeStamp;
        }


        public float getValue() {
                return value;
        }

        public String getTimeStamp() {
                return timeStamp;
        }
}
