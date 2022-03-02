# Seven-segment display
2022-02-28

## Part 2

From part 1, we know the numbers that can be determined from display
length alone are (with length in brackets): 1(2), 4(4), 7(3) and 8(7)

The next move would obviously be to try to figure out other
numbers using this information.\
The easiest way would be to check for overlaps/same letters.\
Since the overlaps with number 8 is the same as the length of the
digit I just left that one out and separated the remaining
digits by their lengths:

```
    length 6:                 length 5:
      1 4 7 total:              1 4 7 total:
    0│2 3 3│8                 2│1 2 2│5
    6│1 3 2│6                 3│2 3 3│8
    9│2 4 3│9                 5│1 3 2│6
```
Looking at this for a while it seems like we can just use the sum
of overlaps of 1 and 4 to identify remaining numbers, since they're
surprisingly all unique.\
Therefore:
```
    length 6:               length 5:
    overlaps:   number:     overlaps:   number:        
    2 + 3 = 5   0           1 + 2 = 3   2     
    1 + 3 = 4   6           2 + 3 = 5   3    
    2 + 4 = 6   9           1 + 3 = 4   5    
```
So we just need to find the display patterns of 1 and 4 from the
10 unique signal patterns, and then identify other digits using
their length in the output.

Don't know why the letters in the 7-segment display don't 
match the usual letter layout tho, being:
```
 AAAA
F    B
F    B
 GGGG
E    C
E    C
 DDDD
```
not that it really matters in this puzzle.
