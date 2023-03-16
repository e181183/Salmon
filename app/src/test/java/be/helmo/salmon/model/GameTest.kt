package be.helmo.salmon.model

import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import be.helmo.salmon.model.Game
import org.junit.Test
import org.junit.Assert.*
import org.mockito.Mockito

internal class GameTest {

    var gameLevel1 = Game(1,0,3)
    var gameLevel5 = Game(5,250,2)
    var gameLevel7 = Game(7,370,1)

    @Test
    fun getNiveau() {
        assertEquals(gameLevel1.getNiveau(), 1)
        assertEquals(gameLevel5.getNiveau(),5)
        assertEquals(gameLevel7.getNiveau(),7)
    }

    @Test
    fun getSc() {
        assertEquals(gameLevel1.getSc(), 0)
        assertEquals(gameLevel5.getSc(), 250)
        assertEquals(gameLevel7.getSc(), 370)
    }

    @Test
    fun getVies() {
        assertEquals(gameLevel1.getVies(), 3)
        assertEquals(gameLevel5.getVies(), 2)
        assertEquals(gameLevel7.getVies(), 1)
    }

    @Test
    fun getSeq_setSeq() {
        assertEquals(gameLevel1.getSeq(), "")
        assertEquals(gameLevel5.getSeq(), "")
        assertEquals(gameLevel7.getSeq(), "")

        gameLevel1.setSeq("2")
        gameLevel5.setSeq("21132")
        gameLevel7.setSeq("2113241")

        assertEquals(gameLevel1.getSeq(), "2")
        assertEquals(gameLevel5.getSeq(), "21132")
        assertEquals(gameLevel7.getSeq(), "2113241")


    }


    @Test
    fun upgradeLevel() {
        gameLevel1.upgradeLevel()
        assertEquals(gameLevel1.getNiveau(), 2)
        gameLevel1.upgradeLevel()
        assertEquals(gameLevel1.getNiveau(), 3)

        gameLevel5.upgradeLevel()
        assertEquals(gameLevel5.getNiveau(), 6)
        gameLevel5.upgradeLevel()
        gameLevel5.upgradeLevel()
        assertEquals(gameLevel5.getNiveau(), 8)

        gameLevel7.upgradeLevel()
        gameLevel7.upgradeLevel()
        assertEquals(gameLevel7.getNiveau(), 9)

    }

    @Test
    fun downgradeLives() {
        gameLevel1.downgradeLives()
        assertEquals(gameLevel1.getVies(), 2)
        gameLevel1.downgradeLives()
        gameLevel1.downgradeLives()
        assertEquals(gameLevel1.getVies(),0)

        gameLevel5.downgradeLives()
        gameLevel7.downgradeLives()
        assertEquals(gameLevel5.getVies(), 1)
        assertEquals(gameLevel7.getVies(),0)

        gameLevel5.downgradeLives()
        assertEquals(gameLevel5.getVies(),0)
    }

    @Test
    fun upgradeScore() {

        gameLevel1.upgradeScore(60)
        assertEquals(gameLevel1.getSc(), 60)

        gameLevel1.upgradeScore(320)
        assertEquals(gameLevel1.getSc(), 380)

        gameLevel5.upgradeScore(60)
        assertEquals(gameLevel5.getSc(), 310)

        gameLevel5.upgradeScore(20)
        assertEquals(gameLevel5.getSc(), 330)

        gameLevel7.upgradeScore(30)
        assertEquals(gameLevel7.getSc(), 400)


    }
}