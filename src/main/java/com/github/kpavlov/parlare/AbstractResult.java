package com.github.kpavlov.parlare;

public abstract class AbstractResult<T, S extends Workflow, SELF extends AbstractResult> implements Result<T, S, SELF> {

    private final T result;
    private final S context;
    private final Throwable error;

    protected AbstractResult(T result, S context) {
        this.result = result;
        this.error = null;
        this.context = context;
    }

    protected AbstractResult(Throwable error, S context) {
        this.error = error;
        this.result = null;
        this.context = context;
    }

    @Override
    public T getResult() {
        return result;
    }

    @Override
    public Throwable getError() {
        return error;
    }


    public S and() {
        return context;
    }


    protected S getContext() {
        return context;
    }


    @Override
    public S andThen() {
        return context;
    }


    @Override
    public S andWhen() {
        return context;
    }


}
