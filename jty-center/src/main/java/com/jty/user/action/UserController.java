package com.jty.user.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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

import com.jty.user.bean.User;
import com.jty.user.service.UserSer;
import com.jty.web.annotation.PagerResolver;
import com.jty.web.bean.PagerInfo;
import com.jty.web.bean.PagerStruct;
import com.jty.web.bean.RequestResult;
import com.jty.web.util.RequestSessionUtil;

@Controller
@RequestMapping("/user")
public class UserController {
    Logger logger = Logger.getLogger(UserController.class);

    @Autowired
    private UserSer userSer;

    @RequestMapping(value = "/{pageName}", method = RequestMethod.GET)
    public ModelAndView viewAdminManagePages(HttpServletRequest request, @PathVariable("pageName") String pageName) throws Exception {
        String path = RequestSessionUtil.getDevicePath(request) + "/user/" + pageName;
        return new ModelAndView(path, RequestSessionUtil.getRequestParamData(request));
    }

    @RequestMapping(value = "/addOrUpdateUser", method = RequestMethod.POST)
    @ResponseBody
    public RequestResult<String> addOrUpdateUser(@RequestBody User User) {
        RequestResult<String> result = new RequestResult<String>();
        try {
            if (User.getId() == null || User.getId() == 0) {
                this.userSer.addUser(User);
            } else {
                this.userSer.updateUser(User);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            result.setCode(100);
            result.setMessage(e.getMessage());
        }
        return result;
    }

    @RequestMapping(value = "/deleteUser", method = RequestMethod.POST)
    @ResponseBody
    public RequestResult<String> deleteUser(@RequestParam("id") Long id) {
        RequestResult<String> result = new RequestResult<String>();
        try {
            this.userSer.deleteUser(id);
            result.setCode(0);
        } catch (Exception e) {
            logger.error(e.getMessage());
            result.setCode(100);
            result.setMessage(e.getMessage());
        }
        return result;
    }

    @RequestMapping(value = "/getUser", method = RequestMethod.POST)
    @ResponseBody
    public RequestResult<User> getUser(HttpServletRequest request) {
        RequestResult<User> result = new RequestResult<User>();
        Map<String, Object> params = RequestSessionUtil.getRequestParamData(request);
        try {
            result.setBody(this.userSer.getUser(params));
        } catch (Exception e) {
            logger.error(e.getMessage());
            result.setCode(100);
            result.setMessage(e.getMessage());
        }
        return result;
    }

    @RequestMapping(value = "/getUserList", method = RequestMethod.POST)
    @ResponseBody
    public PagerStruct<User> getUserList(@RequestParam(value = "keyWord", required = false) String keyWord,
                                         @PagerResolver PagerInfo pagerParam) {
        PagerStruct<User> result = new PagerStruct<User>();
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("keyWord", keyWord);
        try {
            result.setRows(this.userSer.getUserList(params, pagerParam));
            result.setTotal(this.userSer.getUserListCnt(params));
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }
        return result;
    }

    @RequestMapping(value = "/loginCheck", method = RequestMethod.POST)
    @ResponseBody
    public RequestResult<Boolean> loginCheck(@RequestBody User User, HttpServletRequest request) {
        RequestResult<Boolean> result = new RequestResult<Boolean>();
        try {
            Map<String, Object> param = new HashMap<String, Object>();
            param.put("username", User.getUsername());
            param.put("password", User.getPassword());
            User user = userSer.getUser(param);
            if(user == null) {
                result.setBody(false);
                result.setMessage("username or password error!");
            } else {
                result.setBody(true);
                request.getSession().setAttribute("userId", user.getId());
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            result.setCode(100);
            result.setMessage(e.getMessage());
        }
        return result;
    }

    @RequestMapping(value = "/loginOut", method = RequestMethod.POST)
    @ResponseBody
    public RequestResult<Boolean> loginOut(HttpServletRequest request) {
        RequestResult<Boolean> result = new RequestResult<Boolean>();
        try {
            request.getSession().invalidate();
        } catch (Exception e) {
            logger.error(e.getMessage());
            result.setCode(100);
            result.setMessage(e.getMessage());
        }
        return result;
    }
}
