package test

import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.TestMethodOrder
import kotlin.test.Test
import kotlin.test.assertEquals

@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class AppTest {

    private var result: String? = ""
    private var expected: String = ""

    @Test
    @Order(0)
    fun testCreate0() {

        val myTest = MyTest()
        result = myTest.test(33, "Pollo", button = 'c')
        expected = "Data stored correctly : 201"
        assertEquals(expected, result)
    }

    @Test
    @Order(1)
    fun testCreate1() {

        val myTest = MyTest()
        result = myTest.test(33, "Pollo", button = 'c')
        expected = "Error: ID already in use : 409"
        assertEquals(expected, result)
    }

    @Test
    @Order(2)
    fun testRead0() {

        val myTest = MyTest()
        result = myTest.test(33, button = 'r')
        expected = "{ \"id\": 33, \"data\": \"Pollo\" } : 200"
        assertEquals(expected, result)
    }

    @Test
    @Order(3)
    fun testRead1() {

        val myTest = MyTest()
        result = myTest.test(34, button = 'r')
        expected = ": 200"
        assertEquals(expected, result)
    }

    @Test
    @Order(4)
    fun testUpdate0() {

        val myTest = MyTest()
        result = myTest.test(33, "Pollo arrosto", button = 'u')
        expected = "Data updated correctly : 200"
        assertEquals(expected, result)
    }

    @Test
    @Order(5)
    fun testUpdate1() {

        val myTest = MyTest()
        result = myTest.test(34, "Pollo arrosto", button = 'u')
        expected = "Error: Data with the provided ID not found : 404"
        assertEquals(expected, result)
    }

    @Test
    @Order(6)
    fun testDelete0() {

        val myTest = MyTest()
        result = myTest.test(33, button = 'd')
        expected = "Data deleted correctly : 200"
        assertEquals(expected, result)
    }

    @Test
    @Order(7)
    fun testDelete1() {

        val myTest = MyTest()
        result = myTest.test(34, button = 'd')
        expected = "Error: Data with the provided ID not found : 404"
        assertEquals(expected, result)
    }
}
