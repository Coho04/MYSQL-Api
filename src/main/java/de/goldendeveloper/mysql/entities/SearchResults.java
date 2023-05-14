package de.goldendeveloper.mysql.entities;

import java.util.ArrayList;
import java.util.List;

public class SearchResults {

    private final List<SearchResult> item;

    public SearchResults(List<SearchResult> item) {
        this.item = item;
    }

    @SuppressWarnings("unused")
    public List<String> getAsString() {
        List<String> list = new ArrayList<>();
        item.forEach(st -> list.add(st.getAsString()));
        return list;
    }

    @SuppressWarnings("unused")
    public List<Boolean> getAsBoolean() {
        List<Boolean> list = new ArrayList<>();
        item.forEach(st -> list.add(st.getAsBoolean()));
        return list;
    }

    @SuppressWarnings("unused")
    public List<Integer> getAsInt() {
        List<Integer> list = new ArrayList<>();
        item.forEach(st -> list.add(st.getAsInt()));
        return list;
    }

    @SuppressWarnings("unused")
    public List<Long> getAsLong() {
        List<Long> list = new ArrayList<>();
        item.forEach(st -> list.add(st.getAsLong()));
        return list;
    }

    @SuppressWarnings("unused")
    public List<Double> getAsDouble() {
        List<Double> list = new ArrayList<>();
        item.forEach(st -> list.add(st.getAsDouble()));
        return list;
    }

    @SuppressWarnings("unused")
    public List<Short> getAsShort() {
        List<Short> list = new ArrayList<>();
        item.forEach(st -> list.add(st.getAsShort()));
        return list;
    }

    @SuppressWarnings("unused")
    public List<Float> getAsFloat() {
        List<Float> list = new ArrayList<>();
        item.forEach(st -> list.add(st.getAsFloat()));
        return list;
    }

    @SuppressWarnings("unused")
    public List<Object> getAsObject() {
        List<Object> list = new ArrayList<>();
        item.forEach(st -> list.add(st.getAsObject()));
        return list;
    }

    @SuppressWarnings("unused")
    public List<Byte> getAsByte() {
        List<Byte> list = new ArrayList<>();
        item.forEach(st -> list.add(st.getAsByte()));
        return list;
    }

    @SuppressWarnings("unused")
    public List<SearchResult> getAsSearchResults() {
        return this.item;
    }
}
