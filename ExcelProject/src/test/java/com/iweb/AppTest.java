package com.iweb;

import static org.junit.Assert.assertTrue;

import com.iweb.anno.Excel;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue() throws Exception {
        List<String> tags = new ArrayList<>();
        Class c = Class.forName("com.iweb.pojo.Bill");
        Field[] fields = c.getDeclaredFields();
        for(Field f:fields){
            Excel e = f.getAnnotation(Excel.class);
            tags.add(e.name());
        }
        for(String s:tags){
            System.out.println(s);
        }
    }
}
