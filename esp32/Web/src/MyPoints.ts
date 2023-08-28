export class MyPoints {
    
    x: number
    y: number
    timeStamp: bigint
    private static lastDisplayedData: string;

    constructor(x: number, y: number, timeStamp: bigint) {
        
        this.x = x
        this.y = y
        this.timeStamp = timeStamp
    }

    static parse(s: string): MyPoints {
        
        const regex = /Coord\(x=(-?\d+), y=(-?\d+), timeStamp=(\d+)\)/
        const match = s.match(regex)

        if (match) {
            const x = parseInt(match[1])
            const y = parseInt(match[2])
            const ts = BigInt(match[3])

            return new MyPoints(x, y, ts)
        
        } else { throw new Error("Invalid input string format"); }
    }

    static drawPoints(points: MyPoints[]): void {

        const cartesianPlane = document.getElementById('cartesian-plane')

        if (cartesianPlane) {

            cartesianPlane.innerHTML = ''

            points.forEach(point => {

                const div = document.createElement('div')
                const color = points.length % 2 === 0 ? 'green' : 'red'
                div.className = `point ${color}`
                console.log(div.className)
                div.style.left = `${point.x + 250}px`
                div.style.top = `${250 - point.y}px`
                cartesianPlane.appendChild(div)
            })
        }
    }

    static updateServerInput(input) {
        
        if (input !== this.lastDisplayedData) {
        
            const serverDataElement = document.getElementById('server-data')
            serverDataElement.insertAdjacentHTML('beforebegin', `<p>${input}</p>`)
            this.lastDisplayedData = input
        }

        this.lastDisplayedData = input
    }
}
