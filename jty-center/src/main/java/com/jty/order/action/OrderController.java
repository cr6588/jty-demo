package com.jty.order.action;

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

import com.jty.order.bean.Goods;
import com.jty.order.bean.Order;
import com.jty.order.service.OrderSer;
import com.jty.user.action.UserController;
import com.jty.user.bean.User;
import com.jty.web.annotation.PagerResolver;
import com.jty.web.bean.PagerInfo;
import com.jty.web.bean.PagerStruct;
import com.jty.web.bean.RequestResult;
import com.jty.web.util.RequestSessionUtil;

@Controller
@RequestMapping("/order")
public class OrderController {
    Logger logger = Logger.getLogger(OrderController.class);

    @Autowired
    private OrderSer orderSer;

    @RequestMapping(value = "/{pageName}", method = RequestMethod.GET)
    public ModelAndView viewAdminManagePages(HttpServletRequest request, @PathVariable("pageName") String pageName) throws Exception {
        if(request.getSession().getAttribute(UserController.CUR_USER) == null ) {
            return new ModelAndView("redirect:/user/login");
        }
        String path = RequestSessionUtil.getDevicePath(request) + "/order/" + pageName;
        return new ModelAndView(path, RequestSessionUtil.getRequestParamData(request));
    }


    public User getCurUser(HttpServletRequest request) {
        Object o = request.getSession().getAttribute(UserController.CUR_USER);
        if(o == null) {
            return null;
        }
        return (User) o;
    }
    @RequestMapping(value = "/addOrUpdateOrder", method = RequestMethod.POST)
    @ResponseBody
    public RequestResult<String> addOrUpdateOrder(HttpServletRequest request, @RequestBody Order Order) {
        RequestResult<String> result = new RequestResult<String>();
        try {
            Order.setUser(getCurUser(request));
            if (Order.getId() == null || Order.getId() == 0) {
                this.orderSer.addOrder(Order);
            } else {
                this.orderSer.updateOrder(Order);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            result.setCode(100);
            result.setMessage(e.getMessage());
        }
        return result;
    }

    @RequestMapping(value = "/deleteOrder", method = RequestMethod.POST)
    @ResponseBody
    public RequestResult<String> deleteOrder(@RequestParam("id") Long id) {
        RequestResult<String> result = new RequestResult<String>();
        try {
            this.orderSer.deleteOrder(id);
            result.setCode(0);
        } catch (Exception e) {
            logger.error(e.getMessage());
            result.setCode(100);
            result.setMessage(e.getMessage());
        }
        return result;
    }

    @RequestMapping(value = "/getOrder", method = RequestMethod.POST)
    @ResponseBody
    public RequestResult<Order> getOrder(HttpServletRequest request) {
        RequestResult<Order> result = new RequestResult<Order>();
        Map<String, Object> params = RequestSessionUtil.getRequestParamData(request);
        try {
            result.setBody(this.orderSer.getOrder(params));
        } catch (Exception e) {
            logger.error(e.getMessage());
            result.setCode(100);
            result.setMessage(e.getMessage());
        }
        return result;
    }

    @RequestMapping(value = "/getOrderList", method = RequestMethod.POST)
    @ResponseBody
    public PagerStruct<Order> getOrderList(@RequestParam(value = "keyWord", required = false) String keyWord,
                                         @PagerResolver PagerInfo pagerParam) {
        PagerStruct<Order> result = new PagerStruct<Order>();
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("keyWord", keyWord);
        try {
            result.setRows(this.orderSer.getOrderList(params, pagerParam));
            result.setTotal(this.orderSer.getOrderListCnt(params));
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }
        return result;
    }

    @RequestMapping(value = "/addOrUpdateGoods", method = RequestMethod.POST)
    @ResponseBody
    public RequestResult<String> addOrUpdateGoods(HttpServletRequest request, @RequestBody Goods Goods) {
        RequestResult<String> result = new RequestResult<String>();
        try {
            Goods.setUserId(getCurUser(request).getId());
            if (Goods.getId() == null || Goods.getId() == 0) {
                this.orderSer.addGoods(Goods);
            } else {
                this.orderSer.updateGoods(Goods);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            result.setCode(100);
            result.setMessage(e.getMessage());
        }
        return result;
    }

    @RequestMapping(value = "/deleteGoods", method = RequestMethod.POST)
    @ResponseBody
    public RequestResult<String> deleteGoods(@RequestParam("id") Long id) {
        RequestResult<String> result = new RequestResult<String>();
        try {
            this.orderSer.deleteGoods(id);
            result.setCode(0);
        } catch (Exception e) {
            logger.error(e.getMessage());
            result.setCode(100);
            result.setMessage(e.getMessage());
        }
        return result;
    }

    @RequestMapping(value = "/getGoods", method = RequestMethod.POST)
    @ResponseBody
    public RequestResult<Goods> getGoods(HttpServletRequest request) {
        RequestResult<Goods> result = new RequestResult<Goods>();
        Map<String, Object> params = RequestSessionUtil.getRequestParamData(request);
        try {
            result.setBody(this.orderSer.getGoods(params));
        } catch (Exception e) {
            logger.error(e.getMessage());
            result.setCode(100);
            result.setMessage(e.getMessage());
        }
        return result;
    }

    @RequestMapping(value = "/getGoodsList", method = RequestMethod.POST)
    @ResponseBody
    public PagerStruct<Goods> getGoodsList(@RequestParam(value = "keyWord", required = false) String keyWord,
                                         @PagerResolver PagerInfo pagerParam) {
        PagerStruct<Goods> result = new PagerStruct<Goods>();
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("keyWord", keyWord);
        try {
            result.setRows(this.orderSer.getGoodsList(params, pagerParam));
            result.setTotal(this.orderSer.getGoodsListCnt(params));
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }
        return result;
    }
}
