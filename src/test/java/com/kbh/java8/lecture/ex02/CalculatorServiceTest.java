package com.kbh.java8.lecture.ex02;

import com.kbh.java8.lecture.ex02.Subtraction;
import com.kbh.java8.lecture.ex02.Calculation;
import com.kbh.java8.lecture.ex02.Division;
import com.kbh.java8.lecture.ex02.Multiplication;
import org.junit.Test;

import static org.assertj.core.api.Assertions.*;

/**
 * Created by ohjic on 2016-02-22.
 */
public class CalculatorServiceTest {

    @Test
    public void testCalculatAddition() throws Exception {
        /*CalculatorService calculatorService = new CalculatorService(new Addtion());*/
        /*Calculation calculation = new Addtion();*/
        Calculation calculation = (i1,i2) -> i1+i2;         //테스트시!!
        final int actual = calculation.calculate(1,1);
        assertThat(actual).isEqualTo(2);
    }
    @Test
    public void testCalculatSubtraction() throws Exception {
        Calculation calculation =  new Subtraction();
        final int actual = calculation.calculate(1,1);
        assertThat(actual).isEqualTo(0);
    }
    @Test
    public void testCalculatMultiplication() throws Exception {
        Calculation calculation =  new Multiplication();
        final int actual = calculation.calculate(3,8);
        assertThat(actual).isEqualTo(24);
    }
    @Test
    public void testCalculatDivision() throws Exception {
        Calculation calculation =  new Division();
        final int actual = calculation.calculate(3,0);
        assertThat(actual).isEqualTo(3);
    }
}