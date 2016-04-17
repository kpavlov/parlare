package com.github.kpavlov.parlare.sample.calculator;

import com.github.kpavlov.parlare.Result;
import com.github.kpavlov.parlare.ResultAdapter;

 class CalcResult
         extends ResultAdapter<Double, CalculatorOperations, CalcResult>
implements Result<Double, CalculatorOperations, CalcResult> {

    protected CalcResult(Double result, CalculatorOperations context) {
        super(result, context);
    }

     public CalcResult(Throwable e, CalculatorOperations context) {
         super(e, context);
     }
 }
