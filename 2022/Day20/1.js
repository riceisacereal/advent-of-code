const fs = require("fs");

const input = fs.readFileSync("input.txt", "utf-8");
const lines = input.split(/\r?\n/);

class Component {
    constructor(previous, number) {
        this.left = previous;
        this.number = number;
    }

    setLeft(previous) {
        this.left = previous;
    }

    setRight(next) {
        this.right = next;
    }
}

function printList(head, size) {
    let string = "";
    for (let i = 0; i < size; i++) {
        string += head.number + " ";
        head = head.right;
    }
    console.log(string);
}

function decrypt(numberList) {
    for (let component of numberList) {
        let n = component.number;
        if (n === 0) continue;

        component.left.setRight(component.right);
        component.right.setLeft(component.left);

        let newRight, newLeft;
        if (n < 0) {
            newRight = component;
            for (let i = 0; i > n; i--) {
                newRight = newRight.left;
            }
            newLeft = newRight.left;
        } else {
            newLeft = component;
            for (let i = 0; i < n; i++) {
                newLeft = newLeft.right;
            }
            newRight = newLeft.right;
        }

        component.setLeft(newLeft);
        component.setRight(newRight);
        newLeft.setRight(component);
        newRight.setLeft(component);
    }
}

function findCoordinates(numberList) {
    let start;
    for (let n of numberList) {
        if (n.number === 0) {
            start = n;
            break;
        }
    }

    let sum = 0;
    for (let i = 0; i < 3; i++) {
        for (let j = 0; j < 1000; j++) {
            start = start.right;
        }
        sum += start.number;
    }
    return sum;
}

function main() {
    let prev = null;
    let numberList = [];
    for (let line of lines) {
        let component = new Component(prev, parseInt(line));
        numberList.push(component);
        prev = component;
    }
    numberList[0].setLeft(numberList[numberList.length - 1]);

    for (let i = 0; i < numberList.length - 1; i++) {
        let component = numberList[i];
        component.setRight(numberList[i + 1]);
    }
    numberList[numberList.length - 1].setRight(numberList[0]);

    decrypt(numberList);
    console.log(findCoordinates(numberList));
}

main();