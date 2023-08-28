import { myClient } from "../MyClient.ts"

for (let i = 0; i < 10; i++) {

  const id = i
  const data = generateRandomString() // Funzione per generare una stringa casuale

  myClient.create(id, data)
}

function generateRandomString() {

  const characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"
  const length = 10
  let result = ""

  for (let i = 0; i < length; i++) {
    
    const randomIndex = Math.floor(Math.random() * characters.length)
    result += characters.charAt(randomIndex)
  }

  return result
}
