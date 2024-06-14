package io.seventytwo.vaadinjooq.filter;

import com.vaadin.flow.data.provider.ConfigurableFilterDataProvider;
import io.seventytwo.vaadinjooq.filter.JooqFilter;
import io.seventytwo.vaadinjooq.filter.JooqFilterSpecification;

/**
 * This class encapsulate the handling of jooq filter.
 * Is it necessary, if the filter is needed by components in other classes than the original view class.
 */
public class JooqFilterService {

    private final JooqFilter jooqFilter;
    private final ConfigurableFilterDataProvider<?, Void, JooqFilter> dataProvider;


    public JooqFilterService(JooqFilter jooqFilter, ConfigurableFilterDataProvider<?, Void, JooqFilter> dataProvider) {
        this.jooqFilter = jooqFilter;
        this.dataProvider = dataProvider;
    }

    public void add(JooqFilterSpecification spec) {
        this.jooqFilter.add(spec);
    }

    public void remove(JooqFilterSpecification spec) {
        this.jooqFilter.remove(spec);
    }

    public void refresh() {
        this.dataProvider.setFilter(this.jooqFilter);
    }
}
