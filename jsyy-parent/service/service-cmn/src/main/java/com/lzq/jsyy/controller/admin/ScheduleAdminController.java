package com.lzq.jsyy.controller.admin;

import com.lzq.jsyy.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lzq
 */
@RestController
@RequestMapping("/admin/schedule")
public class ScheduleAdminController {
    @Autowired
    private ScheduleService scheduleService;
}
