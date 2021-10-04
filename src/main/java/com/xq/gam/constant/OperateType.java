package com.xq.gam.constant;

import org.springframework.http.HttpMethod;

import java.util.Arrays;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

public enum OperateType {
    /**
     * 开放接口，可以对应任意的http方法
     */
    OPEN(-1, null),
    READ(1, HttpMethod.GET),
    UPDATE(2, HttpMethod.PUT),
    CREATE(4, HttpMethod.POST),
    DELETE(8, HttpMethod.DELETE),
    ;
    private final int level;
    private final HttpMethod httpMethod;

    OperateType(int level, HttpMethod httpMethod) {
        this.level = level;
        this.httpMethod = httpMethod;
    }

    public int getLevel() {
        return level;
    }

    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    public static int calculate(Collection<OperateType> types) {
        return types.stream()
                .mapToInt(OperateType::getLevel)
                .reduce(0, OperateType::mergeLevel);
    }

    public static Set<OperateType> calculate(int level) {
        return Arrays.stream(OperateType.values())
                .filter(type -> isPermission(type, level))
                .collect(Collectors.toSet());
    }

    private static int mergeLevel(int l1, int l2) {
        return l1 & l2;
    }

    public static int mergeLevel(int... levels) {
        return Arrays.stream(levels).reduce(0, OperateType::mergeLevel);
    }

    public static boolean isPermission(OperateType type, int level) {
        return (type.getLevel() & level) > 0;
    }

    public static OperateType findByName(String type) {
        return OperateType.valueOf(type);
    }
}