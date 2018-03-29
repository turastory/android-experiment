package com.midasit.mvp.study;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.exceptions.verification.NoInteractionsWanted;
import org.mockito.exceptions.verification.VerificationInOrderFailure;

import java.util.List;
import java.util.Map;

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

public class MockitoAdvancedStudyTest {
    // Mock annotation - automate mock!
    @Mock private Map map;
    @Mock private List list;
    
    @Before
    public void initializeMockObjects() {
        MockitoAnnotations.initMocks(this);
    }
    
    @Test
    public void test_iteratorStubbing() {
        when(list.iterator().next())
            .thenReturn("foo")
            .thenReturn("bar")
            .thenReturn(null);
        
        assertEquals(list.iterator().next(), "foo");
        assertEquals(list.iterator().next(), "bar");
        assertNull(list.iterator().next());
        
        when(map.get("asdf"))
            .thenReturn("first", "second", "third");
        
        assertEquals(map.get("asdf"), "first");
        assertEquals(map.get("asdf"), "second");
        assertEquals(map.get("asdf"), "third");
        assertEquals(map.get("asdf"), "third");
    }
}