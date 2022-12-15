const fs = require("fs");
const { parse, drawRockLines, pourSand, getDimension } = require("./shared");

const input = fs.readFileSync("input.txt", "utf-8");
const lines = input.split(/\r?\n/);

function main() {
    let rocks = parse(lines);
    let [minX, maxX, maxY] = getDimension(rocks);

    // Ground
    maxY += 2;

    // More room for accumulated sand
    let moreSpace = maxY;
    minX -= moreSpace;
    maxX += moreSpace;

    let scan = drawRockLines(minX, maxX, maxY, rocks, true);
    console.log(pourSand(scan, maxY, minX));
}

main();