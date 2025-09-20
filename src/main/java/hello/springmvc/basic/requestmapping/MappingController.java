package hello.springmvc.basic.requestmapping;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
public class MappingController {

    private Logger log = LoggerFactory.getLogger(getClass());

    @RequestMapping(value = "/hello-basic", method = RequestMethod.GET)
    public String helloBasic() {
        log.info("basic");
        return "ok";
    }

    @GetMapping("/mapping-get-v2")
    public String mappingGetV2() {
        log.info("mappingGetV2");
        return "ok";
    }

    @GetMapping("/mapping/{userId}")
    public String mappingPath(@PathVariable("userId") String data) {
        log.info(data);
        return "ok";
    }

    /**
     * PathVariable 사용 다중
     */
    @GetMapping("/mapping/users/{userId}/orders/{orderId}")
    public String mappingPath(@PathVariable String userId, @PathVariable Long
            orderId) {
        log.info("mappingPath userId={}, orderId={}", userId, orderId);
        return "ok";
    }

    /**
     * 파라미터로 추가 매핑
     * params="mode",
     * params="!mode"
     * params="mode=debug"
     * params="mode!=debug" (! = )
     * params = {"mode=debug","data=good"}
     */
    @GetMapping(value = "/mapping-param", params = "mode=debug") // http://localhost:8080/mapping-param?mode=debug 파라미터까지 mode=debug로 적어야 이 메소드가 실행됨
    public String mappingParam() {
        log.info("mappingParam");
        return "ok";
    }

    /**
     * 특정 헤더로 추가 매핑
     * headers="mode",
     * headers="!mode"
     * headers="mode=debug"
     * headers="mode!=debug" (! = )
     */
    @GetMapping(value = "/mapping-header", headers = "mode=debug") // header에 mode=debug가 있어야 메소드가 실행됨
    public String mappingHeader(@RequestHeader("mode") String mode) { // header에 있는 mode의 값을 꺼내봄.
        log.info("mappingHeader");
        log.info(mode);
        return "ok";
    }

    @PostMapping(value = "/mapping-consume", consumes = "application/json")
    public String consume() { // 헤더의 Content-Type이 application/json인 경우에만 메소드 실행
        // consume은 클라이언트가 서버로 응답 바디의 content-type이 json일 때만 이 메소드가 실행되고
        // 서버에서 클라이언트로 주는 응답 바디의 content-type은 상관없다.
        log.info("mapping-consume");
        return "ok";
    }

    /**
     * Accept 헤더 기반 Media Type
     * produces = "text/html"
     * produces = "!text/html"
     * produces = "text/*"
     * produces = "*\/*"
     */
    @PostMapping(value = "/mapping-produce", produces = "text/html")
    public String mappingProduces() { // 클라이언트가 서버로 보낼 때 Accept헤더가 text/html일 때만 이 메소드가 실행됨. consume과 다른 것은 서버가 클라이언트로 응답을 보낼 때도 text/html이어야 함.
        // 클라이언트가 서버로 보내는 응답 바디의 content-type은 상관없지만 서버에서 클라이언트로 보내는 응답 바디의 content-type은 text/html이어야 함.
        // 즉 클라이언트가 서버로 보낼 때의(요청) Accept헤더 속성이 text/html이라는 건 클라이언트가 서버한테 난 text/html인 응답밖에 못 받아. 라고 통보하는 것.
        log.info("mappingProduces");
        return "ok";
    }
}
