import * as wiki from "https://deno.land/x/denowiki/mod.ts"

//

async function estraiPagina(query: string): Promise<string> {

    const wikiSearchResult: wiki.WikiSearch_Query = await wiki.wikiSearch(
        { language: "it", srsearch: query, srlimit: 1 },
    )

    const pageID: number = wiki.getPageId(wikiSearchResult)

    const wikiPage: wiki.WikiParse_Query = await wiki.wikiParse(
        { pageid: pageID, language: "it", prop: "wikitext|text" },
    )

    const text = wiki.getWikiText(wikiPage)

    return text
}

//

function estrai(testo: string): string {

    const inizioTag = /==\s*Caratteristiche tecniche\s*==/
    const fineTag = "=="
    const matchInizio = testo.match(inizioTag)

    if (!matchInizio) {
        return "Tag di inizio non trovato"
    }

    const inizioPosizione = matchInizio.index

    const finePosizione = testo.indexOf(fineTag, inizioPosizione + matchInizio[0].length)

    if (finePosizione === -1) {
        return "Tag di fine non trovato"
    }

    const caratteristicheTecniche = testo.substring(
        inizioPosizione + matchInizio[0].length,
        finePosizione
    )

    return caratteristicheTecniche.trim()
}

//

function rimpiazza(inputString: string): string {

    const regex0 = /\[\[(.*?)\]\]/g
    const regex1 = /{{Calcio (\w+)\|N}}/g

    const updatedString0 = aux(inputString, regex0)
    const updatedString1 = aux(updatedString0, regex1)

    return updatedString1
}

function aux(inputString: string, regex: RegExp): string {

    const updatedString = inputString.replace(regex, (match, captureGroup) => {
        const words = captureGroup.split('|')
        return words[words.length - 1]
    })

    return updatedString
}

//

function rimuovi(testo: string): string {

    let pattern = /{{!}}.*?}}/g
    let res = testo.replace(pattern, '')

    pattern = /{{.*?}}/g
    res = res.replace(pattern, '')

    pattern = /\|[^|}]+\}\}/g
    res = res.replace(pattern, '')

    pattern = /<[^>]*>/g
    res = res.replace(pattern, '')

    return res.replace(/^\n+/g, '')
}

//

function estraiNomiCalciatori(testo: string): string[] {
    
    const regex = /\{\{Calciatore in rosa\|n=\d+\|nazione=\w+\|nome=\[\[(.*?)\]\]\|ruolo=\w+\}\}/g
    const matches = testo.match(regex)

    if (matches && matches.length > 0) {
        
        const nomiCalciatori: string[] = []

        for (const match of matches) {
            const nomeRegex = /\{\{Calciatore in rosa\|n=\d+\|nazione=\w+\|nome=\[\[(.*?)\]\]\|ruolo=\w+\}\}/
            const nomeMatch = match.match(nomeRegex)

            if (nomeMatch && nomeMatch.length > 1) {
                nomiCalciatori.push(nomeMatch[1])
            }
        }

        return nomiCalciatori
    }

    return []
}

//

let query = Deno.args[0]
const result = await estraiPagina(query)
const calci = estraiNomiCalciatori(result)

if (calci.length > 0) {

    for (const nome of calci) {

        await new Promise((resolve) => setTimeout(resolve, 1000))

        query = nome
        const res = await estraiPagina(query)
        const estratto = estrai(res)
        const rimpiazzato = rimpiazza(estratto)
        const risultatoFinale = rimuovi(rimpiazzato)
        console.log(nome+"\n")
        console.log(risultatoFinale+"\n")
    }

} else { console.log("Nessun calciatore trovato nel testo.") }
