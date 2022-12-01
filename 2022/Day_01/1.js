const fs = require("fs");

const input = fs.readFileSync("input.txt", "utf-8");
const lines = input.split(/\r?\n/);

let calories = 0;
let max_calories = 0;

for (let line of lines) {
    if (line.length === 0) {
        if (calories > max_calories) max_calories = calories;
        calories = 0
        continue
    }

    let c = parseInt(line)
    calories += c
}

console.log(max_calories)