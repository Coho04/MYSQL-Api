package io.github.coho04.mysql.entities;

import java.util.List;

/**
 * The SearchResults class represents a collection of SearchResult objects.
 * It provides methods to retrieve different types of data from the SearchResults.
 */
@SuppressWarnings("unused")
public class SearchResults {

    private final List<SearchResult> item;

    /**
     * The SearchResults class represents a collection of SearchResult objects.
     * It provides methods to retrieve different types of data from the SearchResults.
     *
     * @param item The list of SearchResult objects representing the search results.
     */
    public SearchResults(List<SearchResult> item) {
        this.item = item;
    }

    /**
     * Returns a list of strings representing the search results.
     *
     * @return A list of strings representing the search results.
     */
    public List<String> getAsString() {
        return item.stream().map(SearchResult::getAsString).toList();
    }

    /**
     * Returns a list of boolean values representing the search results.
     *
     * @return A list of boolean values representing the search results.
     */
    public List<Boolean> getAsBoolean() {
        return item.stream().map(SearchResult::getAsBoolean).toList();
    }

    /**
     * Retrieves the search results as a list of integers.
     *
     * @return A list of integers representing the search results.
     */
    public List<Integer> getAsInt() {
        return item.stream().map(SearchResult::getAsInt).toList();
    }

    /**
     * Retrieves the search results as a list of long values.
     *
     * @return A list of long values representing the search results.
     */
    public List<Long> getAsLong() {
        return item.stream().map(SearchResult::getAsLong).toList();
    }

    /**
     * Returns a list of double values representing the search results.
     *
     * @return A list of double values representing the search results.
     */
    public List<Double> getAsDouble() {
        return item.stream().map(SearchResult::getAsDouble).toList();
    }

    /**
     * Retrieves the search results as a list of short values.
     *
     * @return A list of short values representing the search results.
     */
    public List<Short> getAsShort() {
        return item.stream().map(SearchResult::getAsShort).toList();
    }

    /**
     * Retrieves the search results as a list of float values.
     *
     * @return A list of float values representing the search results.
     */
    public List<Float> getAsFloat() {
        return item.stream().map(SearchResult::getAsFloat).toList();
    }

    /**
     * Returns a list of objects representing the search results.
     *
     * @return A list of Objects representing the search results.
     */
    public List<Object> getAsObject() {
        return item.stream().map(SearchResult::getAsObject).toList();
    }

    /**
     * Retrieves the search results as a list of byte values.
     *
     * @return A list of byte values representing the search results.
     */
    public List<Byte> getAsByte() {
        return item.stream().map(SearchResult::getAsByte).toList();
    }

    /**
     * Retrieves the search results as a list of SearchResult objects.
     *
     * @return A list of SearchResult objects representing the search results.
     */
    public List<SearchResult> getAsSearchResults() {
        return this.item;
    }
}
