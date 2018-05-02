package com.tw;

import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.LinkedList;
import java.util.Scanner;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.Matchers.shortThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class LibraryTest {
    private ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @Before
    public void setup() {
        System.setOut(new PrintStream(outContent));
    }

    @Test
    public void testSomeLibraryMethod() {
        Library classUnderTest = new Library();
        assertTrue("someLibraryMethod should return 'true'", classUnderTest.someLibraryMethod());
    }

    @Test
    public void testMockClass() throws Exception {
        // you can mock concrete classes, not only interfaces
        LinkedList mockedList = mock(LinkedList.class);

        // stubbing appears before the actual execution
        String value = "first";
        when(mockedList.get(0)).thenReturn(value);

        assertEquals(mockedList.get(0), value);
    }

    @Test
    public void testMain() {
        Library library = new Library();
        library.printMenu();

        assertEquals(outContent.toString().endsWith("1. 添加学生\n" +
                "2. 生成成绩单\n" +
                "3. 退出\n" +
                "请输入你的选择（1～3）：\n"), true);
    }



    @Test
    public void shouldAddStudent() {
        Library library = new Library();
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream("1\n张三, 123, Math: 98, English: 99\n2\n3\n".getBytes());
        System.setIn(byteArrayInputStream);
        library.menu();

        assertEquals(outContent.toString().endsWith("1. 添加学生\n" +
                "2. 生成成绩单\n" +
                "3. 退出\n" +
                "请输入你的选择（1～3）：\n" +
                "1. 添加学生\n" +
                "2. 生成成绩单\n" +
                "3. 退出\n" +
                "请输入你的选择（1～3）：\n" +
                "请输入学生信息（格式：姓名, 学号, 学科: 成绩, ...），按回车提交：\n" +
                "1. 添加学生\n" +
                "2. 生成成绩单\n" +
                "3. 退出\n" +
                "请输入你的选择（1～3）：\n" +
                "成绩单\n" +
                "姓名|English|Math|平均分|总分\n" +
                "========================\n" +
                "\n" +
                "张三|99|98|98|197\n" +
                "\n" +
                "========================\n" +
                "\n" +
                "全班总分平均数：197\n" +
                "全班总分中位数：197\n" +
                "1. 添加学生\n" +
                "2. 生成成绩单\n" +
                "3. 退出\n" +
                "请输入你的选择（1～3）：\n"), true);
    }


    @Test
    public void shouldNotAddWrongStudent() {
        Library library = new Library();
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream("1\n张三,123,Math:98,English:99\n张三, 123, Math: 98, English: 99\n2\n3\n".getBytes());
        System.setIn(byteArrayInputStream);
        library.menu();

        assertEquals(outContent.toString().endsWith("1. 添加学生\n" +
                "2. 生成成绩单\n" +
                "3. 退出\n" +
                "请输入你的选择（1～3）：\n" +
                "1. 添加学生\n" +
                "2. 生成成绩单\n" +
                "3. 退出\n" +
                "请输入你的选择（1～3）：\n" +
                "请输入学生信息（格式：姓名, 学号, 学科: 成绩, ...），按回车提交：\n" +
                "请按正确的格式输入（格式：姓名, 学号, 学科: 成绩, ...）：\n" +
                "1. 添加学生\n" +
                "2. 生成成绩单\n" +
                "3. 退出\n" +
                "请输入你的选择（1～3）：\n" +
                "成绩单\n" +
                "姓名|English|Math|平均分|总分\n" +
                "========================\n" +
                "\n" +
                "张三|99|98|98|197\n" +
                "\n" +
                "========================\n" +
                "\n" +
                "全班总分平均数：197\n" +
                "全班总分中位数：197\n" +
                "1. 添加学生\n" +
                "2. 生成成绩单\n" +
                "3. 退出\n" +
                "请输入你的选择（1～3）：\n"), true);
    }


    @Test
    public void shouldPrintRightTable() {
        Library library = new Library();
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream("1\n张三, 123, Math: 98, English: 99\n1\n王五, 321, Math: 2, English: 1\n1\n赵四, 213, Math: 5, English: 6\n2\n3\n".getBytes());
        System.setIn(byteArrayInputStream);
        library.menu();

        assertEquals(outContent.toString().endsWith("成绩单\n" +
                "姓名|English|Math|平均分|总分\n" +
                "========================\n" +
                "\n" +
                "张三|99|98|98|197\n" +
                "\n" +
                "王五|1|2|1|3\n" +
                "\n" +
                "赵四|6|5|5|11\n" +
                "\n" +
                "========================\n" +
                "\n" +
                "全班总分平均数：70\n" +
                "全班总分中位数：3\n" +
                "1. 添加学生\n" +
                "2. 生成成绩单\n" +
                "3. 退出\n" +
                "请输入你的选择（1～3）：\n"), true);
    }
}