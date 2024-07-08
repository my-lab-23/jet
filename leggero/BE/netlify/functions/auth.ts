// Middleware per l'autenticazione tramite API key
export const authenticateAPIKey = (req, res, next) => {
    const apiKey = req.get('X-API-KEY');
    if (!apiKey) {
        return res.status(401).send('Missing API key\n');
    }

    // Qui dovresti verificare la validità dell'API key. Per semplicità, in questo esempio
    // assumiamo che la tua API key sia 'my-secret-api-key'.
    if (apiKey !== process.env.API_KEY) {
        return res.status(403).send('Invalid API key\n');
    }

    // Se l'API key è valida, procedi con la richiesta
    next();
};

//

export const { auth } = require('express-openid-connect');

export const config = {
  authRequired: false,
  auth0Logout: true,
  secret: process.env.AUTH0_SECRET,
  // baseURL: 'http://localhost:8888/api',
  baseURL: 'https://leggero.netlify.app/api',
  clientID: process.env.AUTH0_CLIENTID,
  issuerBaseURL: 'https://dev-zqz-kev4.eu.auth0.com/authorize?&prompt=login'
};
