class Dir {
    constructor(name, parent) {
        this.name = name;
        this.parent = parent;
        this.childDirs = [];
        this.fileSizes = 0;
    }

    setSize(size) {
        this.size = size;
    }
}

function cd(currentDir, line) {
    let dir = line.substring(5, line.length);
    switch (dir) {
        case "..":
            currentDir = currentDir.parent;
            break;
        default:
            for (let childDir of currentDir.childDirs) {
                if (childDir.name === dir) {
                    currentDir = childDir;
                    break;
                }
            }
    }

    return currentDir;
}

function addFiles(currentDir, lines, i) {
    let line = lines[++i];
    while (i < lines.length > 0 && line[0] !== "$") {
        switch (line[0]) {
            case "d": // new directory
                let newDir = new Dir(line.substring(4, line.length), currentDir);
                currentDir.childDirs.push(newDir);
                break;
            default: // new file
                let file = line.split(" ");
                currentDir.fileSizes += parseInt(file[0]);
        }
        line = lines[++i];
    }

    return i;
}

function getDirectories(root, lines) {
    let currentDir = root;
    let i = 1;
    while (i < lines.length) {
        let line = lines[i];
        switch(line[2]) {
            case "c":
                currentDir = cd(currentDir, line);
                i++;
                break;
            case "l":
                i = addFiles(currentDir, lines, i);
                break;
        }
    }
}

module.exports = {
    Dir: Dir,
    getDirectories: getDirectories
}