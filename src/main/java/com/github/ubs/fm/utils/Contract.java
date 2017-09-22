package com.github.ubs.fm.utils;

public class Contract {
    private Contract() {
    }

    @FunctionalInterface
    public interface Invariant {
        boolean check();
    }

    public static void require(String comment, Invariant invariant){
        if (!invariant.check()) {
            RuntimeException up = new ContractException(comment);
            throw up;
        }
    }

    public static void require(String comment, boolean val){
        require(comment, () -> val);
    }
}
