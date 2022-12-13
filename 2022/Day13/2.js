const fs = require("fs");
const { compare } = require("./shared");

const input = fs.readFileSync("input.txt", "utf-8");
let lines = input.split(/\r?\n/);

function isDivider(packet) {
    if (packet.length === 1 && packet[0].length === 1) {
        let num = packet[0][0];
        if (num === 2 || num === 6) {
            return true;
        }
    }
    return false;
}

function main() {
    lines = lines.map(x => eval(x)).filter(e => e);
    lines.push([[2]]);
    lines.push([[6]]);
    lines.sort((o1, o2) => compare(o2, o1));

    let decoderKey = 1;
    for (let i = 0; i < lines.length; i++) {
        if (isDivider(lines[i])) {
            decoderKey *= i + 1;
        }
    }

    console.log(decoderKey);
}

main();