package org.example.zcloud;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;

public interface ZCloudFeignClient {
    @GetMapping("/v2/feeds/{uid}")
    String request(@PathVariable("uid") String uid);

    @GetMapping("/v2/feeds/{uid}/datastreams/{channel}")
    String request(@PathVariable("uid") String uid,
                   @PathVariable("channel") String channel,
                   @RequestParam("start") LocalDateTime start,
                   @RequestParam("end") LocalDateTime end,
                   @RequestParam("internal") String internal,
                   @RequestParam("duration") String duration
    );
}
