package com.lzq.jsyy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lzq.jsyy.mapper.PermissionMapper;
import com.lzq.jsyy.model.cmn.Permission;
import com.lzq.jsyy.result.ResultCodeEnum;
import com.lzq.jsyy.service.PermissionService;
import com.lzq.jsyy.vo.cmn.PermissionQueryVo;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lzq
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {
    @Override
    public Page<Permission> selectPage(Page<Permission> pageParam, PermissionQueryVo permissionQueryVo) {
        if (null == permissionQueryVo) {
            return null;
        }

        String name = permissionQueryVo.getName();
        String father = permissionQueryVo.getFather();

        QueryWrapper<Permission> wrapper = new QueryWrapper<>();

        if (!StringUtils.isEmpty(name)) {
            wrapper.like("name", name);
        }
        if (!StringUtils.isEmpty(father)) {
            wrapper.like("father", father);
        }

        Page<Permission> page = baseMapper.selectPage(pageParam, wrapper);

        return page;
    }

    @Override
    public Map<String, Object> add(Permission permission) {
        Map<String, Object> map = new HashMap<>(1);

        if (StringUtils.isEmpty(permission)) {
            map.put("state", ResultCodeEnum.PERMISSION_ADD_ERROR);
            return map;
        }

        QueryWrapper<Permission> wrapper1 = new QueryWrapper<>();
        wrapper1.eq("name", permission.getName());
        wrapper1.ne("id", permission.getId());
        Permission permission1 = baseMapper.selectOne(wrapper1);

        if (StringUtils.isEmpty(permission1)) {
            map.put("state", ResultCodeEnum.PERMISSION_EXIST);
            return map;
        }

        save(permission);
        map.put("state", ResultCodeEnum.SUCCESS);

        return map;
    }
}