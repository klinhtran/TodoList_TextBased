package test;

import model.GroceryList;
import model.Item;
import model.NormalItem;
import model.UrgentItem;
import model.exceptions.TooManyItemsException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.Date;
import java.util.GregorianCalendar;

import static org.junit.jupiter.api.Assertions.fail;

public class TestGroceryList {
    GroceryList list;

    @BeforeEach
    public void newList() {

        list = new GroceryList();
    }

    @Test
    public void testAddItem() throws TooManyItemsException {

        Assertions.assertEquals(0, list.getToBuyCount());
        list.addItem(makeUrgentItem(0, "thing", 2000, 1, 1));
        Assertions.assertEquals(1, list.getToBuyCount());
    }

    @Test
    public void testItemsToBuyString() throws TooManyItemsException {

        list.addItem(makeUrgentItem(1, "thing", 2000, 1, 1));
        Assertions.assertEquals("To Buy List:\n1: thing,1,2/1/2000\n", list.itemsToBuyString());

    }

    @Test
    public void testCrossOffAt() throws TooManyItemsException {

        list.addItem(makeUrgentItem(1, "thing", 2000, 1, 1));
        Assertions.assertEquals(1, list.getToBuyCount());
        Assertions.assertEquals(0, list.getAlreadyBoughtCount());

        list.crossOffAt(1);
        Assertions.assertEquals(0, list.getToBuyCount());
        Assertions.assertEquals(1, list.getAlreadyBoughtCount());
    }

    @Test
    public void testItemsBoughtString() throws TooManyItemsException {

        list.addItem(makeUrgentItem(1, "thing", 2000, 1, 1));
        list.crossOffAt(1);
        Assertions.assertEquals("Already Bought List:\n1: thing,1,2/1/2000\n", list.itemsBoughtString());

    }

    @Test
    public void testLoad() throws IOException, TooManyItemsException {
        list.load("testLoad.txt");
        Assertions.assertEquals("To Buy List:\n"+
                "1: egg,1,1/1/2001\n" +
                "2: milk,2,2/2/2002\n" +
                "3: cheese,1\n",list.itemsToBuyString());
    }

    @Test
    public void testSave() throws IOException, TooManyItemsException {
        list.addItem(makeUrgentItem(1, "thing1", 2000, 1, 1));
        list.addItem(makeUrgentItem(1, "thing2", 2000, 1, 2));
        list.addItem(makeNormalItem(5, "thing3"));
        list.save("testSave.txt");

        Assertions.assertEquals(
                "*thing1,1,2/1/2000\n" +
                "*thing2,1,2/2/2000\n" +
                "-thing3,5\n",readFile("testSave.txt"));

    }

    @Test
    public void testParseException() {
        try{
            list.addItemFromString("*food,1,2000", null); // only for urgent item bc ParseException is for date
            fail("");
        } catch (ParseException e) {

        } catch (TooManyItemsException e) {
            fail("");
        }
    }

    @Test
    public void testTooManyItems() {
        try {
            list.addItem(makeUrgentItem(1, "thing1", 2000, 1, 1));
            list.addItem(makeUrgentItem(1, "thing2", 2000, 1, 2));
            list.addItem(makeNormalItem(5, "thing3"));
            list.addItem(makeUrgentItem(1, "thing1", 2000, 1, 1));
            list.addItem(makeUrgentItem(1, "thing1", 2000, 1, 1));
        } catch (TooManyItemsException e) {
            fail("");
        }

        try {
            list.addItem((makeNormalItem(5, "thing3")));
            fail("");
        } catch (TooManyItemsException e) {

        }
    }

    static Item makeUrgentItem(int quantity, String name, int year, int month, int day) {
        Date needBy = new GregorianCalendar(year, month, day).getTime();
        return new UrgentItem(name, quantity, needBy);
    }

    static Item makeNormalItem(int quantity, String name) {
        return new NormalItem(name, quantity);
    }

    static String readFile(String path) throws IOException {
        return new String(Files.readAllBytes(Paths.get(path)), "UTF-8");
    }
}
