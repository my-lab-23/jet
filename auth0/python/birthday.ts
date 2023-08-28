import * as wiki from "https://deno.land/x/denowiki/mod.ts"

function getMonthNumber(month: string) {

    const months = {
        'gennaio': 1,
        'febbraio': 2,
        'marzo': 3,
        'aprile': 4,
        'maggio': 5,
        'giugno': 6,
        'luglio': 7,
        'agosto': 8,
        'settembre': 9,
        'ottobre': 10,
        'novembre': 11,
        'dicembre': 12
    }

    const lowercaseMonth = month.toLowerCase() // Converte il mese in minuscolo per la corrispondenza esatta

    return months[lowercaseMonth] || 'Mese non valido'
}

function getSign(day: number, month: number, year: number): string | null {

    if (!(1 <= month && month <= 12) || !(1 <= day && day <= 31)) { return null }

    if (month === 2 && day === 29) { return "Pesci" }

    const sign_names: string[] = [
        "Capricorno", "Acquario", "Pesci", "Ariete", "Toro", "Gemelli",
        "Cancro", "Leone", "Vergine", "Bilancia", "Scorpione", "Sagittario",
        "Capricorno"
    ]

    const signs: [number, number][] = [
        [1, 19], [20, 50], [51, 80], [81, 111], [112, 142], [143, 173], [174, 204],
        [205, 235], [236, 266], [267, 296], [297, 326], [327, 356], [357, 365]
    ]

    const days_in_month: number[] = [31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31]
    const day_of_year: number = days_in_month.slice(0, month - 1).reduce((acc, val) => acc + val, 0) + day

    for (let i = 0; i < signs.length; i++) {
        
        if (signs[i][0] <= day_of_year && day_of_year <= signs[i][1]) {
            return sign_names[i]
        }
    }

    return null
}

async function estraiDataDiNascita(nome: string, cognome: string): Promise<string> {

    const nomeCompleto = `${nome} ${cognome}`

    const wikiSearchResult: wiki.WikiSearch_Query = await wiki.wikiSearch(
        { language: "it", srsearch: nomeCompleto, srlimit: 1 },
    )

    const pageID: number = wiki.getPageId(wikiSearchResult)

    const wikiPage: wiki.WikiParse_Query = await wiki.wikiParse(
        { pageid: pageID, language: "it", prop: "wikitext|text" },
    )

    const testo = wiki.getWikiText(wikiPage)
    const regexGiornoMeseNascita = /\|GiornoMeseNascita\s*=\s*([^|]+)/
    const regexAnnoNascita = /\|AnnoNascita\s*=\s*([^|]+)/
    const matchGiornoMese = testo.match(regexGiornoMeseNascita)
    const matchAnno = testo.match(regexAnnoNascita)
    const giornoMeseNascita = matchGiornoMese[1].trim()
    const annoNascita = matchAnno[1].trim()
    const [day, month] = giornoMeseNascita.split(' ')
    const monthNumber = getMonthNumber(month)
    const year = parseInt(annoNascita, 10)
    const sign = getSign(Number(day), monthNumber, year)

    return `${nomeCompleto} è nato/a il ${day} ${month} ${year} sotto il segno: ${sign}`
}

const nome = Deno.args[0]
const cognome = Deno.args[1]

estraiDataDiNascita(nome, cognome)
    .then((result) => {
        console.log(result)

        const filePath = "/home/ema/result.txt"
        Deno.writeTextFile(filePath, result)
            .then(() => console.log(`Il risultato è stato scritto in ${filePath}`))
            .catch((error) => console.error(`Si è verificato un errore durante la scrittura in ${filePath}: ${error}`))
    })
    .catch((error) => console.error(`Si è verificato un errore durante l'estrazione della data di nascita: ${error}`))
