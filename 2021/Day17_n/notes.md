# Acceleration
2022-03-21

## Part 1
When shooting the probe upwards at `n` velocity, the probe falls back to
the starting y level at `2n + 1` steps with `-n` velocity.\
I suck at physics so it took me some time to figure this 
one out (I am really dumb).\
Therefore, at `2n + 2` steps, the probe will be at a y-level of `- (n + 1)`,
therefore we take the lowest y position in range, in my case it was -124,
do a `- (n + 1) = -124` and get `n = 123`, which would be the largest initial
y velocity for the probe to be in range.

Next up I misremembered the formula for `n(n + 1)/2` and thought it was 
`n(n - 1)/2` although I just used it in day 7. And got the wrong answer
message of `Curiously, it's the right answer for someone else;` which was
kinda funny.

## Part 2
I would love to see a mathematician's take on this day's problem. Using
formulas feels a lot more elegant. I am starting to appreciate maths.

My steps were:
1. Check for every initial x, y velocity (~ smaller than half of the
biggest range in that direction) if it would eventually
fall in range
2. Store the steps needed for that
3. Also note down the initial x velocities where the probe stops horizontally
in the range, the probe can fall freely after `n` steps at `n` velocity
4. Do an intersection between each initial velocity of x and y, and initial 
velocities of x where the probe stops in range

Total: 2032\
Free falling probes: 112 (~0.055%)
