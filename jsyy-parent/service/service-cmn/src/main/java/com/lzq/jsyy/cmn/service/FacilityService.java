package com.lzq.jsyy.cmn.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lzq.jsyy.model.cmn.Facility;
import com.lzq.jsyy.vo.cmn.FacilityQueryVo;

import java.util.Map;

/**
 * @author lzq
 */
public interface FacilityService extends IService<Facility> {

    /**
     * 条件分页查询设施
     *
     * @param pageParam
     * @param facilityQueryVo
     * @return
     */
    Page<Facility> selectPage(Page<Facility> pageParam, FacilityQueryVo facilityQueryVo);

    /**
     * 添加一个设施
     *
     * @param facility
     * @return
     */
    Map<String, Object> add(Facility facility);

    /**
     * 修改一个设施
     *
     * @param facility
     * @return
     */
    Map<String, Object> change(Facility facility);

    /**
     * 查询一个设施
     *
     * @param facilityQueryVo
     * @return
     */
    Facility get(FacilityQueryVo facilityQueryVo);
}
