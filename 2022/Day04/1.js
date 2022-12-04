const fs = require("fs");

const input = fs.readFileSync("input.txt", "utf-8");
const lines = input.split(/\r?\n/);

let fullyContain = 0;
for (let line of lines) {
    let elf1 = [0,0];
    let elf2 = [0,0];
    [elf1[0], elf1[1], elf2[0], elf2[1]] = line.split(/[,-]/).map(x => parseInt(x));

    if (elf1[0] <= elf2[0] && elf1[1] >= elf2[1]) {
        // elf1 covers elf2
        fullyContain++;
    } else if (elf1[0] >= elf2[0] && elf1[1] <= elf2[1]) {
        fullyContain++;
    }
}

console.log(fullyContain)