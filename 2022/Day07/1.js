const fs = require("fs");

const input = fs.readFileSync("input.txt", "utf-8");
const lines = input.split(/\r?\n/);

const LIMIT = 100000;

class Dir {
    constructor(name, parent) {
        this.name = name;
        this.parent = parent;
        this.childDirs = new Set();
        this.fileSizes = 0;
    }

    setSize(size) {
        this.size = size;
    }
}

const root = new Dir("/", null);

function cd(line) {
    let dir = line.substring(5, line.length);
    switch (dir) {
        case "..":
            currentDir = currentDir.parent;
            break;
        case "/":
            currentDir = root;
            break;
        default:
            for (let childDir of currentDir.childDirs) {
                if (childDir.name === dir) {
                    currentDir = childDir;
                    break;
                }
            }
    }
}

function addFiles(line) {
    while (i < lines.length > 0 && line[0] !== "$") {
        switch (line[0]) {
            case "d": // new directory
                let newDir = new Dir(line.substring(4, line.length), currentDir);
                currentDir.childDirs.add(newDir);
                break;
            default: // new file
                let file = line.split(" ");
                currentDir.fileSizes += parseInt(file[0]);
        }
        line = lines[++i];
    }
}

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

let currentDir;
let i = 0;
while (i < lines.length) {
    let line = lines[i];
    switch(line[2]) {
        case "c":
            cd(line);
            i++;
            break;
        case "l":
            line = lines[++i];
            addFiles(line);
            break;
    }
}

let underLimit = 0;
calculateSizes(root);
console.log("Under limit: " + underLimit);