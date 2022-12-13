const fs = require("fs");
const { compare } = require("./shared");

const input = fs.readFileSync("input.txt", "utf-8");
const lines = input.split(/\r?\n/);


function main() {
    let sumPairs = 0;

    let i = 0;
    while (i < lines.length) {
        let left = eval(lines[i++]);
        let right = eval(lines[i++]);

        if (compare(left, right) >= 0) {
            let pair = Math.floor(i / 3) + 1;
            sumPairs += pair;
        }
        i++;
    }

    console.log(sumPairs);
}

main();