const fs = require("fs");

const input = fs.readFileSync("input.txt", "utf-8");
const lines = input.split(/\r?\n/);

let sumPriorities = 0;
for (let line of lines) {
    let p2 = line.length / 2;
    let wrongItem = '0';
    for (let i = 0; i < p2; i++) {
        for (let j = p2; j < line.length; j++) {
            if (line[i] === line[j]) {
                wrongItem = line[i];
            }
        }
    }

    let charCode = wrongItem.charCodeAt(0) - 64;
    let priority = charCode % 32 + (1 - Math.floor(charCode / 31)) * 26;
    sumPriorities += priority;
}

console.log("Sum of priorities: " + sumPriorities)