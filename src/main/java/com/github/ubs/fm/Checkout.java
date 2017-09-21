package com.github.ubs.fm;

public interface Checkout {
    /**
     * Called on each item unit that is being checked out.
     * @param itemName Name of currently scanned item.
     */
    void onScan(String itemName);

    /**
     * Calculate total price of already scanned items.
     * @return total price
     */
    int getTotalPrice();
}