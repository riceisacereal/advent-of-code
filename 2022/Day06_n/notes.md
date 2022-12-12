# Window
2022-12-06

I have realized that I am actually stupid. I see a problem that sounds simple and straightforward, and I think it just works and ignore quite a few edge cases.

My idea was just going through the line and checking if the next letter is the same as any letter in the previous 3, and if there was a duplicate, I would slide the window until the duplicate was out of the frame. Like so:
```
          ↓  ↓ next letter
>   "nzn [rnfr] fntjfmvfwmzdfjlvtqnbhcprsg"
          ↑
        ┌ duplicate at 1st place
        │ move window for 1 step
        ↓ ↓   ↓ next
>   "nznr [nfrf] ntjfmvfwmzdfjlvtqnbhcprsg"
            ↑
          ┌ duplicate at 2nd place
          │ move window for 2 steps
          ↓ ↓   ↓ next
>   "nznrnf [rfnt] jfmvfwmzdfjlvtqnbhcprsg"

...
```
This was a working idea but:
1. Remember to check from back to front to get the latest duplicate
```
       ↓ ↓
>   "[nnfn]"
```
2. If the window is smaller than the checking range remember to move until the frame is large enough
```
       ↓↓
>   "[npp] dvjthq"      move 
    "[nppd] vjthq"      1 (for the frame)
    "n [ppdv] jthq"     1 (index of the duplicate in the frame (j - start))
>   "np [pdvj] thq"     1 (get rid of duplicate)
```
3. Take the bigger movement else you will miss a move
```
          ↓↓
>   "d [zvdd] vfvfj"    move = 3
         ↓  ↓
>   "dz [vddv] fvfj"    move = 2 but changed to 1
>   "dzv [ddvf] vfj"    move == 0 but it still contains duplicate d
```