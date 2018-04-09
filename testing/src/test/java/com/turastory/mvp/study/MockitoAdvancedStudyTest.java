package com.turastory.mvp.study;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Map;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;
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