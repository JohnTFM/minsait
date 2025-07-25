package atividade;

public class Main {
        public static void main(String[] args) {

            Order order = new Order();

            order.addItem(new ProductItem("Notebook", 32.00));

            order.addItem(new ProductItem("Mouse", 50.00));

            TotalCalculator calculator = new SimpleTotalCalculator();

            double total = calculator.calculate(order);

            System.out.println("Total: R$ " + total);

    }

}
