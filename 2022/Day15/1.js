const fs = require("fs");
const { parse, merge } = require("./shared");

const input = fs.readFileSync("input.txt", "utf-8");
const lines = input.split(/\r?\n/);

// const LINE = 10;
const LINE = 2000000;

function cover(coordinates) {
    let noBeacon = [];
    for (let c of coordinates) {
        let [s, b, md] = c;

        let horizontalReach = md - Math.abs(s[1] - LINE);
        if (horizontalReach >= 0) {
            let [left, right] = [s[0] - horizontalReach, s[0] + horizontalReach];

            if (b[1] === LINE) {
                // Beacon is on the line
                if (left < b[0]) merge(noBeacon, [left, b[0] - 1]);
                if (b[0] < right) merge(noBeacon, [b[0] + 1, right]);
            } else {
                merge(noBeacon, [left, right]);
            }
        }
    }

    return noBeacon;
}

function main() {
    let coordinates = parse(lines);
    let noBeacon = cover(coordinates);

    let count = 0;
    for (let range of noBeacon) {
        count += range[1] - range[0] + 1;
    }

    console.log(count);
    console.log(noBeacon);
}

main();