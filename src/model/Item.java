package model;

import java.text.ParseException;
import java.util.Objects;


public abstract class Item {


    protected String name;
    protected int quantity;
    protected Section section;

    public Item(String name, int quantity){  // constructor
        this.name = name;
        this.quantity = quantity;
    }

    public Item(Section section) {
        name = "";
        quantity = 0;
        this.section = section;
    }

    public abstract void parseLine(String nextItemLine) throws ParseException;

    public abstract String toFileString();

    public Section getSection(){
        return section;
    }

    public void setSection(Section s){
        this.section = s;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return quantity == item.quantity &&
                Objects.equals(name, item.name) &&
                Objects.equals(section, item.section);
    }

    @Override
    public int hashCode() {

        return Objects.hash(name, quantity, section);
    }
}
