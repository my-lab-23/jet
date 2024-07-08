import express, { Router } from "express";
import serverless from "serverless-http";
import { Pool } from 'pg';

//

import { authenticateAPIKey, auth, config } from "./auth"
import { limiter, validateTextLength } from "./validator"

//

const api = express();
const router = Router();

//

const pool = new Pool({
  user: process.env.PGUSER,
  host: process.env.PGHOST,
  database: process.env.PGDATABASE,
  password: process.env.PGPASSWORD,
  port: Number(process.env.PGPORT),
});

//

router.get('/test', async (req, res) => {
  try {
    const client = await pool.connect();
    const result = await client.query('SELECT * FROM my.test');
    const results = { 'results': (result) ? result.rows : null};
    res.send(JSON.stringify(results));
    client.release();
  } catch (err) {
    res.status(500).send("" + err);
  }
})

//

router.post('/test', limiter, validateTextLength, async (req, res) => {
  try {
    const client = await pool.connect();
    const text = req.body; // Il corpo della richiesta sarà una stringa
    if (text) {
      await client.query('INSERT INTO my.test (text) VALUES ($1)', [text]);
      res.send('Insert successful');
    } else {
      res.status(400).send('Bad Request: Missing text in request body');
    }
    client.release();
  } catch (err) {
    res.status(500).send("" + err);
  }
});

//

router.get('/createTestTable', async (req, res) => {

  //

  if(!req.oidc.isAuthenticated()) {
    return res.send('Logged out');
  } 
  if (req.oidc.user.email!=process.env.TABLE_OWNER) {
    return res.send('Forbidden');
  }

  //

  try {
    const client = await pool.connect();
    const dropTableText = 'DROP TABLE IF EXISTS my.test';
    await client.query(dropTableText);

    const createTableText = `
      CREATE TABLE my.test (
        id SERIAL PRIMARY KEY,
        text VARCHAR(40) NOT NULL
      )
    `;
    await client.query(createTableText);
    res.send('Table created successfully');
    client.release();
  } catch (err) {
    res.status(500).send("" + err);
  }
})

//

router.get('/insertRandomData/:count', authenticateAPIKey, async (req, res) => {
  try {
    const count = parseInt(req.params.count);
    const client = await pool.connect();

    const data: string[] = [];
    for (let i = 0; i < count; i++) {
      const text = Math.random().toString(36).substring(2, 15);
      data.push(`('${text}')`);
    }

    const startTime = Date.now();
    await client.query(`INSERT INTO my.test (text) VALUES ${data.join(",")}`);
    const endTime = Date.now();

    const timeTaken = endTime - startTime;
    res.send(`Insertion of ${count} random records took ${timeTaken} milliseconds\n`);
    client.release();
  } catch (err) {
    res.status(500).send("" + err);
  }
});

//

router.get('/user', (req, res) => {
  if(req.oidc.isAuthenticated()) {
    const email = req.oidc.user.email;
    res.send(`Logged in: ${email}<br><br><a href="/">Home</a>`);
  } else {
    res.send('Logged out<br><br><a href="/">Home</a>')
  }
});


router.get('/', (req, res) => {
  res.redirect('/api/user')
});

//

api.use("/api/", auth(config));
api.use("/api/", router);

//

const osInfoRouter = require('./osInfoRouter');
api.use('/api/', osInfoRouter);

//

export const handler = serverless(api);
