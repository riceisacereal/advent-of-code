const fs = require("fs");

const input = fs.readFileSync("input.txt", "utf-8");
const lines = input.split(/\r?\n/);

let calories = 0;
let all_calories = [];

for (let line of lines) {
    if (line.length === 0) {
        all_calories.push(calories);
        calories = 0;
        continue;
    }

    let c = parseInt(line);
    calories += c;
}

all_calories.sort((a, b) => b - a);
console.log(all_calories[0] + all_calories[1] + all_calories[2]);