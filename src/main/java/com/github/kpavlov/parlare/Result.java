package com.github.kpavlov.parlare;

import org.hamcrest.Matcher;

public interface Result<T, S extends Workflow, SELF extends Result> {

    T getResult();

    Throwable getError();

    @SuppressWarnings("ThrowableResultOfMethodCallIgnored")
    default boolean isError() {
        return getError() != null;
    }

//    default SELF then() {
//        return (SELF) this;
//    }


    S andThen();

    default Matcher<Result<T, S, SELF>> matcher() {
        throw new UnsupportedOperationException();
    }

    S andWhen();
}
