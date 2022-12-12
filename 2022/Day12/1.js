const fs = require("fs");
const { getMinPath, getEmptyGraph } = require("./shared");

const input = fs.readFileSync("input.txt", "utf-8");
const lines = input.split(/\r?\n/).map(x => x.split("").map(x => x.charCodeAt(0)));

const START = "S".charCodeAt(0);
const END = "E".charCodeAt(0);

let minDistance = getEmptyGraph(lines);

let start, end;
for (let i = 0; i < lines.length; i++) {
    for (let j = 0; j < lines[0].length; j++) {
        if (lines[i][j] === START) {
            start = [i, j];
            minDistance[i][j] = 0;
            lines[i][j] = "a".charCodeAt(0);
        } else if (lines[i][j] === END) {
            end = [i, j];
            lines[i][j] = "z".charCodeAt(0);
        }
    }
}

let pathHeads = []
pathHeads.push(start);
console.log(getMinPath(pathHeads, lines, minDistance, end));
