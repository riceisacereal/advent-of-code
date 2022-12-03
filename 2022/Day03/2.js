const fs = require("fs");

const input = fs.readFileSync("input.txt", "utf-8");
const lines = input.split(/\r?\n/);

let sumPriorities = 0;
for (let group = 0; group < lines.length; group += 3) {
    let elf1 = lines[group].split('').sort();
    let elf2 = lines[group + 1].split('').sort();
    let elf3 = lines[group + 2].split('').sort();

    let [i1, i2, i3] = [0, 0, 0];
    let badge = '0';
    while (i1 < elf1.length && i2 < elf2.length && i3 < elf3.length) {
        let [c1, c2, c3] = [elf1[i1], elf2[i2], elf3[i3]]

        // Find equal between 1 & 2
        while (c1 !== c2) {
            (i1 + 1 < elf1.length && c1 < c2) ? c1 = elf1[++i1] : c2 = elf2[++i2];
        }

        if (c1 === c2) {
            // Find equal for 3
            while (i3 + 1 < elf3.length && c3 < c2) c3 = elf3[++i3];

            if (c2 === c3) {
                badge = elf3[i3];
                break;
            } else {
                // Find next unequal
                while (c1 === c2) {
                    (i1 + 1 < elf1.length && c1 === c2) ? c1 = elf1[++i1] : c2 = elf2[++i2];
                }
            }
        }
    }

    let charCode = badge.charCodeAt(0) - 64;
    let priority = charCode % 32 + (1 - Math.floor(charCode / 31)) * 26;
    sumPriorities += priority
}

console.log("Sum of priorities: " + sumPriorities)