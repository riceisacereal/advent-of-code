# Bingo
2022-03-01

This was a fun one to think of solutions.\
My first solution was more object-oriented, I didn't like that,
especially with Python, so I tried a `different_approach` afterwards.
(Creating 1D+ arrays in Python was also kinda annoying.)

## Part 1
Since the bingo numbers are in the range of 0-99 inclusive, instead of storing the
bingo boards, I thought of making a size 100 array, 
where each index stores an array of arrays that contain the **board**, the **row**, 
and **column** the number appears in:
```
Bingo numbers:
[0, 1, 2, ..., 99]              Where b are arrays of:
 b  b  b       b                [boardNum, rowNum, columnNum]
 b     b                        
 b                          
```
Basically a 3D array.\
This way we wouldn't have to loop through all boards when drawing a
number, only the list at the corresponding index.

Then I have a separate array `boardTrack` recording the marked numbers for each board
for each row and column:
```
Board numbers:
[0, 1, 2, ..., totalBoards]     Where b are arrays of marked number counts for:
 b  b  b       b                [row1, row2, row3, ..., col5]
 b     b                        
 b 
```
So for every number drawn, I loop through the corresponding list of 
`[boardNum, rowNum, columnNum]`s, and increment the marked number count in 
`boardTrack` using these numbers.

For calculating the final result, since I store my data weirdly,
I do have to loop through all the number entries
and check if the board appears in those entries, 
but I can stop looping through the remaining entries of a number
after the next board number is bigger than the winning board, since entries are
added in order of increasing board number (as they were scanned as such).

## Different Approach
I thought my first idea was pretty cool, but I didn't like how I kept track of so
much data. One of my friends also said that doing OOP in Python can be kinda weird,
so I tried to think of something more procedural.

<details>
  <summary>I came across this Quora answer that helped me and I really liked:</summary>

  [Link to original answer by Vipluv Shetty](https://qr.ae/pGdpOG)
  > A procedure-oriented programmer thinks, "What do i have to *do* to solve
  > this problem?"\
  > An object-oriented programmer, on the other hand, thinks, "What am i working
  > *with* in this problem?"
</details>

So instead of thinking "how to simulate a bingo game", I started thinking
"how to find the fastest winning bingo board".\
Therefore: Looping through all the boards and calculating
the smallest number of draws each board needs to win.

I am not sure how Python lists work, but I assume list access is done in O(1) time,
so the first thing I did was map the x-th draw to the number drawn in an array,
so I can access the draw time (turn?) of a number in constant time.\
Then, for each board, find the **MAX** draw time for each row and column,
then find the **MIN** draw time among them, which would be the least draws needed
for a board to win.

For calculating the final result, I kept track of the entries of the winning board,
and the number of draws it took for that board to win, and
cycled through the original bingo draw list and removed all numbers that appeared
on the board up and including the winning draw, and I summed the remaining entries.

### Side notes
I have observed that explaining a procedural idea does not require that much ASCII
art to model what I had in mind, don't know if that would be a plus or minus though.
