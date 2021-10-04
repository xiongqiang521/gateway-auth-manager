package com.xq.gam.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;

import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES;
import static com.fasterxml.jackson.databind.SerializationFeature.FAIL_ON_EMPTY_BEANS;
import static com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS;

public class JackUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(JackUtil.class);
    private static final ObjectMapper objectMapper = new ObjectMapper();

    static {
        //对象的所有字段全部列入
        objectMapper.setSerializationInclusion(JsonInclude.Include.ALWAYS);

        //取消默认转换timestamps形式
        objectMapper.configure(WRITE_DATES_AS_TIMESTAMPS, false);

        //忽略空Bean转json的错误
        objectMapper.configure(FAIL_ON_EMPTY_BEANS, false);

        //所有的日期格式都统一为以下的样式，即yyyy-MM-dd HH:mm:ss
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'"));

        //忽略在json字符串中存在，但是在java对象中不存在对应属性的情况。防止错误
        objectMapper.configure(FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public static boolean isJSON(String str) {
        try {
            objectMapper.readTree(str);
            return true;
        } catch (JsonProcessingException e) {
            // ignore JsonProcessingException
            return false;
        }
    }

    /**
     * @Description: 对象转字符串
     * @Auther: GALAace
     * @Date: 2019/6/21 23:15
     */
    public static <T> String obj2String(T obj) {
        if (obj == null) {
            return null;
        }
        try {
            return obj instanceof String ? (String) obj : objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            LOGGER.warn("Parse Object to String error", e);
            return null;
        }
    }

    /**
     * @Description: 对象转的字符串（格式化后）
     * @Auther: GALAace
     * @Date: 2019/6/21 23:15
     */
    public static <T> String obj2StringPretty(T obj) {
        if (obj == null) {
            return null;
        }
        try {
            return obj instanceof String ? (String) obj : objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
        } catch (Exception e) {
            LOGGER.error("Parse Object to String error", e);
            return null;
        }
    }

    /**
     * @Description: 字符串转对象
     * @Auther: GALAace
     * @Date: 2019/6/21 23:17
     */
    public static <T> T string2Obj(String str, Class<T> clazz) {
        if (!StringUtils.hasText(str) || clazz == null) {
            return null;
        }

        try {
            return objectMapper.readValue(str, clazz);
        } catch (Exception e) {
            LOGGER.error("Parse String to Object error", e);
            return null;
        }
    }

    /**
     * @Description: 字符串转复杂对象（List，Map，Set等）
     * @Auther: GALAace
     * @Date: 2019/6/22 0:20
     */
    public static <T> T string2Obj(String str, TypeReference<T> typeReference) {
        if (!StringUtils.hasText(str) || typeReference == null) {
            return null;
        }
        try {
            return objectMapper.readValue(str, typeReference);
        } catch (Exception e) {
            LOGGER.error("Parse String to Object error", e);
            return null;
        }
    }

    /**
     * @Description: 字符串转复杂对象（可变长）
     * @Auther: GALAace
     * @Date: 2019/6/22 0:21
     */
    public static <T> T string2Obj(String str, Class<?> collectionClass, Class<?>... elementClasses) {
        JavaType javaType = objectMapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);
        try {
            return objectMapper.readValue(str, javaType);
        } catch (Exception e) {
            LOGGER.error("Parse String to Object error", e);
            return null;
        }
    }

}
