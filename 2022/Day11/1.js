const fs = require("fs");

const input = fs.readFileSync("input.txt", "utf-8");
const lines = input.split(/\r?\n/);

const ROUNDS = 20;

class Monkey {
    constructor() {
        this.startingItems = [];
        this.inspectCount = 0;
    }

    addStartingItem(worry) {
        this.startingItems.push(worry);
    }

    setPassTo(divisibleBy, trueMonkey, falseMonkey) {
        this.passTo = function (worry) {
            if (worry % divisibleBy === 0) {
                return trueMonkey;
            } else {
                return falseMonkey;
            }
        }
    }

    setOperation(operation) {
        this.operation = function (old) {
            return eval("old " + operation.substring(10, operation.length));
        }
    }
}

// parsing
let monkeys = [];
for (let i = 0; i < lines.length; i++) {
    let monkey = new Monkey();

    let items = lines[++i].split("Starting items: ")[1].split(", ");
    for (let item of items) {
        monkey.addStartingItem(parseInt(item));
    }

    monkey.setOperation(lines[++i].split("Operation: ")[1]);

    let divisibleBy = parseInt(lines[++i].split("Test: divisible by ")[1]);
    let trueMonkey = parseInt(lines[++i].split("If true: throw to monkey ")[1]);
    let falseMonkey = parseInt(lines[++i].split("If false: throw to monkey ")[1]);
    monkey.setPassTo(divisibleBy, trueMonkey, falseMonkey);

    monkeys.push(monkey);
    i++;
}

// console.log(monkeys);

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

    // for (let monkey of monkeys) {
    //     console.log(monkey.startingItems);
    // }
}

let monkeyInspections = monkeys.map(x => x.inspectCount);
monkeyInspections.sort((o1, o2) => o2 - o1);

console.log(monkeyInspections);
console.log(monkeyInspections[0] * monkeyInspections[1]);

// let monkey = new Monkey();
// monkey.setOperation("new = old * 5");
// console.log(monkey.operation(150));