package io.seventytwo.vaadinjooq.filter;

public enum JooqFilterType {

    IN_VALUE_SET,
    NUMBER_EXACT_MATCH,
    NUMBER_FROM,
    NUMBER_TO,
    STRING_EXACT_MATCH,
    STRING_LIKE_MATCH,
    STRING_STARTSWITH,
    STRING_REG_EXP,
    STRING_AS_NUMBER_EXACT_MATCH, // ein textfield value (String) als number interpretiert
    DATE_FROM, // führt zu einer DB-Date.greaterThan(filter-date) condition
    DATE_TO,
    ENUM_VALUE_EXACT_MATCH,
    ENUM_VALUE_NOT_EXACT_MATCH,
    ;
}
