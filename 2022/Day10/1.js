const fs = require("fs");

const input = fs.readFileSync("input.txt", "utf-8");
const lines = input.split(/\r?\n/);

function addCycle(n) {
    for (let i = 0; i < n; i++) {
        cycle++;
        if ((cycle - 20) % 40 === 0) {
            console.log(`Cycle: ${cycle} X: ${x}`);
            totalSignalStrength += cycle * x;
        }
    }
}

let x = 1;
let cycle = 0;
let totalSignalStrength = 0;
for (let line of lines) {
    switch (line[0]) {
        case "n":
            addCycle(1);
            break;
        case "a":
            addCycle(2);
            let v = parseInt(line.substring(5, line.length));
            x += v;
            break;
    }
}

console.log(totalSignalStrength)