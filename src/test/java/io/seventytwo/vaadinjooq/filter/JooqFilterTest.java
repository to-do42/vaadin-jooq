package io.seventytwo.vaadinjooq.filter;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;

public class JooqFilterTest {

    @Test
    void testBuilder() {
        JooqFilterSpecification spec = JooqFilterSpecification.builder()
            .withId(Optional.of("custom-id"))
            //.withTableField()
            .withType(JooqFilterType.NUMBER_EXACT_MATCH)
            .withValue(42)
            .build();

        assertEquals(Optional.of("custom-id"), spec.getId());
        assertEquals(JooqFilterType.NUMBER_EXACT_MATCH, spec.getType());
        assertEquals(42, spec.getValue());
    }
}
