#!/bin/bash
tsc
netlify deploy --dir=./build --site=crudites --prod
