package hello.springmvc.basic.request;

import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.nio.charset.StandardCharsets;

@Slf4j
@Controller
public class RequestBodyStringController {

    @PostMapping("/request-body-string-v1")
    public void requestBodyStringV1(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ServletInputStream inputstream = request.getInputStream();
        String messagebody = StreamUtils.copyToString(inputstream, StandardCharsets.UTF_8);
        log.info("messagebody={}", messagebody);
        response.getWriter().write("ok");
    }

    /**
     스프링 MVC는 다음 파라미터를 지원한다.**
     InputStream(Reader): HTTP 요청 메시지 바디의 내용을 직접 조회
     OutputStream(Writer): HTTP 응답 메시지의 바디에 직접 결과 출력
     */
    @PostMapping("/request-body-string-v2")
    public void requestBodyStringV2(InputStream inputStream, Writer responseWriter) throws IOException { // 파라미터에 바로 InputStream, Writer를 쓸 수 있다
        String messagebody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
        log.info("messagebody={}", messagebody);
        responseWriter.write("ok");
    }

    /**
     * **HttpEntity**: HTTP header, body 정보를 편리하게 조회
     * 메시지 바디 정보를 직접 조회
     * 요청 파라미터를 조회하는 기능과 관계 없음 `@RequestParam` X, `@ModelAttribute` X
     * **HttpEntity는 응답에도 사용 가능**
     * 메시지 바디 정보 직접 반환
     * 헤더 정보 포함 가능
     * view 조회X
     */
    @PostMapping("/request-body-string-v3")
    public HttpEntity<String> requestBodyStringV3(HttpEntity<String> httpEntity) throws IOException { // 메시지 바디에 들어있는 정보를 바로 문자열로 만들어줌.
        String body = httpEntity.getBody();
        log.info("messagebody={}", body);
        return new HttpEntity<>("ok"); // 응답 바디에 반환, 지금 컨트롤러는 @Controller이기 때문에 응답에 view를 쓰지 않고 바로 응답에 바로 반환
    }

    /**
     @RequestBody
     `@RequestBody` 를 사용하면 HTTP 메시지 바디 정보를 편리하게 조회할 수 있다. 참고로 헤더 정보가 필요하다면
     `HttpEntity` 를 사용하거나 `@RequestHeader` 를 사용하면 된다.
     이렇게 메시지 바디를 직접 조회하는 기능은 요청 파라미터를 조회하는 `@RequestParam` , `@ModelAttribute` 와
     는 전혀 관계가 없다.
     */
    @ResponseBody // @RestController 역할
    @PostMapping("/request-body-string-v4")
    public String requestBodyStringV4(@RequestBody String messageBody) throws IOException { // 요청의 바디 정보를 바로 받아옴.
        log.info("messagebody={}", messageBody);
        return "ok";
    }

    /*
    요청 파라미터 vs HTTP 메시지 바디
    요청 파라미터를 조회하는 기능: `@RequestParam` , `@ModelAttribute`
    HTTP 메시지 바디를 직접 조회하는 기능: `@RequestBody`
    @ResponseBody
    `@ResponseBody` 를 사용하면 응답 결과를 HTTP 메시지 바디에 직접 담아서 전달할 수 있다.
    물론 이 경우에도 view를 사용하지 않는다.
    */
}
