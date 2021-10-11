package com.lzq.jsyy.cmn.client;


import com.lzq.jsyy.model.cmn.Permission;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 权限调用
 *
 * @author lzq
 */
@Component
@FeignClient("service-cmn")
public interface PermissionFeignClient {

    /**
     * 查询获取权限
     *
     * @param type
     * @return
     */
    @GetMapping("/api/permission/inner/get")
    Permission getByType(String type);
}