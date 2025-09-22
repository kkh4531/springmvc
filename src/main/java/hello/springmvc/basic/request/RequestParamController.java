package hello.springmvc.basic.request;

import hello.springmvc.basic.HelloData;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.Map;

@Slf4j
@Controller//@Controller는 view render에 필요한 값을 return해야함. 그냥 문자열을 반환하려면 @RestController
public class RequestParamController {

    @RequestMapping("/request-param-v1") // get, post 둘 다 되고 그 외의 메소드도 전부 다 실행됨.
    public void requestParamV1(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        int age = Integer.parseInt(request.getParameter("age"));
        log.info("username={}", username);
        log.info("age={}", age);
        response.getWriter().write("ok");
    }

    @ResponseBody // @Controller인데 그냥 문자열(http 메시지 바디)를 리턴하고싶으면 @ResponseBody를 쓰면 됨.
    @RequestMapping("/request-param-v2")
    public String requestParamV2(@RequestParam("username") String memberName,
                                 @RequestParam("age") int memberAge) {
        log.info("memberName={}", memberName);
        log.info("memberAge={}", memberAge);
        return "ok";
    }

    @ResponseBody // @Controller인데 그냥 문자열(http 메시지 바디)를 리턴하고싶으면 @ResponseBody를 쓰면 됨.
    @RequestMapping("/request-param-v3") // 변수명을 파라미터의 키와 똑같이 한다면 @RequestParma안에 굳이 파라미터 키를 안 써도 됨.
    public String requestParamV3(@RequestParam String username,
                                 @RequestParam int age) {
        log.info("username={}", username);
        log.info("age={}", age);
        return "ok";
    }

    @ResponseBody // @Controller인데 그냥 문자열(http 메시지 바디)를 리턴하고싶으면 @ResponseBody를 쓰면 됨.
    @RequestMapping("/request-param-v4") // 더 나아가 그냥 @RequestParam을 생략할 수도 있다 이것도 조건은 변수명이 파라미터 키와 같아야 함.
    public String requestParamV4(String username, int age) {
        log.info("username={}", username);
        log.info("age={}", age);
        return "ok";
    }

    @ResponseBody // @Controller인데 그냥 문자열(http 메시지 바디)를 리턴하고싶으면 @ResponseBody를 쓰면 됨.
    @RequestMapping("/request-param-required")
    public String requestParamRequired(@RequestParam(required = false) String username,
                                 @RequestParam(required = false) int age) { // required default는 true이고 false로 해놓으면 파라미터 값이 없어도 됨. 그러나 primitive 타입 변수는 사용 불가 쓰려면 Integer, Double... 써야함.
        log.info("username={}", username);
        log.info("age={}", age);
        return "ok";
    }

    @ResponseBody // @Controller인데 그냥 문자열(http 메시지 바디)를 리턴하고싶으면 @ResponseBody를 쓰면 됨.
    @RequestMapping("/request-param-default")
    public String requestParamDefault(@RequestParam(required = true, defaultValue = "guest") String username,
                                       @RequestParam(required = false, defaultValue = "-1") int age) { //파라미터 값이 없으면 default 값으로 채움.
        log.info("username={}", username);
        log.info("age={}", age);
        return "ok";
    }

    @ResponseBody // @Controller인데 그냥 문자열(http 메시지 바디)를 리턴하고싶으면 @ResponseBody를 쓰면 됨.
    @RequestMapping("/request-param-map")
    public String requestParamMap(@RequestParam Map<String, Object> paramMap) {//파라미터의 모든 값들을 map으로 받는다.
        String username = (String) paramMap.get("username");
        int age = Integer.parseInt((String) paramMap.get("age"));
        log.info("username={}", username);
        log.info("age={}", age);
        return "ok";
    }

    /**
     스프링MVC는 `@ModelAttribute` 가 있으면 다음을 실행한다.
     `HelloData` 객체를 생성한다.
     요청 파라미터의 이름으로 `HelloData` 객체의 프로퍼티를 찾는다. 그리고 해당 프로퍼티의 setter를 호출해서
     파라미터의 값을 입력(바인딩) 한다.
     예) 파라미터 이름이 `username` 이면 `setUsername()` 메서드를 찾아서 호출하면서 값을 입력한다.
     */
    @ResponseBody
    @RequestMapping("/model-attribute-v1")
    public String modelAttributeV1(@ModelAttribute HelloData helloData) {
        log.info("username={}, age={}", helloData.getUsername(), helloData.getAge());
        return "ok";
    }

    /**
     스프링은 해당 생략시 다음과 같은 규칙을 적용한다.
     `String` , `int` , `Integer` 같은 단순 타입 = `@RequestParam`
     나머지 = `@ModelAttribute` (argument resolver 로 지정해둔 타입 외)
     */
    @ResponseBody
    @RequestMapping("/model-attribute-v2")
    public String modelAttributeV2(HelloData helloData) { // @ModelAttribute 생략 가능
        log.info("username={}, age={}", helloData.getUsername(), helloData.getAge());
        return "ok";
    }
}
