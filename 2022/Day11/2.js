const fs = require("fs");
const { getMonkeys } = require("./shared");

const input = fs.readFileSync("input.txt", "utf-8");
const lines = input.split(/\r?\n/);

const ROUNDS = 10000;

monkeys = getMonkeys(lines);

let common_denominator = 1;
for (let monkey of monkeys) {
    common_denominator *= monkey.divisibleBy;
}

for (let round = 0; round < ROUNDS; round++) {
    for (let monkey of monkeys) {
        while (monkey.startingItems.length > 0) {
            monkey.inspectCount++;
            let item = monkey.operation(monkey.startingItems.shift()) % common_denominator;

            let receivingMonkey = monkey.passTo(item);
            monkeys[receivingMonkey].addStartingItem(item);
        }
    }
}

let monkeyInspections = monkeys.map(x => x.inspectCount);
monkeyInspections.sort((o1, o2) => o2 - o1);

console.log(monkeyInspections[0] * monkeyInspections[1]);