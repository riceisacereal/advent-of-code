const fs = require("fs");

const input = fs.readFileSync("test.txt", "utf-8");
const lines = input.split(/\r?\n/);

const [OREBOT, CLAYBOT, OBSBOT, GEOBOT] = [0, 1, 2, 3];
const [ORE, CLAY, OBSIDIAN, GEODE] = [0, 1, 2, 3];

class Blueprint {
    constructor(line) {
        let numbers = line.split(/costs | ore\.| ore and | clay\.| obsidian\./);
        this.requiredMaterials = [parseInt(numbers[1]), parseInt(numbers[3]),
            [parseInt(numbers[5]), parseInt(numbers[6])], [parseInt(numbers[8]), parseInt(numbers[9])]];
    }
}

class Backpack {
    constructor() {
        this.time = 0;
        // OreBot, ClayBot, ObsidianBot, GeodeBot
        this.bots = [1, 0, 0, 0];
        // Ore, Clay, Obsidian, Geode
        this.materials = [0, 0, 0, 0];
    }

    copyBackpack(backpack) {
        this.time = backpack.time;
        this.bots = [...backpack.bots];
        this.materials = [...backpack.materials];
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

    // buildGeoBot(blueprint) {
    //     if (this.materials[ORE] >= blueprint.geoBot[0] && this.materials[OBSIDIAN] >= blueprint.geoBot[1]) {
    //         this.materials[ORE] -= blueprint.geoBot[0];
    //         this.materials[OBSIDIAN] -= blueprint.geoBot[1];
    //         return true;
    //     }
    //     return false;
    // }
    //
    // buildObsBot(blueprint) {
    //     if (this.materials[ORE] >= blueprint.obsBot[0] && this.materials[CLAY] >= blueprint.obsBot[1]) {
    //         this.materials[ORE] -= blueprint.geoBot[0];
    //         this.materials[CLAY] -= blueprint.geoBot[1];
    //         return true;
    //     }
    //     return false;
    // }

    collectMaterials() {
        for (let i = 0; i < 3; i++) {
            this.materials[i] += this.bots[i];
        }
    }
}

function exploreBuildingSequence(blueprint, backpack, buildBot) {
    // buildBot = 0-3;
    // TODO: Always build with max priority: geode -> obsidian -> clay -> ore
    // TODO: If any of obsidian/clay can be built, check with one level higher: obsidian -> geode, clay -> obsidian
    // *TODO: If ore can be built, check with all three up
    // TODO: check if it's more beneficial to build bot or to wait
    // TODO: If for example, can build obsidian, if obsidian 0, build, if obsidian >= 1, calculate geode + obsidian material for:
    // TODO: Building, not building

    // // TODO: Every time step: check to build bots first (geode -> obsidian -> clay & ore), then add material
    // // TODO: While can't build bot, increase time and collect materials
    // while (backpack.time < 24) {
    //     backpack.time++;
    //
    //     let build = backpack.buildBot(blueprint, buildBot);
    //
    //     backpack.collectMaterials();
    //
    //     if (build) {
    //         backpack.bots[buildBot]++;
    //         break;
    //     }
    // }
    //
    // if (backpack.time === 24) {
    //     // Time up, report geode gain
    //     return backpack.materials[3];
    // }
    //
    // // TODO: Needed bot built, branch into 4 next bots
    // let maxGeodes = 0;
    // for (let bot = 0; bot < 4; bot++) {
    //     let newBackPack = new Backpack();
    //     newBackPack.copyBackpack(backpack);
    //     let geodes = exploreBuildingSequence(blueprint, newBackPack, bot);
    //     if (geodes > maxGeodes) maxGeodes = geodes;
    // }
    //
    // return maxGeodes;


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