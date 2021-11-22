package com.example.lykkehjultest.data

import com.example.lykkehjultest.model.Words

class Memory {

    fun loadWords(): List<Words> {
        return listOf<Words>(
            //Words("kotlin"),
            //Words("java"),
            //Words("python"),
            //Words("swift")
            Words("tiger"),
            Words("fulg"),
            Words("bjørn"),
            Words("krokodille"),
            Words("slange"),
            Words("edderkop"),
            //Words("jeg elsker kotlin"),
            //Words("mit navn er egon"),
            //Words("tesla model s"),
            //Words("mercedes amg"),
            //Words("audi etron"),
            //Words("bugatti"),


            )

    }

    fun loadWheel(): List<Words> {
        return listOf<Words>(
            Words("1.000kr"),
            Words("1.000kr"),
            Words("2.500kr"),
            Words("2.500kr"),
            Words("5.000kr"),
            Words("5.000kr"),
            Words("10.000kr"),
            Words("10.000kr"),
            Words("500kr"),
            Words("500kr"),
            Words("10kr"),
            Words("Tabt Tur"),
            Words("Ekstra Tur"),
            Words("Fallit"),

            )
    }

}
