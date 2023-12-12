interface MySquare {

    highlightSquare: () => number
    colorSquare: (targetIndex: number, squares: NodeListOf<Element>) => void
    handleClick: (greenSquares: string[]) => void
    saltati: (lastClick: number) => number
}

export const mySquare: MySquare = {

    highlightSquare: function (): number {

        const currentDate = new Date()
        const currentDay = currentDate.getDate()

        let targetIndex: number

        if (currentDay >= 1 && currentDay <= 9) {
            targetIndex = currentDay - 1
        } else if (currentDay >= 10 && currentDay <= 18) {
            targetIndex = currentDay - 10
        } else if (currentDay >= 19 && currentDay <= 27) {
            targetIndex = currentDay - 19
        } else if (currentDay >= 27 && currentDay <= 31) {
            targetIndex = currentDay - 27
        } else {
            targetIndex = -1
        }

        return targetIndex
    },

    colorSquare: function (targetIndex: number, squares: NodeListOf<Element>): void {

        const timestampAttuale = new Date().getTime()

        const lastClick = parseInt(localStorage.getItem("lastClick"))
        const oggi = new Date(lastClick)
        oggi.setHours(0, 0, 0, 0)

        const prossimoGiornoTimestamp = oggi.getTime() + 24 * 60 * 60 * 1000
        const saltati = this.saltati(prossimoGiornoTimestamp)

        const squaresArray: Element[] = Array.from(squares)
        const firstGreen = squaresArray.findIndex(square => square.classList[1] === 'green')

        // Debug

        const debug1 = document.getElementById("debug1")
        const debug2 = document.getElementById("debug2")
        const debug3 = document.getElementById("debug3")
        const debug4 = document.getElementById("debug4")
        const debug5 = document.getElementById("debug5")

        debug1.textContent = `timestampAttuale: ` + timestampAttuale
        debug2.textContent = `prossimoGiornoTimestamp: ` + prossimoGiornoTimestamp
        debug3.textContent = `lastClick: ` + lastClick
        debug4.textContent = `countDown: ` + (prossimoGiornoTimestamp-timestampAttuale)
        debug5.textContent = `saltati: ` + saltati

        if(saltati > 1) alert(`Giorni saltati: ${Math.floor(saltati)}`)

        // Debug

        if (firstGreen === -1) {

            squares[targetIndex].classList.add('red')

        } else if (timestampAttuale > prossimoGiornoTimestamp) {

            squares[targetIndex].classList.remove('red')

            let lastGreen: number

            for (let i = firstGreen; i < squares.length; ++i) {

                if (squares[i].classList[1] === 'green') {

                    lastGreen = i

                } else { break }
            }

            for (let i = 1; i <= squares.length; ++i) {

                let index = (lastGreen + i) % squares.length

                if (squares[index].classList[1] === 'yellow') {

                    squares[index].classList.remove('yellow')
                    squares[index].classList.add('red')
                    break
                }
            }

        } else { squares[targetIndex].classList.remove('red') }
    },

    handleClick: function (greenSquares: string[]): void {

        this.classList.toggle('green')

        const lastClick = new Date().getTime()
        localStorage.setItem("lastClick", lastClick.toString())

        // Debug

        const debug3 = document.getElementById("debug3")

        debug3.textContent = `lastClick - ` + lastClick

        // Debug

        if (this.classList.contains('green')) {

            greenSquares.push(this.getAttribute('data-id'))
            this.classList.remove('red')

        } else {

            const index = greenSquares.indexOf(this.getAttribute('data-id'))
            if (index !== -1) { greenSquares.splice(index, 1) }
        }

        if (greenSquares.length === 9) {
            localStorage.removeItem('greenSquares')
        } else {
            localStorage.setItem('greenSquares', JSON.stringify(greenSquares))
        }
    },

    saltati: function (lastClick: number): number {
        const now = new Date().getTime()
        const diffInDays = (now - lastClick) / (1000 * 60 * 60 * 24)
        return diffInDays
    }    
}
