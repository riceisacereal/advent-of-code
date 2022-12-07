# Alignment
2022-12-03

## Part 1
It took two whole days to brainstorm and write out a solution for this. After that
it also took a whole day to debug some edge cases.

### Aligning

At the beginning I was trying to make sense of how a coordinate would transform
when rotated to the 24 different alignments, so I had written out some
transformations (`scrap.txt`) and tried to find a pattern but quickly
gave up because I didn't want to deal with 24 things (and I also 
have a trash working memory).

The different orientations essentially made the axis out of order, and made
coordinates inverted per axis. The thing that doesn't change here is the gaps
in between consecutive points on that axis would be the same.

```
...B.        S.B.
B....        ....
....B   ->   ....
S....        ...B
             .B..

```


Therefore we can sort the points per axis in one scanner, and find the right corresponding axis
in the second scanner. Here we are
assuming that we already have both clusters of overlapping points in both scanners.

`scanner 0` and 
`scanner x` (`scanner 0` referring to any already aligned scanner, and `scanner x` 
one that does not have the same orientation)

### Overlap

I ignored the problem of alignment for a bit and focused on trying to find **overlapping
beacons**. My first method was to calculate the distances between a beacon and every
other beacon in range of the same scanner, and find the intersection of these
distances with another scanner's beacon distances.

Surprisingly, this method just worked. I had "oh but what if the distances are the same 
as something else that is not in the overlapping cluster" in mind, and I simply checked
for the frequencies of the points appearing in an equal distance edge and discarded
the ones that were < 11, but I haven't seen this case actually pop up while debugging.

Of course this would not have worked if everything was placed neatly on a grid, but with
this dataset this method was sufficient.





Problems:
- Finding overlap
- Overcoming alignment issues
  - Use gaps between points
    - Problem: secondary order can differ