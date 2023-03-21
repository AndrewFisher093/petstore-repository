package io.swagger.helpers;

public enum Scopes {

    READ("read:pets"),
    WRITE("write:pets");

    private final String scope;

    Scopes(String scope) {
        this.scope = scope;
    }

    public String getScope() {
        return scope;
    }
}