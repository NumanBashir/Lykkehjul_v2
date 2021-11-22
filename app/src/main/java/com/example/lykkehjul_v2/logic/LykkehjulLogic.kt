package com.example.lykkehjul_v2.logic

object LykkehjulLogic {

    fun guessLetter(ord: String, hemmeligtOrd: String, bogstav: String): CharArray {

        val ordArray: CharArray = ord.toCharArray()

        if (validateGætBogstavLength(bogstav)) {
            var index = 0

            // Lav funktion for hvad der sker i løkken
            while (index < hemmeligtOrd.length) {
                if (hemmeligtOrd.get(index) == bogstav.get(0)) {
                    ordArray[index] = bogstav.get(0)
                }
                index++
            }
        }

        return ordArray
    }

    fun validateGætBogstavLength(bogstav: String): Boolean {
        return bogstav.length > 0
    }

    fun erBogstavIHemmeligOrd(hemmeligtOrd: String, bogstav: String): Boolean {
        return hemmeligtOrd.contains(bogstav)
    }

    fun fårAntalDuplikeretBogstav(ord: String, bogstav: String): Int {
        var count = 0
        for (i in ord) {
            if(i == bogstav.get(0)) {
                count++
            }
        }
        return count
    }

}
