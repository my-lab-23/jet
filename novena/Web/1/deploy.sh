#!/bin/bash
tsc
netlify deploy --dir=./build --site=novena9 --prod
