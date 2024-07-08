export let collectedCrystals: any[] = [];
export let lastJumpDirection: string | null = null;

export let gameStates = {
    superJump: false,
    // other states...
};

export function input(
    cursors: Phaser.Types.Input.Keyboard.CursorKeys | undefined,
    guerriero: Phaser.Physics.Arcade.Sprite
): void {

    if (!guerriero || !guerriero.body || !cursors) {
        return;
    }

    let isOnGround: boolean = guerriero.body.touching.down;
    let isOnWall: boolean = guerriero.body.touching.right || guerriero.body.touching.left;
    let velocity: number = (isOnGround || isOnWall) ? -330 : 0;
    let animation: string = isOnGround ? 'run' : 'jump';

    if (cursors.up.isDown && isOnGround) {
        guerriero.setVelocityY(gameStates.superJump ? velocity * 1.5 : velocity);
        guerriero.anims.play('jump', true);
        lastJumpDirection = null;
    } else if (cursors.up.isDown && isOnWall) {
        let direction: string = cursors.right.isDown ? 'right' : 'left';
        if (lastJumpDirection !== direction) {
            guerriero.setVelocityY(gameStates.superJump ? velocity * 1.5 : velocity);
            guerriero.anims.play('jump', true);
            lastJumpDirection = direction;
        }
    } else if (cursors.left.isDown || cursors.right.isDown) {
        let direction: number = cursors.right.isDown ? 1 : -1;
        guerriero.setVelocityX(160 * direction);
        guerriero.anims.play(animation, true);
        guerriero.setFlipX(cursors.left.isDown);
    } else {
        guerriero.setVelocityX(0);
        if (isOnGround) {
            guerriero.anims.play('idle', true);
        } else {
            guerriero.anims.stop();
        }
    }
}

export function loadSpritesheets(scene: Phaser.Scene): void {

    const spritesheets: { key: string, path: string }[] = [
        { key: 'idle', path: 'guerriero/Idle.png' },
        { key: 'jump', path: 'guerriero/Jump.png' },
        { key: 'run', path: 'guerriero/Run.png' }
    ];

    spritesheets.forEach(sheet => {
        scene.load.spritesheet(sheet.key, sheet.path, {
            frameWidth: 128,
            frameHeight: 128
        });
    });
}

export function createAnimations(scene: Phaser.Scene): void {

    const animations: { key: string, start: number, end: number, frameRate: number, repeat: number }[] = [
        { key: 'idle', start: 0, end: 7, frameRate: 10, repeat: -1 },
        { key: 'jump', start: 0, end: 7, frameRate: 10, repeat: -1 },
        { key: 'run', start: 0, end: 7, frameRate: 10, repeat: -1 }
    ];

    animations.forEach(anim => {
        scene.anims.create({
            key: anim.key,
            frames: scene.anims.generateFrameNumbers(anim.key, { start: anim.start, end: anim.end }),
            frameRate: anim.frameRate,
            repeat: anim.repeat
        });
    });
}

export function setGuerriero(guerriero: Phaser.Physics.Arcade.Sprite): void {

    guerriero.setBounce(0.25);
    guerriero.setCollideWorldBounds(true);

    if (guerriero.body) {
        guerriero.body.setSize(55, 75, true);
        guerriero.body.setOffset(36, 52);
    }
}
