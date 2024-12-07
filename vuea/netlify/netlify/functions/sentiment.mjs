// functions/sentiment.js
import { GoogleGenerativeAI } from '@google/generative-ai';

export async function handler(event, context) {
  const { inputJson, name } = JSON.parse(event.body);

  if (!inputJson || !name) {
    return {
      statusCode: 400,
      body: JSON.stringify({ error: 'Parametri json e name obbligatori' })
    };
  }

  try {
    const prompt =
      "// Riassumi i testi presenti nel json ed estrai il sentiment. " +
      "Rispondi in italiano. " +
      "All'inizio della risposta metti un intero da 0 a 100 che esprima il sentiment.";

    const inputForGemini = inputJson + prompt;

    const response = await geminiGenerateContent(inputForGemini);

    return {
      statusCode: 200,
      body: JSON.stringify({ input: inputJson, prompt, response })
    };
  } catch (error) {
    console.error("Errore durante il processamento del sentiment:", error);
    return {
      statusCode: 500,
      body: JSON.stringify({ error: 'Errore durante il processamento del sentiment', dettagli: error.message })
    };
  }
}

async function geminiGenerateContent(prompt) {
  const genAI = new GoogleGenerativeAI(process.env.API_KEY);
  const model = genAI.getGenerativeModel({ model: 'gemini-1.5-flash' });
  const result = await model.generateContent(prompt);
  return result.response.text();
}
