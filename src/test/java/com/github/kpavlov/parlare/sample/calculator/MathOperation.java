package com.github.kpavlov.parlare.sample.calculator;

import com.github.kpavlov.parlare.AbstractOperation;

import java.util.function.BiFunction;

class MathOperation extends AbstractOperation<Double, CalculatorOperations> {
    private double a;
    private double b;
    private BiFunction<Double, Double, Double> function;

    protected MathOperation(BiFunction<Double, Double, Double> function, double a, double b, CalculatorOperations context) {
        super(context);
        this.a = a;
        this.b = b;
        this.function = function;
    }

    protected double getA() {
        return a;
    }

    protected double getB() {
        return b;
    }

    @Override
    public CalcResult execute() {
        try {
            final Double result = function.apply(getA(), getB());
            return new CalcResult(result, context());
        } catch (Throwable e) {
             return new CalcResult(e, context());
        }
    }
}
