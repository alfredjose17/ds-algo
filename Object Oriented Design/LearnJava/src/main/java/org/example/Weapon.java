package org.example;

public class Weapon extends Item{
    private int damage;
    private String type;

    public Weapon(String name, int quantity, int damage, String type) {
        super(name, quantity);
        this.damage = damage;
        this.type = type;
    }

    public int getDamage() {
        return damage;
    }

    public String getType() {
        return type;
    }

    // Method overriding
    @Override
    public void displayInfo() {
        System.out.println("Item: " + getName() + ", Quantity: " + getQuantity() + ", Damage: " + damage + ", Type: " + type);
    }
}
