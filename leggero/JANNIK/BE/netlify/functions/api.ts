import express, { Router } from "express";
import serverless from "serverless-http";
import NewsAPI from 'newsapi';
import log from './logger';

//

import { getLimiter } from "./validator"
import { insertEvent, searchEvent } from "./client"

//

const newsapi = new NewsAPI(process.env.NEWS_API);

const api = express();
const router = Router();

//

// Funzione per calcolare il conteggio delle notizie per giorno
function countNewsPerDay(articles) {
  let newsCountPerDay = {};

  // Ottieni la data corrente e la data di una settimana fa in UTC
  let today = new Date(Date.now());
  let lastWeek = new Date(Date.now() - 7 * 24 * 60 * 60 * 1000);

  log.debug(today.toISOString())

  // Crea un array di date per ogni giorno tra 'lastWeek' e 'today'
  for (let day = lastWeek; day <= today; day.setUTCDate(day.getUTCDate() + 1)) {
    // Formatta la data nel formato 'YYYY-MM-DD'
    let date = day.toISOString().split('T')[0];
    // Inizializza il conteggio delle notizie per quella data a 0
    newsCountPerDay[date] = 0;
  }

  // Conta le notizie per ogni giorno
  for (let article of articles) {
    let date = article.publishedAt.split('T')[0];
    newsCountPerDay[date]++;
  }

  return newsCountPerDay;
}

// Funzione per ordinare l'oggetto per data
function orderCounts(counts) {
  return Object.keys(counts).sort().reduce((obj, key) => {
    obj[key] = counts[key];
    return obj;
  }, {});
}

// Funzione per calcolare il totale delle notizie
function calculateTotal(counts: { [key: string]: number }): number {
  return Object.values(counts).reduce((a: number, b: number) => a + b, 0);
}

router.post('/news', express.text(), getLimiter(5000, 1), async (req, res) => {
  let today = new Date(Date.now());
  let lastWeek = new Date(Date.now() - 7 * 24 * 60 * 60 * 1000);
  let todayStr = today.toISOString().split('T')[0];
  let lastWeekStr = lastWeek.toISOString().split('T')[0];

  if (process.env.NETLIFY_DEV === 'true') {
    log.debug("Sto eseguendo in ambiente di sviluppo!");
    res.set('Access-Control-Allow-Origin', 'http://localhost:5173');
    res.set('Access-Control-Allow-Headers', 'Content-Type,Authorization,X-Requested-With');
  }

  log.debug(req.body)

  try {
    const response = await newsapi.v2.everything({
      q: req.body || 'Jannik Sinner',
      from: lastWeekStr,
      to: todayStr,
      language: 'it',
      sortBy: 'relevancy'
    });

    let dailyCounts = countNewsPerDay(response.articles);
    let orderedDailyCounts = orderCounts(dailyCounts);
    let total = calculateTotal(dailyCounts);

    log.debug(orderedDailyCounts)

    // Crea un nuovo evento 'current'
    let current = {
      startDate: lastWeek,
      endDate: today,
      result: response
    };

    // Tenta di spedirlo con insertEvent
    let statusCode = await insertEvent(current);
    log.debug(`Event insertion status: ${statusCode}`);

    res.json({ dailyCounts: orderedDailyCounts, total: total });

  } catch (error) {
    log.error(error);
    if (error.response && error.response.status === 429) {
      res.status(429).send('Hai superato il limite di richieste. Riprova più tardi.');
    } else {
      res.status(500).send('Si è verificato un errore durante l\'elaborazione della tua richiesta');
    }
  }
});

//

router.post('/day', express.text(), getLimiter(2000, 1), async (req, res) => {
  const selectedDay = req.body;

  if (process.env.NETLIFY_DEV === 'true') {
    log.debug("Sto eseguendo in ambiente di sviluppo!");
    res.set('Access-Control-Allow-Origin', 'http://localhost:5173');
    res.set('Access-Control-Allow-Headers', 'Content-Type,Authorization,X-Requested-With');
  }

  try {
    let event = await searchEvent(selectedDay)
    res.send(event.data.articles);
  } catch (error) {
    log.error(error);
    if (error.response && error.response.status === 429) {
      res.status(429).send('Hai superato il limite di richieste. Riprova più tardi.');
    } else {
      res.status(500).send('Si è verificato un errore durante l\'elaborazione della tua richiesta');
    }
  }
});

//

api.use("/api/", router);
api.use(express.json());

//

export const handler = serverless(api);
