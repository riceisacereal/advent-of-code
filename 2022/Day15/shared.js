function manhattanDistance(coordinate){
    let [s, b] = coordinate;
    return Math.abs(s[0] - b[0]) + Math.abs(s[1] - b[1]);
}

function parse(lines) {
    let coordinates = lines.map(x => x.split(/Sensor at |: closest beacon is at /)).map(x => x.filter(e => e));

    for (let i = 0; i < coordinates.length; i++) {
        let temp = coordinates[i].map(x => x.split(/, |=/));
        coordinates[i] = [[parseInt(temp[0][1]), parseInt(temp[0][3])], [parseInt(temp[1][1]), parseInt(temp[1][3])]];
        coordinates[i].push(manhattanDistance(coordinates[i]));
    }

    return coordinates;
}

function merge(noBeacon, newRange) {
    // Insert new range
    let insert = noBeacon.length;
    for (let i = 0; i < noBeacon.length; i++) {
        // Stop when start of newRange is after start of thisRange
        if (noBeacon[i][0] < newRange[0]) continue;
        insert = i;
        break;
    }
    noBeacon.splice(insert, 0, newRange);

    // Merge range with other ranges
    let merged = false;
    while (!merged) {
        merged = true;
        for (let i = 0; i < noBeacon.length - 1; i++) {
            let left = noBeacon[i];
            let right = noBeacon[i + 1];

            // If left end overlaps with right end
            if (left[1] >= right[0] - 1) {
                let newRange = [left[0], Math.max(left[1], right[1])];
                noBeacon.splice(i, 2, newRange);
                merged = false;
            }
        }
    }
}

module.exports = {
    parse: parse,
    merge: merge
}