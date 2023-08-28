import { readLines } from "https://deno.land/std/io/mod.ts"

async function createHtmlPage(endpoints: string[]): Promise<string> {

  let html = "<html><head><title>Active Endpoints</title></head><body>"

  for (const endpoint of endpoints) {
    html += `<a href='${endpoint}'>${endpoint}</a><br>`
  }

  html += "</body></html>"

  return html
}

if (Deno.args.length !== 1) {
  console.log("Usage: deno run --allow-read --allow-write --allow-net endpoint_scanner.ts domain.com")
  Deno.exit(1)
}

const domain = Deno.args[0]
const endpoints: string[] = []

const file = await Deno.open("endpoints.txt")

for await (const line of readLines(file)) {
  endpoints.push(line)
}

file.close()

const alive: string[] = []

for (const endpoint of endpoints) {

  const url = `https://${domain}${endpoint}`
  const response = await fetch(url)

  if (response.status === 200) {
    console.log(`[+] Found endpoint: ${url}`)
    alive.push(url)
  } else {
    console.log(`[-] Endpoint not found: ${url}`)
  }
}

const html = await createHtmlPage(alive)

await Deno.writeTextFile("endpoints.html", html)
