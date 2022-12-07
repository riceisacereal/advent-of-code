const fs = require("fs");
const { Dir, getDirectories } = require("./shared");

const input = fs.readFileSync("input.txt", "utf-8");
const lines = input.split(/\r?\n/);

const LIMIT = 100000;

const root = new Dir("/", null);

function calculateSizes(dir) {
    let sizeOfDir = 0;
    // get size of its own files
    sizeOfDir += dir.fileSizes;
    // get size of child directories
    for (let childDir of dir.childDirs) {
        sizeOfDir += calculateSizes(childDir);
    }

    if (sizeOfDir < LIMIT) underLimit += sizeOfDir;

    return sizeOfDir;
}

getDirectories(root, lines);
let underLimit = 0;
calculateSizes(root);
console.log("Under limit: " + underLimit);