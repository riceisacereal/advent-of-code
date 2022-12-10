const fs = require("fs");

const input = fs.readFileSync("input.txt", "utf-8");
const lines = input.split(/\r?\n/);

let row = 0;
let col = 0;
function drawPixel() {
    if (Math.abs(x - col) <= 1) {
        crt[row][col] = "#";
    }
    col++;
    if (col > 39) {
        col = 0;
        row++;
    }
}

function addCycle(n) {
    for (let i = 0; i < n; i++) {
        drawPixel();
        cycle++;
    }
}

let crt = [];
for (let i = 0; i < 6; i++) {
    let row = [];
    for (let j = 0; j < 40; j++) {
        row.push(".");
    }
    crt.push(row);
}

let x = 1;
let cycle = 1;
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

for (let r of crt) {
    console.log(r.join(""));
}