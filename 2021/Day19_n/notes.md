# Alignment
2022-12-03

## Part 1
It took two whole days to brainstorm and write out a solution for this. After that it also took a whole day to debug some edge cases.

### Finding Overlap

My first method was to calculate the distances between a beacon and every other beacon in range of the same scanner, and find the intersection of these distances with another scanner's beacon distances. (Make edges calculate weight.)

So if `Edge 1-12` from `scanner0` had the same weight as `Edge 6-8` from `scannerX`, they would be marked down as the same edge.

Surprisingly, this method just worked. I thought about "oh but what if there is an edge not in the cluster that has the same weight as an edge in the cluster", and I simply checked for the frequencies of the beacons appearing in an equal distance edge and discarded the ones that were < 11. ~~Proof by contradiction. If there was a point *not* in the cluster that had more than 11 identical edges, *you are in the overlapping cluster*.~~

Of course this would not have worked if beacons weren't placed that randomly and had less unique distances between each other.

### Aligning

At the beginning I was trying to make sense of how a coordinate would transform when rotated to the 24 different alignments, so I had written out some transformations (`scrap.txt`) but quickly gave up on trying to find a cool pattern.

I ignored the 24 different orientations for a bit and focused on how I would correspond the same beacons being detected by different scanners with each other.

Assume we have already identified an overlapping cluster between `scanner0` and `scannerX` using the method above.

#### Thought 1

We already know which edge from `scanner0` is the same as another edge in `scannerX` from the distance comparison (ignoring non-unique distance values for now). That means the two beacons that 

#### Thought 2

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