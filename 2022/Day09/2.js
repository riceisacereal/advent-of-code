const fs = require("fs");
const { getSpans, getCleanGraph, updateTail } = require("./shared");

const input = fs.readFileSync("input.txt", "utf-8");
const lines = input.split(/\r?\n/);

function updateTails() {
    for (let i = 0; i < 9; i++) {
        updateTail(knots[i], knots[i + 1]);
    }

    let cX = knots[9][0] + Math.abs(spanX[0]);
    let cY = spanY[1] - knots[9][1];
    if (visited[cY][cX] === ".") {
        visited[cY][cX] = "#";
        count++;
    }
}

// Loop through all movement to see span
let spanX, spanY;
[spanX, spanY] = getSpans(lines);

// Get empty graph
let visited = getCleanGraph(spanX, spanY);

let knots = [];
for (let i = 0; i < 10; i++) {
    knots.push([0, 0])
}
let count = 0;

for (let line of lines) {
    let steps = parseInt(line.substring(2, line.length));
    switch (line[0]) {
        case "U":
            for (let i = 0; i < steps; i++) {
                knots[0][1]++;
                updateTails();
            }
            break;
        case "D":
            for (let i = 0; i < steps; i++) {
                knots[0][1]--;
                updateTails();
            }
            break;
        case "L":
            for (let i = 0; i < steps; i++) {
                knots[0][0]--;
                updateTails();
            }
            break;
        case "R":
            for (let i = 0; i < steps; i++) {
                knots[0][0]++;
                updateTails();
            }
            break;
    }
}

console.log(count)
// console.log(visited)