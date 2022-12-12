function getEmptyGraph(lines) {
    let minDistance = [];
    for (let i = 0; i < lines.length; i++) {
        let row = [];
        for (let j = 0; j < lines[0].length; j++) {
            row.push(314159265);
        }
        minDistance.push(row);
    }

    return minDistance
}

function getMinPath(pathHeads, lines, minDistance, end) {
    while (pathHeads.length > 0) {
        let [i, j] = pathHeads.shift();
        let curChar = lines[i][j];
        if (i === end[0] && j === end[1]) {
            return minDistance[i][j];
        }

        let curDistance = minDistance[i][j];
        // up
        if (i > 0 && lines[i - 1][j] <= curChar + 1) {
            if (minDistance[i - 1][j] > curDistance + 1) {
                minDistance[i - 1][j] = curDistance + 1;
                pathHeads.push([i - 1, j]);
            }
        }
        // down
        if (i < lines.length - 1 && lines[i + 1][j] <= curChar + 1) {
            if (minDistance[i + 1][j] > curDistance + 1) {
                minDistance[i + 1][j] = curDistance + 1;
                pathHeads.push([i + 1, j]);
            }
        }
        // left
        if (j > 0 && lines[i][j - 1] <= curChar + 1) {
            if (minDistance[i][j - 1] > curDistance + 1) {
                minDistance[i][j - 1] = curDistance + 1;
                pathHeads.push([i, j - 1]);
            }
        }

        // right
        if (j < lines[0].length - 1 && lines[i][j + 1] <= curChar + 1) {
            if (minDistance[i][j + 1] > curDistance + 1) {
                minDistance[i][j + 1] = curDistance + 1;
                pathHeads.push([i, j + 1]);
            }
        }

    }
}

module.exports = {
    getMinPath: getMinPath,
    getEmptyGraph: getEmptyGraph
}