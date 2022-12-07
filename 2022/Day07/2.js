const fs = require("fs");

const input = fs.readFileSync("input.txt", "utf-8");
const lines = input.split(/\r?\n/);

const LIMIT = 70000000;
const UPDATE_SIZE = 30000000;

class Dir {
    constructor(name, parent) {
        this.name = name;
        this.parent = parent;
        this.childDirs = new Set();
        this.content = new Map();
    }

    setSize(size) {
        this.size = size;
    }
}

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

function addContent(line) {
    line = lines[++i];
    while (i < lines.length > 0 && line[0] !== "$") {
        if (line[0] === "d") {
            // new directory
            let name = line.substring(4, line.length);
            let newDir = new Dir(name, currentDir);
            currentDir.childDirs.add(newDir);
        } else {
            // new file
            let file = line.split(" ");
            currentDir.content.set(file[1], parseInt(file[0]));
        }
        line = lines[++i];
    }
}

function getSize(dir) {
    let sizeOfDir = 0;
    for (let fileSize of dir.content.values()) {
        sizeOfDir += fileSize;
    }

    let childDirSize = 0;
    for (let childDir of dir.childDirs) {
        childDirSize += getSize(childDir);
    }
    sizeOfDir += childDirSize;

    dir.setSize(sizeOfDir);

    return sizeOfDir;
}

let root = new Dir("/", null);
let currentDir = root;
let i = 0;
while (i < lines.length) {
    let line = lines[i];
    if (line[0] === "$") {
        switch(line[2]) {
            case "c":
                cd(line);
                i++;
                break;
            case "l":
                addContent(line);
                break;
        }
    }
}

console.log(root);

getSize(root);
console.log("Root size: " + root.size);
console.log("Has free: " + (LIMIT - root.size));
console.log("Needs to free up: " + (UPDATE_SIZE - (LIMIT - root.size)));

function findLargerThan(dir, threshold) {
    if (dir.size > threshold) larger.push(dir.size);
    for (let childDir of dir.childDirs) {
        findLargerThan(childDir, threshold);
    }
}

let larger = [];
findLargerThan(root, UPDATE_SIZE - (LIMIT - root.size));
console.log(Math.min(...larger))
