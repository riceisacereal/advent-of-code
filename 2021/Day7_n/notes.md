# Crab
2022-02-21

## Part 1

Made me think of Surface Area Heuristics in Computer Graphics.

Quick-select to find the median. There are an even number of crabs, 
so taking both the (n / 2) - 1 th place or the (n / 2)th place
are the same.
```
(n / 2) - 1 th place:        (n / 2)th place:             Even further:
                   
                Fuel:                       Fuel:                      Fuel:
     +-o          2             +--o          +           +----o         +    
      +o          1              +-o          +            +---o         +    
       +          0               +o          +             +--o         +    
       o+         2                +          -               +o         +    
       o-+        2                o+         -                +         -   
       o--+       3                o-+        -                o+        -   
                    
Total:            10                          10                         11
```
For comparison:
```
Aligning at one crab:

    +              0
    o+             1
    o-+            2
    o--+           3
    o----+         5
    o-----+        6
    o------+       7
    o-------+      8

Total:             32
```
Thought of the solution with the median before going to bed, didn't
fall asleep fast enough and tried to implement it sleep-deprived.\
Had some issues with implementing quick-select, so I made a quick sort
algorithm on the side real quick to see if the sorting was actually
working.

## Part 2
Thought about using the average, it was a decimal number, so
it had to be rounded, but then the rounded average had a
bigger fuel total than average - 1. So I tried inputting the fuel
total calculated with average - 1, and it just worked, huh.

Also had to look up the formula for n(n - 1)/2 because I forgor.

### Footnotes
<details>
  <summary>*My wrong implementation outputs:</summary>

    Part 1:
    355991 - wrong quick select
    355999 - wrong boundary conditions - probably something with equal numbers

    Part 2:
    102245535 - fuel total calculated with average

</details>
