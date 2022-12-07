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

console.log("Root size:\n" + root.size);
console.log("Has free: " + (LIMIT - root.size));
console.log("Needs to free up: " + (UPDATE_SIZE - (LIMIT - root.size)));

let min = LIMIT;
findFree(root, UPDATE_SIZE - (LIMIT - root.size));
console.log("Smallest directory over threshold: ", min)
