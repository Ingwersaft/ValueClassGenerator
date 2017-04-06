package com.kesselring.valuegenerator.generator;

import com.kesselring.valuegenerator.parsed.Type;

import java.util.Arrays;
import java.util.List;

public class SupportedClasses {
    public static final List<Type> ALL = Arrays.asList(
            new Type("java.lang.String"),
            new Type("java.lang.Integer")
    );
}
