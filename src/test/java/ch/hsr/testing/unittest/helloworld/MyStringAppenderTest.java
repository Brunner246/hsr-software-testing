package ch.hsr.testing.unittest.helloworld;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MyStringAppenderTest {
    private MyStringAppender myStringAppender;
    @BeforeEach
    public void setUp() {
        System.out.println("setUp");
        this.myStringAppender = new MyStringAppender();
    }

    @BeforeEach
    public void beforeEach() {
        System.out.println("beforeEach");
    }

    @Test
    public void append() {
        String lAppended = myStringAppender.append("hello");
        assertEquals(lAppended, "hello");
    }

    @Test
    public void appendNull() {
        String lAppended = myStringAppender.append("");
        assertEquals(lAppended, "");
    }

    @Test
    public void provocateError()
    {
//        assertThrows(NullPointerException.class, () -> {
//            myStringAppender.append(null);
//        });
    }

    @AfterEach
    void tearDown() {
        System.out.println("tearDown");
    }
}