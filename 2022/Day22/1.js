const fs = require("fs");

const input = fs.readFileSync("test.txt", "utf-8");
const lines = input.split(/\r?\n/);

class Operation {
    constructor(operation) {
        this.operation = operation;
        this.start = 0;
        this.end = 0;
        this.updateEnd(0);
    }

    updateEnd(start) {
        if (start === this.operation.length) {
            this.end = start;
            return;
        }

        switch (this.operation[start]) {
            case "L":
            case "R":
                this.end = start;
                return;
            default:
                start++;
                this.updateEnd(start);
        }
    }

    getNext() {
        if (this.start === this.operation.length) return null;

        if (this.start === this.end) {
            let op = this.operation[this.start++];
            this.updateEnd(this.start);
            return op;
        } else {
            let op = parseInt(this.operation.substring(this.start, this.end));
            this.start = this.end;
            return op;
        }
    }
}

function printMap(map) {
    for (let line of map) {
        console.log(line);
    }
}

function main() {
    let map = [];
    for (let line of lines) {
        if (line.length <= 1) break;
        map.push(line);
    }

    let operation = new Operation(lines[lines.length - 1]);


    printMap(map);
}

main();