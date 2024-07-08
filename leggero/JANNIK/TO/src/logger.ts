// logger.ts
import log from 'loglevel';

// Configurazione del livello di log in base all'ambiente
if (process.env.NODE_ENV === 'production') {
    log.setLevel('silent'); // Disabilita i log in produzione
} else {
    log.setLevel('debug'); // Abilita i log di debug in sviluppo
}

export default log;
