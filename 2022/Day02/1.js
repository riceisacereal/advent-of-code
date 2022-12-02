const fs = require("fs");

const input = fs.readFileSync("input.txt", "utf-8");
const lines = input.split(/\r?\n/);

let opponent = ["A", "B", "C"];
let me = ["X", "Y", "Z"];
// rock paper scissor
// 0 > 2, 1 > 0, 2 > 1
// 1 > 0 > 2 > 1 > 0
// 0 < 1 < 2 < 0
let totalScore = 0;
for (let line of lines) {
    if (line.length === 0) continue;

    let [o, m] = line.split(" ");
    totalScore += me.indexOf(m) + 1
    if (me.indexOf(m) === (opponent.indexOf(o) + 1) % 3) {
        // I win
        totalScore += 6
    } else if (me.indexOf(m) === opponent.indexOf(o)) {
        // draw
        totalScore += 3
    }
}

console.log("Total score: " + totalScore)