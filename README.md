# vaadin-jooq

vaadin-jooq is a small open source library to integrate [Vaadin](https://www.vaadin.com) and [jOOQ](https://www.jooq.org)

## How To

### Dependency

#### jOOQ 3.17.x

If you are using jOOQ 3.17.x you must use 2.x.x because this version requires Java 17

```xml
<dependency>
    <groupId>io.seventytwo.oss</groupId>
    <artifactId>vaadin-jooq</artifactId>
    <version>2.0.0</version>
</dependency>
```

#### jOOQ 3.16.x

If you are using jOOQ 3.16 you must use 1.x.x

Add a dependency to the current version:

```xml
<dependency>
    <groupId>io.seventytwo.oss</groupId>
    <artifactId>vaadin-jooq</artifactId>
    <version>1.2.0</version>
</dependency>
```

### RecordGrid

The RecordGrid uses the table fields generated by jOOQ to create Vaadin Grid.
It contains a Builder that can be used to create the Grid. 

#### Example
 
```java
RecordGrid<VEmployeeRecord> grid = 
        new RecordGrid.Builder<>(V_EMPLOYEE, dslContext)
            .withColumns(V_EMPLOYEE.EMPLOYEE_ID, V_EMPLOYEE.EMPLOYEE_NAME, V_EMPLOYEE.DEPARTMENT_NAME)
            .withSort(Map.of(V_EMPLOYEE.EMPLOYEE_NAME, true))
            .build();
```

For a fully integrated example have a look at the [showcase project](https://github.com/simasch/vaadin-jooq-employee).

### VaadinJooqUtil

The VaadinJooqUtil class provides a convenience method to convert sort orders from a Vaadin DataProvider to OrderFields that can be used in a orderBy clause with jOOQ.

#### Example

```java
dataProvider = new CallbackDataProvider<VEmployeeRecord, Condition>(
    query -> dsl
        .selectFrom(V_EMPLOYEE)
        .where(query.getFilter().orElse(DSL.noCondition()))
        .orderBy(orderFields(V_EMPLOYEE, query)) // usage of VaadinJooUtil
        .offset(query.getOffset())
        .limit(query.getLimit())
        .fetchStream(),
    
    query -> dsl
        .selectCount()
        .from(V_EMPLOYEE)
        .where(query.getFilter().orElse(DSL.noCondition()))
        .fetchOneInto(Integer.class),
        
    VEmployeeRecord::getEmployeeId)
        
    .withConfigurableFilter();
```
