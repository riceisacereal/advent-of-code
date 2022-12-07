const fs = require("fs");
const { Dir, getDirectories } = require("./shared");

const input = fs.readFileSync("test.txt", "utf-8");
const lines = input.split(/\r?\n/);

const LIMIT = 70000000;
const UPDATE_SIZE = 30000000;

const root = new Dir("/", null);

function calculateSizes(dir) {
    let sizeOfDir = 0;
    // get size of its own files
    sizeOfDir += dir.fileSizes;
    // get size of child directories
    for (let childDir of dir.childDirs) {
        sizeOfDir += calculateSizes(childDir);
    }

    dir.setSize(sizeOfDir);
    return sizeOfDir;
}

function findFree(dir, threshold) {
    if (dir.size > threshold && dir.size < min) min = dir.size;
    for (let childDir of dir.childDirs) {
        findFree(childDir, threshold);
    }
}

getDirectories(root, lines);
calculateSizes(root);

const PIECES = 100;
const USED_SPACE = Math.floor(root.size * PIECES / LIMIT);
const FREE_UP = USED_SPACE - Math.floor(((LIMIT - UPDATE_SIZE)) * PIECES / LIMIT);
console.log("Used space:\n" + "█".repeat(USED_SPACE) + "░".repeat(PIECES - USED_SPACE));
console.log("Needs to free up: \n" + "█".repeat(USED_SPACE - FREE_UP) + "▒".repeat(FREE_UP)
    + "░".repeat(PIECES - USED_SPACE));

let min = LIMIT;
findFree(root, UPDATE_SIZE - (LIMIT - root.size));
console.log("Smallest directory to delete: " + min)
const DELETE = Math.floor(min * PIECES / LIMIT);
console.log("█".repeat(USED_SPACE - DELETE) + "░".repeat(PIECES - USED_SPACE + DELETE));
