package com.wxcd.yc.model;

/**
 * Created by wenjusun on 11/4/16.
 */
public class DeviceCounter {
    private String eventDate;
    private int allCounter;
    private int uniqueCounter;

    private DeviceCounter(){

    }

    public String getEventDate() {
        return eventDate;
    }


    public int getAllCounter() {
        return allCounter;
    }


    public int getUniqueCounter() {
        return uniqueCounter;
    }

    @Override
    public String toString() {
        return "DeviceCounter{" +
                "eventDate='" + eventDate + '\'' +
                ", allCounter=" + allCounter +
                ", uniqueCounter=" + uniqueCounter +
                '}';
    }

    public static final class Builder{
        private DeviceCounter result = null;
        private Builder(){

        }
        public static Builder create(){
            Builder builder = new Builder();
            builder.result = new DeviceCounter();
            return builder;
        }
        public Builder setEventDate(String eventDate) {

            result.eventDate = eventDate;
            return this;
        }
        public Builder setAllCounter(int allCounter) {
            result.allCounter = allCounter;
            return this;
        }
        public Builder setUniqueCounter(int uniqueCounter) {
            result.uniqueCounter = uniqueCounter;
            return this;
        }

        public DeviceCounter build(){
            if (result == null) throw new IllegalStateException("build() has already been called on this Builder.");

            DeviceCounter me = result;

            result = null;
            return me;
        }

    }
}
