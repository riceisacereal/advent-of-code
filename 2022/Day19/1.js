const fs = require("fs");
const { Blueprint, GeodeTracker, getMaxGeode } = require("./shared");

const input = fs.readFileSync("input.txt", "utf-8");
const lines = input.split(/\r?\n/);

function main() {
    console.time("Time lapsed: ");

    let sumQuality = 0;
    for (let i = 1; i <= lines.length; i++) {
        let geodeCount = new GeodeTracker();
        let blueprint = new Blueprint(lines[i - 1]);
        getMaxGeode(blueprint, geodeCount, 24);
        console.log(geodeCount.maxGeodes);
        sumQuality += i * geodeCount.maxGeodes;
    }
    console.log(sumQuality);

    console.timeEnd("Time lapsed: ");
}

main();