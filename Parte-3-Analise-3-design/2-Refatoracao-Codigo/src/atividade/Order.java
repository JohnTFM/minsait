package atividade;

import java.util.ArrayList;
import java.util.List;

public class Order {
    private final List<Item> items;

    public Order() {
        this.items = new ArrayList<>();
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public List<Item> getItems() {
        return items;
    }
}
