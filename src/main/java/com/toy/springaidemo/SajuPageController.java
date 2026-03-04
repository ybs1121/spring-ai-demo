package com.toy.springaidemo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SajuPageController {

    @GetMapping("/saju-stock")
    public String stockPage() {
        return "/index"; // templates/saju-stock.html을 바라봄
    }
}
