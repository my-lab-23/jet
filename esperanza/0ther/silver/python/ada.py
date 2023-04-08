import requests
import json

# Inserisci qui la tua chiave API
API_KEY = ""

# Definisci l'URL dell'API di CoinMarketCap per ottenere il prezzo di ADA Cardano in euro
url = "https://pro-api.coinmarketcap.com/v1/cryptocurrency/quotes/latest"

# Imposta i parametri della richiesta
parameters = {
    "symbol": "ADA",
    "convert": "EUR"
}

# Imposta l'header della richiesta con la tua chiave API
headers = {
    "Accepts": "application/json",
    "X-CMC_PRO_API_KEY": API_KEY
}

# Esegui la richiesta e ottieni i dati in formato JSON
response = requests.get(url, headers=headers, params=parameters)
data = response.json()

# Estrai il prezzo di ADA Cardano in euro dai dati
price = data["data"]["ADA"]["quote"]["EUR"]["price"]

# Estrai il timestamp della richiesta
timestamp = data["status"]["timestamp"]

# Crea un dizionario Python con il prezzo di ADA Cardano in euro
result = {"price": price, "timestamp": timestamp}

# Scrivi il risultato in formato JSON nel file "ada_result.txt"
with open("ada_result.txt", "w") as f:
    json.dump(result, f)
