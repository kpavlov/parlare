package com.github.kpavlov.parlare.sample.calculator;

import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;

public class CalculatorTest {

    private final Calculator sut = new Calculator();

    CalculatorOperations workflow;

    @Before
    public void beforeMethod() {
        workflow = new CalculatorOperations(sut);
    }

    @Test
    public void testTwoPlusTwoEloquent() {
        workflow
                .when()
                .add(2, 2).execute()
                .verifyResult("4", is(4.0))
                .andThen()
                .multiply(2, 2)
                .execute()
                .assertTrue(result -> (result > 1));
    }

    @Test
    public void testTwoByTwoShort() {
        workflow
                .multiply(2, 2)
                .execute()
                .assertTrue(result -> (result == 4));
    }

    @Test
    public void testFailDivideByZero() {
        workflow.divide(2, 0)
                .execute()
                .assertTrue(value -> value == Double.POSITIVE_INFINITY)
                .mapAndVerify(Double::isFinite, CoreMatchers.is(false));
    }
}
