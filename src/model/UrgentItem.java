package model;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class UrgentItem extends Item{
    protected static DateFormat dateFormat = new SimpleDateFormat("M/d/y");
    Date needBy;

    public UrgentItem(String name, int quantity, Date needBy){  // constructor
        super(name,quantity);
        this.needBy = needBy;
    }

    public UrgentItem(Section section) {
        super(section);
        this.needBy = null;
    }

    @Override
    public String toString() {
        return name + "," + quantity + "," + dateFormat.format(needBy);
    }

    public String toFileString() {
        return "*" + this;
    }
    public void parseLine(String nextItemLine) throws ParseException {
        String[] parts = nextItemLine.split(",");
        this.name = parts[0];
        this.quantity = new Integer(parts[1]);
        this.needBy = dateFormat.parse(parts[2]);
    }
}
