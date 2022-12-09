const fs = require("fs");
const { getSpans, getCleanGraph, updateTail, footprint, pullRope } = require("./shared");

const input = fs.readFileSync("input.txt", "utf-8");
const lines = input.split(/\r?\n/);

function updateTails() {
    for (let i = 0; i < 9; i++) {
        updateTail(knots[i], knots[i + 1]);
    }
    count += footprint(visited, spanX, spanY, knots[9]);
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
pullRope(knots[0], updateTails, lines);
console.log(count)