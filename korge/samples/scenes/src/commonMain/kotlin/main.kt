import com.soywiz.korge.Korge
import com.soywiz.korge.input.*
import com.soywiz.korge.scene.*
import com.soywiz.korge.view.*
import com.soywiz.korim.color.Colors
import com.soywiz.korinject.AsyncInjector
import com.soywiz.korma.geom.Anchor
import com.soywiz.korma.geom.ScaleMode
import com.soywiz.korma.geom.SizeInt
import kotlin.reflect.KClass

suspend fun main() = Korge(Korge.Config(module = MyModule))

object MyModule : Module() {

    override val size = SizeInt(512, 512)
    override val windowSize = SizeInt(512, 512)

    override val scaleAnchor = Anchor.MIDDLE_CENTER
    override val scaleMode = ScaleMode.SHOW_ALL
    override val clipBorders = true

	override val mainScene: KClass<out Scene> = MyScene1::class

	override suspend fun AsyncInjector.configure() {
		mapInstance(MyDependency(""))
		mapPrototype { MyScene1(get()) }
		mapPrototype { MyScene2(get()) }
	}
}

class MyDependency(val value: String)

const val MARGIN = 20

class MyScene1(private val myDependency: MyDependency) : Scene() {
    override suspend fun Container.sceneInit() {
        val mainText = text("Cliccare il quadrato " +
                "per iniziare la partita.", 24.0) {
            smoothing = false
            position(MARGIN, MARGIN)
        }

        text("\nDurante la partita premere N " +
                "per tornare alla schermata iniziale.\n\n" +
                "Ogni restart aumenta di 4 unità le navi nemiche (max 32).\n\n" +
                "Per ricominciare da 4 ricaricare il sito.\n\n" +
                "La nave si muove con i cursori e spara\n" +
                "con la barra spaziatrice.", 18.0) {
            alignTopToBottomOf(mainText, 10)
            positionX(MARGIN)
        }

        solidRect(100, 100, Colors.DARKCYAN) {
            position(300, 300)
            onClick {
                sceneContainer.changeTo<MyScene2>()
                println(enemy)
                if(enemy<=28) enemy += 4
            }
        }
    }
}
