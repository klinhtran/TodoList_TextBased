package test;

import model.Item;
import model.NormalItem;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.ParseException;

public class TestNormalItem {
    Item item;

    @BeforeEach
    public void makeTestItem() {
        item = new NormalItem("thing", 1);
    }

    @Test
    public void testToStringNormal() {
        Assertions.assertEquals("thing,1", item.toString());
    }

    @Test
    public void testParseItemNormal() throws ParseException {
        item.parseLine("thing,1");
        Assertions.assertEquals("thing,1", item.toString());
    }

}
