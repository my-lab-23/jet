import { myClient } from "./MyClient.js"
import { MyPoints } from "./MyPoints.js"

const points = []

setInterval(() => {

    const response = myClient.fetch()

    response.then(response => {

        const parsed = MyPoints.parse(response)

        const x = parsed.x
        const y = parsed.y
        const tS = parsed.timeStamp

        const point = new MyPoints(x, y, tS)
        points.push(point)

        MyPoints.drawPoints(points)
        MyPoints.updateServerInput(response)
    })

}, 5000)
