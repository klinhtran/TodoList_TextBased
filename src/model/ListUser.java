package model;

public class ListUser implements Observer {
    @Override
    public void update() {
        System.out.println("An item was added");
    }
}
