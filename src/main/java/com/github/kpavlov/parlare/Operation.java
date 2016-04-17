package com.github.kpavlov.parlare;

public interface Operation<T, S extends Workflow> {

    <R extends Result<T, S, ? extends Result>> R execute();
}
