const fs = require("fs");
const { Rock, JetStream, getEmptyMap, extendMap, printMap } = require("./shared");

console.time("time");

const input = fs.readFileSync("input.txt", "utf-8");
const lines = input.split(/\r?\n/);

class Capture {
    constructor(rockType, highPoint, rocksFallen, map) {
        this.rockType = rockType;
        this.highPoint = highPoint;
        this.rocksFallen = rocksFallen;
        this.topLayer = [...map[highPoint], ...map[highPoint - 1], ...map[highPoint - 2]];
    }

    equals(other) {
        if (this.rockType !== other.rockType) return false;
        for (let i = 0; i < 3; i++) {
            for (let j = 0; j < 7; j++) {
                if (this.topLayer[i][j] !== other.topLayer[i][j]) return false;
            }
        }
        return true;
    }
}

function main() {
    let map = getEmptyMap();
    let jetStream = new JetStream(lines);

    let highPoint = -1;
    let rockType = 0;
    let fallingRocks = 1000000000000;

    let captures = [];
    let cut = false;
    let cutGain = 0;
    for (let rocks = 0; rocks < fallingRocks; rocks++) {
        let thisRock = new Rock(rockType, highPoint);

        let still = false;
        while (!still) {
            // Do while can still fall down
            thisRock.jetPush(jetStream.getNext(), map);
            // Fall
            still = thisRock.fall(map);
            if (jetStream.index === 0 && cut === false) {
                let newCapture = new Capture(rockType, highPoint, rocks, map);
                for (let oldCapture of captures) {
                    if (newCapture.equals(oldCapture)) {
                        let heightGain = newCapture.highPoint - oldCapture.highPoint;
                        let rocksFallen = newCapture.rocksFallen - oldCapture.rocksFallen;

                        let loopAppears = Math.floor((fallingRocks - rocks) / rocksFallen);
                        fallingRocks = rocks + (fallingRocks - rocks) % rocksFallen;
                        cutGain = heightGain * loopAppears;

                        cut = true;
                    }
                }
                captures.push(newCapture);
            }
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

    console.log(cutGain + highPoint + 1);
}

main();
console.timeEnd("time");