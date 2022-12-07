const fs = require("fs");
const { Dir, getDirectories } = require("./shared");

const input = fs.readFileSync("input.txt", "utf-8");
const lines = input.split(/\r?\n/);

const root = new Dir("/", null);

function printDirectories(dir, level, parents, last) {
    // print out self
    let prefix = last > 0 ? "└─ " : "├─ ";
    let line = "";
    for (let i = 0; i < level; i++) {
        line += parents.includes(i) ? "│  " : "   ";
    }
    console.log(line + prefix + dir.name + " (dir)");

    // print out file contents
    level++;
    for (let i = 0; i < dir.files.length; i++) {
        line = "";
        for (let j = 0; j < level; j++) {
            line += parents.includes(j) ? "│  " : "   ";
        }
        prefix = i === dir.files.length - 1 && dir.childDirs.length === 0 ? "└─ " : "├─ ";
        console.log(line + prefix + dir.files[i].join(" "));
    }

    if (dir.childDirs.length === 0) return;

    //print out child directories
    parents.push(level);
    last = false;
    for (let i = 0; i < dir.childDirs.length; i++) {
        if (i === dir.childDirs.length - 1) {
            parents.pop();
            last = true;
        }
        printDirectories(dir.childDirs[i], level, parents, last);
    }
}

getDirectories(root, lines);
printDirectories(root, 0, [], true);