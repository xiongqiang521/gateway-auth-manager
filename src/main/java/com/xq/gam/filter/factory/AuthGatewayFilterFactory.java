package com.xq.gam.filter.factory;

import com.xq.gam.constant.ExchangeNames;
import com.xq.gam.constant.OperateType;
import com.xq.gam.entity.auth.ResourceAreaEntity;
import com.xq.gam.entity.auth.RoleEntity;
import com.xq.gam.entity.user.UserEntity;
import com.xq.gam.exception.ExceptionEnum;
import com.xq.gam.exception.ServiceException;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.style.ToStringCreator;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * Description:
 *
 * @author 13797
 * @version v0.0.1
 * 2021/10/6 20:46
 */
@Component
public class AuthGatewayFilterFactory extends AbstractGatewayFilterFactory<AuthGatewayFilterFactory.Config> {

    public static final String OPERATE_FILED = "operate";
    public static final String CATEGORY_FILED = "category";
    public static final String PATH_FILED = "path";

    public AuthGatewayFilterFactory() {
        super(AuthGatewayFilterFactory.Config.class);
    }

    @Override
    public List<String> shortcutFieldOrder() {
        return Arrays.asList(OPERATE_FILED, CATEGORY_FILED, PATH_FILED);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            UserEntity user = exchange.getAttribute(ExchangeNames.USER);
            List<RoleEntity> roleList = user.getRoleList();
            boolean match = roleList.stream()
                    .flatMap(role -> role.getStrategyList().stream())
                    .flatMap(strategyEntity -> strategyEntity.getResourceAreaList().stream())
                    .anyMatch(area -> matchArea(area, config));
            if (match) {
                return chain.filter(exchange);
            }else {
                throw new ServiceException(ExceptionEnum.PARAMETER_ERROR);
            }
        };
    }

    private boolean matchArea(ResourceAreaEntity resourceArea, Config config) {
        String resource;
        switch (resourceArea.getStrategyType()) {
            case CATEGORY_API:
                resource = config.getCategory();
                break;
            case SINGLE_API:
                resource = config.getPath();
                break;
            default:
                throw new ServiceException(ExceptionEnum.PARAMETER_ERROR);
        }

        // todo resourceArea 中的 OperateType 应该是一个集合，或者对应的int类型；client中 OperateType 是单个接口的操作方式，是单个enum
        return Objects.equals(resourceArea.getOperateType(), config.getOperate())
                && Objects.equals(resourceArea.getResource(), resource);
    }

    public static class Config {
        private OperateType operate;
        private String category;
        private String path;

        public OperateType getOperate() {
            return operate;
        }

        public void setOperate(OperateType operate) {
            this.operate = operate;
        }

        public void setOperate(String operate) {
            this.operate = OperateType.findByName(operate);
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        @Override
        public String toString() {
            return new ToStringCreator(this)
                    .append(OPERATE_FILED, operate)
                    .append(CATEGORY_FILED, category)
                    .append(PATH_FILED, path)
                    .toString();
        }
    }
}
