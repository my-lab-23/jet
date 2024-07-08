const fs = require('fs');
const path = require('path');

// Prendi il percorso del file .env dall'argomento della riga di comando
const envPath = process.argv[2];

if (!envPath) {
  console.error('Per favore, fornisci il percorso del file .env come argomento.');
  process.exit(1);
}

// Assicurati che il percorso sia assoluto
const absoluteEnvPath = path.isAbsolute(envPath) ? envPath : path.join(__dirname, envPath);

// Leggi il file .env
const envFile = fs.readFileSync(absoluteEnvPath, 'utf8');

// Mappa le vecchie chiavi alle nuove chiavi
const keyMap = {
  'POSTGRESQL_ADDON_HOST': 'PG_HOST',
  'POSTGRESQL_ADDON_DB': 'PG_DATABASE',
  'POSTGRESQL_ADDON_USER': 'PG_USER',
  'POSTGRESQL_ADDON_PORT': 'PG_PORT',
  'POSTGRESQL_ADDON_PASSWORD': 'PG_PASSWORD',
};

// Sostituisci le vecchie chiavi con le nuove
const updatedEnvFile = envFile.split('\n').map(line => {
  const [key, value] = line.split('=');
  if (value && keyMap[key]) {
    return `${keyMap[key]}=${value}`;
  }
  return line;
}).join('\n');

// Stampa il file .env aggiornato
console.log(updatedEnvFile);
