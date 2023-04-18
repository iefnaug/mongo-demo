package com.gf.web;

import com.gf.server.BarService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author GF
 * @since 2023/4/18
 */
@RestController
@RequestMapping("/api")
public class MongoController {

    @Resource
    private BarService barService;

    @GetMapping
    public void tx() {
        barService.writeConcernInTx();
    }

}
