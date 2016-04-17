package com.github.kpavlov.parlare;

import org.hamcrest.Matcher;
import org.hamcrest.MatcherAssert;

import java.util.function.Function;
import java.util.function.Predicate;

public abstract class ResultAdapter<T, S extends Workflow, SELF extends ResultAdapter> implements Result<T, S, SELF> {

    private final T result;
    private final S context;
    private final Throwable error;

    protected ResultAdapter(T result, S context) {
        this.result = result;
        this.error = null;
        this.context = context;
    }

    protected ResultAdapter(Throwable error, S context) {
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


    @SuppressWarnings("unchecked")
    public SELF verify(Matcher<SELF> matcher) {
        matcher.matches(result);
        return (SELF) this;
    }


    @SuppressWarnings("unchecked")
    public SELF verifyResult(String reason, Matcher<? super T> matcher) {
        MatcherAssert.assertThat(reason, result, matcher);
        return (SELF) this;
    }

    @SuppressWarnings("unchecked")
    public SELF verifyResult(String reason, Function<T, Matcher<? super T>> f) {
        MatcherAssert.assertThat(reason, result, f.apply(result));
        return (SELF) this;
    }

    public SELF verifyResult(Function<T, Matcher<? super T>> f) {
        return verifyResult("", f);
    }

    @SuppressWarnings("unchecked")
    public SELF assertTrue(Predicate<T> resultPredicate) {
        if (!resultPredicate.test(result)) {
            throw new AssertionError("");
        }
        return (SELF) this;
    }

}
