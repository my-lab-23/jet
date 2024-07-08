class DaysUntilAugust extends HTMLElement {

    private daysDisplay: HTMLDivElement

    constructor() {
        super()

        const shadow = this.attachShadow({ mode: 'open' })

        this.daysDisplay = document.createElement('div')

        // Aggiungiamo dello stile al nostro elemento
        this.daysDisplay.style.color = 'blue'
        this.daysDisplay.style.fontWeight = 'bold'
        this.daysDisplay.style.fontSize = '20px'
        this.daysDisplay.style.textAlign = 'center'
        this.daysDisplay.style.marginTop = '10px'

        shadow.appendChild(this.daysDisplay)

        this.calculateDaysUntilAugust()
    }

    calculateDaysUntilAugust() {
        const today = new Date()
        const augustFirst = new Date(today.getFullYear(), 7, 1) // Mesi sono da 0 (gennaio) a 11 (dicembre)

        // Se oggi è dopo il primo agosto, calcola i giorni fino al primo agosto dell'anno prossimo
        if (today > augustFirst) {
            augustFirst.setFullYear(augustFirst.getFullYear() + 1)
        }

        const diffTime = Math.abs(augustFirst.getTime() - today.getTime())
        const diffDays = Math.ceil(diffTime / (1000 * 60 * 60 * 24)) // Calcola la differenza in giorni

        this.displayDays(diffDays)
    }

    displayDays(days: number) {
        this.daysDisplay.textContent = `Mancano ${days} giorni al primo agosto.`
    }
}

customElements.define('my-dua', DaysUntilAugust)
