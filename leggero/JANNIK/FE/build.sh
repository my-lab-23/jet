#!/bin/bash

# Chiedi all'utente di inserire l'URL
echo "Inserisci l'URL del backend:"
echo "Premi invio per http://localhost:8888"
echo "Scrivi 1 per https://leggero-jannik.netlify.app"
read input

# Controlla l'input dell'utente e scrivi l'URL corrispondente nel file .env.production
if [ "$input" == "1" ]; then
  echo "VITE_BACKEND_URL=https://leggero-jannik.netlify.app" > .env.production
else
  echo "VITE_BACKEND_URL=http://localhost:8888" > .env.production
fi

###

npm run build
# remove the /netlify/functions directory in BETO if it exists
rm -rf ../BE/assets
# copy the contents of the build directory to /Netlify/Functions
cp -r dist/* ../BE
