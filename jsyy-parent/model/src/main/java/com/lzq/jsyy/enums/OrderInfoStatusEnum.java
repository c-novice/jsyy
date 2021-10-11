package com.lzq.jsyy.enums;

/**
 * @author lzq
 */

public enum OrderInfoStatusEnum {
    // 预约记录状态
    OVER_TIME(1, "已过期"),
    PAYING(2, "支付中"),
    ORDERED(3, "已预约"),
    CURRENT(4, "使用中"),;

    private Integer status;
    private String name;

    OrderInfoStatusEnum(Integer status, String name) {
        this.status = status;
        this.name = name;
    }

    public static String getStatusNameByStatus(Integer status) {
        AuthStatusEnum[] arrObj = AuthStatusEnum.values();
        for (AuthStatusEnum obj : arrObj) {
            if (status.intValue() == obj.getStatus().intValue()) {
                return obj.getName();
            }
        }
        return "";
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
