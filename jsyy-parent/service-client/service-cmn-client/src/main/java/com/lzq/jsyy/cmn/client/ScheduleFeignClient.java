package com.lzq.jsyy.cmn.client;

import com.lzq.jsyy.model.cmn.Schedule;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 预约排班调用
 *
 * @author lzq
 */
@Component
@FeignClient("service-cmn")
public interface ScheduleFeignClient {
    /**
     * 根据id查询预约排班
     * @param id
     * @return
     */
    @GetMapping("/api/schedule/inner/getById")
    Schedule getById(String id);
}
