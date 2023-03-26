package com.jida.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.List;

import static org.mockito.Matchers.anyString;

@RunWith(PowerMockRunner.class)  //告诉JUnit使用PowerMockRunner进行测试
@PrepareForTest({MyToolService.class}) //所有需要测试的类列在此处，适用于模拟final类或有final, private, static, native方法的类
public class MyToolServiceTest {
    @InjectMocks
    MyToolService myToolService;

    @Test
    public void testFindById() throws Exception {
        String result = myToolService.findById(0);
    }

    @Test
    public void testFindAll() throws Exception {
        List<String> result = myToolService.findAll(0);
    }

    @Test
    public void testGetIdValue() throws Exception {
//        Method checkIds = PowerMockito.method(MyToolService.class, "checkIds", String.class);
        MyToolService spy = PowerMockito.spy(myToolService);
        PowerMockito.when(spy, "checkIds", anyString()).thenReturn(true);//私有方法mockito不行了，需要用无所不能的PowerMock监视spy
        int result = spy.getIdValue("1");
    }

    @Test
    public void testCheckIdRange() throws Exception {
        boolean result = MyToolService.checkIdRange(0);
        Assert.assertEquals(true, result);
    }

    @Test
    public void testConvertStr() throws Exception {
        String result = myToolService.convertStr("str");
    }

    @Test
    public void testQuantity() throws Exception {
        myToolService.quantity(0);
    }

    @Test
    public void testExecQuantity() throws Exception {
        myToolService.execQuantity("ids");
    }
}