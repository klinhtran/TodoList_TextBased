package model;

import java.text.ParseException;
import java.util.Date;

public class NormalItem extends Item{

    public NormalItem(String name, int quantity){  // constructor
        super(name,quantity);
    }

    public NormalItem(Section section) {
        super(section);
    }

    @Override
    public String toString() {
        return name + "," + quantity;
    }

    public String toFileString() {
        return "-" + this;
    }

    public void parseLine(String nextItemLine) throws ParseException {
        String[] parts = nextItemLine.split(",");
        this.name = parts[0];
        this.quantity = new Integer(parts[1]);
    }

}
