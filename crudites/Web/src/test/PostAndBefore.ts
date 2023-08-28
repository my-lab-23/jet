import { readLines } from "https://deno.land/std/io/mod.ts"

async function replaceJSwithTS(filePath: string): Promise<void> {
  
  try {
    const file = await Deno.open(filePath)
    const decoder = new TextDecoder()
    const encoder = new TextEncoder()
    const lines: string[] = []

    let isFirstLine = true

    for await (const line of readLines(file)) {
  
      const modifiedLine = isFirstLine ? line.replace(/js|ts/g, match => match === "js" ? "ts" : "js") : line
      lines.push(modifiedLine)
      isFirstLine = false
    }

    file.close()

    const updatedContent = lines.join("\n")
    const updatedData = encoder.encode(updatedContent)

    const updatedFile = await Deno.open(filePath, { write: true, truncate: true, create: true })
    await updatedFile.write(updatedData)

    updatedFile.close()
  
  } catch (error) {
  
    console.error("Si è verificato un errore:", error.message)
  }
}

const filePath = "/home/ema/sesto/crudites/Web/src/MyClient.ts"
replaceJSwithTS(filePath)
