# ubs-homework

## Requirements:

Design and implement supermarket checkout component with readable API that 
calculates the total price of a number of items.
Checkout mechanism can scan items and return actual price (is stateful)
Our goods are priced individually. In addition, some items are multi-priced: 
"buy n of them, and they’ll cost you y cents"
 

>  Item   Unit      Special
>
>              Price     Price
>
>  --------------------------
>
>    A        40       3 for 70
>
>    B        10       2 for 15
>
>    C        30
>
>    D        25

## Assumptions

Each price (total price included) can be represented as simple int.

"x for y" is not proportional. For example, 3 items B will cost 25 (2 for 15 + 
1 for 10), and not 22.5 (3/2 * 15).
