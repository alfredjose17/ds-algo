package org.example;

import java.sql.SQLOutput;
import java.util.ArrayList;

public class Inventory {
    private ArrayList<Item> items;

    public Inventory() {
        items = new ArrayList<>();
    }

    public void addItem (Item item) {
        items.add(item);
    }

    public void displayInventory() {
        for (Item item : items) {
            item.displayInfo();
        }
    }

    // Method overloading
    public void displayInventory(String type) {
        for (Item item : items) {
            if (item instanceof Fruit && ((Fruit) item).getType().equalsIgnoreCase(type)) {
                item.displayInfo();
            }
            else if (item instanceof Weapon && ((Weapon) item).getType().equalsIgnoreCase(type)) {
                item.displayInfo();
            }
        }
    }
}
