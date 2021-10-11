package com.lzq.jsyy.cmn.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lzq.jsyy.model.cmn.Permission;
import com.lzq.jsyy.vo.cmn.PermissionQueryVo;

import java.util.Map;

/**
 * @author lzq
 */
public interface PermissionService extends IService<Permission> {
    /**
     * 分页条件查询
     *
     * @param pageParam
     * @param permissionQueryVo
     * @return
     */
    Page<Permission> selectPage(Page<Permission> pageParam, PermissionQueryVo permissionQueryVo);

    /**
     * 添加一个权限
     *
     * @param permission
     * @return
     */
    Map<String, Object> add(Permission permission);

    /**
     * 获取权限
     *
     * @param permissionVo
     * @return
     */
    Permission get(PermissionQueryVo permissionVo);
}
