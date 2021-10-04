package com.xq.gam.config.converter.entity;

import com.xq.gam.constant.OperateType;

import javax.persistence.AttributeConverter;
import java.util.Arrays;

/**
 * describe: 数据库实体类中枚举类型转换
 *
 * @author xiong-qiang
 * @version v1.0
 * 2021/9/11 20:53
 */
public class OperateTypeConverter implements AttributeConverter<OperateType, Integer> {

    @Override
    public Integer convertToDatabaseColumn(OperateType attribute) {
        return attribute.getLevel();
    }

    @Override
    public OperateType convertToEntityAttribute(Integer dbData) {
        if (dbData == null) {
            return null;
        }
        return Arrays.stream(OperateType.values()).filter(type -> type.getLevel() == dbData).findFirst().orElse(null);
    }
}
