const fs = require("fs");

const input = fs.readFileSync("input.txt", "utf-8");
const lines = input.split(/\r?\n/);

/* Rock generation

highest index for new block generation

0123 -> highest index 0/1/2/3

 4
012 -> highest index 4
 3

  4
  3
012 -> highest index 4

3
2
1
0 -> highest index 3

13
02 -> highest index 1/3

*/

class Rock {
    constructor(type, highPoint) {
        this.type = type;
        let minHeight = highPoint + 4;
        switch (type) {
            case 0:
                this.parts = [];
                for (let i = 2; i <= 5; i++) {
                    this.parts.push([i, minHeight]);
                }
                break;
            case 1:
                this.parts = [];
                for (let i = 2; i <= 4; i++) {
                    this.parts.push([i, minHeight + 1]);
                }
                this.parts.push([3, minHeight]);
                this.parts.push([3, minHeight + 2]);
                break;
            case 2:
                this.parts = [];
                for (let i = 2; i <= 4; i++) {
                    this.parts.push([i, minHeight]);
                }
                this.parts.push([4, minHeight + 1]);
                this.parts.push([4, minHeight + 2]);
                break;
            case 3:
                this.parts = [];
                for (let i = minHeight; i <= minHeight + 3; i++) {
                    this.parts.push([2, i]);
                }
                break;
            case 4:
                this.parts = [];
                for (let i = 2; i <= 3; i++) {
                    this.parts.push([i, minHeight]);
                    this.parts.push([i, minHeight + 1]);
                }
                break;
        }
    }

    getHighPoint() {
        switch (this.type) {
            case 0:
                return this.parts[0][1];
            case 1:
                return this.parts[4][1];
            case 2:
                return this.parts[4][1];
            case 3:
                return this.parts[3][1];
            case 4:
                return this.parts[3][1];
        }
    }

    checkValidity(newParts, map) {
        for (let p of newParts) {
            if (p[1] < 0) return false;
            if (map[p[1]][p[0]] !== ".") return false;
        }
        this.parts = newParts;
        return true;
    }

    jetPush(direction, map) {
        let movement = direction === "<" ? -1 : 1;
        let newParts = [];
        for (let p of this.parts) {
            newParts.push([p[0] + movement, p[1]]);
        }
        this.checkValidity(newParts, map);
    }

    fall(map) {
        let newParts = [];
        for (let p of this.parts) {
            newParts.push([p[0], p[1] - 1]);
        }
        return !this.checkValidity(newParts, map);
    }
}

function getEmptyRow() {
    let r = [];
    for (let col = 0; col < 7; col++) {
        r.push(".");
    }
    return r;
}

function getEmptyMap() {
    let map = [];
    for (let row = 0; row < 4; row++) {
        map.push(getEmptyRow());
    }
    return map;
}

let rockHeights = [1, 3, 3, 4, 2];
function extendMap(map, highPoint, nextType) {
    for (let height = map.length; height < highPoint + rockHeights[nextType] + 4; height++) {
        map.push(getEmptyRow());
    }
}

let jetStream;
let index = 0;
function setJetStream(lines) {
    jetStream = lines[0];
}

function getJetStream() {
    let next = jetStream[index];
    index++;
    if (index >= jetStream.length) index -= jetStream.length;
    return next;
}

function printMap(map){
    for (let row = map.length - 1; row >= 0; row--) {
        console.log("|" + map[row].join("") + "|");
        // console.log(map[row].join(""));
    }
    console.log("+-------+");
}

function main() {
    let map = getEmptyMap();
    setJetStream(lines);

    // Let 2022 rocks fall
    let highPoint = -1;
    let rockType = 0;
    for (let rocks = 0; rocks < 2022; rocks++) {
        let thisRock = new Rock(rockType, highPoint);
        // Do while can still fall down
        let still = false;
        while (!still) {
            thisRock.jetPush(getJetStream(), map);
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