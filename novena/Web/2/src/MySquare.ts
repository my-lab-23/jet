import { myClient } from "./MyClient.js"

interface MySquare {

    highlightSquare: () => number
    colorSquare: (targetIndex: number, squares: NodeListOf<Element>) => void
    handleClick: (greenSquares: string[]) => void
    fetchDataAndDoSomething: (targetIndex: number) => Promise<void>
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

        const lastClick = parseInt(localStorage.getItem("lastClick"));
        const oggi = new Date(lastClick)
        oggi.setHours(0, 0, 0, 0)

        const prossimoGiornoTimestamp = oggi.getTime() + 24 * 60 * 60 * 1000

        const squaresArray: Element[] = Array.from(squares)
        const firstGreen = squaresArray.findIndex(square => square.classList[1] === 'green')

        // Debug

        const debug1 = document.getElementById("debug1")
        const debug2 = document.getElementById("debug2")
        const debug3 = document.getElementById("debug3")
        const debug4 = document.getElementById("debug4")

        debug1.textContent = `timestampAttuale - ` + timestampAttuale
        debug2.textContent = `prossimoGiornoTimestamp - ` + prossimoGiornoTimestamp
        debug3.textContent = `lastClick - ` + lastClick
        debug4.textContent = `countDown - ` + (prossimoGiornoTimestamp-timestampAttuale)

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

    handleClick: async function (greenSquares: string[]): Promise<void> {

        this.classList.toggle('green')

        const lastClick = new Date().getTime()
        localStorage.setItem("lastClick", lastClick.toString());

        // Debug

        const debug3 = document.getElementById("debug3")

        debug3.textContent = `lastClick - ` + lastClick;

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

        //

        const squares = document.querySelectorAll('.square') as NodeListOf<HTMLElement>
        const s = generateStringFromSquares(squares)
        await myClient.write(s)
    },

    fetchDataAndDoSomething: async function(targetIndex: number): Promise<void> {

        const content = await myClient.read()

        if (content) {

            const data = content.trim().split('\n')
            const squares = document.querySelectorAll('.square') as NodeListOf<HTMLElement>

            squares.forEach((square, index) => {
                
                const color = data[index]
                square.classList.remove('yellow', 'green', 'red')
                if (color === 'green') { square.classList.add('green') } 
            })

            if(areAllGreen(squares)) {
            
                squares.forEach((square, index) => {
                    square.classList.remove('yellow', 'green', 'red')
                })                
                
                squares[targetIndex].classList.add('red')
            
            }

        } else { console.log('Impossibile ottenere il contenuto dal server.') }
    }
}

function generateStringFromSquares(squares: NodeListOf<Element>) {
    const classNames = Array.from(squares).map(square => square.classList.contains('green') ? 'green' : 'null')
    return classNames.join('\n')
}

function areAllGreen(squares) {
    for (const square of squares) {
        if (!square.classList.contains('green')) {
            return false;
        }
    }
    return true;
}
