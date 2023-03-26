package com.jida.service;

import com.jida.common.SnowflakeIdWorkerUtil;
import com.jida.mapper.LoginUserVOMapper;
import com.jida.mapper.MyOrderVOMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import static org.mockito.Mockito.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest( {OrderService.class, SnowflakeIdWorkerUtil.class})
public class OrderServiceTest {
    @Mock
    MyOrderVOMapper myOrderVOMapper;
    @Mock
    LoginUserVOMapper loginUserVOMapper;
    @Mock
    StringRedisTemplate redisTemplate;
    @InjectMocks
    OrderService orderService;
    @Mock
    private ValueOperations valueOperations;


    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        Mockito.when(redisTemplate.opsForValue()).thenReturn(valueOperations);
    }

    @Test
    public void testNewOrder() throws Exception {
        when(myOrderVOMapper.insert(any())).thenReturn(0);
        OrderService spyOrderService = spy(orderService);
        spyOrderService.newOrder();
    }

    @Test
    public void testNewOrderNo() throws Exception {
        when(myOrderVOMapper.insert(any())).thenReturn(0);
        when(myOrderVOMapper.getMaxOrderNo()).thenReturn(Long.valueOf(1));

        String result = orderService.newOrderNo();
        Assert.assertEquals("replaceMeWithExpectedResult", result);
    }

    @Test
    public void testNewOrderNoNew() throws Exception {
        when(myOrderVOMapper.insert(any())).thenReturn(0);
        when(myOrderVOMapper.getMaxOrderNo()).thenReturn(Long.valueOf(1));
        Mockito.when(redisTemplate.execute(any(),anyList(),any(),any())).thenReturn("1");
        PowerMockito.mockStatic(SnowflakeIdWorkerUtil.class);
        PowerMockito.when(SnowflakeIdWorkerUtil.getInstance()).thenReturn(new SnowflakeIdWorkerUtil(1,1));
        String result = orderService.newOrderNoNew();
    }

    @Test
    public void testNewOrderNoNewMysql() throws Exception {
        when(myOrderVOMapper.insert(any())).thenReturn(0);
        when(loginUserVOMapper.getNextSerialNo(anyString())).thenReturn(Long.valueOf(1));
        when(loginUserVOMapper.updateSerial(anyString(), anyLong(), anyLong())).thenReturn(0);

        OrderService spyOrderService = PowerMockito.spy(orderService);
        PowerMockito.doNothing().when(spyOrderService, "getNextSerial", any(), any(), any());
        PowerMockito.mockStatic(SnowflakeIdWorkerUtil.class);
        PowerMockito.when(SnowflakeIdWorkerUtil.getInstance()).thenReturn(new SnowflakeIdWorkerUtil(1,1));

        String result = spyOrderService.newOrderNoNewMysql();
    }

    @Test
    public void testNewOrderNoNewMysql2() throws Exception {
        when(myOrderVOMapper.insert(any())).thenReturn(0);
        when(loginUserVOMapper.getNextSerialNoForUpdate(anyString())).thenReturn(Long.valueOf(1));
        when(loginUserVOMapper.updateSerial(anyString(), anyLong(), anyLong())).thenReturn(0);

        String result = orderService.newOrderNoNewMysql2();
        Assert.assertEquals("replaceMeWithExpectedResult", result);
    }
}