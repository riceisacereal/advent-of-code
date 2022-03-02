# Lava tubes
2022-03-02

This one was hard to think of a way to lower calculations needed.\
(I don't think I did a good job with that.)

## Part 1
I compare the current line with the previous line and keep track of points where
the number goes down.

```
        [9 9 9 9 9 9 9 9 9 9]     (line -1: starting line that doesn't exits)
         ↓ ↓       ↓ ↓ ↓ ↓ ↓
        [2 1 9 9 9 4 3 2 1 0]     (current line)
         ↓ ↓       ↓ ↓ ↓ ↓ ↓
        [1 1 0 0 0 1 1 1 1 1]     (indexes where number goes down)
```
Then I do a to-right comparison and a to-left comparison in the current line
and get rid of fake low points that can still flow to the left or right.
```
        [2 1 9 9 9 4 3 2 1 0]     (current line)

        [0→1 0 0 0 0→0→0→0→1]     (flow to the right)
        [0 1 0 0 0 0 0 0 0 1]     (flow to the left, in this case no flow occured)
```
Then I get the next line and check if the current low points can still flow
further down. In this case they can't, so the low points are real.
```
        [2 1 9 9 9 4 3 2 1 0]     (previous line)
        [0 1 0 0 0 0 0 0 0 1]
           X               X
        [3 9 8 7 8 9 4 9 2 1]     (current line)
```
Then the process repeats.

Briefly calculating the comparisons needed, for a normal method where we just
check if there is a smaller number in the surrounding area we would need about
4\*3\*8 + 3\*(8+8+3+3) + 2\*4 = 170 comparisons, whereas in mine... worst case scenario 
it would be 10\*5 + 18\*5 +
5\*5 = 165 for the current line previous line comparisons, the left and right flow
for each row, and the low points flowing further comparisons, not counting the
array access for `if prevLowIndexes[i]`, there really isn't that much difference.

Part 2 was just recursively expanding and setting the visited places to 9, so it 
returns 0. Also used an array-based min heap to keep track of the 3 largest basins.

## Visualization
During trying to figure out a good way to do part 2, I was thinking of visualizing
the terrain using ASCII art to see how it actually looked like. I never tried making
ASCII art, so I searched online for common characters people use to represent different
degrees of density. I found someone suggesting the complicated Chinese character
"龖", and thought, why *don't* I use Chinese characters to make Unicode art?

Using "丶二口曰田" just came natural to me, "画", "魂" and "葡" were
half-random picks because there were way too many fitting choices, so I just 
picked some characters that I recognized and liked.

The end result is in `terrain.txt` and it looks pretty awesome :)
