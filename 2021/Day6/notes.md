#Fish
2022-02-20

The idea was pretty easy to understand, I thought of doing it by:
1. Shifting the fish counts through the array on every day
2. Having a pointer that just moves through the array and adds the new fish
3. Using a queue

in that order.

Of course, it was too easy and boring to simply use a queue, so I wanted to
try out method 2 first. (Spoiler alert: I failed 4 times because I'm stupid.*)

My idea was basically having a pointer (`int` that stores the index) to 
the 0 internal timer fish, shift it by one every day, and add the
number of new baby fish using that pointer, or something like that.\
I took a piece of paper and drew out:
```
Simple example of starting fish count: [1,2,3,0,0]
- indexes as internal timers
- index 3 and 4 for baby fish

       ┌──────────┐
      [1,2,3,0,0] │
      ╱ ╱ ╱ ╱ ╱ __│
     ╱ ╱ ╱ ╱ ╱ ╱
    │ [2,3,1,0,1]
    └──────┘+
```
and observed that, if we think of new fish as "old fish circulating back around",
then we don't add the amount of new fish, but rather the old fish returning to
an internal timer of 6.

So I made the pointer point to fish of internal timer 6 (`age6Pointer`), and the
old fish going back to their cycle would be +3 (actually +2) 
cycling back to the beginning relative to that index.

```
     ┌──────────── age6Pointer + 3 (where the old fish are now)
     v           v age6Pointer (where old fish return to after giving birth)
    [0,1,2,3,4,5,6,7,8]
```
So I'd only have to do `fish[age6Pointer] += fish[(age6Pointer + 3) % 8]`, and
then shift my pointer `age6Pointer = (age6Pointer + 1) % 8`.

Ran the code, was not the right answer, fixed the size of the array which
should have been 9 instead of 8, still wasn't right.\
So I went back to my diagram, and observed that it should actually be:
```
     ┌────────────── 1. new age6Pointer + 2 (where the old fish are at the start of the day/
     │                                       where the new fish will be at the end of the day)
     │           ┌── 2. old age6Pointer (fish that are age 6 at the start of the day)
     v           v v 3. new age6Pointer (fish that are age 6 at the end of the day)
    [0,1,2,3,4,5,6,7,8]
```
What I was supposed to do was:
1. Make all fish age (shift pointer by 1) so new fish are automatically being born
2. Add the old fish (1) back to start of the reproduction cycle (3)

What I was doing was:
1. Add (1) to (2)
2. Make all fish age

My 2 mistakes were:
1. I was effectively adding old fish to age 5 by my incorrect pointer
2. Adding before shifting made it so that for the first day:
```
     ┌─────┐+               ┌───┐+
     │   v v       into     │   v v
    [1,2,3,0,0]     ->     [1,2,3,0,0]
    [2,3,1,0,1]            [2,3,1,0,0]
```
I essentially increased all fish ages of the starting data by 1.
As there were none age 0 fishes in the provided input, I effectively 
just missed one day in the loop.

After fixing these mistakes everything worked.
I also just implemented a queue method while waiting for my
answer cooldown on the AoC website.

###Footnotes
<details>
  <summary>*My wrong implementation outputs:</summary>

    2188480 (wrong array size)
    1274590 (forgot to change array size for when shifting pointer)
    653233 (changed both array sizes, pointer still wrong)
    687372 (making it 81 days bcz I skipped a day, pointer still wrong)

</details>

