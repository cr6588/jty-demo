package com.jty.web.messages;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.AbstractMessageSource;

import com.jty.web.bean.I18n;
import com.jty.web.bean.Language;
import com.jty.web.service.I18nSer;

public class DataBaseMessageResource extends AbstractMessageSource implements BeanClassLoaderAware {
    @Autowired
    private I18nSer systemMangeSer;

    protected final static String I18N_SPLIT_CODE = "_";

    private final Map<Language, Map<String, String>> language_texts = new HashMap<Language, Map<String, String>>(Language.values().length);

    @PostConstruct 
    public void loadI18NMessages() throws Exception {
        for (Language language : Language.values()) {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("language", language);
            params.put("active", 1);

            List<I18n> resources = systemMangeSer.getI18nList(params, null);
            Map<String, String> messages = new HashMap<String, String>();
            if (resources != null && resources.size() != 0) {
                for (I18n message : resources) {
                    messages.put(message.getCode(), message.getValue());
                }
            }
            language_texts.put(language, messages);
        }
    }

    private String getText(String code, Locale locale) {
        Language language = Language.getLanuage(locale);
        Map<String, String> messagePoor = getLocalMessagePool(language);
        if (messagePoor == null) {
            return code;
        }

        String localeText = messagePoor.get(code);
        if (localeText == null) {
            try {
                if (getParentMessageSource() != null) {
                    localeText = getParentMessageSource().getMessage(code, null, locale);
                }
            } catch (Exception e) {
                logger.error("Cannot find message with code: " + code);
            }
        }
        if(localeText == null){
            localeText = code;
        }
        return localeText;
    }

    public Map<String, String> getLocalMessagePool(Language language) {
        Map<String, String> messagePoor = language_texts.get(language);
        return messagePoor;
    }

    @Override
    protected MessageFormat resolveCode(String code, Locale locale) {
        String msg = getText(code, locale);
        MessageFormat result = createMessageFormat(msg, locale);
        return result;
    }

    @Override
    protected String resolveCodeWithoutArguments(String code, Locale locale) {
        String result = getText(code, locale);
        return result;
    }

    @Override
    public void setBeanClassLoader(ClassLoader classLoader) {
    }
}
