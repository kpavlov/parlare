# Parlare - Fluent Test DSL for Java

[![Build Status](https://travis-ci.org/kpavlov/parlare.png?branch=master)](https://travis-ci.org/kpavlov/parlare) [![Build Status](https://drone.io/github.com/kpavlov/parlare/status.png)](https://drone.io/github.com/kpavlov/parlare/latest) [![Coverage Status](https://img.shields.io/codecov/c/github/kpavlov/parlare.svg)](https://codecov.io/github/kpavlov/parlare) [![MIT License](http://img.shields.io/badge/license-MIT-green.svg)](https://github.com/kpavlov/parlare/blob/master/LICENSE)

How to write fluent tests:

1. Implement a class representing your `Workflow`:
    
    ```java
    class CalculatorOperations implements Workflow<CalculatorOperations> {
    
        private final Calculator calculator;
    
    
        public CalculatorOperations(Calculator calculator) {
            this.calculator = calculator;
        }
    
        MathOperation add(double a, double b) {
            return new MathOperation(calculator::add, a, b, this);
        }
    
        MathOperation multiply(double a, double b) {
            return new MathOperation(calculator::multiply, a, b, this);
        }
    
        public MathOperation divide(double a, double b) {
            return new MathOperation(calculator::divide, a, b, this);
        }
    }
    ```
2. Implement Operations. You may extend `AbstractOperation`  or implement `Operation` interface.
3. Implement operation results for each operaiton. You may extend `AbstractResult` or implement `Result` interface.
4. Now you may write a test using DSL, e.g.:

    ```java
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
                    .assertTrue(value -> value == Double.POSITIVE_INFINITY);
        }
    }
    ```