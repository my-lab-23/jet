import * as Phaser from 'phaser';
import { collectedCrystals, gameStates } from './commons.ts';

//

export class Inventario extends Phaser.Scene {
    text: Phaser.GameObjects.Text;
    hint: Phaser.GameObjects.Text;

    constructor() {
        super({ key: 'inventario' });
    }

    preload(): void {
        this.load.image('yellowCrystal', 'crystal/Yellow_crystal1.png');
        this.load.image('blueCrystal', 'crystal/Blue_crystal1.png');
    }

    create(): void {
        // Crea uno sfondo marrone
        this.cameras.main.setBackgroundColor('#D9EAD3');

        this.text = this.add.text(50, 50, 'Inventario: ', { font: '64px Arial', color: '#013220' });
        this.hint = this.add.text(50, 120, "Clicca sui cristalli per provare l'effetto. Premi K per tornare alla partita.",
            { font: '16px Arial', color: '013220' });

        // Per ogni elemento in collectedCrystals, crea un cristallo
        let startX = 100; // Posizione iniziale del primo cristallo
        let gap = 70; // Spazio tra i cristalli
        for (let i = 0; i < collectedCrystals.length; i++) {
            let crystal = this.add.image(startX + i * gap, 200, collectedCrystals[i]);

            // Se il cristallo è yellowCrystal o blueCrystal, aggiungi un evento di clic
            if (collectedCrystals[i] === 'yellowCrystal' || collectedCrystals[i] === 'blueCrystal') {
                crystal.setInteractive();
                crystal.on('pointerdown', () => {
                    // Imposta superJump o superRun a true e distruggi il cristallo
                    if (collectedCrystals[i] === 'yellowCrystal') {
                        gameStates.superJump = true;
                    } else if (collectedCrystals[i] === 'blueCrystal') {
                        gameStates.superJump = false;
                    }
                    crystal.destroy();
                    // Rimuovi il cristallo dall'array collectedCrystals
                    collectedCrystals.splice(i, 1);
                });
            }
        }

        // Aggiungi il listener per il tasto 'K'
        this.input.keyboard?.on('keydown-K', () => {
            // Riprendi la scena precedente e chiudi l'inventario
            this.scene.resume('level4');
            this.scene.stop('inventario');
        });
    }
}
