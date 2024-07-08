#!/bin/bash
tsc
# rename build/index.js to api.js
mv build/index.js build/api.js
# create the /netlify/functions directory in BETO if it doesn't exist
mkdir -p ../BETO/netlify/functions
# copy the contents of the build directory to /Netlify/Functions
cp -r build/* ../BETO/netlify/functions
