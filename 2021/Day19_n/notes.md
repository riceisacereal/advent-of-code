# Alignment
2022-12-03

## Part 1
It took two whole days to brainstorm and write out a solution for this. After that it also took a whole day to debug some edge cases.

### Finding Overlap

My first method was to calculate the distances between a beacon and every other beacon in range of the same scanner, and find similarities between these distances with another scanner's beacon distances.

If `Edge 1-12` connecting `Beacon 1` and `Beacon 12` from `scanner0` had the same weight as `Edge 6-8` connecting `Beacon 6` and `Beacon 8` from `scannerX`, they would be considered as the same edge. Then we count how many times each beacon appears in these edges. If they appear at least 11 times, that means they are probably connected to 11 other points that are also detected by the other scanner. Then it is added into the overlapping cluster of beacons.

Surprisingly, this method just worked. The distances were unique enough that it didn't misclassify any beacons as being in the cluster. ~~Proof by contradiction. If there was a point *not* in the cluster that had more than 11 identical edges, *you are in the overlapping cluster*.~~

### Aligning

Figuring out the patterns of transformation for the 24 orientations was a bit too much thinking for my liking, so I came up with 2 other approaches.

Assume we have already identified an overlapping cluster between `scanner0` and `scannerX` using the method above.

#### Thought 1

We already know which edge from `scanner0` is the same as another edge in `scannerX` from the distance comparison (ignoring non-unique distance values for now). Therefore, if `Edge 1-12` from `scanner0` is the same as `Edge 6-8` from `scannerX`, either beacon 1 is also beacon 6 (and 12 = 8), or 1 = 8 (12 = 6).

We can check which beacon corresponds to which by introducing a third edge into the equation. At this point it was becoming too complicated and I went to try and find an alternative way.

#### Thought 2

The different orientations essentially made the axis out of order, and made coordinates inverted per axis. What doesn't change is the gaps between consecutive points for that axis:

```
    ...1.
    2....          2                       3
    ....3          .                       1
    S....          .                       .
      ↓            1                       .
    2..13     →    3    →    31..2    →    2
```

The gap between beacon 2 and 1 is always 3 units, and the gap between beacon 1 and 3 is always 1 unit.

Therefore, we can sort all beacons per axis in ascending order, and find the right corresponding axis by comparing gaps.

Showcase: Let's say the beacons in `scanner0` have coordinates `[x, y, z]`, if you rotate it by the z-axis, they would have coordinates `[-y, x, z]`. Let's say `scannerX` has this orientation, and we want to align it with `scanner0`:
1. We sort both clusters of beacons on the x (first) -axis
2. We take the first axis, which is `x` for `scanner0` and `-y` for `scannerX`, and compare their gaps. We will find that the gaps do not align.
   1. We should also compare gaps with the reverse order of this ascending-ordered cluster, as there is a possibility that we are comparing `x` and `-x`, then the gap order would also be reversed
3. Since they are not the right corresponding axis, we swap axes for the cluster in `scannerX` so we have coordinates `[x, -y, z]`, and sort this cluster on the first axis again
4. If we check the gaps now, they should align perfectly, the first beacon in the `scanner0` cluster is the same beacon as the first in the `scannerX` cluster, and the second one, and so on. (Unless encountering the second case in the problems mentioned below.) We still need to check the sign and alignment of the remaining two axes
5. For the y (second) -axis, we have `y` against `-y`. Comparing gap sizes we know that these are the same axis, so there's no need to swap axes. However, the sign has been inverted, and we can check this by comparing the original (and not absolute) gap sizes. So we invert the sign of this axis, and get coordinates `[x, y, z]`
   1. As said, since rotations just switch around axes and invert their signs, we can take these as separate operations and perform them freely. (I think.)
6. The third axis has the right sign, so we are done with our alignment

Problems I encountered:
1. Needing to swap axes 2 times when switching an axis to check (for the first axis), so it will cycle through all axes: `[x, y, z]` => `[y, z, x]` => `[z, x, y]` => `[x, y, z]`
2. If two beacons have the same position for the first axis, e.g. `[10, 4, 5]` and `[10, 14, 8]`, there is no guarantee on their order when trying to link them with their counterpart in another cluster, since we only sort them on the first axis. If we sort on the second axis secondarily (and maybe third axis tertiarily), we can assure order