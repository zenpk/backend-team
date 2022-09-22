package org.my.basic.controller;

import org.my.basic.model.DormInfo;
import org.my.basic.repository.DormInfoRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DormInfoController {
    private final DormInfoRepository dormInfoRepository;

    public DormInfoController(DormInfoRepository dormInfoRepository) {
        this.dormInfoRepository = dormInfoRepository;
    }

    // 访问 /dorm_info，显示数据库中相关表格的全部数据
    @GetMapping("/dorm_info")
    public List<DormInfo> getAllDormInfos() {
        // This returns a JSON of DormInfos
        return dormInfoRepository.findAll();
    }
}