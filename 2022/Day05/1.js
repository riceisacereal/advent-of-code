const fs = require("fs");

const input = fs.readFileSync("input.txt", "utf-8");
const lines = input.split(/\r?\n/);

let rows = []
let lineIndex = 0;
// Read all crate lines
while (lineIndex < lines.length) {
    let row = lines[lineIndex].split(/[\[\]]/).filter(e => e);
    if (row.length === 0) {
        break;
    }
    rows.push(row);
    lineIndex++;
}

// Populate stacks with empty arrays
let stacks = rows.pop()[0].match(/\d/g);
for (let i = 0; i < stacks.length; i++) {
    stacks[i] = [];
}

// Push the crates onto the stacks
while (rows.length > 0) {
    let row = rows.pop();
    let stackIndex = 0;
    for (let e of row) {
        if (e.length === 1 && e.match(/[A-Z]/)) {
            stacks[stackIndex].push(e);
            stackIndex++;
        } else {
            stackIndex += Math.floor(e.length / 4);
        }
    }
}

lineIndex++;
// Move crates
while (lineIndex < lines.length) {
    let operation = lines[lineIndex].match(/\d+/g).map(e => parseInt(e));
    for (let i = 0; i < operation[0]; i++){
        stacks[operation[2] - 1].push(stacks[operation[1] - 1].pop())
    }
    lineIndex++;
}

console.log(stacks)

let tops = []
// Get top crate of every stack
for (let stack of stacks) {
    tops.push(stack.pop())
}

console.log(tops.join(""))