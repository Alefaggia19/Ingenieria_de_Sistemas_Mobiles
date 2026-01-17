package com.checkit.checkit_backend.controller;

import com.checkit.checkit_backend.dto.KpiResponseDto;
import com.checkit.checkit_backend.service.KpiService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/stats")
public class KpiController {
    private final KpiService kpiService;

    public KpiController(KpiService kpiService) {
        this.kpiService = kpiService;
    }

    @GetMapping
    public KpiResponseDto getKpis() {
        return kpiService.calculateKpis();
    }
}
