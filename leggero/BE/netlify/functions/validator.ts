const rateLimit = require("express-rate-limit");

// Limite di una richiesta al secondo
export const limiter = rateLimit({
  windowMs: 5000, // 5 secondi
  max: 1, // limite per ogni finestra
  keyGenerator: function (req) {
    // Usa l'indirizzo IP dal contesto della funzione lambda
    return req.headers['client-ip'];
  }
});

// Middleware per validare la lunghezza del testo
export const validateTextLength = (req, res, next) => {
  const text = req.body;
  if (text && text.length > 12) {
    return res.status(400).send('Bad Request: Text length should not exceed 12 characters');
  }
  next();
};
