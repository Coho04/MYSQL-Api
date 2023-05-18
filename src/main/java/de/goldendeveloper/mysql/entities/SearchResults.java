package de.goldendeveloper.mysql.entities;

import java.util.List;

@SuppressWarnings("unused")
public class SearchResults {

    private final List<SearchResult> item;

    public SearchResults(List<SearchResult> item) {
        this.item = item;
    }

    public List<String> getAsString() {
        return item.stream().map(SearchResult::getAsString).toList();
    }

    public List<Boolean> getAsBoolean() {
        return item.stream().map(SearchResult::getAsBoolean).toList();
    }

    public List<Integer> getAsInt() {
        return item.stream().map(SearchResult::getAsInt).toList();
    }

    public List<Long> getAsLong() {
        return item.stream().map(SearchResult::getAsLong).toList();
    }

    public List<Double> getAsDouble() {
        return item.stream().map(SearchResult::getAsDouble).toList();
    }

    public List<Short> getAsShort() {
        return item.stream().map(SearchResult::getAsShort).toList();
    }

    public List<Float> getAsFloat() {
        return item.stream().map(SearchResult::getAsFloat).toList();
    }

    public List<Object> getAsObject() {
        return item.stream().map(SearchResult::getAsObject).toList();
    }

    public List<Byte> getAsByte() {
        return item.stream().map(SearchResult::getAsByte).toList();
    }

    public List<SearchResult> getAsSearchResults() {
        return this.item;
    }
}
