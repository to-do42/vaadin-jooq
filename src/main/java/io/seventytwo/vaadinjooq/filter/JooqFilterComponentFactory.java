package io.seventytwo.vaadinjooq.filter;

import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.textfield.TextFieldVariant;
import com.vaadin.flow.data.provider.ConfigurableFilterDataProvider;
import com.vaadin.flow.data.value.ValueChangeMode;

public class JooqFilterComponentFactory {

    public static TextField createTextField(JooqFilterSpecification filterSpec,
                                            JooqFilter jooqFilter,
                                            ConfigurableFilterDataProvider dataProvider) {

        TextField textField = new TextField();
        textField.setValueChangeMode(ValueChangeMode.EAGER);
        textField.setClearButtonVisible(true);
        textField.addValueChangeListener(event -> {
            filterSpec.setValue(event.getValue());
            if (event.getValue() != null) {
                jooqFilter.add(filterSpec);
            } else {
                jooqFilter.remove(filterSpec);
            }
            dataProvider.setFilter(jooqFilter);

        });
        return textField;
    }
}
