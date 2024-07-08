const rateLimit = require("express-rate-limit");

export function getLimiter(windowMs, max) {
  return rateLimit({
    windowMs,
    max,
    keyGenerator: function (req) {
      // Usa l'indirizzo IP dal contesto della funzione lambda
      return req.headers['client-ip'];
    }
  });
}

// Middleware per validare la lunghezza del testo
export const validateTextLength = (req, res, next) => {
  const text = req.body;
  if (text && text.length > 12) {
    return res.status(400).send('Bad Request: Text length should not exceed 12 characters');
  }
  next();
};
