package com.example.alunos.batalhanavalandroid

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import java.io.ObjectInputStream
import java.io.ObjectOutputStream

abstract class JogoActivity: AppCompatActivity() {

    val g = Global.getInstance()

    fun menssagemErroLoad(){

        runOnUiThread {
            val text = Toast.makeText(this, "O jogo ainda não começou.",
                    Toast.LENGTH_SHORT)
            text.show()
        }

    }

    fun loadArquivo(){
        try {
            val fileName = "save.ser"
            val fi = openFileInput(fileName)
            val oi = ObjectInputStream(fi)
            val save = oi.readObject() as Global
            oi.close()
            fi.close()

            g.humano = save.humano
            g.bot = save.bot
            g.ultimaActivityIntent = save.ultimaActivityIntent

            val derrotaHumano = g.humano.tabuleiro.todosNaviosDestruidos()
            val derrotaBot = g.bot.tabuleiro.todosNaviosDestruidos()

            if(derrotaHumano || derrotaBot){
                menssagemErroLoad()
            }
            else{
                startActivity(g.ultimaActivityIntent)
                finish()
            }
        }
        catch (e: Exception) {
            menssagemErroLoad()
        }
    }

    fun menssagemErroSave(){

        runOnUiThread {
            val text = Toast.makeText(this, "Não foi possível salvar o jogo.",
                    Toast.LENGTH_SHORT)
            text.show()
        }

    }

    fun salvarArquivo(){
        g.ultimaActivityIntent = intent

        try {
            val fileName = "save.ser"
            val f = openFileOutput(fileName, Context.MODE_PRIVATE)
            val o = ObjectOutputStream(f)
            o.writeObject(g)
            f.close()
            o.close()
        }
        catch (e: Exception) {
            menssagemErroSave()
        }
    }

}