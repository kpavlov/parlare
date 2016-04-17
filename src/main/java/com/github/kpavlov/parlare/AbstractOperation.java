package com.github.kpavlov.parlare;

public abstract class AbstractOperation<T, S extends Workflow> extends WorkflowHolder<S> implements Operation<T, S> {

    protected AbstractOperation(S context) {
        super(context);
    }

    protected S context() {
        return getSession();
    }
}
