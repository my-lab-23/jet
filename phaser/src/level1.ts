import * as Phaser from 'phaser';
import { loadSpritesheets, setGuerriero, createAnimations, input } from './commons.ts';

//

type CollisionElement = Phaser.Tilemaps.Tile | Phaser.Types.Physics.Arcade.GameObjectWithBody;

export class Level1 extends Phaser.Scene {

    cursors: Phaser.Types.Input.Keyboard.CursorKeys | undefined;
    text: Phaser.GameObjects.Text;

    platforms: Phaser.Physics.Arcade.StaticGroup;
    ostacoli: Phaser.Physics.Arcade.StaticGroup;
    coins: Phaser.Physics.Arcade.StaticGroup;
    guerriero: Phaser.Physics.Arcade.Sprite;

    points: number = 0;

    //

    constructor() {
        super({ key: 'level1' });
    }

    preload(): void {

        this.load.image('terra', 'other/terra.png');
        this.load.image('ostacolo', 'other/ostacolo.png');
        this.load.image('plateau', 'other/plateau.png');
        this.load.image('coin', 'other/coin.png');

        // Commons
        loadSpritesheets(this);
    }

    create(): void {

        console.log('Versione di Phaser: ' + Phaser.VERSION);

        //

        this.points = 0;

        this.cursors = this.input.keyboard?.createCursorKeys();
        this.setText()

        this.platforms = this.physics.add.staticGroup();
        this.ostacoli = this.physics.add.staticGroup();
        this.coins = this.physics.add.staticGroup();
        this.guerriero = this.physics.add.sprite(100, 0, "idle");

        //

        this.add.text(100, 175, 'Prossimo livello a 300 punti.', { font: '32px Arial', color: '#ffffff' })
            .setDepth(1);

        this.platforms.create(400, 600, "terra");
        this.platforms.create(700, 400, "plateau");

        this.ostacoli.create(400, 525, "ostacolo");

        this.coins.create(700, 200, "coin");
        this.coins.create(700, 550, "coin");
        this.coins.create(400, 450, "coin");

        //

        this.physics.add.collider(this.guerriero, this.platforms);
        this.physics.add.collider(this.ostacoli, this.platforms); // Aggiungi questa linea
        this.physics.add.collider(this.guerriero, this.ostacoli);
        this.physics.add.overlap(this.guerriero, this.coins, this.getPoints, undefined, this);

        // Commons
        setGuerriero(this.guerriero);
        createAnimations(this);
    }

    update(): void {
        // Commons
        input(this.cursors, this.guerriero);
    }

    getPoints(guerriero: CollisionElement, coin: CollisionElement): void {

        coin.destroy();
        this.points += 100;
        this.text?.destroy();
        this.setText()

        if (this.points >= 300) {
            this.scene.start('level2');
        }
    }

    setText() {
        this.text = this.add.text(100, 100, 'Punti: ' + this.points, { font: '64px Arial', color: '#ffffff' })
            .setDepth(1);
    }
}
