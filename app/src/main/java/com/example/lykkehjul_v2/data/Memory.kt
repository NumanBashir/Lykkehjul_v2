package com.example.lykkehjul_v2.data

import com.example.lykkehjul_v2.model.Words

class Memory {

    fun loadAnimalsWords(): List<Words> {
        return listOf<Words>(
            Words("new zealand"),
            Words("saudi arabien"),
            Words("sri lanka"),
            Words("costa rica"),
            Words("sierra leone"),
            Words("danmark"),
            Words("pakistan"),
            Words("nepal"),
            Words("spanien"),
            Words("maldiverne"),
            Words("argentina"),
            Words("canada"),
            Words("egypten"),
            Words("england"),
            Words("ungarn"),

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
