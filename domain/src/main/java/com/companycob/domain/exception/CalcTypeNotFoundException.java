package com.companycob.domain.exception;

public class CalcTypeNotFoundException extends RuntimeException {
    private int id;

    public CalcTypeNotFoundException(int id) {
        this.id = id;
    }

    @Override
    public String getMessage() {
        return String.format("CalcType with id %d not found.", id);
    }
}
