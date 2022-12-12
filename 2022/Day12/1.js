const fs = require("fs");

const input = fs.readFileSync("input.txt", "utf-8");
const lines = input.split(/\r?\n/).map(x => x.split("").map(x => x.charCodeAt(0)));

const START = "S".charCodeAt(0);
const END = "E".charCodeAt(0);

let minDistance = [];
for (let i = 0; i < lines.length; i++) {
    let row = [];
    for (let j = 0; j < lines[0].length; j++) {
        row.push(314159265);
    }
    minDistance.push(row);
}

let start, end;
for (let i = 0; i < lines.length; i++) {
    for (let j = 0; j < lines[0].length; j++) {
        if (lines[i][j] === START) {
            start = [i, j];
            minDistance[i][j] = 0;
            lines[i][j] = "a".charCodeAt(0);
        }
        if (lines[i][j] === END) {
            end = [i, j];
            lines[i][j] = "z".charCodeAt(0);
        }
    }
}

function getMinPath(pathHeads) {
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

let pathHeads = []
pathHeads.push(start);
console.log(getMinPath(pathHeads));
