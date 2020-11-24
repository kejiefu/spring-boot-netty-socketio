package com.project.chat.exception;

import com.alibaba.fastjson.JSON;
import com.project.chat.model.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;


@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public String errorHandler(HttpServletRequest req, Exception e) throws Exception {
        log(e, req);
        return JSON.toJSONString(new Result(-1, e.getMessage()));
    }

    @ExceptionHandler(value = ParameterException.class)
    @ResponseBody
    public String parameterHandler(Exception e) throws Exception {
        return JSON.toJSONString(new Result(-1, e.getMessage()));
    }

    /**
     * 重复
     *
     * @param e
     * @return
     * @throws Exception
     */
    @ExceptionHandler(value = RepeatException.class)
    @ResponseBody
    public String repeatHandler(Exception e) throws Exception {
        return JSON.toJSONString(new Result(-1, e.getMessage()));
    }

    private void log(Exception ex, HttpServletRequest request) {
        log.error("有异常啦：============》" + ex.getMessage());
        log.error("************************异常开始*******************************");
        log.error("", ex);
        log.error("请求地址：" + request.getRequestURL());
        Enumeration enumeration = request.getParameterNames();
        log.error("请求参数：");
        while (enumeration.hasMoreElements()) {
            String name = enumeration.nextElement().toString();
            log.error(name + "---" + request.getParameter(name));
        }

        StackTraceElement[] error = ex.getStackTrace();
        for (StackTraceElement stackTraceElement : error) {
            log.error(stackTraceElement.toString());
        }
        log.error("************************异常结束*******************************");
    }

}