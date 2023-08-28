#!/bin/bash
curl -H "X-api-key: $CRUDITES_API_KEY" https://2desperados.it/crudites/resetData
deno run --allow-read --allow-write ./src/test/PostAndBefore.ts
deno run --allow-env --allow-net ./src/test/Test.ts
deno run --allow-read --allow-write ./src/test/PostAndBefore.ts
