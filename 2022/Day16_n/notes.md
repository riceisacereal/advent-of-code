# NP
2022-12-21

This was worse than 2021.19.

My failed attempts:
1. Finding a good cost measure for a Greedy node picking approach - none were optimal
   1. Future released pressure potential
   2. Released pressure per minute / distance away
   3. Released pressure per minute / distance away ^ 2
2. Realizing I need to do O(n!) checks, so generated all permutations and calculated everything for those permutations
3. Realizing that I am doing way too many duplicate calculations so made a queue to keep track of every path head at every minute - ran in a reasonable amount of time for part 1, ran for 4 hours for part 2 and last found maximum was `1732`, killed it. Actual answer was `2052`, so it would've needed to run for another day or so to reach the final answer.

What worked:
1. Depth first search with time skip (ideas stolen from ChatGPT and a good friend)