const fs = require("fs");
const { getMonkeys } = require("./shared");

const input = fs.readFileSync("input.txt", "utf-8");
const lines = input.split(/\r?\n/);

const ROUNDS = 20;

monkeys = getMonkeys(lines);

for (let round = 0; round < ROUNDS; round++) {
    for (let monkey of monkeys) {
        while (monkey.startingItems.length > 0) {
            monkey.inspectCount++;
            let item = monkey.operation(monkey.startingItems.shift());
            item = Math.floor(item / 3);

            let receivingMonkey = monkey.passTo(item);
            monkeys[receivingMonkey].addStartingItem(item);
        }
    }
}

let monkeyInspections = monkeys.map(x => x.inspectCount);
monkeyInspections.sort((o1, o2) => o2 - o1);

console.log(monkeyInspections[0] * monkeyInspections[1]);