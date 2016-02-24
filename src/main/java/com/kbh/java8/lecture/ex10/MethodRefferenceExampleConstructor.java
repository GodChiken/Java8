package com.kbh.java8.lecture.ex10;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.function.Function;

/**
 * Created by ohjic on 2016-02-24.
 */
public class MethodRefferenceExampleConstructor {
    public static void main(String[] args) {
        final Section section1 = new Section(1);

        final Function<Integer,Section> sectionFactoryWithLambdaExpression = number -> new Section(number);
        final Section section1WithLambdaExpression= sectionFactoryWithLambdaExpression.apply(1);

        //생성자를 받을 파라미터만 만들어 놓은것 뿐이다. 생성한것이 아니다.
        final Function<Integer,Section> sectionFactoryWithMethodRefference = Section::new;
        //이렇게 해서 생성한다.
        final Section section1WithMethodRefference = sectionFactoryWithMethodRefference.apply(1);
        System.out.println(section1);
        System.out.println(section1WithLambdaExpression);
        System.out.println(section1WithMethodRefference);

        final OldProduct product = new OldProduct(1L,"A",new BigDecimal("100"));

        final OldProductCreator productCreator = OldProduct::new;
        productCreator.create(1L,"A",new BigDecimal("100"));

        /*가령 이렇게 생성하게되면 이프문같은 것으로 A생성 B생성 같은 분기문을 안만들어도된다.*/
        final ProductA a = createProduct(1L, "A", new BigDecimal("123"), ProductA::new);
        final ProductB b = createProduct(2L, "B", new BigDecimal("111"), ProductB::new);
        final ProductC c = createProduct(3L, "C", new BigDecimal("10"), ProductC::new);

        System.out.println(a);
        System.out.println(b);
        System.out.println(c);


    }
    private static <T extends Product> T createProduct(
            final Long id,
            final String name,
            final BigDecimal price,
            final ProductCreator<T> productCreator){
        if (id == null || id < 1L) {
            throw new IllegalArgumentException("The id must be a positive Long.");
        }
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("The name is not given.");
        }
        if (price == null || price.compareTo(BigDecimal.ZERO) <= 0) { // price <= ZERO
            throw new IllegalArgumentException("The price must be greater then 0.");
        }
        return productCreator.create(id, name, price);
    }
}
/**
 * Functional Interface
 * 한개의 인자값은?  Function
 * 두개의 인자값은?  BiFunction
 * 3개라면? 직접만들자.
* */
@FunctionalInterface
interface ProductCreator<T extends Product>{
    T create(Long id,String name,BigDecimal price);
}
@FunctionalInterface
interface OldProductCreator{
    OldProduct create(Long id,String name,BigDecimal price);
}

@AllArgsConstructor
@Data
class Section{
    private int number;
}

@AllArgsConstructor
@Data
class OldProduct{
    private Long id;
    private String name;
    private BigDecimal price;
}
@AllArgsConstructor
@Data
abstract class Product{
    private Long id;
    private String name;
    private BigDecimal price;
}

class ProductA extends Product{

    public ProductA(Long id, String name, BigDecimal price) {
        super(id, name, price);
    }

    @Override
    public String toString() {
        return "A="+super.toString();
    }
}
class ProductB extends Product{

    public ProductB(Long id, String name, BigDecimal price) {
        super(id, name, price);
    }

    @Override
    public String toString() {
        return "B="+super.toString();
    }
}
class ProductC extends Product {

    public ProductC(final Long id, final String name, final BigDecimal price) {
        super(id, name, price);
    }

    @Override
    public String toString() {
        return "C=" + super.toString();
    }
}