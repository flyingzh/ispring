package com.zf.servlet;

import com.alibaba.fastjson.JSONObject;
import com.zf.annotation.Autowired;
import com.zf.annotation.Service;
import com.zf.pojo.Result;
import com.zf.service.TransferService;
import com.zf.service.impl.TestServiceAnnoServiceImpl;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author admin
 * @date 2020/3/5 10:56
 * @description
 */
@Service
public class TargetAdapter extends TransferServlet implements Target {

    @Autowired
    private TestServiceAnnoServiceImpl testServiceAnnoServiceImpl;

    //    @Autowired
    private TransferService transferService;

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 设置请求体的字符编码
        req.setCharacterEncoding("UTF-8");

        String fromCardNo = req.getParameter("fromCardNo");
        String toCardNo = req.getParameter("toCardNo");
        String moneyStr = req.getParameter("money");
        int money = Integer.parseInt(moneyStr);

        Result result = new Result();

        try {

            testServiceAnnoServiceImpl.test1();

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

    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        // 设置请求体的字符编码
        req.setCharacterEncoding("UTF-8");

        String fromCardNo = req.getParameter("fromCardNo");
        String toCardNo = req.getParameter("toCardNo");
        String moneyStr = req.getParameter("money");
        int money = Integer.parseInt(moneyStr);

        Result result = new Result();

        try {

            testServiceAnnoServiceImpl.test1();

            transferService.transfer(fromCardNo,toCardNo,money);
            result.setStatus("200");
        } catch (Exception e) {
            e.printStackTrace();
            result.setStatus("400");
            result.setMessage(e.toString());
        }
        // 响应
        res.setContentType("application/json;charset=utf-8");
        res.getWriter().print(JSONObject.toJSONString(result));
    }


}
