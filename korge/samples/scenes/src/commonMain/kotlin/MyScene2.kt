import com.soywiz.klock.timesPerSecond
import com.soywiz.korev.Key
import com.soywiz.korge.input.keys
import com.soywiz.korge.scene.Scene
import com.soywiz.korge.view.Container
import com.soywiz.korge.view.addFixedUpdater
import com.soywiz.korim.color.Colors
import kotlin.random.*

var enemy = 4
var aliveEnemy = enemy

class MyScene2(private val myDependency: MyDependency) : Scene() {

	override suspend fun Container.sceneInit() {

		val spaceShip = SpaceShip(false)
		spaceShip.drawSpaceShip(Colors.DARKCYAN)
		spaceShip.moveSpaceShipFixed(256.0, 256.0)
		addChild(spaceShip)

		val fire = Fire()
		addChild(fire)

		val enemySpaceShips = mutableListOf<SpaceShip>()

		repeat(enemy) {

			val enemySpaceShip = SpaceShip(true)
			enemySpaceShips.add(enemySpaceShip)
			addChild(enemySpaceShip)
			enemySpaceShip.drawSpaceShip(Colors.GOLDENROD)
			enemySpaceShip.moveSpaceShipFixed(randomCoord(), randomCoord())
			enemySpaceShip.autoMove()

			//

			var isRunning = true

			fire.addFixedUpdater(0.25.timesPerSecond) {
				if (!isRunning) return@addFixedUpdater

				fire.fire(
					enemySpaceShip.x,
					enemySpaceShip.y,
					enemySpaceShip.myRotation,
					mutableListOf(spaceShip))

				if (enemySpaceShip.crashed) {
					isRunning = false
				}
			}

			//
		}

		keys {
			down {
				when (it.key) {
					Key.LEFT -> spaceShip.moveSpaceShip(Direction.LEFT)
					Key.RIGHT -> spaceShip.moveSpaceShip(Direction.RIGHT)
					Key.UP -> spaceShip.moveSpaceShip(Direction.TOP)
					Key.DOWN -> spaceShip.moveSpaceShip(Direction.BOTTOM)
					Key.SPACE -> {

						if(!spaceShip.crashed) {

							fire.fire(
								spaceShip.x,
								spaceShip.y,
								spaceShip.myRotation,
								enemySpaceShips
							)
						}
					}
					Key.N -> { sceneContainer.changeTo<MyScene1>() }
					else -> Unit
				}
			}
		}
	}
}

enum class Direction {
    LEFT, RIGHT, TOP, BOTTOM
}

fun randomDirection(): Direction {
    val directions = Direction.values()
    val randomIndex = Random.nextInt(directions.size)
    return directions[randomIndex]
}

fun randomCoord(): Double {
    return Random.nextDouble(20.0, 492.0)
}
