package de.goldendeveloper.mysql.entities;

import java.util.ArrayList;
import java.util.List;

public class SearchResults {

    private final List<SearchResult> item;

    public SearchResults(List<SearchResult> item) {
        this.item = item;
    }

    public List<String> getAsString() {
        List<String> list = new ArrayList<>();
        for (SearchResult st : item) {
            list.add(st.getAsString());
        }
        return list;
    }

    public List<Boolean> getAsBoolean() {
        List<Boolean> list = new ArrayList<>();
        for (SearchResult st : item) {
            list.add(st.getAsBoolean());
        }
        return list;
    }

    public List<Integer> getAsInt() {
        List<Integer> list = new ArrayList<>();
        for (SearchResult st : item) {
            list.add(st.getAsInt());
        }
        return list;
    }

    public List<Long> getAsLong() {
        List<Long> list = new ArrayList<>();
        for (SearchResult st : item) {
            list.add(st.getAsLong());
        }
        return list;
    }

    public List<Double> getAsDouble() {
        List<Double> list = new ArrayList<>();
        for (SearchResult st : item) {
            list.add(st.getAsDouble());
        }
        return list;
    }

    public List<Short> getAsShort() {
        List<Short> list = new ArrayList<>();
        for (SearchResult st : item) {
            list.add(st.getAsShort());
        }
        return list;
    }

    public List<Float> getAsFloat() {
        List<Float> list = new ArrayList<>();
        for (SearchResult st : item) {
            list.add(st.getAsFloat());
        }
        return list;
    }

    public List<Object> getAsObject() {
        List<Object> list = new ArrayList<>();
        for (SearchResult st : item) {
            list.add(st.getAsObject());
        }
        return list;
    }

    public List<Byte> getAsByte() {
        List<Byte> list = new ArrayList<>();
        for (SearchResult st : item) {
            list.add(st.getAsByte());
        }
        return list;
    }

    public List<SearchResult> getAsSearchResults() {
        return this.item;
    }
}
