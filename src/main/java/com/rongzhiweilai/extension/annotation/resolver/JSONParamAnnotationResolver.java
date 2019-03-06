package com.rongzhiweilai.extension.annotation.resolver;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.rongzhiweilai.extension.annotation.JSONParam;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.bind.annotation.ValueConstants;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;

/**
 * @author : championjing
 * @ClassName: JSONParamAnnotationResolver
 * @Description: TODO
 * @Date: 3/5/2019 5:08 PM
 */
public class JSONParamAnnotationResolver implements HandlerMethodArgumentResolver {
    private static Logger LOGGER = LoggerFactory.getLogger(JSONParamAnnotationResolver.class);
    private static final String CONTENT_TYPE = "application/json";
    
//    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        LOGGER.debug("初始化json参数解析器");
        JSONParam jsonParam = parameter.getParameterAnnotation(JSONParam.class);
        if( jsonParam == null ) {
            return false;
        } else {
            return true;
        }
    }

//    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        String contentType = request.getContentType();
        // application/json
        if( !CONTENT_TYPE.equalsIgnoreCase( contentType )) {
            LOGGER.warn("please check request header for contentType");
            throw new Exception("just support application/json request type");
        }
        ServletServerHttpRequest inputStream = new ServletServerHttpRequest(request);
        InputStream body = inputStream.getBody();
        String paramString = IOUtils.toString(body);
        JSONObject paramJson = null;
        try {
            paramJson = (JSONObject) JSONObject.parse(paramString);
        } catch ( JSONException ex) {
            LOGGER.warn("please check request body for json format");
            throw new Exception("format of request body is error");
        }
        
        JSONParam jsonParam = parameter.getParameterAnnotation(JSONParam.class);
        //是否必须
        boolean required = jsonParam.required();
        String defaultValue = jsonParam.defaultValue();
        // 参数类型
        Class<?> parameterType = parameter.getParameterType();
        // 参数 的name，优先级 name > value > ParameterName
        String name = jsonParam.name();
        if ( StringUtils.isBlank(name) ) {
            name = jsonParam.value();
        }
        if ( StringUtils.isBlank(name) ) {
            name = parameter.getParameterName();
        }
        Object result = null;
        try{
            if(parameterType.equals(String.class)){
                result = paramJson.getString(name);
            }else if(parameterType.equals(Long.class) || parameterType.equals(long.class)){
                result = paramJson.getLong(name);
            }else if(parameterType.equals(Integer.class) || parameterType.equals(int.class)){
                result = paramJson.getInteger(name);
            }else if(parameterType.equals(Byte.class) || parameterType.equals(byte.class) ){
                result = paramJson.getByte(name);
            }else{
                result = paramJson.get(name);
            }
        } catch (JSONException ex) {
            //类型转换异常处理
            throw new Exception(ex.getMessage());
        } catch (NullPointerException ex) {
            result = null;
        }
        if( required && result == null ) {
            StringBuilder sb = new StringBuilder("parameter '");
            sb.append( name );
            sb.append("' is required");
            LOGGER.warn(sb.toString());
            throw new Exception( sb.toString() );
        } else if( !ValueConstants.DEFAULT_NONE.equals( defaultValue ) ){
            try {
                if(parameterType.equals(String.class)){
                    result = defaultValue;
                }else if(parameterType.equals(Long.class) || parameterType.equals(long.class)){
                    result = Long.parseLong( defaultValue );
                }else if(parameterType.equals(Integer.class) || parameterType.equals(int.class)){
                    result = Integer.parseInt( defaultValue );
                }else if(parameterType.equals(Byte.class) || parameterType.equals(byte.class) ){
                    result = Byte.parseByte( defaultValue );
                }
            }  catch (NumberFormatException ex) {
                StringBuilder sb = new StringBuilder(" parameter '");
                sb.append( parameter.getParameterName() );
                sb.append("' defalutValue is error");
                LOGGER.warn(sb.toString());
                throw new Exception( sb.toString() );
            }
        }
        return result;
    }
}
