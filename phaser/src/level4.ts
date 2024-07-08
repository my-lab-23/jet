import * as Phaser from 'phaser';
import {
    loadSpritesheets, setGuerriero, createAnimations, input,
    collectedCrystals, gameStates
} from './commons.ts';

//

type CollisionElement = Phaser.Tilemaps.Tile | Phaser.Types.Physics.Arcade.GameObjectWithBody;

export class Level4 extends Phaser.Scene {

    cursors: Phaser.Types.Input.Keyboard.CursorKeys | undefined;
    text: Phaser.GameObjects.Text;
    hint: Phaser.GameObjects.Text;

    platforms: Phaser.Physics.Arcade.StaticGroup;
    coins: Phaser.Physics.Arcade.StaticGroup;
    crystal: Phaser.Physics.Arcade.StaticGroup;
    guerriero: Phaser.Physics.Arcade.Sprite;

    points: number = 0;

    //

    constructor() {
        super({ key: 'level4' });
    }

    preload(): void {
        this.load.image('terra1600', 'other/terra1600.png')
        this.load.image('yellowCrystal', 'crystal/Yellow_crystal1.png');
        this.load.image('blueCrystal', 'crystal/Blue_crystal1.png');
        this.load.image('plateau', 'other/plateau.png');
        this.load.image('coin', 'other/coin.png');

        // Commons
        loadSpritesheets(this);
    }

    create(): void {

        this.points = 0;

        this.cursors = this.input.keyboard?.createCursorKeys();

        this.platforms = this.physics.add.staticGroup()
        this.coins = this.physics.add.staticGroup();
        this.crystal = this.physics.add.staticGroup()
        this.guerriero = this.physics.add.sprite(100, 500, "idle");

        this.text = this.add.text(this.guerriero.x, 50, 'Punti: ' + this.points, { font: '32px Arial', color: '#ffffff' });
        this.hint = this.add.text(this.guerriero.x, 85, "Premi K per aprire l'inventario.",
            { font: '16px Arial', color: '#ffffff' });

        //

        this.platforms.create(800, 600, "terra1600")
        this.platforms.create(700, 400, "plateau");

        this.crystal.create(400, 550, "yellowCrystal").setData('label', 'yellowCrystal');
        this.crystal.create(600, 550, "yellowCrystal").setData('label', 'yellowCrystal');
        this.crystal.create(1200, 553, "blueCrystal").setData('label', 'blueCrystal');

        this.coins.create(700, 200, "coin");
        this.coins.create(700, 550, "coin");
        this.coins.create(1400, 550, "coin");

        //

        this.physics.add.collider(this.guerriero, this.platforms);
        this.physics.add.overlap(this.guerriero, this.coins, this.getPoints, undefined, this);

        this.physics.add.overlap(this.guerriero, this.crystal, function (guerriero, specificCrystal) {
            if ('getData' in specificCrystal) {
                collectedCrystals.push(specificCrystal.getData('label')); // Aggiungi l'etichetta del cristallo all'array
                specificCrystal.destroy()
            }
        }, undefined, this);

        // Imposta i limiti del mondo
        this.physics.world.setBounds(0, 0, 1600, 600); // Ad esempio, un mondo largo 1600 pixel

        // Fai in modo che la camera segua il guerriero
        this.cameras.main.startFollow(this.guerriero);
        this.cameras.main.setBounds(0, 0, 1600, 600); // Ad esempio, una camera larga 1600 pixel

        // Commons
        setGuerriero(this.guerriero)
        //createAnimations(this);
    }

    update(): void {
        input(this.cursors, this.guerriero);

        let keyK = this.input.keyboard?.addKey(Phaser.Input.Keyboard.KeyCodes.K);

        if (keyK && Phaser.Input.Keyboard.JustDown(keyK)) {
            this.scene.pause();
            this.scene.launch('inventario');
        }

        this.text.destroy()
        this.text = this.add.text(this.guerriero.x, 50, 'Punti: ' + this.points, { font: '32px Arial', color: '#ffffff' });
        this.hint.destroy()
        this.hint = this.add.text(this.guerriero.x, 85, "Premi K per aprire l'inventario.",
            { font: '16px Arial', color: '#ffffff' });
    }

    getPoints(guerriero: CollisionElement, coin: CollisionElement): void {

        coin.destroy()

        this.points += 100;

        if (this.points >= 300) {
            gameStates.superJump = false;
            this.scene.start('level5');
        }
    }
}
