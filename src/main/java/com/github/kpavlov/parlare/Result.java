package com.github.kpavlov.parlare;

import org.hamcrest.Matcher;
import org.hamcrest.MatcherAssert;

import java.util.function.Function;
import java.util.function.Predicate;

public interface Result<T, S extends Workflow, SELF extends Result> {

    T getResult();

    Throwable getError();

    @SuppressWarnings("ThrowableResultOfMethodCallIgnored")
    default boolean isError() {
        return getError() != null;
    }

    /**
     * Switch to {@link Workflow} context.
     */
    S andThen();

    /**
     * Switch to {@link Workflow} context.
     */
    S andWhen();

    @SuppressWarnings("unchecked")
    default SELF verify(Matcher<SELF> matcher) {
        matcher.matches(getResult());
        return (SELF) this;
    }

    @SuppressWarnings("unchecked")
    default <X> SELF mapAndVerify(Function<T, X> mapper, Matcher<X> matcher) {
        matcher.matches(mapper.apply(getResult()));
        return (SELF) this;
    }

    @SuppressWarnings("unchecked")
    default SELF verifyResult(String reason, Matcher<? super T> matcher) {
        MatcherAssert.assertThat(reason, getResult(), matcher);
        return (SELF) this;
    }

    @SuppressWarnings("unchecked")
    default SELF verifyResult(String reason, Function<T, Matcher<? super T>> f) {
        MatcherAssert.assertThat(reason, getResult(), f.apply(getResult()));
        return (SELF) this;
    }

    default SELF verifyResult(Function<T, Matcher<? super T>> f) {
        return verifyResult("", f);
    }

    @SuppressWarnings("unchecked")
    default SELF assertTrue(Predicate<T> resultPredicate) {
        if (!resultPredicate.test(getResult())) {
            throw new AssertionError("");
        }
        return (SELF) this;
    }

    default Matcher<Result<T, S, SELF>> matcher() {
        throw new UnsupportedOperationException();
    }

}
