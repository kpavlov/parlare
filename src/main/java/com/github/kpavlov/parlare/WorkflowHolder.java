package com.github.kpavlov.parlare;

public abstract class WorkflowHolder<S extends Workflow> {
    private final S session;

    public WorkflowHolder(S session) {
        this.session = session;
    }

    protected S getSession() {
        return session;
    }


    public S andThen() {
        return session;
    }

    public S and() {
        return session;
    }
}
