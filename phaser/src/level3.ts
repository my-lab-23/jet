import * as Phaser from 'phaser';
import { loadSpritesheets, setGuerriero, createAnimations, input } from './commons.ts';

//

type CollisionElement = Phaser.Tilemaps.Tile | Phaser.Types.Physics.Arcade.GameObjectWithBody;

export class Level3 extends Phaser.Scene {

    cursors: Phaser.Types.Input.Keyboard.CursorKeys | undefined;
    text: Phaser.GameObjects.Text;

    platforms: Phaser.Physics.Arcade.StaticGroup;
    ostacoliMobili: Phaser.Physics.Arcade.Group;
    ostacolo: Phaser.Physics.Arcade.Sprite;
    coins: Phaser.Physics.Arcade.StaticGroup;
    guerriero: Phaser.Physics.Arcade.Sprite;

    points: number = 0;
    isColliding: boolean;

    //

    constructor() {
        super({ key: 'level3' });

        // Variabile per tenere traccia dello stato della collisione
        this.isColliding = false;
    }

    preload(): void {
        this.load.image('terra1600', 'other/terra1600.png')
        this.load.image('ostacolo', 'other/ostacolo.png');
        this.load.image('plateau', 'other/plateau.png');
        this.load.image('coin', 'other/coin.png');

        // Commons
        loadSpritesheets(this);
    }

    create(): void {

        this.points = 0;

        this.cursors = this.input.keyboard?.createCursorKeys();

        this.platforms = this.physics.add.staticGroup()
        this.ostacoliMobili = this.physics.add.group();
        this.ostacolo = this.ostacoliMobili.create(1400, 525, "ostacolo");
        this.coins = this.physics.add.staticGroup();
        this.guerriero = this.physics.add.sprite(100, 500, "idle");

        this.text = this.add.text(this.guerriero.x, 50, 'Punti: ' + this.points, { font: '32px Arial', color: '#ffffff' });

        //

        this.platforms.create(800, 600, "terra1600")
        this.platforms.create(700, 400, "plateau");

        this.ostacolo.setBounce(0.25);
        this.ostacolo.setCollideWorldBounds(true);

        this.coins.create(700, 200, "coin");
        this.coins.create(700, 550, "coin");
        this.coins.create(1400, 450, "coin");

        this.physics.add.collider(this.guerriero, this.platforms);
        this.physics.add.collider(this.ostacoliMobili, this.platforms); // Aggiungi questa linea
        this.physics.add.overlap(this.guerriero, this.coins, this.getPoints, undefined, this);

        this.physics.add.collider(this.guerriero, this.ostacoliMobili, this.manageCollision, undefined, this);

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
        // Commons
        input(this.cursors, this.guerriero);

        if (!this.isColliding) {
            this.ostacolo.setVelocityX(0);
        }
        // Resetta lo stato della collisione per il prossimo frame
        this.isColliding = false;

        if (this.ostacolo.x >= 1550) {
            this.ostacolo.x = 1400;
        }

        if (this.ostacolo.x <= 50) {
            this.ostacolo.x = 200;
        }

        if (this.text) {
            this.text.destroy()
        }
        this.text = this.add.text(this.guerriero.x, 50, 'Punti: ' + this.points, { font: '32px Arial', color: '#ffffff' });
    }

    getPoints(guerriero: CollisionElement, coin: CollisionElement): void {

        coin.destroy()

        this.points += 100;

        if (this.points >= 300) {
            this.scene.start('level4');
        }
    }

    manageCollision(guerriero: CollisionElement, ostacolo: CollisionElement): void {

        if (!(ostacolo instanceof Phaser.Physics.Arcade.Sprite) || !ostacolo.body) {
            return;
        }

        if (ostacolo.body.touching.left) {
            this.ostacolo.setVelocityX(+50);
            this.isColliding = true;
        } else if (ostacolo.body.touching.right) {
            this.ostacolo.setVelocityX(-50);
            this.isColliding = true;
        } else {
            this.ostacolo.setVelocityX(0);
            this.isColliding = true;
        }
    }
}
