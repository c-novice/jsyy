package com.lzq.jsyy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lzq.jsyy.mapper.FacilityMapper;
import com.lzq.jsyy.model.cmn.Facility;
import com.lzq.jsyy.result.ResultCodeEnum;
import com.lzq.jsyy.service.FacilityService;
import com.lzq.jsyy.vo.cmn.FacilityQueryVo;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lzq
 */
@Service
public class FacilityServiceImpl extends ServiceImpl<FacilityMapper, Facility> implements FacilityService {
    @Override
    public Page<Facility> selectPage(Page<Facility> pageParam, FacilityQueryVo facilityQueryVo) {
        if (StringUtils.isEmpty(facilityQueryVo)) {
            return null;
        }

        String facilityId = facilityQueryVo.getFacilityId();
        String name = facilityQueryVo.getName();

        QueryWrapper<Facility> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(facilityId)) {
            wrapper.eq("facility_id", facilityId);
        }
        if (!StringUtils.isEmpty(name)) {
            wrapper.like("name", name);
        }

        Page<Facility> page = baseMapper.selectPage(pageParam, wrapper);
        // TODO 补充教室数

        return page;
    }

    @Override
    public Map<String, Object> add(Facility facility) {
        Map<String, Object> map = new HashMap<>(1);
        if (StringUtils.isEmpty(facility)) {
            map.put("state", ResultCodeEnum.FACILITY_ADD_ERROR);
            return map;
        }

        String facilityId = facility.getFacilityId();
        String name = facility.getName();
        String id = facility.getId();

        QueryWrapper<Facility> wrapper1 = new QueryWrapper<>();
        if (!StringUtils.isEmpty(facilityId)) {
            wrapper1.eq("facility_id", facilityId);
        }
        Facility facility1 = baseMapper.selectOne(wrapper1);
        if (!StringUtils.isEmpty(facility1)) {
            map.put("state", ResultCodeEnum.FACILITY_ADD_ERROR);
            return map;
        }
        wrapper1.ne("id", id);

        QueryWrapper<Facility> wrapper2 = new QueryWrapper<>();
        if (!StringUtils.isEmpty(name)) {
            wrapper1.eq("name", name);
        }
        Facility facility2 = baseMapper.selectOne(wrapper2);
        if (!StringUtils.isEmpty(facility2)) {
            map.put("state", ResultCodeEnum.FACILITY_ADD_ERROR);
            return map;
        }
        wrapper2.ne("id", id);

        baseMapper.insert(facility);
        map.put("state", ResultCodeEnum.SUCCESS);
        return map;
    }

    @Override
    public Map<String, Object> change(Facility facility) {
        Map<String, Object> map = new HashMap<>(1);
        if (StringUtils.isEmpty(facility)) {
            map.put("state", ResultCodeEnum.FACILITY_CHANGE_ERROR);
            return map;
        }

        String facilityId = facility.getFacilityId();
        String name = facility.getName();
        String id = facility.getId();

        QueryWrapper<Facility> wrapper1 = new QueryWrapper<>();
        if (!StringUtils.isEmpty(facilityId)) {
            wrapper1.eq("facility_id", facilityId);
        }
        Facility facility1 = baseMapper.selectOne(wrapper1);
        if (!StringUtils.isEmpty(facility1)) {
            map.put("state", ResultCodeEnum.FACILITY_CHANGE_ERROR);
            return map;
        }
        wrapper1.ne("id", id);

        QueryWrapper<Facility> wrapper2 = new QueryWrapper<>();
        if (!StringUtils.isEmpty(name)) {
            wrapper1.eq("name", name);
        }
        wrapper2.ne("id", id);

        baseMapper.updateById(facility);
        map.put("state", ResultCodeEnum.SUCCESS);
        return map;
    }

    @Override
    public Facility get(FacilityQueryVo facilityQueryVo) {
        if (StringUtils.isEmpty(facilityQueryVo)) {
            return null;
        }

        String facilityId = facilityQueryVo.getFacilityId();

        QueryWrapper<Facility> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(facilityId)) {
            wrapper.eq("facility_id", facilityId);
        }
        return baseMapper.selectOne(wrapper);
    }
}
