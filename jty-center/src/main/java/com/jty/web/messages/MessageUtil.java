package com.jty.web.messages;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.MessageSource;

import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.util.WebUtils;

/**
 * 使用Spring-mvc i18n获取message的多语言信息
 * @author Mx
 */
public class MessageUtil {
    private static MessageSource messageSource;

    public static MessageSource getMessageSource() {
        return MessageUtil.messageSource;
    }

    public void setMessageSource(MessageSource messageSource) {
        MessageUtil.messageSource = messageSource;
    }

    public static String getMessage(HttpServletRequest request, String str) {
        Locale locale = (Locale) WebUtils.getSessionAttribute(request, SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME);
        return messageSource.getMessage(str, null, locale);
    }

    public static String getLanguage(HttpServletRequest request) {
        Locale locale = (Locale) WebUtils.getSessionAttribute(request, SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME);
        if (locale == null) {
            locale = request.getLocale();
        }
        return locale.getLanguage();
    }

    public static Locale getLocale(HttpServletRequest request) {
        Locale locale = (Locale) WebUtils.getSessionAttribute(request, SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME);
        return locale == null ? request.getLocale() : locale;
    }
}
