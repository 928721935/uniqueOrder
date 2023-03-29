package com.jida.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.mockito.Mockito.doNothing;

@RunWith(PowerMockRunner.class)
@PrepareForTest( {MockService.class, MockService2.class})
public class MockServiceTest {
//    @Mock
//    MockService mockService;

    @Test
    public void testAa() throws Exception {
        MockService.aa();
    }

    @Test
    public void testnn() throws Exception {
        MockedStatic<MockService> mockServiceMockedStatic = Mockito.mockStatic(MockService.class);
        doNothing().when(mockServiceMockedStatic).when(MockService::aa);

        MockService.bb();
    }

    @Test
    public void testBb() throws Exception {
//        PowerMockito.mockStatic(MockService.class);
//        PowerMockito.doNothing().when(MockService.class, "aa");
        PowerMockito.spy(MockService.class);
        PowerMockito.doNothing().when(MockService.class, "aa");

        PowerMockito.mockStatic(MockService2.class);
        PowerMockito.doNothing().when(MockService2.class, "cc");

        MockService.bb();
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme