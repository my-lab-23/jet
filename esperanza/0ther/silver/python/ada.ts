const API_KEY = ""
const url = "https://pro-api.coinmarketcap.com/v1/cryptocurrency/quotes/latest"

const parameters = {
  symbol: "ADA",
  convert: "EUR"
}

const headers = new Headers({
  Accepts: "application/json",
  "X-CMC_PRO_API_KEY": API_KEY
})

const response = await fetch(`${url}?symbol=${parameters.symbol}&convert=${parameters.convert}`, {
  method: "GET",
  headers
})

const data = await response.json()
const price = data.data.ADA.quote.EUR.price
const timestamp = data.status.timestamp
const result = { price, timestamp }

await Deno.writeTextFile("ada_result.txt", JSON.stringify(result))
