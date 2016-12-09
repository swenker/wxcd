package com.wxcd.yc.model;

/**
 * Created by wenjusun on 12/8/16.
 */
public class SimpleDeviceCounter {
    private String eventTime;
    private int counter;

    private SimpleDeviceCounter() {
    }

    public String getEventTime() {
        return eventTime;
    }

    public int getCounter() {
        return counter;
    }

    @Override
    public String toString() {
        return "SimpleDeviceCounter{" +
                "eventTime='" + eventTime + '\'' +
                ", counter=" + counter +
                '}';
    }

    public final static class Builder{
        private SimpleDeviceCounter result;


        public static Builder create(){
            Builder builder = new Builder();
            builder.result = new SimpleDeviceCounter();
            return builder;
        }

        public Builder setEventTime(String eventTime){
            result.eventTime = eventTime;
            return this;
        }

        public Builder setCounter(int counter){
            result.counter = counter;
            return this;
        }

        public SimpleDeviceCounter build(){
            if (result == null) throw new IllegalStateException("build() has already been called on this Builder.");

            SimpleDeviceCounter me = result;

            result = null;

            return me;
        }

    }
}
