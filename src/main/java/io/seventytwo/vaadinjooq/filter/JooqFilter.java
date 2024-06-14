package io.seventytwo.vaadinjooq.filter;

import java.util.HashMap;
import java.util.Map;


public class JooqFilter {

    private Map<String, JooqFilterSpecification> filterParams = new HashMap<>();


    public Map<String, JooqFilterSpecification> getFilterParams() {
        return filterParams;
    }

    public void add(JooqFilterSpecification filter) {
        String key = this.getKey(filter);
        this.filterParams.put(key, filter);
    }

    public void remove(JooqFilterSpecification filter) {
        String key = this.getKey(filter);
        this.filterParams.remove(key);
    }

    private String getKey(JooqFilterSpecification filter) {
        String result = filter.getField().getName();
        if (filter.getId().isPresent()) {
            result = filter.getId().get();
        }
        return result;
    }

}