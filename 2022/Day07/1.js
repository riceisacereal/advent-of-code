const fs = require("fs");

const input = fs.readFileSync("input.txt", "utf-8");
const lines = input.split(/\r?\n/);

const LIMIT = 100000;

class Dir {
    constructor(name, parent) {
        this.name = name;
        this.parent = parent;
        this.childDirs = new Set();
        this.files = new Map();
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
        if (line[0] === "d") {
            // new directory
            let name = line.substring(4, line.length);
            let newDir = new Dir(name, currentDir);
            currentDir.childDirs.add(newDir);
        } else {
            // new file
            let file = line.split(" ");
            currentDir.files.set(file[1], parseInt(file[0]));
        }
        line = lines[++i];
    }
}

function calculateSizes(dir) {
    if (dir.size !== undefined) return dir.size;

    let sizeOfDir = 0;
    // get size of its own files
    for (let fileSize of dir.files.values()) {
        sizeOfDir += fileSize;
    }
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