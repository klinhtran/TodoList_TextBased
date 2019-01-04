package model;

import java.util.*;

public class Section {
    private static Collection<Section> allSections;
    private Set<Item> items;
    private String name;

    public Section(String name){
        this.name = name;
        items = new HashSet<>();
    }

    public static Collection<Section> getAllSections() {
        return allSections;
    }

    public void addItem(Item i){
        if (!items.contains(i)){
            items.add(i);
            i.setSection(this);
        }
    }

    public void removeItem(Item i){
        if (items.contains(i)){
            items.remove(i);

        }

    }

    public static void initSections() {
        allSections = new ArrayList<>();
        allSections.add(new Section("Dairy"));
        allSections.add(new Section("Meat"));
        allSections.add(new Section("Pets"));
        allSections.add(new Section("Cleaning"));
        allSections.add(new Section("Others"));
    }

    public static void printAllSections() {
        for (Section s: allSections) {
            System.out.println(s.name);
        }
    }

    public static Section getSectionForName(String name) {
        for (Section s : allSections) {
            if (s.name.equalsIgnoreCase(name)) {
                return s;
            }
        }
        return null;
    }
}
