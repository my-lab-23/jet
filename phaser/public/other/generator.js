const fs = require('fs');
const PNG = require('pngjs').PNG;
const yargs = require('yargs/yargs');
const { hideBin } = require('yargs/helpers');
const convert = require('color-convert');

const argv = yargs(hideBin(process.argv)).argv;

let nome = argv.nome;
let colore = argv.colore;
let dimensioni = argv.dimensioni.split('x');

let png = new PNG({
    width: parseInt(dimensioni[0]),
    height: parseInt(dimensioni[1]),
    filterType: -1
});

let rgb = convert.keyword.rgb(colore);

for (let y = 0; y < png.height; y++) {
    for (let x = 0; x < png.width; x++) {
        let idx = (png.width * y + x) << 2;

        png.data[idx] = rgb[0];
        png.data[idx + 1] = rgb[1];
        png.data[idx + 2] = rgb[2];
        png.data[idx + 3] = 255;
    }
}

png.pack().pipe(fs.createWriteStream(nome + '.png'));
