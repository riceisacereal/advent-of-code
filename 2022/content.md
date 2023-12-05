# Overview
With date finished, and the example inputs
### Day 1: Calorie Counting
2022-12-01\
Elf cookies
```text
1000
2000
3000

4000

5000
6000

7000
8000
9000

10000
```
### Day 2: Rock Paper Scissors
2022-12-02\
Beach tournament
```text
A Y
B X
C Z
```
### Day 3: Rucksack Reorganization
2022-12-03\
Snack groups
```text
vJrwpWtwJgWrhcsFMMfFFhFp
jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL
PmmdzqPrVvPwwTWBwg
wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn
ttgJtRGJQctTZtZT
CrZsJsPPZsGzwwsLwLmpwMDw
```
### Day 4: Camp Cleanup
2022-12-04\
Schedule overlap
```text
2-4,6-8
2-3,4-5
5-7,7-9
2-8,3-7
6-6,4-6
2-6,4-8
```
### Day 5: Supply Stacks
2022-12-05\
CrateMover 9001
```text
    [D]    
[N] [C]    
[Z] [M] [P]
 1   2   3 

move 1 from 2 to 1
move 3 from 1 to 3
move 2 from 2 to 1
move 1 from 1 to 2
```
### Day 6: Tuning Trouble
2022-12-06\
Sliding window
```text
mjqjpqmgbljsphdztnvjfqwrcgsmlb: first marker after character 7
bvwbjplbgvbhsrlpgdmjqwftvncz: first marker after character 5
nppdvjthqldpwncqszvftbrmjlhg: first marker after character 6
nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg: first marker after character 10
zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw: first marker after character 11
```
### Day 7: No Space Left On Device
2022-12-07\
Me when I had an iPhone 4S that only had 5GB of storage
```text
- / (dir)
  - a (dir)
    - e (dir)
      - i (file, size=584)
    - f (file, size=29116)
    - g (file, size=2557)
    - h.lst (file, size=62596)
  - b.txt (file, size=14848514)
  - c.dat (file, size=8504156)
  - d (dir)
    - j (file, size=4060174)
    - d.log (file, size=8033020)
    - d.ext (file, size=5626152)
    - k (file, size=7214296)
```
### Day 8: Treetop Tree House
2022-12-08\
We climb onto each tree and see
```text
30373
25512
65332
33549
35390
```
### Day 9: Rope Bridge
2022-12-09\
Snek
```text
.....    .....    .....
.TH.. -> .T.H. -> ..TH.
.....    .....    .....

...    ...    ...
.T.    .T.    ...
.H. -> ... -> .T.
...    .H.    .H.
...    ...    ...
```
### Day 10: Cathode-Ray Tube
2022-12-10\
[timing](https://youtu.be/sJFnWZH5FXc/)
```text
noop
addx 3
addx -5
```
### Day 11: Monkey in the Middle
2022-12-11\
Anxiety overflow
```text
Monkey 0:
  Starting items: 79, 98
  Operation: new = old * 19
  Test: divisible by 23
    If true: throw to monkey 2
    If false: throw to monkey 3

Monkey 1:
  Starting items: 54, 65, 75, 74
  Operation: new = old + 6
  Test: divisible by 19
    If true: throw to monkey 2
    If false: throw to monkey 0

Monkey 2:
  Starting items: 79, 60, 97
  Operation: new = old * old
  Test: divisible by 13
    If true: throw to monkey 1
    If false: throw to monkey 3

Monkey 3:
  Starting items: 74
  Operation: new = old + 3
  Test: divisible by 17
    If true: throw to monkey 0
    If false: throw to monkey 1
```
### Day 12: Hill Climbing Algorithm
2022-12-12\
Not quite Dijkstra
```text
Sabqponm        v..v<<<<
abcryxxl        >v.vv<<^
accszExk        .>vv>E^^
acctuvwj        ..v>>>^^
abdefghi        ..>>>>>^
```
### Day 13: Distress Signal
2022-12-13\
Why do manual parsing when you can do `eval()`?
```text
[1,1,3,1,1]
[1,1,5,1,1]

[[1],[2,3,4]]
[[1],4]

[9]
[[8,7,6]]

[[4,4],4,4]
[[4,4],4,4,4]

[7,7,7,7]
[7,7,7]

[]
[3]

[[[]]]
[[]]

[1,[2,[3,[4,[5,6,7]]]],8,9]
[1,[2,[3,[4,[5,6,0]]]],8,9]
```
### Day 14: Regolith Reservoir
2022-12-15\
[sugar, sugar](https://www.bontegames.com/2021/02/sugar-sugar-2021-browser.html)
```text
.......+...
.......~...
......~o...
.....~ooo..
....~#ooo##
...~o#ooo#.
..~###ooo#.
..~..oooo#.
.~o.ooooo#.
~#########.
~..........
~..........
~..........
```
### Day 15: Beacon Exclusion Zone
2022-12-15\
[Beacons and scanners PTSD](https://adventofcode.com/2021/day/19)
```text
               1    1    2    2
     0    5    0    5    0    5
-2 ..........#.................
-1 .........###................
 0 ....S...#####...............
 1 .......#######........S.....
 2 ......#########S............
 3 .....###########SB..........
 4 ....#############...........
 5 ...###############..........
 6 ..#################.........
 7 .#########S#######S#........
 8 ..#################.........
 9 ...###############..........
10 ....B############...........
11 ..S..###########............
12 ......#########.............
13 .......#######..............
14 ........#####.S.......S.....
15 B........###................
16 ..........#SB...............
17 ................S..........B
18 ....S.......................
19 ............................
20 ............S......S........
21 ............................
22 .......................B....
```
### Day 16: Proboscidea Volcanium
2022-12-19\
Factorial trial and error
```text
Valve AA has flow rate=0; tunnels lead to valves DD, II, BB
Valve BB has flow rate=13; tunnels lead to valves CC, AA
Valve CC has flow rate=2; tunnels lead to valves DD, BB
Valve DD has flow rate=20; tunnels lead to valves CC, AA, EE
Valve EE has flow rate=3; tunnels lead to valves FF, DD
Valve FF has flow rate=0; tunnels lead to valves EE, GG
Valve GG has flow rate=0; tunnels lead to valves FF, HH
Valve HH has flow rate=22; tunnel leads to valve GG
Valve II has flow rate=0; tunnels lead to valves AA, JJ
Valve JJ has flow rate=21; tunnel leads to valve II
```
### Day 17: Pyroclastic Flow
2022-12-19\
Looping Tetris
```text
>>><<><>><<<>><>>><<<>>><<<><<<>><>><<>>

|...@...|
|..@@@..|
|...@...|
|.......|
|.......|
|.......|
|..####.|
+-------+
```
### Day 18: Boiling Boulders
2022-12-21\
Positive negative search
```text
2,2,2
1,2,2
3,2,2
2,1,2
2,3,2
2,2,1
2,2,3
2,2,4
2,2,6
1,2,5
3,2,5
2,1,5
2,3,5
```
### Day 19: Not Enough Minerals
2022-12-27\
Incremental game nerd
```text
Blueprint 1:
  Each ore robot costs 4 ore.
  Each clay robot costs 2 ore.
  Each obsidian robot costs 3 ore and 14 clay.
  Each geode robot costs 2 ore and 7 obsidian.

Blueprint 2:
  Each ore robot costs 2 ore.
  Each clay robot costs 3 ore.
  Each obsidian robot costs 3 ore and 8 clay.
  Each geode robot costs 3 ore and 12 obsidian.
```
### Day 20: Grove Positioning System
2022-12-30\
1-off
```text
1
2
-3
3
-2
0
4

Initial arrangement:
1, 2, -3, 3, -2, 0, 4

1 moves between 2 and -3:
2, 1, -3, 3, -2, 0, 4

2 moves between -3 and 3:
1, -3, 2, 3, -2, 0, 4

-3 moves between -2 and 0:
1, 2, 3, -2, -3, 0, 4

3 moves between 0 and 4:
1, 2, -2, -3, 0, 3, 4

-2 moves between 4 and 1:
1, 2, -3, 0, 3, 4, -2

0 does not move:
1, 2, -3, 0, 3, 4, -2

4 moves between -3 and 0:
1, 2, -3, 4, 0, 3, -2
```
### Day 21: Monkey Math
2023-01-02\
Gradient descent my beloved
```text
root: pppw + sjmn
dbpl: 5
cczh: sllz + lgvd
zczc: 2
ptdq: humn - dvpt
dvpt: 3
lfqf: 4
humn: 5
ljgn: 2
sjmn: drzm * dbpl
sllz: 4
pppw: cczh / lfqf
lgvd: ljgn * ptdq
drzm: hmdt - zczc
hmdt: 32
```