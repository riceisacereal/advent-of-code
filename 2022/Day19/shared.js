class Blueprint {
    constructor(line) {
        let numbers = line.split(/costs | ore\.| ore and | clay\.| obsidian\./);
        this.oreDemand = [parseInt(numbers[1]), parseInt(numbers[3]), parseInt(numbers[5]), parseInt(numbers[8])];
        this.maxOreSupplyNeeded = Math.max(...this.oreDemand);
        this.requiredMaterials = [this.oreDemand[0], this.oreDemand[1],
            [this.oreDemand[2], parseInt(numbers[6])], [this.oreDemand[3], parseInt(numbers[9])]];
    }
}

class Backpack {
    constructor() {
        this.time = 0;
        // OreBot, ClayBot, ObsidianBot, GeodeBot
        this.bots = [1, 0, 0, 0];
        // Ore, Clay, Obsidian, Geode
        this.materials = [0, 0, 0, 0];

        // TODO: remove after debug
        this.snapshots = [];
    }

    copyBackpack(backpack) {
        this.time = backpack.time;
        this.bots = [...backpack.bots];
        this.materials = [...backpack.materials];

        // TODO: remove after debug
        for (let snap of backpack.snapshots) {
            this.snapshots.push([[...snap[0]], [...snap[1]], snap[2]]);
        }
    }

    buildBot(blueprint, bot) {
        this.time++;
        switch (bot) {
            case 0:
            case 1:
                this.materials[0] -= blueprint.requiredMaterials[bot];
                break;
            case 2:
            case 3:
                this.materials[0] -= blueprint.requiredMaterials[bot][0];
                this.materials[bot - 1] -= blueprint.requiredMaterials[bot][1];
                break;
        }
        this.collectMaterials();
        this.bots[bot]++;
    }

    timeNeeded(blueprint, bot) {
        switch (bot) {
            case 0:
            case 1:
                if (blueprint.requiredMaterials[bot] <= this.materials[0]) {
                    return 0;
                }
                return Math.ceil((blueprint.requiredMaterials[bot] - this.materials[0]) / this.bots[0]) ;
            case 2:
            case 3:
                if (blueprint.requiredMaterials[bot][0] <= this.materials[0] &&
                    blueprint.requiredMaterials[bot][1] <= this.materials[bot - 1]) {
                    return 0;
                }
                return Math.max(Math.ceil((blueprint.requiredMaterials[bot][0] - this.materials[0]) / this.bots[0]),
                    Math.ceil((blueprint.requiredMaterials[bot][1] - this.materials[bot - 1]) / this.bots[bot - 1]));
        }
    }

    timeSkip(time) {
        this.time += time;
        for (let i = 0; i < 4; i++) {
            this.materials[i] += time * this.bots[i];
        }
    }

    timeSkipToEnd(end) {
        let time = end - this.time;
        return this.materials[3] + time * this.bots[3];
    }

    collectMaterials() {
        for (let i = 0; i < 4; i++) {
            this.materials[i] += this.bots[i];
        }
    }

    enoughSupply(blueprint, bot) {
        switch (bot) {
            case 0:
                return this.bots[bot] >= blueprint.maxOreSupplyNeeded;
            case 1:
            case 2:
                return this.bots[bot] >= blueprint.requiredMaterials[bot + 1][2];
        }
    }
}

class GeodeTracker {
    constructor() {
        this.maxGeodes = 0;
        this.optimalState = undefined;
    }

    compareAndSet(backpack, duration) {
        let n = backpack.timeSkipToEnd(duration);
        if (n > this.maxGeodes) {
            this.maxGeodes = n;
            for (let snap of backpack.snapshots) {
                let geodes = snap[1][3];
                if (geodes === 1) this.optimalState = snap;
            }
        }
    }

    onPaceWithOptimal(backpack) {
        if (this.optimalState === undefined) {
            return true;
        }

        let snap;
        for (let s of backpack.snapshots) {
            let geodes = s[1][3];
            if (geodes === 1) {
                snap = s;
                break;
            }
        }

        let botsOnPace = true;
        // for ()

    }
}

function exploreBuildingSequence(blueprint, backpack, buildBot, geodeCount, duration) {
    let timeNeeded = backpack.timeNeeded(blueprint, buildBot);
    if (backpack.time + timeNeeded < duration) {
        backpack.timeSkip(timeNeeded);
        backpack.buildBot(blueprint, buildBot);
    }

    // TODO: remove after debug
    let snapShot = [[...backpack.bots], [...backpack.materials], backpack.time];
    backpack.snapshots.push(snapShot);

    // Compare to previous best
    // if (backpack.materials[3] === 1) {
    //     if (!geodeCount.onPaceWithOptimal(backpack)) {
    //         return;
    //     }
    // }

    // By the time bot is built can't gain anyway, stop
    if (backpack.time >= duration - 1) {
        let count = backpack.timeSkipToEnd(duration);
        if (count === 9) console.log(backpack.snapshots);
        geodeCount.compareAndSet(backpack, duration);
        return;
    }

    // Bot built, branch into 4 next bots
    for (let bot = 0; bot < 4; bot++) {
        // Does not have required materials to build the bot
        if (bot > 1 && backpack.bots[bot - 1] === 0) continue;

        // Bot has enough supply for demand
        if (backpack.enoughSupply(blueprint, bot)) continue;

        // End of search
        if (backpack.time + backpack.timeNeeded(blueprint, bot) >= duration) {
            let count = backpack.timeSkipToEnd(duration);
            if (count === 9) console.log(backpack.snapshots);
            geodeCount.compareAndSet(backpack, duration);
            continue;
        }

        let newBackPack = new Backpack();
        newBackPack.copyBackpack(backpack);
        exploreBuildingSequence(blueprint, newBackPack, bot, geodeCount, duration);
    }
}

function getMaxGeode(blueprint, geodeCount, duration) {
    for (let bot = 0; bot < 4; bot++) {
        let newBackPack = new Backpack();
        exploreBuildingSequence(blueprint, newBackPack, bot, geodeCount, duration);
    }
}

module.exports = {
    Blueprint: Blueprint,
    GeodeTracker: GeodeTracker,
    getMaxGeode: getMaxGeode,
}