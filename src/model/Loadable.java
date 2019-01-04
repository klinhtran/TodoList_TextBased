package model;

import model.exceptions.TooManyItemsException;

import java.io.IOException;

public interface Loadable {
    void load(String s) throws IOException, TooManyItemsException;
}
