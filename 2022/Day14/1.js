const fs = require("fs");
const { parse, drawRockLines, pourSand, getDimension} = require("./shared");

const input = fs.readFileSync("input.txt", "utf-8");
const lines = input.split(/\r?\n/);

function main() {
    let rocks = parse(lines);

    // Find largest y, if sand passes this rock it will fall forever
    // Find x dimensions to draw 2D array
    let [minX, maxX, maxY] = getDimension(rocks);

    let scan = drawRockLines(minX, maxX, maxY, rocks, false);
    scan[0][500 - minX] = "+";

    console.log(pourSand(scan, maxY, minX));
    for (let rockLine of scan) {
        console.log(rockLine.join(""));
    }
}

main();