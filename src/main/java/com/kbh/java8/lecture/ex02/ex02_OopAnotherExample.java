package com.kbh.java8.lecture.ex02;

public class ex02_OopAnotherExample {
    /*
    기존 OOP의 특성과 Functional Programming 시 병렬프로그래밍(Parallel Programming)의 작성의 유용함과
    또다른 장점을 확인하는 예제제    * */
    public static void main(String[] args) {
        final CalculatorService calculatorService = new CalculatorService(new Addtion(),new Subtraction());
        final int addresultesult = calculatorService.calculate(11,4);
        System.out.println(addresultesult);
        final int subtractionResult = calculatorService.calculate(11,1);
        System.out.println(subtractionResult);
        final int multiplicationResult = calculatorService.calculate(11,2);
        System.out.println(multiplicationResult);
        final int divisionResult = calculatorService.calculate(20,4);
        System.out.println(divisionResult);
        /*First Class Citizen
        *
        * 프로그래밍 언어의 설계 시에 런타임에 프로그램 흐름의 중심으로 결정한 엔터티를 의미한다.
        * 예를 들어, 객체 지향 언어에서 first-class citizen은 ‘객체’라 볼 수 있으며,
        * 프로그램이 실행되면 객체들 간의 관계(어떤 객체가 어떤 객체를 참조하는지,
        * 메소드는 어떤 객체를 인수로 받아서 어떤 객체를 반환하는지)에 의해 프로그램의 행태가 결정된다.
        *
        * 1. 구성요소를 파라미터로 넘겨받을수 있어야한다.
        * Element elm = ~~~;
        * getNumber(elm);
        *
        * 2. 구성요소에 해당하는 결과값을 받아낼 수 있다.
        * Element result = getResult();
        *
        * 3.변수나 데이터 구조안에 담을 수 있다.
        * List<Element> list = Arrays.asList(elm,result);
        *
        * 그렇다면 자바에서는 메서드의 파라미터에 또다른 매서드를 파마리터로 세팅이 불가하다.
        * public String getName(){}
        * public String findByName(getname) ??? // 불가
        *
        * public doSometion(){ return getname; } //불가
        *
        * List<?> list = Arrays.asList(getname); // 불가
        *
        * 또한 변수(Variable)에 부여(assign)하는 SomeMethod m = getname; 역시 불가하다
        * 이러하기 때문에 다른 언어의 Function에 해당하는 자바의 method는 FCC가 아니게 된다.
        * 만약에 자바에서의 메서드가 FCC가 적용된다하면 우리는 First Class Function을 지원한다 말할 수 있다.
        * 자바에선 1.8부터 이것이 가능해졌다.
        * */
        final FpCalculatorService fpCalculatorService = new FpCalculatorService();
        System.out.println("       addtion : "+fpCalculatorService.calculate(new Addtion(),11,4));
        System.out.println("    subtration : "+fpCalculatorService.calculate(new Subtraction(),11,1));
        System.out.println("multiplication : "+fpCalculatorService.calculate(new Multiplication(),11,2));
        System.out.println("      division : "+fpCalculatorService.calculate(new Division(),20,4));

        final Calculation calculation = (i1, i2) -> i1 + i2;
        System.out.println("       addtion : "+fpCalculatorService.calculate(calculation,11,4));
        final Calculation calculation1 = (i1, i2) -> i1 - i2;
        System.out.println("    subtration : "+fpCalculatorService.calculate(calculation1,11,1));
        final Calculation calculation2 = (i1, i2) -> i1 * i2;
        System.out.println("multiplication : "+fpCalculatorService.calculate(calculation2,11,2));
        final Calculation calculation3 = (i1, i2) -> i1 / i2;
        System.out.println("      division : "+fpCalculatorService.calculate(calculation3,20,4));
        final Calculation calculation4 = (i1, i2) -> i1 / i2 * 2;
        System.out.println("        custom : "+fpCalculatorService.calculate(calculation4,20,4));
    }
}

/*사용자는 테스트와 유지보수를 위하여 코드를 처음부터 살펴보야하는 필요가있다.
* 이러한점을 해결하기위해 OOP에서는 Strategy Pattern을 활용하기도한다.
* Effective Java의 책을 보게되면 Fuction Object를 사용하는 방법도 StrategyPattern으로 보고있고
* Function Object는 function이 없던 자바시절에 Object 내부에 하나의 메소드만 가지고 쓰던 방법*/
interface  Calculation{
    int calculate(int num1, int num2);
}

class Addtion implements Calculation {
    @Override
    public int calculate(final int num1,final int num2){
        return  num1+num2;
    }
}

class Subtraction implements Calculation {
    @Override
    public int calculate(final int num1,final int num2){
        return  num1-num2;
    }
}

class Multiplication implements Calculation {
    @Override
    public int calculate(final int num1,final int num2){
        return  num1*num2;
    }
}

class Division implements Calculation {
    @Override
    public int calculate(final int num1,final int num2){
        return  num1/num2;
    }
}


/*
* 가령 덧셈서비스만 만들었다 가정시 요구사항이 추가된다면?
*
* */
class CalculatorService{
    //Strategy Pattern 적용
    private final Calculation calculation;
    private final Calculation calculation2;

    public CalculatorService(Calculation calculation, Calculation calculation2) {
        this.calculation = calculation;
        this.calculation2 = calculation2;
    }
    public int calculate(int num1, int num2){

        //1차 요구사항 적용
        /*return num1+num2;*/

        //2차 요구사항 적용
       /*if(calculatoin == '+'){
            return num1+num2;
        }else if (calculatoin == '-'){
            return num1-num2;
        }else if (calculatoin == '*'){
            return  num1*num2;
        }else if (calculatoin == '/'){
           return  num1/num2;
        }else {
            throw  new IllegalArgumentException("Unknown calcultation :" + calculatoin);
        }*/

        if(num1 > 10 && num2 < num1){
            //Strategy Pattern 적용
            return calculation.calculate(num1,num2);
        }else{
            throw new IllegalArgumentException("Invalid Exception : " + num1 +"::::"+num2);
        }

    }
    public int compute(int num1, int num2){
        if(num1 > 10 && num2 < num1){
            //Strategy Pattern 적용
            return calculation2.calculate(num1,num2);
        }else{
            throw new IllegalArgumentException("Invalid Exception : " + num1 +"::::"+num2);
        }
    }
}

class FpCalculatorService{
    public int calculate(Calculation calculation,int num1, int num2){
        if(num1 > 10 && num2 < num1){
            //Strategy Pattern 적용
            return calculation.calculate(num1,num2);
        }else{
            throw new IllegalArgumentException("Invalid Exception : " + num1 +"::::"+num2);
        }
    }
}
