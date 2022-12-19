const fs = require("fs");
const { Rock, JetStream, getEmptyMap, extendMap, printMap } = require("./shared");

const input = fs.readFileSync("input.txt", "utf-8");
const lines = input.split(/\r?\n/);

function main() {
    let map = getEmptyMap();
    let jetStream = new JetStream(lines);

    // Let 2022 rocks fall
    let highPoint = -1;
    let rockType = 0;
    for (let rocks = 0; rocks < 2022; rocks++) {
        let thisRock = new Rock(rockType, highPoint);

        let still = false;
        while (!still) {
            // Do while can still fall down
            thisRock.jetPush(jetStream.getNext(), map);
            // Fall
            still = thisRock.fall(map);
        }

        // Set rock on map
        for (let p of thisRock.parts) {
            map[p[1]][p[0]] = "#";
        }
        // printMap(map);

        // Update highpoint
        if (thisRock.getHighPoint() > highPoint) highPoint = thisRock.getHighPoint();

        // Increase rock counter and extend map
        rockType++;
        if (rockType >= 5) rockType -= 5;
        extendMap(map, highPoint, rockType)
    }

    console.log(highPoint + 1);
}

main();