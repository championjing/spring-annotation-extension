package com.rongzhiweilai.extension.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.core.annotation.AliasFor;
import org.springframework.web.bind.annotation.ValueConstants;

/**
 * @author : championjing
 * ClassName: JSONParam
 * Description: For spring web, @JSONParam like @RequestParam, difference is as below
 * -----------------------------------------------------------------------
 * content-type |  application/json  |  application/x-www-form-urlencoded
 * -----------------------------------------------------------------------
 *              |  @JSONParam        |     @RequestParam
 * -----------------------------------------------------------------------
 * @since: 3/5/2019 5:05 PM
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface JSONParam {
	
	/**
     * The name of the request attribute to bind to.
     */
    @AliasFor("name")
    String value() default "";

    /**
     *  下步考虑做成数组，用于自定义对象，最后返回对象
     * @return
     */
    @AliasFor("value")
    String name() default "";
    /**
     * Whether the parameter is required. Default is true, leading to an
     * exception thrown in case of the parameter missing in the request. Switch
     * this to false if you prefer a null in case of the parameter missing.
     * Alternatively, provide a {@link #defaultValue() defaultValue}, which
     * implicitly sets this flag to false.
     */
    boolean required() default true;

    /**
     * The default value to use as a fallback when the request parameter is
     * not provided or has an empty value.
     * <p>Supplying a default value implicitly sets {@link #required} to
     * {@code false}.
     */
    String defaultValue() default ValueConstants.DEFAULT_NONE;
}
