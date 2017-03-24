package com.jty.web.action;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import com.jty.web.annotation.PagerResolver;
import com.jty.web.bean.I18n;
import com.jty.web.bean.Language;
import com.jty.web.bean.PagerInfo;
import com.jty.web.bean.PagerStruct;
import com.jty.web.bean.RequestResult;
import com.jty.web.messages.DataBaseMessageResource;
import com.jty.web.messages.MessageUtil;
import com.jty.web.service.I18nSer;
import com.jty.web.util.RequestSessionUtil;
import com.sun.mail.handlers.message_rfc822;

@Controller
@RequestMapping("/i18n")
public class I18nController {
	private static final String CUR_LANGUAGE = "curLanguage";

    Logger logger = Logger.getLogger(I18nController.class);

    @Autowired
    private SessionLocaleResolver localeResolver;
	@Autowired
	private I18nSer i18nSer;
	@Autowired
	private MessageUtil messageUtil;

    @RequestMapping(value = "/{pageName}", method = RequestMethod.GET)
    public ModelAndView viewAdminManagePages(HttpServletRequest request, @PathVariable("pageName") String pageName) throws Exception {
        String path = RequestSessionUtil.getDevicePath(request) + "/i18n/" + pageName;
        return new ModelAndView(path, RequestSessionUtil.getRequestParamData(request));
    }

	@RequestMapping(value = "/addOrUpdateI18n", method = RequestMethod.POST)
	@ResponseBody
	public RequestResult<String> addOrUpdateI18n(@RequestBody I18n I18n) {
		RequestResult<String> result = new RequestResult<String>();
		try {
			if (I18n.getId() == null || I18n.getId() == 0) {
				this.i18nSer.addI18n(I18n);
			} else {
				this.i18nSer.updateI18n(I18n);
			}
			// DataBaseMessageResource dbMessageSource =
			// (DataBaseMessageResource) messageUtil.getMessageSource();
			// dbMessageSource.loadI18NMessages();
		} catch (Exception e) {
			logger.error(e.getMessage());
			result.setCode(100);
			result.setMessage(e.getMessage());
		}
		return result;
	}

	@RequestMapping(value = "/deleteI18n", method = RequestMethod.POST)
	@ResponseBody
	public RequestResult<String> deleteI18n(@RequestParam("id") Long id) {
		RequestResult<String> result = new RequestResult<String>();
		try {
			this.i18nSer.deleteI18n(id);
			result.setCode(0);
		} catch (Exception e) {
			logger.error(e.getMessage());
			result.setCode(100);
			result.setMessage(e.getMessage());
		}
		return result;
	}

	@RequestMapping(value = "/getI18n", method = RequestMethod.POST)
	@ResponseBody
	public RequestResult<I18n> getI18n(HttpServletRequest request) {
		RequestResult<I18n> result = new RequestResult<I18n>();
		Map<String, Object> params = RequestSessionUtil.getRequestParamData(request);
		try {
			result.setBody(this.i18nSer.getI18n(params));
		} catch (Exception e) {
			logger.error(e.getMessage());
			result.setCode(100);
			result.setMessage(e.getMessage());
		}
		return result;
	}

	@RequestMapping(value = "/getI18nList", method = RequestMethod.POST)
	@ResponseBody
	public PagerStruct<I18n> getI18nList(HttpServletRequest request,@RequestParam(value = "keyWord", required = false) String keyWord,
			@RequestParam(value = "language", required = false) String languageStr, @PagerResolver PagerInfo pagerParam) {
	    PagerStruct<I18n> result = new PagerStruct<I18n>();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("keyWord", keyWord);
		if(languageStr != null && !languageStr.isEmpty()) {
		    Language language = Language.create(languageStr);
		    params.put("language", language);
		}
//		System.out.println(dataBaseMessageResource.);
		try {
			result.setRows(this.i18nSer.getI18nList(params, pagerParam));
			result.setTotal(this.i18nSer.getI18nListCnt(params));
		} catch (Exception e) {
		    logger.error(e);
			logger.error(e.getMessage());
		}
		return result;
	}

    /**
     * 更换语言
     * @param request
     * @param locale
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/changeLanguage", method = RequestMethod.POST)
    @ResponseBody
    public RequestResult<String> changeLanguage(@RequestParam(value = "locale", required = false) String languageStr, HttpServletRequest request, HttpServletResponse response) {
        RequestResult<String> result = new RequestResult<String>();
        Language language = null;
        Locale locale = null;
        Language curLanguage = null;
        if(request.getSession().getAttribute(CUR_LANGUAGE) != null) {
            curLanguage = (Language) request.getSession().getAttribute(CUR_LANGUAGE);
        }

        if (languageStr != null) {
            language = Language.create(languageStr);
            switch (language) {
                case zh_CN:
                case en_US:
                    locale = new Locale(language.getShortName());
                    break;
                default:
                    locale = request.getLocale();
                    break;
            }
        } else {
            Language[] values = Language.values();
            for (int i = 0; i < values.length; i++) {
                if (values[i] == curLanguage) {
                    language = values[(i + 1) % values.length];
                    locale = new Locale(language.getShortName());
                    break;
                }
            }
        }

        localeResolver.setLocale(request, response, locale);
        request.getSession().setAttribute(CUR_LANGUAGE, language);
        return result;
    }

    @RequestMapping(value = "/getMessages", method = RequestMethod.POST)
    @ResponseBody
    public RequestResult<Map<String, String>> getMessages(HttpServletRequest request, @RequestParam(value = "locale", required = false) String lanaugeStr) {
        RequestResult<Map<String, String>> result = new RequestResult<Map<String, String>>();
        try {
            Object o = request.getSession().getAttribute(CUR_LANGUAGE);
            Language lanauge =  o != null ? (Language) o : Language.zh_CN;
            if (lanaugeStr != null) {
                lanauge = Language.create(lanaugeStr);
            }
            if (MessageUtil.getMessageSource() instanceof DataBaseMessageResource) {
                DataBaseMessageResource messageSource = (DataBaseMessageResource) MessageUtil.getMessageSource();
                Map<String, String> map = messageSource.getLocalMessagePool(lanauge);
                result.setBody(map);
            } else {
                throw new Exception("can not cast messageSource ");
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            result.setCode(100);
            result.setMessage(e.getMessage());
        }
        return result;
    }
}
