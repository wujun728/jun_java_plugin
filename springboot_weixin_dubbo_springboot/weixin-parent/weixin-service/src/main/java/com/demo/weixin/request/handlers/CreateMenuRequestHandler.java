package com.demo.weixin.request.handlers;

import com.alibaba.fastjson.annotation.JSONField;
import com.demo.weixin.enums.MenuType;
import com.demo.weixin.exception.WeixinException;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Wujun
 * @description 创建菜单
 * @date 2017/7/31
 * @since 1.0
 */
public class CreateMenuRequestHandler extends AbstractRequestHandler {

    private Integer agentId; // 必填。企业应用的id，整型。可在应用的设置页面查看
    private List<Menu> button; // 必填。一级菜单数组，个数应为1~3个。

    public CreateMenuRequestHandler() {
    }

    public CreateMenuRequestHandler(int agentId, List<Menu> button) {
        this.agentId = agentId;
        this.button = button;
    }

    @Override
    public Map<String, Object> getParamMap() throws WeixinException {
        // 验证必要的参数
        if (CollectionUtils.isEmpty(button)) {
            throw new WeixinException(-1,"CreateMenu参数缺失");
        }
        // TODO sub_button 过滤判断
        List<Menu> menuDTOList = button.stream().filter(menuDTO -> {
            MenuType menuType = menuDTO.getType();
            if (menuType == null) {
                return false;
            }
            return menuType == MenuType.view ? StringUtils.isNotBlank(menuDTO.getUrl()) : StringUtils.isNotBlank(menuDTO.getKey());
        }).collect(Collectors.toList());

        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("agentid", agentId.toString());
        paramMap.put("button", menuDTOList);

        return paramMap;
    }

    class Menu {
        private String name; // 菜单名称
        private MenuType type; // 必填。 菜单的响应动作类型
        private String key; // click等点击类型必填。菜单KEY值，用于消息接口推送，不超过128字节
        private String url; // view类型必填。网页链接，成员点击菜单可打开链接，不超过1024字节
        @JSONField(name = "sub_button")
        private List<Menu> subButton; // 二级菜单数组，个数应为1~5个

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public MenuType getType() {
            return type;
        }

        public void setType(MenuType type) {
            this.type = type;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public List<Menu> getSubButton() {
            return subButton;
        }

        public void setSubButton(List<Menu> subButton) {
            this.subButton = subButton;
        }
    }


    public Integer getAgentId() {
        return agentId;
    }

    public void setAgentId(Integer agentId) {
        this.agentId = agentId;
    }

    public List<Menu> getButton() {
        return button;
    }

    public void setButton(List<Menu> button) {
        this.button = button;
    }
}
