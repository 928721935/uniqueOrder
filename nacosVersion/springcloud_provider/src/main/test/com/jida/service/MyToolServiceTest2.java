package com.jida.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author xu
 * @Date 2021/6/28 14:59
 */

@RunWith(PowerMockRunner.class)  //告诉JUnit使用PowerMockRunner进行测试
@PrepareForTest({MyToolService.class}) //所有需要测试的类列在此处，适用于模拟final类或有final, private, static, native方法的类
public class MyToolServiceTest2 {


    //powermockito private方法
    @Test
    public void getEntity_getIdValue_return_false_test() throws Exception {
        MyToolService myToolService = PowerMockito.spy(new MyToolService());

        //没有去真实的执行私有的checkIds，但是按我们的要求，直接返回了false
        PowerMockito.doReturn(false).when(myToolService, "checkIds", Mockito.anyString());

        int idValue = myToolService.getIdValue(Mockito.anyString());

        Assert.assertEquals(0, idValue);
    }

    //powerMockito private方法
    @Test
    public void getEntity_getIdValue_return_true_test() throws Exception {
        MyToolService myToolService = PowerMockito.spy(new MyToolService());

        //没有去真实的执行私有的checkIds，但是按我们的要求，直接返回了true
        PowerMockito.doReturn(true).when(myToolService, "checkIds", Mockito.anyString());

        int idValue = myToolService.getIdValue("2");

        Assert.assertEquals(2, idValue);
    }


    //powerMockito 执行static方法
    @Test
    public void getEntity_check_id_range_return_false_test() throws Exception {
        PowerMockito.mockStatic(MyToolService.class);

        //没有去真实的执行静态的checkIdRange，但是按我们的要求，直接返回了false
        Mockito.when(MyToolService.checkIdRange(Mockito.anyInt())).thenReturn(false);

        MyToolService myToolService = PowerMockito.spy(new MyToolService());
        //没有去真实的执行私有的checkIds，但是按我们的要求，直接返回了true
        PowerMockito.doReturn(true).when(myToolService, "checkIds", Mockito.anyString());

        int idValue = myToolService.getIdValue("2");

        Assert.assertEquals(0, idValue);
    }

    @Test
    public void finalMethodTest() throws Exception {
        MyToolService myToolService = PowerMockito.spy(new MyToolService());

        //没有去真实的执行私有的finalMethods，但是按我们的要求，直接返回了true
        PowerMockito.doReturn(true).when(myToolService, "finalMethod", Mockito.anyString());

        String result = myToolService.convertStr("abc");

        Assert.assertEquals(result, "ABC");

    }


    @Test
    public void finalMethod_lower_Test() throws Exception {
        MyToolService myToolService = PowerMockito.spy(new MyToolService());

        //没有去真实的执行私有的finalMethods，但是按我们的要求，直接返回了true
        PowerMockito.doReturn(false).when(myToolService, "finalMethod", Mockito.anyString());

        String result = myToolService.convertStr("abc");

        Assert.assertEquals(result, "abc");
    }


    //对于一些没有返回值 的方法，如何判断
    //判断某个方法执行过了,执行了几次
    //判断类成员变量是否做了更改。
    @Test
    public void quantityTest() throws Exception {
        MyToolService myToolService = PowerMockito.spy(new MyToolService());

        //没有去真实的执行私有的checkIds，但是按我们的要求，直接返回了true
        PowerMockito.doReturn(true).when(myToolService, "checkIds", Mockito.anyString());

        myToolService.execQuantity(Mockito.anyString());

        //断言 dataLog 被执行一次
        PowerMockito.verifyPrivate(myToolService, Mockito.times(1)).invoke("dataLog", Mockito.anyString());


        //重新打桩
        //没有去真实的执行私有的checkIds，但是按我们的要求，直接返回了false
        PowerMockito.doReturn(false).when(myToolService, "checkIds", Mockito.anyString());

        //再次执行
        myToolService.execQuantity(Mockito.anyString());

        //白盒，获取修改的成员变量
        String newStr = (String) Whitebox.getField(MyToolService.class, "name").get(myToolService);

        Assert.assertTrue("ABC".equals(newStr));
    }

    //查找私有方法，并执行之
    @Test
    public void privateTest() throws InvocationTargetException, IllegalAccessException {

        MyToolService myToolService = PowerMockito.spy(new MyToolService());
        Method checkIds = PowerMockito.method(MyToolService.class, "checkIds", String.class);
        Object invoke = checkIds.invoke(myToolService, "121,363");
        System.out.println(invoke);
    }

    //private String checkPrivate(String str) {

    //私有方法嵌套，则mock之
    //查找私有方法，并执行之
    @Test
    public void privateTest1() throws Exception {

        MyToolService myToolService = PowerMockito.spy(new MyToolService());

        Method checkIds = PowerMockito.method(MyToolService.class, "checkPrivate", String.class);

        //没有去真实的执行私有的checkIds，但是按我们的要求，直接返回了true
        PowerMockito.doReturn(true).when(myToolService, "checkIds", Mockito.anyString());

        Object invoke = checkIds.invoke(myToolService, "121");
        System.out.println(invoke);
    }

    //关于抽象类的模拟，注意第二个参数
    //
    //mSpyAbstractActivity =  Mockito.mock(AbstractActivity.class, Mockito.CALLS_REAL_METHODS)

}