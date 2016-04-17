# Parlare - Fluent Java Test DSL

[![Build Status](https://travis-ci.org/kpavlov/parlare.png?branch=master)](https://travis-ci.org/kpavlov/parlare) [![Build Status](https://drone.io/github.com/kpavlov/parlare/status.png)](https://drone.io/github.com/kpavlov/parlare/latest) [![Coverage Status](https://img.shields.io/codecov/c/github/kpavlov/parlare.svg)](https://codecov.io/github/kpavlov/parlare) [![MIT License](http://img.shields.io/badge/license-MIT-green.svg)](https://github.com/kpavlov/parlare/blob/master/LICENSE)

**Parlare** -- From Vulgar Latin *paraulare* ‎(“to speak”), from Medieval Latin, Late Latin parabolare, from Latin parabola.

## Overview

*Parlare* is a small API destined to write concise and clear integration tests.
 It uses [Hamcrest](http://hamcrest.org/JavaHamcrest) to make assertions easier. Please have a look at [Hamcrest extensions](https://github.com/hamcrest/JavaHamcrest/wiki/Related-Projects) to use proper matchers.

API implements [Fluent Interface](https://en.wikipedia.org/wiki/Fluent_interface) pattern.

*Workflow* is a sequence of *Operations*. Each Operation returns a *Result*.
`Result` refers to `Workflow` using methods like `andThen() / andWhen()` and also provides some assertion methods like `verifyResult(Matcher)`.

Workflow is good to represent user's session abstraction. Scenario may look like this:
```java
Session.of(login, password)
    .login()
        .execute()
    .then()
    .getProfile()
        .execute()
            .mapAndVerify(Profile::getName(), CoreMatchers.endsWith("Smith"))
        .andThen()
    .logout()
        .execute();
```

## Recipe

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