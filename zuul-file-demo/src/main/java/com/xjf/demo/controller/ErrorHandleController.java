package com.xjf.demo.controller;

import com.xjf.demo.base.ResponseCode;
import com.xjf.demo.base.ResponseData;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 统一异常处理
 * 实现 ErrorController 接口，实现自定义错误返回( json 格式)
 *
 * @author xjf
 * @date 2020/1/29 11:26
 */
@RestController
public class ErrorHandleController implements ErrorController {

    @Autowired
    private ErrorAttributes errorAttributes;

    @Override
    public String getErrorPath() {
        return "/error";
    }

    @RequestMapping("/error")
    public ResponseData error(HttpServletRequest request){
        Map<String, Object> errorAttributes = getErrorAttributes(request);
        String message = (String) errorAttributes.get("message");
        String trace = (String) errorAttributes.get("trace");

        if (StringUtils.isNotBlank(trace)){
            message += String.format(" and trace %s", trace);
        }

        return ResponseData.fail(message, ResponseCode.SERVER_ERROR_CODE.getCode());
    }

    private Map<String, Object> getErrorAttributes(HttpServletRequest request){
        return errorAttributes.getErrorAttributes(new ServletWebRequest(request), true);
    }
}
