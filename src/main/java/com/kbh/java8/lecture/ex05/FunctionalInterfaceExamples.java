package com.kbh.java8.lecture.ex05;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Created by ohjic on 2016-02-22.
 */
public class FunctionalInterfaceExamples {
    public static void main(String args[]){
        final Product productA = new Product(1L, "A", new BigDecimal("10.00"));
        final Product productB = new Product(2L, "B", new BigDecimal("55.50"));
        final Product productC = new Product(3L, "C", new BigDecimal("17.45"));
        final Product productD = new Product(4L, "D", new BigDecimal("20.00"));
        final Product productE = new Product(5L, "E", new BigDecimal("110.99"));
        final List<Product> products = Arrays.asList(productA, productB, productC, productD, productE);
        final BigDecimal twenty = new BigDecimal("20");
        /*List<Product> result = filter(products,product -> product.getPrice().compareTo(twenty) >= 0); //작으면1 같으면0 크면 -1
        * System.out.println(result);*/
         /* 가격이 $20 이상 되는 Product 찾기 */
        System.out.println("products >= $20: " +
                        filter(products, product -> product.getPrice().compareTo(twenty) >= 0)
        );
    /* 가격이 $10 이하 되는 Product 찾기 */
        System.out.println("products <= $10: " +
                        filter(products, product -> product.getPrice().compareTo(new BigDecimal("10")) <= 0)
        );

    /* 가격이 $50 초과되는 Product 찾기 */
        final List<Product> expensiveProducts =
                filter(products, product -> product.getPrice().compareTo(new BigDecimal("50")) > 0);
        final List<DiscountedProduct> discountedProducts = map(expensiveProducts,product -> new DiscountedProduct(product.getId(),product.getName(),product.getPrice().multiply(new BigDecimal("0.5"))));

        System.out.println(" expensive products: " + expensiveProducts);
        System.out.println("discounted products: " + discountedProducts);
     /*가령 여기에서 Predicate의 Generics의 타입을 Product 혹은 DiscoutnProduct 타입으로 지정하게되면 각각의 fileter에서 에러가 나게된다.
     * DiscountProduct로 지정하게되면 filter를 만들시 DiscountProduct를 위해서 만들어 졌기에 에러가나고
     * Product를 지정하게되면 반대의 경우가 생기고 우리는 filter 부분에 수정의 필요성을 느낀다.
      *Predicate<T> ===> Predicate<? super T>로 변경하면 해결이 된다.*/
        final Predicate<Product> lessThanOrEqualTo30  = product -> product.getPrice().compareTo(new BigDecimal("30")) <= 0;
        System.out.println("discounted products (<= $30)" +
                        filter(discountedProducts, lessThanOrEqualTo30 )
        );
        System.out.println("           products (<= $30)" +
                        filter(products, lessThanOrEqualTo30)
        );

        final List<BigDecimal> prices = map(products,product-> product.getPrice());
        BigDecimal total = BigDecimal.ZERO;
        for(final  BigDecimal price : prices){
            total =  total.add(price);
        }
        System.out.println("total : "+total);

        final BigDecimal newTotal = total(products, product -> product.getPrice());
        System.out.println("new total : "+newTotal);

        final BigDecimal disCountTotal = total(discountedProducts, product -> product.getPrice());
        System.out.println("new total : "+disCountTotal);


        //Order Class

        final Order order = new Order(1L, "on-1234", Arrays.asList(
                new OrderedItem(1L, productA, 2),
                new OrderedItem(2L, productC, 1),
                new OrderedItem(3L, productD, 10)
        ));

        BigDecimal orderTotal = BigDecimal.ZERO;
        for (OrderedItem item : order.getItems()) {
            orderTotal =
          /* price * quantity = OrderedItem 가격합계 */
                    orderTotal.add(
                            item.getProduct().getPrice().multiply(new BigDecimal(item.getQuantity()))
                    );
        }
        System.out.println("order total in old way: " + orderTotal);
        System.out.println("           order total: " + order.totalPrice());



    }

    private static <T>  List<T> filter(List<T> list, Predicate<? super T> predicate){
        final List<T> result = new ArrayList<>();
        for(final T t : list){
            if(predicate.test(t)){
                result.add(t);
            }
        }
        return result;
    }
    private static <T, R> List<R> map(final List<T> list, final Function<T, R> function) {
        final List<R> result = new ArrayList<>();
        for (final T t : list) {
      /* function을 이용해서 타입변환: T -> R */
            result.add(function.apply(t));
        }
        return result;
    }
    private static <T> BigDecimal total(final List<T> list, final Function<T, BigDecimal> mapper) {
        BigDecimal total = BigDecimal.ZERO;
        for (final T t : list) {
      /* mapper를 이용해서 T타입을 BigDecimal로 변환 */
            total = total.add(mapper.apply(t));
        }
        return total;
    }


    /*Lombok Plugin과 각종 세팅, 그리고 mvnrepository를 통한 라이브러리를 추가하면 Lombok Annotation을 활용할 수 있습니다.
* @AllArgsConstructor를 사용하게되면 모든필드를 사용하여 Constuctor를 사용한다.
* */
    @AllArgsConstructor
    @Data
    static class Product{
        private Long id;
        private String name;
        private BigDecimal price;
    }

    @ToString(callSuper = true)
    static class DiscountedProduct extends Product{
        public DiscountedProduct(final Long id,final String name,final BigDecimal price) {
            super(id, name, price);
        }
    }

    /*사용자는 변수에만 신경쓰고싶지만 여러 잡다한 노이즈 코드를 작성하게된다.*/
    static class Product_Default{
        private Long id;
        private String name;
        private BigDecimal price;

        public Product_Default(final Long id,final String name,final BigDecimal price) {
            this.id = id;
            this.name = name;
            this.price = price;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public BigDecimal getPrice() {
            return price;
        }

        public void setPrice(BigDecimal price) {
            this.price = price;
        }

        @Override
        public String toString() {
            return new StringBuilder("Product{")
                    .append("id=" + id )
                    .append(", name = '" + name + '\'')
                    .append(", price=" + price).toString();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Product)) return false;

            final Product_Default product = (Product_Default) o;

            return Objects.equals(getId(),product.getId())
                    && Objects.equals(getName(),product.getName())
                    && Objects.equals(getPrice(),product.getPrice());
        }

        @Override
        public int hashCode() {
            return Objects.hash(getId(),getName(),getPrice());
        }
    }

    @AllArgsConstructor
    @Data
    static class OrderedItem {
        private Long id;
        private Product product;
        private int quantity;

        public BigDecimal getItemTotal() {
            return product.getPrice().multiply(new BigDecimal(quantity));
        }
    }

    @AllArgsConstructor
    @Data
    static class Order {
        private Long id;
        private String orderNumber;
        private List<OrderedItem> items;

        public BigDecimal totalPrice() {
            return total(items, item -> item.getItemTotal());
        }
    }

}

