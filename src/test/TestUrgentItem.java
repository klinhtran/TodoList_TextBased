package test;

import model.Item;
import model.NormalItem;
import model.UrgentItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.Assertions;

import java.text.ParseException;
import java.util.Date;
import java.util.GregorianCalendar;

public class TestUrgentItem {
    Item item;

    @BeforeEach
    public void makeTestItem() {
        Date needBy = new GregorianCalendar(2012, 1, 13).getTime();
        item = new UrgentItem("thing", 1, needBy);
    }

    @Test
    public void testToStringUrgent() {
        Assertions.assertEquals("thing,1,2/13/2012", item.toString());
    }

    @Test
    public void testParseItemUrgent() throws ParseException {
        item.parseLine("thing,1,2/13/2012");
        Assertions.assertEquals("thing,1,2/13/2012", item.toString());
    }

    @Test
    public void testParseItemWithBadDate() {

        boolean failed = false;
        try {
            item.parseLine("thing,1,2/13");
        } catch (ParseException e) {
            failed = true;
        }

        Assertions.assertTrue(failed);
    }
}
