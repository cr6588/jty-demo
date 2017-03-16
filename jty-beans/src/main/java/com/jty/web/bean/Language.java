package com.jty.web.bean;

import java.util.Locale;

import org.codehaus.jackson.annotate.JsonCreator;

public enum Language {
    zh_CN(), en_US();
    private final static String I18N_SPLIT_CODE = "_";

    @JsonCreator
    public static Language create(String languageStr) {
        Language[] values = Language.values();
        for (Language language : values) {
            String languageName = language.toString();

            if (languageName.equalsIgnoreCase(languageStr)) {
                return language;
            }

            String shortName = languageName.split(I18N_SPLIT_CODE)[0];
            if (shortName.equalsIgnoreCase(languageStr)) {
                return language;
            }
        }
        return Language.zh_CN;
    }

    public static Language getLanuage(Locale locale) {
        Language[] values = Language.values();
        for (Language language : values) {
            String languageName = locale.getLanguage().substring(0, 2);

            if (language.toString().contains(languageName)) {
                return language;
            }
        }
        return Language.en_US;
    }

    public String getShortName() {
        String languageName = this.toString();
        return languageName.split(I18N_SPLIT_CODE)[0];
    }

    private static Locale zhLocale = new Locale("zh");
    private static Locale enLocale = new Locale("en");

    public static Locale getLocale(String languageStr) {
        if (languageStr.contains("zh")) {
            return zhLocale;
        } else {
            return enLocale;
        }
    }
}
