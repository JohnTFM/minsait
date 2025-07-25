package atividade;

public class SimpleTotalCalculator implements TotalCalculator {
    @Override
    public double calculate(Order order) {
        return order.getItems().stream()
                .mapToDouble(Item::getPrice)
                .sum();
    }
}

