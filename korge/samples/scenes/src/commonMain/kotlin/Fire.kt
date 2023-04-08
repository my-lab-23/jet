import com.soywiz.klock.*
import com.soywiz.korge.view.*
import com.soywiz.korim.color.*

class Fire : Container() {

    fun fire(x: Double,
             y: Double,
             myRotation: Int,
             enemySpaceShips: MutableList<SpaceShip>) {

        var xF = x
        var yF = y

        when(myRotation) {

            180 -> { xF -= 40; yF -= 10 }
            0 -> xF += 40
            -90 -> yF -= 40
            90 -> { yF += 40; xF -= 10 }
        }

        val fire = solidRect(10, 10, Colors.RED).position(xF, yF)

        var isRunning = true

        fire.addFixedUpdater(2.timesPerSecond) {
            if (!isRunning) return@addFixedUpdater

            //

            enemySpaceShips.forEach { enemySpaceShip ->

                val obj1Bounds = fire.globalBounds
                val obj2Bounds = enemySpaceShip.globalBounds

                if (obj1Bounds.intersects(obj2Bounds)) {

                    if(enemySpaceShip.isEnemy) { aliveEnemy-- }
                    else { showMsg("Hai perso!") }

                    //

                    if (aliveEnemy == 0) { showMsg("Hai vinto!") }

                    //

                    removeChild(fire)
                    enemySpaceShip.removeChildren()
                    enemySpaceShip.crashed = true
                    isRunning = false
                }
            }

            //

            when(myRotation) {

                180 -> xF -= 10
                0 -> xF += 10
                -90 -> yF -= 10
                90 -> yF += 10
            }

            fire.position(xF, yF)

            if (xF > 522 || xF < -12) {
                removeChild(fire)
                isRunning = false
            }

            if (yF > 522 || yF < -12) {
                removeChild(fire)
                isRunning = false
            }
        }
    }

    private fun showMsg(msg: String) { showDialog(msg) }

    private fun Container.showDialog(msg: String) {
        val dialogBox = DialogBox(200.0, 100.0, msg)
        addChild(dialogBox)
        dialogBox.position(100.0, 100.0)
    }
}
