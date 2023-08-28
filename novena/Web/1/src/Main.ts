import { mySquare } from "./MySquare.js"

const squares: NodeListOf<Element> = document.querySelectorAll('.square')
const greenSquares: string[] = JSON.parse(localStorage.getItem('greenSquares') || '[]')

document.addEventListener('DOMContentLoaded', function () {

    squares.forEach(function (square, index) {
        square.addEventListener('click', function (evt) {
            mySquare.handleClick.call(square, greenSquares)
        })
        square.setAttribute('data-id', String(index))
    
        if (greenSquares.includes(String(index))) {
            square.classList.add('green')
        } else {
            square.classList.add('yellow')
        }
    })

    //
    
    const targetIndex = mySquare.highlightSquare()
    mySquare.colorSquare(targetIndex, squares)
})
