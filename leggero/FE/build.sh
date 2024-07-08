#!/bin/bash
npm run build
# remove the /netlify/functions directory in BETO if it exists
rm -rf ../BE/assets
# copy the contents of the build directory to /Netlify/Functions
cp -r dist/* ../BE
