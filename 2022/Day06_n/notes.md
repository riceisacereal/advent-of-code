# Window
2022-12-06

I have realized my brain stinks a bit. When it sees a problem that looks simple and straightforward, it ignores quite a few edge cases.

The first idea I had was just going through the line and checking if the next letter is the same as any letter in the previous 3, and if there was a duplicate, I would slide the window until the duplicate was out of the frame. Like so:
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



nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg