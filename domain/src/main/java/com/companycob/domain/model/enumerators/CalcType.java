package com.companycob.domain.model.enumerators;

import com.companycob.domain.exception.CalcTypeNotFoundException;

import java.util.Arrays;

public enum CalcType {

    DEFAULT(0, "Default");

    private int id;
    private String description;

    CalcType(int id, String description) {
        this.id = id;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public static CalcType of(int id) throws CalcTypeNotFoundException {
        return Arrays.stream(CalcType.values())
                .filter(c -> c.getId() == id)
                .findFirst()
                .orElseThrow(() -> new CalcTypeNotFoundException(id));
    }
}
