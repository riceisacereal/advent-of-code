# Lava tubes
2022-03-02

This one was hard to think of a way to lower calculations

## Part 1
My wacky solution basically works line by line.\
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
4\*3\*8 + 3\*(8+8+3+3) + 2\*4 = 170 comparisons, whereas in mine... I don't even
know how to calculate this but worst case scenario it would be 10\*5 + 20\*5 +
10\*5 = 200 for the current line previous line comparisons, the left and right flow
for each row, and the low point flowing further comparisons.\
I am bad at calculating time complexity, I'm just happy my weird method works.
