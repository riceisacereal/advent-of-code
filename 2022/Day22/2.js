const fs = require("fs");

const input = fs.readFileSync("input.txt", "utf-8");
const lines = input.split(/\r?\n/);

class Operation {
    constructor(operation) {
        this.operation = operation.replace(/L/g, ",L,").replace(/R/g, ",R,").split(",");
        this.index = 0;
    }

    getNext() {
        if (this.index >= this.operation.length) return null;
        return this.operation[this.index++];
    }
}

class Dice {
    constructor() {
        this.faces= [];
        for (let i = 0; i < 6; i++) {
            this.faces.push(null);
        }
    }

    setFace(num, obj) {

    }
}

function main() {
    let map = [];
    let maxLength = 0;
    for (let line of lines) {
        if (line.length <= 1) break;
        if (line.length > maxLength) maxLength = line.length;
        map.push(line.split(""));
    }

    for (let line of map) {
        if (line.length < maxLength) {
            let add = maxLength - line.length;
            for (let i = 0; i < add; i++) {
                line.push(" ");
            }
        }
    }

    let operations = new Operation(lines[lines.length - 1]);
    console.log(walkPath(map, operations));
}

main();