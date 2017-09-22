package com.github.ubs.fm;

import com.github.ubs.fm.dto.ItemDefinition;
import com.github.ubs.fm.dto.ItemPricing;
import com.google.common.collect.Maps;

import java.util.*;
import java.util.stream.Collectors;

import static com.github.ubs.fm.utils.Contract.require;

public class StandardCheckout implements Checkout {
    //you can assume that pricings are sorted by quantity descending
    private Map<String, List<ItemPricing>> pricingsPerItemName;
    protected Map<String, Integer> scannedItems;

    public StandardCheckout(Collection<ItemDefinition> definitions) {
        validateContracts(definitions);
        this.pricingsPerItemName = Maps.newHashMap();
        definitions.stream().forEach((definition) -> {
                pricingsPerItemName.put(
                    definition.getName(),
                        definition.getPrices().
                            stream().
                            sorted(
                                Comparator.
                                    comparing(ItemPricing::getQuantity).
                                    reversed()
                            ).collect(Collectors.toList())
                );
            }
        );
        scannedItems = Maps.newHashMap();
    }

    private void validateContracts(Collection<ItemDefinition> definitions){
        require(
            "Each item must be defined just once",
            definitions.
                stream().
                map(d -> d.getName()).
                collect(Collectors.toSet()).
                size() == definitions.size()
        );
        definitions.stream().forEach((definition) -> {
                Map<Integer, List<ItemPricing>> pricingByQUantity = definition.getPrices().
                        stream().
                        collect(
                            Collectors.groupingBy(
                                ItemPricing::getQuantity
                            )
                        );
                require(
                    "Each quantity of item "+definition.getName()+" must be given just once",
                    pricingByQUantity.values().stream().allMatch((x) -> x.size() == 1)
                );
                require(
                    "Item "+definition.getName()+" must have single unit price defined",
                        pricingByQUantity.containsKey(1)
                );
            }
        );
    }

    @Override
    public void onScan(String itemName) {
        require(
            "Scanned item must be defined",
            pricingsPerItemName.containsKey(itemName)
        );
        scannedItems.computeIfAbsent(itemName, (name) -> 0);
        scannedItems.compute(itemName, (name, val) -> val+1);
    }

    @Override
    public int getTotalPrice() {
        return scannedItems.
            keySet().
            stream().
            mapToInt(
                this::calculateTotalPriceOfGivenItem
            ).
            sum();
    }

    protected int calculateTotalPriceOfGivenItem(String name){
        int quantityToPrice = scannedItems.get(name);
        int result = 0;
        Iterator<ItemPricing> pricingIterator = pricingsPerItemName.get(name).iterator();
        ItemPricing biggestAvailablePricing = pricingIterator.next();
        while (quantityToPrice > 0) {
            //no check on "hasNext" because we ensured that there will always be an unit pricing
            while (quantityToPrice < biggestAvailablePricing.getQuantity())
                biggestAvailablePricing = pricingIterator.next();
            quantityToPrice -= biggestAvailablePricing.getQuantity();
            result += biggestAvailablePricing.getPrice();
        }
        return result;
    }
}
