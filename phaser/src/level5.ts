import * as Phaser from 'phaser';
import { loadSpritesheets, setGuerriero, createAnimations, input } from './commons.ts';

//

type CollisionElement = Phaser.Tilemaps.Tile | Phaser.Types.Physics.Arcade.GameObjectWithBody;

export class Level5 extends Phaser.Scene {

    cursors: Phaser.Types.Input.Keyboard.CursorKeys | undefined;
    text: Phaser.GameObjects.Text;

    platforms: Phaser.Physics.Arcade.StaticGroup;
    coins: Phaser.Physics.Arcade.StaticGroup;
    muri: Phaser.Physics.Arcade.StaticGroup;
    guerriero: Phaser.Physics.Arcade.Sprite;

    points: number = 0;

    //

    constructor() {
        super({ key: 'level5' });
    }

    preload() {
        this.load.image('terra', 'other/terra.png');
        this.load.image('ostacolo', 'other/ostacolo.png');
        this.load.image('coin', 'other/coin.png');

        // Commons
        loadSpritesheets(this);
    }

    create() {

        let muro1 = this.add.rectangle(450, 200, 50, 500, 0x808080);
        let muro2 = this.add.rectangle(600, 325, 50, 500, 0x808080);

        this.points = 0;

        this.cursors = this.input.keyboard?.createCursorKeys();
        this.setText()

        this.platforms = this.physics.add.staticGroup();
        this.coins = this.physics.add.staticGroup();
        this.muri = this.physics.add.staticGroup();

        // 

        this.platforms.create(400, 600, "terra");

        this.coins.create(550, 550, "coin");
        this.coins.create(600, 50, "coin");
        this.coins.create(725, 550, "coin");

        this.muri.add(muro1);
        this.muri.add(muro2);

        this.guerriero = this.physics.add.sprite(100, 500, "idle");

        //

        this.physics.add.collider(this.guerriero, this.platforms);
        this.physics.add.collider(this.muri, this.platforms);
        this.physics.add.collider(this.guerriero, this.muri);
        this.physics.add.overlap(this.guerriero, this.coins, this.getPoints, undefined, this);

        // Commons
        setGuerriero(this.guerriero)
        //createAnimations(this);
    }

    update() {
        // Commons
        input(this.cursors, this.guerriero);
    }

    getPoints(guerriero: CollisionElement, coin: CollisionElement) {

        coin.destroy()

        this.points += 100;
        this.text.destroy();
        this.setText();

        if (this.points >= 300) {
            this.scene.start('level1');
        }
    }

    setText() {
        this.text = this.add.text(100, 100, 'Punti: ' + this.points, { font: '64px Arial', color: '#ffffff' })
            .setDepth(1);
    }
}
