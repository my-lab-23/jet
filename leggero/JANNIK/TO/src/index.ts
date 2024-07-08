import express, { Router } from 'express';
import serverless from 'serverless-http';
import log from './logger';

//

import { AppDataSource } from "./data-source";
import { Event } from "./entity/Event";

//

const api = express();
api.use(express.json()); // Aggiungi questa linea
const router = Router();

//

router.post('/insertEvent', async (req, res) => {
    try {
        if (!AppDataSource.isInitialized) {
            await AppDataSource.initialize();
        }

        const eventRepository = AppDataSource.getRepository(Event);

        // Validazione dei dati in ingresso
        if (!req.body.startDate || !req.body.endDate || !req.body.result) {
            return res.status(400).send({ message: 'Dati in ingresso non validi.' });
        }

        // Crea le date senza l'orario
        let startDate = new Date(req.body.startDate);
        startDate.setHours(0, 0, 0, 0);
        let endDate = new Date(req.body.endDate);
        endDate.setHours(0, 0, 0, 0);

        // Controlla se esiste già un evento con le stesse date
        const existingEvent = await eventRepository.findOne({
            where: {
                startDate: startDate,
                endDate: endDate
            }
        });

        if (existingEvent) {
            // return res.status(409).send({ message: 'Un evento con le stesse date di inizio e fine esiste già.' });
        }

        const newEvent = new Event();
        newEvent.startDate = startDate;
        newEvent.endDate = endDate;
        newEvent.result = req.body.result;

        // Temp: esegui TRUNCATE sulla tabella
        await eventRepository.manager.query('TRUNCATE TABLE my.event RESTART IDENTITY CASCADE');
        await eventRepository.manager.query('TRUNCATE TABLE my.event2 RESTART IDENTITY CASCADE');

        //

        await eventRepository.save(newEvent);

        log.debug(newEvent.id)

        res.status(201).send({ message: 'Evento creato con successo', event: newEvent.id });
    } catch (error) {
        log.error(error);
        res.status(500).send({ message: 'Si è verificato un errore durante la creazione dell\'evento.' });
    }
});

//

router.post('/searchEvent', async (req, res) => {
    try {
        if (!AppDataSource.isInitialized) {
            await AppDataSource.initialize();
        }

        const eventRepository = AppDataSource.getRepository(Event);

        // Crea la data senza l'orario
        // let date = new Date(req.body.date);
        // date.setHours(0, 0, 0, 0);
        let date = req.body.date
        log.debug(date)

        //

        // Cerca l'evento più recente che include la data
        const event = await eventRepository.createQueryBuilder("event")
            .where(":date BETWEEN event.startDate AND event.endDate", { date: date })
            .orderBy("event.id", "DESC") // Ordina per ID invece che per data di inizio
            .getOne();

        // Stampa l'ID dell'evento trovato
        if (event) {
            log.debug(`L'ID dell'evento trovato è: ${event.id}`);
        } else {
            log.debug("Nessun evento trovato per questa data.");
        }

        //

        if (!event) {
            return res.status(404).send({ message: 'Nessun evento trovato per la data fornita.' });
        }

        // Restituisce i primi 23 caratteri del risultato dell'evento
        const result = event.result // .substring(0, 23);

        log.debug(date)
        //log.debug(result)

        res.status(200).send(result);

    } catch (error) {
        //log.error(error);
        log.debug(req.body.date)
        res.status(500).send({ message: 'Si è verificato un errore durante la ricerca dell\'evento.' });
    }
});

//

api.use("/api/", router);

// Se la variabile d'ambiente NETLIFY non è impostata, avvia il server Express normalmente
if (!process.env.NETLIFY && !process.env.NETLIFY_DEV) {
    const port = process.env.PORT || 3000;
    api.listen(port, () => log.debug(`Server running on port ${port}`));
}

export const handler = serverless(api);
