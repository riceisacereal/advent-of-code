const fs = require("fs");
const { Blueprint, GeodeTracker, getMaxGeode } = require("./shared");

const input = fs.readFileSync("test.txt", "utf-8");
const lines = input.split(/\r?\n/);

function main() {
    let multiple = 1;
    for (let i = 1; i <= Math.min(3, lines.length); i++) {
        let geodeCount = new GeodeTracker();
        let blueprint = new Blueprint(lines[i - 1]);
        getMaxGeode(blueprint, geodeCount, 32);
        console.log(geodeCount.maxGeodes);
        multiple *= geodeCount.maxGeodes;
    }
    console.log(multiple);
}

main();