package com.midasit.mvp.study;

import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.exceptions.verification.NoInteractionsWanted;
import org.mockito.exceptions.verification.VerificationInOrderFailure;

import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;
import static org.mockito.ArgumentMatchers.anyFloat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.atMost;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

public class MockitoStudyTest {
    @Test
    public void test_mockObject() {
        List mockList = mock(List.class);
        
        mockList.add("asdf");
        
        // Mock Object는 자신의 행동을 기억한다.
        // Verify를 통해서 해당하는 행동이 잘 수행되었는지 확인할 수 있다.
        verify(mockList).add("asdf");
    }
    
    @Test
    public void test_stubbing() {
        List mockList = mock(List.class);
    
        // Stub: 메서드의 행동을 재정의함
        when(mockList.get(0)).thenReturn(10);
        // Exception도 날릴 수 있다.
        when(mockList.get(1)).thenThrow(new RuntimeException());
    
        assertEquals(mockList.get(0), 10);
        
        try {
            assertEquals(mockList.get(1), 10);
            fail();
        } catch (RuntimeException e) {
        
        }
    
        assertNull(mockList.get(10));
    
        // 순서는 상관없다.
        verify(mockList).get(10);
        verify(mockList).get(0);
        verify(mockList).get(1);
    }
    
    @Test
    public void test_argumentMatchers() {
        List mockList = mock(List.class);
    
        when(mockList.get(anyInt())).thenReturn("integer");
        when(mockList.add(anyFloat())).thenReturn(true);
        when(mockList.add(anyString())).thenReturn(true);
        
        assertEquals(mockList.get(10), "integer");
        assertEquals(mockList.get(0), "integer");
        assertEquals(mockList.get(-1), "integer");
        assertEquals(mockList.get(Integer.MAX_VALUE), "integer");
        
        assertTrue(mockList.add(14.5f));
        
        assertTrue(mockList.add("hello world"));
        assertTrue(mockList.add(""));
        assertTrue(mockList.add("c-wj2506uqjgfvb98a834mv09smsz"));
        
        // default: 1 times
        verify(mockList).add(anyFloat());
    
        verify(mockList, times(3)).add(anyString());
        verify(mockList, times(1)).add(eq("hello world"));
        verify(mockList, never()).add("android studio");
        
        verify(mockList, atLeast(3)).get(anyInt());
        verify(mockList, atMost(4)).get(anyInt());
    }
    
    @Test
    public void test_stubbingVoidMethods() {
        List mockList = mock(List.class);
        
        // methods which return void, should do as follows.
        doThrow(new RuntimeException()).when(mockList).size();
        
        try {
            mockList.size();
            fail();
        } catch (Exception e) {
        
        }
        
        verify(mockList).size();
    }
    
    @Test(expected = VerificationInOrderFailure.class)
    public void test_verifyMethodsInOrder() {
        List mockList = mock(List.class);
    
        mockList.add("first");
        mockList.size();
        mockList.add("second");
    
        InOrder inOrder = inOrder(mockList);
    
        inOrder.verify(mockList).add("first");
        inOrder.verify(mockList).add("second");
    
        // size가 second를 추가하는 동작보다 먼저 실행됬으므로 여기서 exception을 던진다.
        inOrder.verify(mockList).size();
    }
    
    @Test
    public void test_verifyMockInOrder() {
        List first = mock(List.class);
        List second = mock(List.class);
    
        first.add("first");
        second.add("second");
    
        InOrder inOrder = inOrder(first, second);
    
        inOrder.verify(first).add("first");
        inOrder.verify(second).add("second");
    }
    
    @Test(expected = NoInteractionsWanted.class)
    public void test_verifyNoMoreInteractions() {
        List mockList = mock(List.class);
        
        mockList.add(0);
        mockList.get(0);
        
        verify(mockList).get(0);
        
        // This line should throw NoInteractionWanted exception
        // because we didn't verify mockList.add(0);
        verifyNoMoreInteractions(mockList);
    }
}
