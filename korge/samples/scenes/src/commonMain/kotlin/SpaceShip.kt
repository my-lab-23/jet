import com.soywiz.klock.*
import com.soywiz.korge.view.*
import com.soywiz.korim.color.*
import com.soywiz.korma.geom.*
import kotlin.random.Random

class SpaceShip : Container() {

    private val spaceShip = Container()
    var myRotation = 0
    var crashed = false

    fun drawSpaceShip(color: RGBA) {

        spaceShip.apply {
            solidRect(10, 10, color).position(0, 0)
            solidRect(10, 10, color).position(0, -10)
            solidRect(10, 10, color).position(0, 10)
            solidRect(10, 10, color).position(10, 0)
            solidRect(10, 10, Colors.LIGHTGRAY).position(20, 0)
        }

        spaceShip.position(0, 0)

        addChild(spaceShip)
    }

    fun moveSpaceShip(direction: Direction) {

        when(direction) {

            Direction.LEFT -> { x -= 10 }
            Direction.RIGHT -> { x += 10 }
            Direction.TOP -> { y -= 10 }
            Direction.BOTTOM -> { y += 10 }
        }

        if(x>512) x = 0.0
        if(y>512) y = 0.0

        if(x<0) x = 512.0
        if(y<0) y = 512.0

        when(direction) {

            Direction.LEFT -> rotateSpaceShip(180)
            Direction.RIGHT -> rotateSpaceShip(0)
            Direction.TOP -> rotateSpaceShip(-90)
            Direction.BOTTOM -> rotateSpaceShip(90)
        }
    }

    fun moveSpaceShipFixed(x: Double, y: Double) {

        this.x = x
        this.y = y
    }

    fun autoMove() {

        var i: Int
        var direction = randomDirection()

        spaceShip.addFixedUpdater(1.timesPerSecond) {

            moveSpaceShip(direction)
            i = Random.nextInt(5)
            if(i==4)direction = randomDirection()
        }
    }

    private fun rotateSpaceShip(d: Int = 90) {

        myRotation = d
        spaceShip.rotation = d.degrees
    }
}
