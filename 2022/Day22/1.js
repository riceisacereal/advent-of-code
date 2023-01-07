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

function printMap(map, pos) {
    if (pos) map[pos[0]][pos[1]] = "X";
    for (let line of map) {
        console.log(line.join(""));
    }
    console.log("\n");
    if (pos) map[pos[0]][pos[1]] = ".";
}

function wrapAround(map, pos, steps, condition, loop, dyx) {
    for (let i = 0; i < steps; i++) {
        let yx = [...pos];
        yx[dyx[0]] += dyx[1];
        let [y, x] = yx;

        let stop = false;
        // Out of bounds wrap around
        if (condition(y, x)) {
            for (let j = loop[0]; j !== loop[1]; j += loop[2]) {
                if (map[j][x] === "#") {
                    stop = true;
                    break;
                } else if (map[j][x] === ".") {
                    y = j;
                    break;
                }
            }
        } else if (map[y][x] === "#") {
            stop = true;
        }

        if (stop) break;
        pos[dyx[0]] = yx[dyx[0]];
    }
}

function walkForward(op, orientation, pos, map) {
    let steps = parseInt(op);
    switch (orientation) {
        case 3:
            // UP
            // wrapAround(map, pos, steps, function(y, x) {
            //     return y < 0 || x >= map[y].length || map[y][x] === " ";
            // }, [map.length - 1, 0, -1], [-1, 0]);
            for (let i = 0; i < steps; i++) {
                let [y, x] = [pos[0] - 1, pos[1]];

                let stop = false;
                // Out of bounds wrap around
                if (y < 0 || map[y][x] === " ") {
                    for (let j = map.length - 1; j >= 0; j--) {
                        if (map[j][x] === "#") {
                            stop = true;
                            break;
                        } else if (map[j][x] === ".") {
                            y = j;
                            break;
                        }
                    }
                } else if (map[y][x] === "#") {
                    stop = true;
                }

                if (stop) break;
                pos[0] = y;
            }
            break;
        case 0:
            // RIGHT
            // wrapAround(map, pos, steps, function(y, x) {
            //     return x >= map[y].length || map[y][x] === " ";
            // }, [0, map[pos[0]].length, 1], [0, 1]);

            for (let i = 0; i < steps; i++) {
                let [y, x] = [pos[0], pos[1] + 1];

                let stop = false;
                // Out of bounds wrap around
                if (x >= map[y].length || map[y][x] === " ") {
                    for (let j = 0; j < map[y].length; j++) {
                        if (map[y][j] === "#") {
                            stop = true;
                            break;
                        } else if (map[y][j] === ".") {
                            x = j;
                            break;
                        }
                    }
                } else if (map[y][x] === "#") {
                    stop = true;
                }

                if (stop) break;
                pos[1] = x;
            }
            break;
        case 1:
            // DOWN
            for (let i = 0; i < steps; i++) {
                let [y, x] = [pos[0] + 1, pos[1]];

                let stop = false;
                // Out of bounds wrap around
                if (y >= map.length || map[y][x] === " ") {
                    for (let j = 0; j < map.length; j++) {
                        if (map[j][x] === "#") {
                            stop = true;
                            break;
                        } else if (map[j][x] === ".") {
                            y = j;
                            break;
                        }
                    }
                } else if (map[y][x] === "#") {
                    stop = true;
                }

                if (stop) break;
                pos[0] = y;
            }
            break;
        case 2:
            // LEFT
            for (let i = 0; i < steps; i++) {
                let [y, x] = [pos[0], pos[1] - 1];

                let stop = false;
                // Out of bounds wrap around
                if (x < 0 || map[y][x] === " ") {
                    for (let j = map[y].length - 1; j >= 0; j--) {
                        if (map[y][j] === "#") {
                            stop = true;
                            break;
                        } else if (map[y][j] === ".") {
                            x = j;
                            break;
                        }
                    }
                } else if (map[y][x] === "#") {
                    stop = true;
                }

                if (stop) break;
                pos[1] = x;
            }
            break;
    }

    // printMap(map, pos);
}

function calculatePassword(pos, orientation) {
    return 1000 * (pos[0] + 1) + 4 * (pos[1] + 1) + orientation;
}

function walkPath(map, operations) {
    let pos;
    for (let i = 0; i < map[0].length; i++) {
        if (map[0][i] === ".") {
            pos = [0, i];
            break;
        }
    }

    // NESW = 3012
    let orientation = 0;
    let op = operations.getNext();
    while (op !== null) {
        switch (op) {
            case "L":
                orientation = orientation - 1 < 0 ? 3 : orientation - 1;
                break;
            case "R":
                orientation = orientation + 1 > 3 ? 0 : orientation + 1;
                break;
            default:
                walkForward(op, orientation, pos, map);
        }

        op = operations.getNext();
    }

    return calculatePassword(pos, orientation);
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