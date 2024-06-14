package io.seventytwo.vaadinjooq.filter;

import java.time.LocalDate;
import java.util.Set;

import org.jooq.Condition;
import org.jooq.Context;
import org.jooq.EnumType;
import org.jooq.Field;
import org.jooq.SQLDialect;
import org.jooq.TableField;
import org.jooq.impl.CustomCondition;
import org.jooq.impl.DSL;


public class JooqFilterWhereConditionFactory {

    private JooqFilterWhereConditionFactory() {
    }

    @SuppressWarnings("unchecked")
    public static Condition buildWhereCondition(JooqFilter filter) {
        Condition condition = DSL.noCondition();

        if (filter != null) {
            for (String key : filter.getFilterParams().keySet()) {
                JooqFilterSpecification filterSpec = filter.getFilterParams().get(key);
                Field<?> field = filterSpec.getField();
                Object value = filterSpec.getValue();
                if (filterSpec.getType().equals(JooqFilterType.IN_VALUE_SET)) {
                    condition = condition.and(field.in((Set<?>) value));
                } else if (filterSpec.getType().equals(JooqFilterType.STRING_EXACT_MATCH)) {
                    TableField<?, String> stringField = (TableField<?, String>) field;
                    condition = condition.and(stringField.eq((String) value));
                } else if (filterSpec.getType().equals(JooqFilterType.STRING_LIKE_MATCH)) {
                    condition = condition.and(field.likeIgnoreCase("%" + value + "%"));
                } else if (filterSpec.getType().equals(JooqFilterType.STRING_STARTSWITH)) {
                    condition = condition.and(field.likeIgnoreCase((String) value + "%"));
                } else if (filterSpec.getType().equals(JooqFilterType.STRING_REG_EXP)) {
                    condition = condition.and(caseInsensitiveLikeRegex((TableField<?, String>) field, (String) value));

                // NUMBER
                } else if (filterSpec.getType().equals(JooqFilterType.NUMBER_EXACT_MATCH)) {
                    TableField<?, Number> stringField = (TableField<?, Number>) field;
                    condition = condition.and(stringField.eq((Number) value));
                } else if (filterSpec.getType().equals(JooqFilterType.NUMBER_FROM)) {
                    TableField<?, Number> numberField = (TableField<?, Number>) field;
                    condition = condition.and(numberField.ge((Number) value));
                } else if (filterSpec.getType().equals(JooqFilterType.NUMBER_TO)) {
                    TableField<?, Number> numberField = (TableField<?, Number>) field;
                    condition = condition.and(numberField.le((Number) value));
                } else if (filterSpec.getType().equals(JooqFilterType.STRING_AS_NUMBER_EXACT_MATCH)) {
                    TableField<?, Number> tableField = (TableField<?, Number>) field;
                    Double numberValue = Double.valueOf((String)value);
                    condition = condition.and(tableField.eq(numberValue));

                // DATES
                } else if (filterSpec.getType().equals(JooqFilterType.DATE_FROM)) {
                    TableField<?, LocalDate> dateField = (TableField<?, LocalDate>) field;
                    condition = condition.and(dateField.ge((LocalDate) value));
                } else if (filterSpec.getType().equals(JooqFilterType.DATE_TO)) {
                    TableField<?, LocalDate> dateField = (TableField<?, LocalDate>) field;
                    condition = condition.and(dateField.le((LocalDate) value));
                } else if (filterSpec.getType().equals(JooqFilterType.ENUM_VALUE_EXACT_MATCH)) {
                    TableField<?, EnumType> tableField = (TableField<?, EnumType>) field;
                    condition = condition.and(tableField.eq(((EnumType)value)));
                } else if (filterSpec.getType().equals(JooqFilterType.ENUM_VALUE_NOT_EXACT_MATCH)) {
                    TableField<?, EnumType> tableField = (TableField<?, EnumType>) field;
                    condition = condition.and(tableField.ne(((EnumType)value)));
                }

            }
        }
        return condition;
    }

    public static Condition caseInsensitiveLikeRegex(TableField<?,String> field, String regex) {
        String normalizedRegExp = normalize(regex);
        return new CustomCondition() {
            @Override
            public void accept(Context<?> ctx) {
                if (ctx.family() == SQLDialect.POSTGRES)
                    ctx.visit(DSL.condition("{0} ~* {1}", field, DSL.val(normalizedRegExp)));
                else
                    ctx.visit(DSL.lower(field).likeRegex(normalizedRegExp.toLowerCase()));
            }
        };
    }

    // die user sollen '*' anstelle von '.*' eingeben können
    // FLN*020 ==> FLN.*020 aber auch *fln.*020* ==> .*fln.*020.*
    // siehe NormalizeRegexpTest
    public static String normalize(String regexp) {
        int startIndex = 0;
        while (regexp.indexOf("*", startIndex) != -1) {
            int idx = regexp.indexOf("*", startIndex);
            if (idx == 0) {
                // an erster Stelle steht '*'
                regexp = ".*" + regexp.substring(idx + 1);
            } else if (regexp.charAt(idx - 1) != '.') {
                // setze ein '.' davor
                regexp = regexp.substring(0, idx) + ".*" + regexp.substring(idx + 1);
            }
            startIndex = idx + 2;
        }
        return regexp;
    }

}
