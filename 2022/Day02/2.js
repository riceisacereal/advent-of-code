const fs = require("fs");

const input = fs.readFileSync("input.txt", "utf-8");
const lines = input.split(/\r?\n/);

let opponent = ["A", "B", "C"];
let outcome = ["X", "Y", "Z"];

let totalScore = 0;
for (let line of lines) {
    if (line.length === 0) continue;

    let [o, out] = line.split(" ");
    let c = outcome.indexOf(out);
    totalScore += c * 3;

    let score;
    if (c === 1) {
        // draw
        score = opponent.indexOf(o);
    } else if (c === 0) {
        // lose
        score = (opponent.indexOf(o) + 2) % 3;
    } else {
        // win
        score = (opponent.indexOf(o) + 1) % 3;
    }

    totalScore += score + 1;

}

console.log("Total score: " + totalScore);