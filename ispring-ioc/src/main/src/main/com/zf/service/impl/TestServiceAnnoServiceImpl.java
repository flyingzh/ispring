package com.zf.service.impl;

import com.zf.annotation.Service;
import com.zf.service.TestServiceAnnoService;

/**
 * @author admin
 * @date 2020/3/4 15:35
 * @description
 */
@Service
public class TestServiceAnnoServiceImpl implements TestServiceAnnoService {
//    @Override
    public void test1() {
        System.out.println(123123123);
    }
}
