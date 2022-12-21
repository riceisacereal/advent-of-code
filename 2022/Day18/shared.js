function updateMaximum(coordinate, max) {
    for (let i = 0; i < 3; i++) {
        if (coordinate[i] > max[i]) max[i] = coordinate[i];
    }
}

function getEmptySpace(max) {
    let space = [];
    for (let i = 0; i <= max[0] + 2; i++) {
        let a = [];
        for (let j = 0; j <= max[1] + 2; j++) {
            let b = [];
            for (let k = 0; k <= max[2] + 2; k++) {
                b.push(0);
            }
            a.push(b);
        }
        space.push(a);
    }
    return space;
}

module.exports = {
    updateMaximum: updateMaximum,
    getEmptySpace: getEmptySpace
}