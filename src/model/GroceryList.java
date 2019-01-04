package model;

import model.exceptions.TooManyItemsException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class GroceryList extends Subject implements Loadable, Saveable{
    private List<Item> itemsToBuy;
    private List<Item> itemsBought;
    private Set<Section> sections;

    public GroceryList() {
        super();
        itemsToBuy = new ArrayList<>();
        itemsBought = new ArrayList<>();
    }

    //REFACTOR!!!!!!
    public void addItem(Item nextItem) throws TooManyItemsException{
//        if (itemsToBuy.size() >= 5){
//            throw new TooManyItemsException();
//        }
        checkTooManyItems(itemsToBuy.size());
        itemsToBuy.add(nextItem);
        if (nextItem.section != null) {
            nextItem.section.addItem(nextItem);
        }
        notifyObserver();
    }

    /* EFFECTS: print itemsToBuy list
     */
    public void printItemsToBuy() {
        System.out.println(this.itemsToBuyString());
    }

    // EFFECTS: turn what would be printed into String so we can test it
    public String itemsToBuyString() {
        String out = "To Buy List:\n";
        int position = 1;
        out = printEachItem(out, position, itemsToBuy);
        return out;
    }

    public String printEachItem(String out, int position, List list) {
        for (Object o : list) {
            out += (position + ": " + o) + "\n";
            ++position;
        }
        return out;
    }


    public void crossOffAt(int position) throws TooManyItemsException {
        checkTooManyItems(itemsBought.size());
        Item cross = itemsToBuy.get(position - 1);
        itemsBought.add(cross);
        itemsToBuy.remove(position-1);
        if (cross.section != null) {
            cross.section.removeItem(cross);
        }
    }

    private void checkTooManyItems(int size) throws TooManyItemsException {
        if (size >= 5) {
            throw new TooManyItemsException();
        }
    }


    public void printItemsBought() {
        System.out.println(this.itemsBoughtString());
    }

    //EFFECTS: turn what would be printed from itemsBought into String so we can test it

    public String itemsBoughtString() {
        String out = "Already Bought List:\n";
        int position = 1;
        out = printEachItem(out, position, itemsBought);
        return out;
    }

    public int getToBuyCount() {
        return this.itemsToBuy.size();
    }

    public int getAlreadyBoughtCount() {
        return this.itemsBought.size();
    }

    public void load(String filename) throws IOException, TooManyItemsException { //already got rid of marker
        List<String> lines = Files.readAllLines(Paths.get(filename));
        for (String line : lines){
            try {
                addItemFromString(line, null);
            } catch (ParseException e) {

            }
        }
    }

    public void save(String fileName) throws FileNotFoundException, UnsupportedEncodingException {
        PrintWriter writer = new PrintWriter(fileName,"UTF-8");
        for (Item i : itemsToBuy){
            writer.println(i.toFileString()); //add - and *
        }
        writer.close();
    }

    public void addItemFromString (String line, Section section) throws ParseException, TooManyItemsException {
        Item nextItem;
        if(line.charAt(0)=='*'){  // it knows which one is urgent/not when reading from txt file
            nextItem = new UrgentItem(section);
        } else {
            nextItem = new NormalItem(section);
        }
        nextItem.parseLine(line.substring(1)); //gets rid of marker (- or *) here
        addItem(nextItem);
    }


}


