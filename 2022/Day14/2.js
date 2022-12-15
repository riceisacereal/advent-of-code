const fs = require("fs");

const input = fs.readFileSync("test.txt", "utf-8");
const lines = input.split(/\r?\n/);


function parse() {
    let rocks = [];
    for (let line of lines) {
        rocks.push(line.split(" -> ").map(x => x.split(",").map(x => parseInt(x))));
    }
    return rocks;
}

function drawRockLines(minX, maxX, maxY, rocks) {
    // Create empty scan
    let scan = [];
    for (let i = 0; i <= maxY; i++) {
        let row = [];
        for (let j = 0; j <= (maxX - minX); j++) {
            row.push(".");
        }
        scan.push(row);
    }

    // Draw rock structures
    for (let rockLine of rocks) {
        for (let i = 0; i < rockLine.length - 1; i++) {
            let s = rockLine[i];
            let e = rockLine[i + 1];


            if (s[0] === e[0]) {
                // Vertical
                let [start, end] = e[1] > s[1] ? [s[1], e[1]] : [e[1], s[1]];
                for (let j = start; j <= end; j++) {
                    scan[j][s[0] - minX] = "#";
                }
            } else {
                // Horizontal
                let [start, end] = e[0] > s[0] ? [s[0], e[0]] : [e[0], s[0]];
                for (let j = start; j <= end; j++) {
                    scan[s[1]][j - minX] = "#";
                }
            }
        }
    }

    // Draw ground
    for (let i = 0; i < scan[0].length; i++) {
        scan[maxY][i] = "#";
    }

    return scan;
}

function pourSand(scan, maxY, minX) {
    let countSand = 0;

    let [y, x] = [0, 500 - minX];
    while (y < maxY && x >= 0 && x < scan[0].length) {
        // Down
        if (scan[y + 1][x] === ".") {
            y++;
            continue;
        }

        // Left down
        if (scan[y + 1][x - 1] === ".") {
            y++;
            x--;
            continue;
        }
        // Right down
        if (scan[y + 1][x + 1] === ".") {
            y++;
            x++;
            continue;
        }

        // Sand can not move in any direction, sand has settled
        scan[y][x] = "o";
        countSand++;
        if (y === 0 && x === 500 - minX) {
            // Sand stuck at spawn
            console.log("Sand stopped");
            break;
        }
        [y, x] = [0, 500 - minX];
    }

    return countSand;
}

function main() {
    let rocks = parse();

    // find largest y, if sand passes this rock it will fall forever
    // find smallest x to draw 2D array
    let minX, maxX, maxY;
    minX = maxX = 500;
    maxY = 0;
    for (let rockLine of rocks) {
        for (let node of rockLine) {
            let [x, y] = node;
            if (x < minX) minX = x;
            if (x > maxX) maxX = x;
            if (y > maxY) maxY = y;
        }
    }

    // Ground
    maxY += 2;

    // More room for accumulated sand
    minX -= maxY;
    maxX += maxY;

    let scan = drawRockLines(minX, maxX, maxY, rocks);
    scan[0][500 - minX] = "+";

    console.log(pourSand(scan, maxY, minX));

    for (let rockLine of scan) {
        console.log(rockLine.join(""));
    }
}

main();