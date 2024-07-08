import * as Phaser from 'phaser';
import { loadSpritesheets, setGuerriero, createAnimations, input } from './commons.ts';

//

type CollisionElement = Phaser.Tilemaps.Tile | Phaser.Types.Physics.Arcade.GameObjectWithBody;

export class Level2 extends Phaser.Scene {

    cursors: Phaser.Types.Input.Keyboard.CursorKeys | undefined;
    text: Phaser.GameObjects.Text;

    platforms: Phaser.Physics.Arcade.StaticGroup;
    ostacoliMobili: Phaser.Physics.Arcade.Group;
    ostacolo: Phaser.Physics.Arcade.Sprite;
    guerriero: Phaser.Physics.Arcade.Sprite;

    points: number = 0;

    //

    constructor() {
        super({ key: 'level2' });
    }

    preload(): void {
        this.load.image('terra', 'other/terra.png');
        this.load.image('ostacolo', 'other/ostacolo.png');

        // Commons
        loadSpritesheets(this);
    }

    create(): void {

        let n = Math.floor(Math.random() * (700 - 100 + 1)) + 100;

        this.points = 0;

        this.cursors = this.input.keyboard?.createCursorKeys();
        this.setText()

        this.platforms = this.physics.add.staticGroup();
        this.ostacoliMobili = this.physics.add.group();
        this.ostacolo = this.ostacoliMobili.create(n, 0, "ostacolo");
        this.guerriero = this.physics.add.sprite(100, 500, "idle");

        //

        this.add.text(100, 175, 'Prossimo livello a 900 punti.', { font: '32px Arial', color: '#ffffff' })
            .setDepth(1);

        this.platforms.create(400, 600, "terra");

        this.ostacolo.setBounce(0.25);
        this.ostacolo.setCollideWorldBounds(true);

        if (n <= 400) {
            this.ostacolo.setVelocityX(50);
        } else {
            this.ostacolo.setVelocityX(-50);
        }

        //

        this.physics.add.collider(this.guerriero, this.platforms);
        this.physics.add.collider(this.ostacoliMobili, this.platforms); // Aggiungi questa linea
        this.physics.add.collider(this.guerriero, this.ostacoliMobili, this.manageCollision, undefined, this)

        // Commons
        setGuerriero(this.guerriero);
        //createAnimations(this);
    }

    update(): void {
        // Commons
        input(this.cursors, this.guerriero);
    }

    manageCollision(guerriero: CollisionElement, ostacolo: CollisionElement): void {

        if (!(ostacolo instanceof Phaser.Physics.Arcade.Sprite) || !ostacolo.body) {
            return;
        }

        if (ostacolo.body.touching.up) {
            let n = Math.floor(Math.random() * (700 - 100 + 1)) + 100;
            this.ostacolo.x = n; // Posizione x di partenza
            this.ostacolo.y = 0; // Posizione y di partenza
            this.points += 100;
            if (this.points >= 900) {
                this.scene.start('level3');
            }
            if (this.text) {
                this.text.destroy();
            }
            this.setText()
            if (n <= 400) {
                this.ostacolo.setVelocityX(50); // Aggiungi questa linea
            } else {
                this.ostacolo.setVelocityX(-50); // Aggiungi questa linea
            }
        } else {
            this.ostacolo.setVelocityX(0); // Aggiungi questa linea
        }
    }

    setText() {
        this.text = this.add.text(100, 100, 'Punti: ' + this.points, { font: '64px Arial', color: '#ffffff' })
            .setDepth(1);
    }
}
