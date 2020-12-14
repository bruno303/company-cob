package com.companycob.domain.model.enumerators;

import java.util.Arrays;

import com.companycob.domain.exception.CalcTypeNotFoundException;

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

    public static CalcType of(int id) {
        return Arrays.stream(CalcType.values())
                .filter(c -> c.getId() == id)
                .findFirst()
                .orElseThrow(() -> new CalcTypeNotFoundException(id));
    }
}
