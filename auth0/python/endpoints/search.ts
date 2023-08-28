import { existsSync } from "https://deno.land/std/fs/mod.ts"
import { walkSync } from "https://deno.land/std@0.201.0/fs/walk.ts"

const ENDPOINT_REGEX = /(get|post)\(\"([^\"]+)\"/

function findEndpointsInFile(filePath: string): [string, string][] {

    const endpoints: [string, string][] = []
    const fileContent: string = Deno.readTextFileSync(filePath)
    const lines = fileContent.split("\n")
    
    for (const line of lines) {

        const match = line.match(ENDPOINT_REGEX)
        
        if (match) {
            
            const httpMethod = match[1]
            const endpointPath = match[2]
            endpoints.push([httpMethod, endpointPath])
        }
    }
    
    return endpoints
}

function findEndpointsInDirectory(directoryPath: string): [string, string][] {

    const endpoints: [string, string][] = []

    for (const entry of walkSync(directoryPath)) {

        if (entry.isFile && entry.name.endsWith(".kt")) {
            endpoints.push(...findEndpointsInFile(entry.path))
        }
    }

    return endpoints
}

function main() {

    const directories = [
        "/home/ema/desperado/auth0_ID/src/main/kotlin",
        "/home/ema/desperado/esperanza_S/src/main/kotlin"
    ]

    const outputPath = "endpoints.txt"

    if (existsSync(outputPath)) { Deno.remove(outputPath) }

    const endpoints = directories.flatMap((directory) =>
        findEndpointsInDirectory(directory)
    )

    const filteredEndpoints = endpoints.filter((endpoint) =>
        !endpoint[1].includes("http")
    )

    for (const endpoint of filteredEndpoints) {
        Deno.writeTextFileSync(outputPath, endpoint[1]+'\n', { append: true })
    }
}

main()
