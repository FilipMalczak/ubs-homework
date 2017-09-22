package com.github.ubs.fm

import com.github.ubs.fm.dto.ItemDefinition
import com.github.ubs.fm.dto.ItemPricing
import spock.lang.Specification

class CheckoutTest extends Specification {
    Checkout checkout;

    void setup() {
        checkout = new StandardCheckout(
            [
                ItemDefinition.builder().
                    name("A").
                    price(new ItemPricing(1, 40)).
                    price(new ItemPricing(3, 70)).
                    build(),
                ItemDefinition.builder().
                    name("B").
                    price(new ItemPricing(1, 10)).
                    price(new ItemPricing(2, 15)).
                    build(),
                ItemDefinition.builder().
                    name("C").
                    price(new ItemPricing(1, 30)).
                    build(),
                ItemDefinition.builder().
                    name("D").
                    price(new ItemPricing(1, 30)).
                    build()
            ]
        )
    }

    void "No items cost 0"(){
        given:
        expect:
            checkout.totalPrice == 0
    }

    void "Single A item costs 40"(){
        when:
            checkout.onScan("A");
        then:
            checkout.totalPrice == 40
    }

    void "3 A items cost 70"(){
        when:
            3.times {
                checkout.onScan("A");
            }
        then:
            checkout.totalPrice == 70
    }

    void "4 A items cost 110"(){
        when:
            4.times {
                checkout.onScan("A");
            }
        then:
            checkout.totalPrice == 110
    }

    void "B and D cost 35"(){
        when:
            checkout.onScan("B");
            checkout.onScan("D");
        then:
            checkout.totalPrice == 40
    }

    void "2 B and 2 C cost 75"(){
        when:
            2.times {
                checkout.onScan("B")
            }
            2.times {
                checkout.onScan("D")
            }
        then:
            checkout.totalPrice == 75
    }

    void "order doesnt matter"(){
        when:
            2.times {
                checkout.onScan("B")
                checkout.onScan("D")
            }
        then:
            checkout.totalPrice == 75
    }

    //todo: constructor tests
    //todo: unknown items test
}
