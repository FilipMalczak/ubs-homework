package com.github.ubs.fm

import spock.lang.Specification

class CheckoutTest extends Specification {
    Checkout checkout;

    void setup() {
        //todo: instantiate checkout implementation
    }

    void "No items cost 0"(){
        when:
        then:
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
            checkout.onScan("A");
            checkout.onScan("D");
        then:
            checkout.totalPrice == 35
    }

    void "2 B and 2 C cost 85"(){
        when:
            2.times {
                checkout.onScan("B")
            }
            2.times {
                checkout.onScan("D")
            }
        then:
            checkout.totalPrice == 85
    }

    void "order doesnt matter"(){
        when:
            2.times {
                checkout.onScan("B")
                checkout.onScan("D")
            }
        then:
            checkout.totalPrice == 85
    }
}
