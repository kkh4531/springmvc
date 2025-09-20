package hello.springmvc.basic;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class LogTestController {

    @RequestMapping("/log-test")
    public String logTest() {
        String name = "Spring";
        log.trace("trace log={}", name);
        log.trace("trace log=" + name); // 이렇게 쓰면 안됨 이유는 문자열 + 문자열은 연산이 일어남 즉 필요없는 메모리를 쓰고 cpu를 소모함.
        log.debug("debug log={}", name);
        log.info("info log={}", name);
        log.warn("warn log={}", name);
        log.error("error log={}", name);
        return "ok";
    }
}
