const fs = require("fs");

const input = fs.readFileSync("test.txt", "utf-8");
const lines = input.split(/\r?\n/);

// const [OREBOT, CLAYBOT, OBSBOT, GEOBOT] = [0, 1, 2, 3];
// const [ORE, CLAY, OBSIDIAN, GEODE] = [0, 1, 2, 3];

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
        // this.snapshots = [];
    }

    copyBackpack(backpack) {
        this.time = backpack.time;
        this.bots = [...backpack.bots];
        this.materials = [...backpack.materials];
        // for (let snap of backpack.snapshots) {
        //     this.snapshots.push([[...snap[0]], [...snap[1]], snap[2]]);
        // }
    }

    buildBot(blueprint, bot) {
        switch (bot) {
            case 0:
            case 1:
                if (this.materials[0] >= blueprint.requiredMaterials[bot]) {
                    this.materials[0] -= blueprint.requiredMaterials[bot];
                    return true;
                }
                return false;
            case 2:
            case 3:
                if (this.materials[0] >= blueprint.requiredMaterials[bot][0] &&
                    this.materials[bot - 1] >= blueprint.requiredMaterials[bot][1]) {
                    this.materials[0] -= blueprint.requiredMaterials[bot][0];
                    this.materials[bot - 1] -= blueprint.requiredMaterials[bot][1];
                    return true;
                }
                return false;
        }
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
        this.timeSkip(time);
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

function exploreBuildingSequence(blueprint, backpack, buildBot) {
    // buildBot = 0-3;
    let timeNeeded = backpack.timeNeeded(blueprint, buildBot);
    // backpack.snapshots.push([[...backpack.bots], [...backpack.materials], backpack.time]);
    if (backpack.time + timeNeeded < 24) {
        backpack.timeSkip(timeNeeded);
        backpack.time++;
        backpack.buildBot(blueprint, buildBot);
        backpack.collectMaterials();
        backpack.bots[buildBot]++;
    }

    // backpack.snapshots.push([[...backpack.bots], [...backpack.materials], backpack.time]);

    if (backpack.time === 24) {
        // Time up, report geode gain
        return backpack.materials[3];
    }

    // TODO: Needed bot built, branch into 4 next bots
    let maxGeodes = 0;
    for (let bot = 0; bot < 4; bot++) {
        // Does not have required materials to build the bot
        if (bot > 1 && backpack.bots[bot - 1] === 0) continue;

        // Has enough supply for this bot
        if (backpack.enoughSupply(blueprint, bot)) {
            continue;
        }

        if (backpack.time + backpack.timeNeeded(blueprint, bot) >= 24) {
            // Can't buy more bots, this is the end
            backpack.timeSkipToEnd(24);
            if (backpack.materials[3] > maxGeodes) maxGeodes = backpack.materials[3];
            continue;
        }

        let newBackPack = new Backpack();
        newBackPack.copyBackpack(backpack);
        let geodes = exploreBuildingSequence(blueprint, newBackPack, bot);

        // console.log(geodes);

        if (geodes > maxGeodes) maxGeodes = geodes;
    }

    return maxGeodes;


    // let moreClay = 0;
    // let moreOre = 0;
    // while (backpack.time < 24 && (oreTime || clayTime)) {
    //     backpack.time++;
    //
    //     let buildGeoBot = backpack.buildGeoBot(blueprint);
    //     let buildObsBot = backpack.buildObsBot(blueprint);
    //     let buildClayBot = backpack.materials[ORE] >= blueprint.clayBot;
    //     let buildOreBot = backpack.materials[ORE] >= blueprint.oreBot;
    //
    //     // Add materials
    //     backpack.collectMaterials();
    //
    //     if (buildGeoBot) backpack.bots[GEOBOT]++;
    //     if (buildObsBot) backpack.bots[OBSBOT]++;
    //     // Go through the time steps until you reach a point where you can either buy an ore or clay bot
    //     if (clayTime || oreTime) {
    //         if (clayTime && buildClayBot) {
    //             // build clay bot
    //             backpack.materials[ORE] -= blueprint.clayBot;
    //             backpack.bots[CLAYBOT]++;
    //             clayTime = false;
    //         } else if (oreTime && buildOreBot) {
    //             // build ore bot
    //             backpack.materials[ORE] -= blueprint.oreBot;
    //             backpack.bots[OREBOT]++;
    //             oreTime = false;
    //         }
    //     }
    //
    //     if (!clayTime && !oreTime) {
    //         // TODO: branch
    //         // TODO: new backpacks
    //         let clayBackPack = new Backpack();
    //         clayBackPack.copyBackpack(backpack);
    //         let oreBackPack = new Backpack();
    //         oreBackPack.copyBackpack(backpack);
    //
    //         moreClay = moreOreOrMoreClay(blueprint, clayBackPack, true, false);
    //         moreOre = moreOreOrMoreClay(blueprint, oreBackPack, false, true);
    //     }
    // }
    //
    // return Math.max(backpack.materials[GEODE], moreClay, moreOre);
}

function getMaxGeode(blueprint) {
    let maxGeodes = 0;
    for (let bot = 0; bot < 4; bot++) {
        let newBackPack = new Backpack();
        let geodes = exploreBuildingSequence(blueprint, newBackPack, bot);
        if (geodes > maxGeodes) maxGeodes = geodes;
    }
    return maxGeodes;
    // let moreClay = moreOreOrMoreClay(blueprint, new Backpack(), true, false);
    // let moreOre = moreOreOrMoreClay(blueprint, new Backpack(), false, true);
    // return Math.max(moreClay, moreOre);
}

function main() {
    for (let line of lines) {
        let blueprint = new Blueprint(line);
        let maximum = getMaxGeode(blueprint);
        console.log(maximum);
    }
}

main();
// Geodes -> obsidian-collecting robots -> ore
// Waterproof obsidian-collecting robot -> needs clay
// clay-collecting robots -> ore
// ore-collecting robot - 1
// 1 resource/minute
// 1 robot/minute