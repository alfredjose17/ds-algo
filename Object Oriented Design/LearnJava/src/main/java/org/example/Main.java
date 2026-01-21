package org.example;


public class Main {
    static void main() {
        Inventory inventory = new Inventory();

        Fruit fruit = new Fruit("Apple", 20, "Fuji");
        Weapon weapon = new Weapon("Sword", 2, 75, "Melee");

        inventory.addItem(fruit);
        inventory.addItem(weapon);

        inventory.displayInventory();
        inventory.displayInventory("Fuji");
    }
}
