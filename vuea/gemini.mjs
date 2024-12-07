import express from 'express';
import { GoogleGenerativeAI } from '@google/generative-ai';
import rateLimit from 'express-rate-limit';

// Configura il middleware di rate limiting
const limiter = rateLimit({
  windowMs: 15 * 60 * 1000, // 15 minuti
  max: 20, // Numero massimo di richieste per IP in questo intervallo
  message: { error: 'Troppe richieste da questo IP, riprova più tardi.' },
  standardHeaders: true, // Invia informazioni sulle limitazioni nelle intestazioni di risposta
  legacyHeaders: false, // Disabilita le intestazioni personalizzate x-rate-limit-*
});

// La funzione per generare contenuto utilizzando GoogleGenerativeAI
async function gemini(str) {
  const genAI = new GoogleGenerativeAI(process.env.API_KEY);
  const model = genAI.getGenerativeModel({ model: 'gemini-1.5-flash' });
  const prompt = str; // Usa la stringa passata come prompt
  const result = await model.generateContent(prompt);
  console.log(result.response.text());
  return result.response.text();
}

// Crea una nuova app Express
const app = express();

// Middleware per parsing JSON
app.use(express.json());

// Applica il rate limiter a tutte le richieste
app.use(limiter);

// Endpoint /gemini
app.post('/gemini', async (req, res) => {
  const { inputString } = req.body;
  if (typeof inputString === 'string') {
    try {
      const generatedText = await gemini(inputString);
      res.json({ gemini: generatedText });
    } catch (error) {
      console.error(error);
      res.status(500).json({ error: 'Errore nel generare il contenuto', dettagli: error.message });
    }
  } else {
    res.status(400).json({ error: 'inputString deve essere una stringa' });
  }
});

// Avvia il server
const PORT = process.env.PORT || 3000;
app.listen(PORT, () => {
  console.log(`Server in esecuzione sulla porta ${PORT}`);
});
