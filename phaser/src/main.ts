import { Level1 } from './level1.ts';
import { Level2 } from './level2.ts';
import { Level3 } from './level3.ts';
import { Level4 } from './level4.ts';
import { Inventario } from './inventario.ts';
import { Level5 } from './level5.ts';

//

const config = {
  width: 800,
  height: 600,
  backgroundColor: 0x87CEEB,
  scene: [Level1, Level2, Level3, Level4, Inventario, Level5],
  physics: {
    default: 'arcade',
    arcade: {
      gravity: { x: 0, y: 300 }, // Add 'x' property here
      debug: false
    }
  }
}

const game = new Phaser.Game(config)
