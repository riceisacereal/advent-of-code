const fs = require("fs");
const { parse, merge } = require("./shared");

const input = fs.readFileSync("input.txt", "utf-8");
const lines = input.split(/\r?\n/);

// const LIMIT = 20;
const LIMIT = 4000000;

function cover(coordinates, LINE) {
    let noBeacon = [];
    for (let c of coordinates) {
        let [s, b, md] = c;

        let horizontalReach = md - Math.abs(s[1] - LINE);
        if (horizontalReach >= 0) {
            let [left, right] = [s[0] - horizontalReach, s[0] + horizontalReach];
            merge(noBeacon, [left, right]);
        }
    }

    return noBeacon;
}

function main() {
    let coordinates = parse(lines);

    for (let i = 0; i < LIMIT; i++) {
        let noBeacon = cover(coordinates, i);
        if (noBeacon.length > 1) {
            console.log(4000000 * (noBeacon[0][1] + 1) + i);
        }
    }
}

main();