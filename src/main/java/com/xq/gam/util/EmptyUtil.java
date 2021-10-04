package com.xq.gam.util;

import com.xq.gam.exception.ExceptionEnum;
import com.xq.gam.exception.ServiceException;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

/**
 * Description:
 *
 * @author 13797
 * @version v0.0.1
 * @date 2021/10/4 20:22
 */
public class EmptyUtil {
    private EmptyUtil() {
        throw new AssertionError();
    }

    public static boolean isEmpty(Object o) {
        if (o == null) {
            return true;
        } else if (o instanceof CharSequence) {
            return ((CharSequence) o).length() == 0;
        } else if (o instanceof Collection) {
            return ((Collection<?>) o).isEmpty();
        } else if (o instanceof Map) {
            return ((Map<?, ?>) o).isEmpty();
        } else if (o instanceof Optional) {
            return !((Optional<?>) o).isPresent();
        } else if (o.getClass().isArray()) {
            return Array.getLength(o) == 0;
        } else {
            return false;
        }
    }

    public static boolean notEmpty(Object o) {
        return !isEmpty(o);
    }

    public static void isEmpty(ExceptionEnum exceptionEnum, Object... objects) {
        for (Object object : objects) {
            if (isEmpty(object))
                throw new ServiceException(exceptionEnum);
        }
    }

    public static void isEmpty(Object o, ExceptionEnum exceptionEnum) {
        if (isEmpty(o))
            throw new ServiceException(exceptionEnum);
    }

    public static void notEmpty(Object o, ExceptionEnum exceptionEnum) {
        if (notEmpty(o))
            throw new ServiceException(exceptionEnum);
    }

    public static <T, R> R empty(T target, Function<T, R> trueFunc, Function<T, R> falseFunc) {
        if (isEmpty(target)) {
            return trueFunc.apply(target);
        } else {
            return falseFunc.apply(target);
        }
    }

}
