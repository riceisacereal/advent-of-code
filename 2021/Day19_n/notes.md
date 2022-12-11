# Alignment
2022-12-03

## Part 1
It took two whole days to brainstorm and write out a solution for this. After that it also took a whole day to debug some edge cases.

### Finding Overlap

My first method was to calculate the distances between a beacon and every other beacon in range of the same scanner, and find the intersection of these distances with another scanner's beacon distances.

So if `Edge 1-12` from `scanner0` had the same distance as `Edge 6-8` from `scannerX`, they would be marked down as the same edge.

Surprisingly, this method just worked. I thought about "oh but what if the distances are the same as something else that is not in the overlapping cluster", and I simply checked for the frequencies of the points appearing in an equal distance edge and discarded the ones that were < 11. And if there was a point not in the cluster that had more than 11 identical edges, *you are in the overlapping cluster*. (Proof by contradiction.)

Of course this would not have worked if everything was placed exactly on a 3D grid on every intersection.

### Aligning

At the beginning I was trying to make sense of how a coordinate would transform when rotated to the 24 different alignments, so I had written out some transformations (`scrap.txt`) and quickly gave up on trying to find a pattern.

I ignored the 24 different orientations for a bit and focused on how I would correspond two exact beacons being detected by different scanners with each other.

Assume we have already identified a viable cluster (having more than 12 units) of beacons that are in range of two different scanners. We have a cluster from `scanner0` with the relative positions of the beacons from `scanner0`, and the same with the other scanner, `scannerX`. We know that every relative point in one beacon corresponds to exactly one other beacon in the other scanner.


The different orientations essentially made the axis out of order, and made coordinates inverted per axis. The thing that doesn't change here is the gaps in between consecutive points on that axis would be the same.

```
...B.        S.B.
B....        ....
....B   ->   ....
S....        ...B
             .B..

```


Therefore we can sort the points per axis in one scanner, and find the right corresponding axis in the second scanner. Here we are assuming that we already have both clusters of overlapping points in both scanners.

`scanner 0` and`scanner x` (`scanner 0` referring to any already aligned scanner, and `scanner x` one that does not have the same orientation)







Problems:
- Finding overlap
- Overcoming alignment issues
  - Use gaps between points
    - Problem: secondary order can differ