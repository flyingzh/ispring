package com.zf.servlet;

import com.alibaba.fastjson.JSONObject;
import com.zf.annotation.Autowired;
import com.zf.factory.BeanFactory;
import com.zf.factory.ProxyFactory;
import com.zf.pojo.Result;
import com.zf.service.TestServiceAnnoService;
import com.zf.service.TransferService;
import com.zf.service.impl.TestServiceAnnoServiceImpl;
import com.zf.service.impl.TransferServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *zf
 */
@WebServlet(name="transferServlet",urlPatterns = "/transferServlet")
public class TransferServlet extends HttpServlet {

//    private ProxyFactory proxyFactory = (ProxyFactory) BeanFactory.getBean("proxyFactory");
//    private TransferService transferService = (TransferService) proxyFactory.getJdkProxy(BeanFactory.getBean("transferService")) ;
    /*private TestServiceAnnoService testServiceAnnoService = (TestServiceAnnoService) BeanFactory.getBean("testServiceAnnoServiceImpl");
    private TransferService transferService = (TransferServiceImpl) BeanFactory.getBean("transferService");
*/
//    @Autowired
//    private TestServiceAnnoService testServiceAnnoService = (TestServiceAnnoService) BeanFactory.getBean("testServiceAnnoServiceImpl");
    private TransferService transferService = (TransferServiceImpl) BeanFactory.getBean("transferServiceImpl");

//    @Autowired
//    private TransferService transferService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 设置请求体的字符编码
        req.setCharacterEncoding("UTF-8");

        String fromCardNo = req.getParameter("fromCardNo");
        String toCardNo = req.getParameter("toCardNo");
        String moneyStr = req.getParameter("money");
        int money = Integer.parseInt(moneyStr);

        Result result = new Result();

        try {
            transferService.transfer(fromCardNo,toCardNo,money);
            result.setStatus("200");
        } catch (Exception e) {
            e.printStackTrace();
            result.setStatus("400");
            result.setMessage(e.toString());
        }
        // 响应
        resp.setContentType("application/json;charset=utf-8");
        resp.getWriter().print(JSONObject.toJSONString(result));
    }
}
