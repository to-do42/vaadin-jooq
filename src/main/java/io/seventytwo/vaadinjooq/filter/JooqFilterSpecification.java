package io.seventytwo.vaadinjooq.filter;

import java.util.Optional;

import org.jooq.Field;

/**
 * Eine Spezifikation eines Filters für ein UI Element das ein JOOQ Tablefiled
 * filtert.
 *
 * @author Dominik
 *
 */
public class JooqFilterSpecification {

    private Optional<String> id;

    private Field<?> field;

    private JooqFilterType type;

    private Object value;


    private JooqFilterSpecification(Optional<String> id, Field<?> field, JooqFilterType type, Object value) {
        this.id = id;
        this.field = field;
        this.type = type;
        this.value = value;
    }

    public Optional<String> getId() {
        return id;
    }

    public void setId(Optional<String> id) {
        this.id = id;
    }

    public Field<?> getField() {
        return field;
    }

    public void setField(Field<Object> field) {
        this.field = field;
    }

    public JooqFilterType getType() {
        return type;
    }

    public void setType(JooqFilterType type) {
        this.type = type;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public static JooqFilterSpecificationBuilder builder() {
        return new JooqFilterSpecificationBuilder();
    }

    public static class JooqFilterSpecificationBuilder {
        private Optional<String> id = Optional.empty();
        private Field<?> field;
        private JooqFilterType type;
        private Object value;

        public JooqFilterSpecificationBuilder withId(Optional<String> id) {
            this.id = id;
            return this;
        }

        public JooqFilterSpecificationBuilder withField(Field<?> field) {
            this.field = field;
            return this;
        }

        public JooqFilterSpecificationBuilder withType(JooqFilterType type) {
            this.type = type;
            return this;
        }

        public JooqFilterSpecificationBuilder withValue(Object value) {
            this.value = value;
            return this;
        }

        public JooqFilterSpecification build() {
            return new JooqFilterSpecification(id, field, type, value);
        }
    }

}