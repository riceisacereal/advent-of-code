const fs = require("fs");
const { getSpans, getCleanGraph, updateTail, footprint, pullRope } = require("./shared");

const input = fs.readFileSync("test.txt", "utf-8");
const lines = input.split(/\r?\n/);

function pullTail() {
    updateTail(knots[0], knots[1]);
    count += footprint(visited, spanX, spanY, knots[1]);
}

// Loop through all movement to see span
let spanX, spanY;
[spanX, spanY] = getSpans(lines);
// Get empty graph
let visited = getCleanGraph(spanX, spanY);

let knots = [[0, 0], [0, 0]];
let count = 0;
pullRope(knots[0], pullTail, lines);
console.log(count)

for (let i = 0; i < visited.length; i++) {
    console.log(visited[i].join(""));
}