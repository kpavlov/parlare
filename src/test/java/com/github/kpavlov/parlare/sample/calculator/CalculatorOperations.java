package com.github.kpavlov.parlare.sample.calculator;

import com.github.kpavlov.parlare.Workflow;

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
