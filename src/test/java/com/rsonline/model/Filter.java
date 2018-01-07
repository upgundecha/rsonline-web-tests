package com.rsonline.model;

public class Filter {

    String filter;
    String value;

    public Filter(String filter, String value) {
        this.filter = filter;
        this.value = value;
    }

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
