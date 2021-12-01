package com.example.lykkehjul_v2.view

import android.os.Bundle
import android.os.Handler
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lykkehjul_v2.R
import com.example.lykkehjul_v2.adapter.ItemAdapter
import com.example.lykkehjul_v2.data.Memory
import com.example.lykkehjul_v2.logic.LykkehjulLogic
import com.example.lykkehjul_v2.model.Words

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"



class playGame : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    lateinit var ord: RecyclerView
    lateinit var tastBogstav: Button
    lateinit var gætBogstav: EditText
    lateinit var drejHjulKnap: Button
    lateinit var wheelOutcomeDisplay: TextView
    lateinit var pointsNumber: TextView
    lateinit var antalLiv: TextView
    lateinit var nyRecyclerView: RecyclerView
    lateinit var OpenFragment: Button

    var lives = 5
    var points = 0
    var visHemmeligOrd = ""
    var underScoreOrd = mutableListOf<Words>()
    var secretOrd = ""
    var hemmeligtOrd = ""
    var drejEllerIndtastState = true
    var randomOutcome = ""


    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<ItemAdapter.ViewHolder>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_play_game, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        nyRecyclerView = view.findViewById(R.id.nyRecyclerView)

        layoutManager = LinearLayoutManager(activity)
        nyRecyclerView.layoutManager = layoutManager

        //adapter = ItemAdapter()
        nyRecyclerView.adapter = adapter

        //Ord fra textview
        ord = view.findViewById(R.id.nyRecyclerView)

        pointsNumber = view.findViewById(R.id.pointsNumber)
        antalLiv = view.findViewById(R.id.antalLiv)


        // Load words
        val myDataset = Memory().loadAnimalsWords()

        // Pick a random element from dataset
        hemmeligtOrd = myDataset.random().toString()


        Toast.makeText(context,"Drej venligst hjulet", Toast.LENGTH_SHORT).show()

        drejHjulKnap = view.findViewById(R.id.drejHjul)
        wheelOutcomeDisplay = view.findViewById(R.id.wheelOutcome)
        gætBogstav = view.findViewById(R.id.gætBogstav)
        tastBogstav = view.findViewById(R.id.tastBogstav)


        val charArray = hemmeligtOrd.toCharArray()

        val data: MutableList<Words> = ArrayList()
        for(i in charArray) {
            if(i.equals(' ')) {
                data.add(Words(" "))
            } else {
                data.add(Words("_"))
            }


        }

        underScoreOrd = data

        layoutManager = LinearLayoutManager(activity,LinearLayoutManager.HORIZONTAL, false)
        adapter = ItemAdapter(data)

        nyRecyclerView.layoutManager = layoutManager
        nyRecyclerView.setHasFixedSize(true)
        nyRecyclerView.adapter = adapter


        drejHjul()
        guessLetter()



    }

    private fun drejHjul() {
        // Spin wheel
        drejHjulKnap.setOnClickListener {

            if (drejEllerIndtastState) {

                // Get data for loadWheel
                val wheelOutcomes = Memory().loadWheel()
                // Set textView to random element from list
                randomOutcome = wheelOutcomes.random().toString()
                wheelOutcomeDisplay.setText(randomOutcome)

                // If spin wheel lands on any of the threes, then lives changes
                drejHjulStatement()

            } else {
                // If user has not entered a letter
                Toast.makeText(context,"Du har ikke indtastet et bogstav",Toast.LENGTH_SHORT).show()
            }
            tabtSpil()
            vundetSpil()
        }

    }

    private fun guessLetter() {
        tastBogstav.setOnClickListener {

            if (!drejEllerIndtastState) {
                if(TextUtils.isEmpty(gætBogstav.text.toString())) {
                    Toast.makeText(context,"Indtast et bogstav før du går videre",Toast.LENGTH_SHORT).show()
                } else {
                    val brugerIndtastet = gætBogstav.text.toString()
                    if (LykkehjulLogic.erBogstavIHemmeligOrd(hemmeligtOrd, brugerIndtastet)) {
                        calculatePoint(brugerIndtastet)
                        gætBogstav.setText("")

                        var setUnderscoreOrd = ""
                        for (words in underScoreOrd) {
                            setUnderscoreOrd += words
                        }
                        val charArray = LykkehjulLogic.guessLetter(setUnderscoreOrd, hemmeligtOrd, brugerIndtastet)

                        val data: MutableList<Words> = ArrayList()
                        for(i in charArray) {
                            data.add(Words("$i"))

                        }

                        underScoreOrd = data

                        layoutManager = LinearLayoutManager(activity,LinearLayoutManager.HORIZONTAL, false)
                        adapter = ItemAdapter(data)

                        if(!setUnderscoreOrd.contains("_")) {
                            Navigation.findNavController(requireView()).navigate(R.id.playGameToVundet)
                        }

                        nyRecyclerView.layoutManager = layoutManager
                        nyRecyclerView.setHasFixedSize(true)
                        nyRecyclerView.adapter = adapter

                        Toast.makeText(context,"Drej venligst hjulet",Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context,"Bostavet var der ikke, du mister et liv, drej hjulet igen ",Toast.LENGTH_LONG).show()
                        lives -= 1
                        antalLiv.setText(lives.toString())
                        gætBogstav.setText("")
                    }
                    drejEllerIndtastState = true
                }
            } else {
                Toast.makeText(context,"Du har ikke drejet hjulet endnu",Toast.LENGTH_SHORT).show()
            }


            tabtSpil()
            vundetSpil()

        }
    }

    private fun vundetSpil() {
        if (!underScoreOrd.contains(Words("_"))) {

            Navigation.findNavController(requireView()).navigate(R.id.playGameToVundet)
        }
    }

    private fun tabtSpil() {
        if (lives == 0) {

            Navigation.findNavController(requireView()).navigate(R.id.playGameToTabt)
        }
    }

    private fun drejHjulStatement() {
        if (randomOutcome.contains("Tabt Tur")) {
            Toast.makeText(
                context,
                "Du mistede et liv, drej hjulet igen",
                Toast.LENGTH_SHORT
            ).show()
            lives -= 1
            antalLiv.setText(lives.toString())
        } else if (randomOutcome.contains("Ekstra Tur")) {
            Toast.makeText(
                context,
                "Du fik et ekstra liv, drej hjulet igen",
                Toast.LENGTH_SHORT
            ).show()
            lives += 1
            antalLiv.setText(lives.toString())
        } else if (randomOutcome.contains("Fallit")) {
            Toast.makeText(context, "Du ramte fallit, du tabte", Toast.LENGTH_SHORT)
                .show()
            lives = 0
            antalLiv.setText(lives.toString())
        } else {
            // if not any of those, then change state
            Toast.makeText(context, "Indtast venligst bogstav", Toast.LENGTH_SHORT)
                .show()
            drejEllerIndtastState = false
        }
    }

    private fun calculatePoint(brugerIndtastet: String) {
        if (randomOutcome.contains("1.000kr")) {
            points += 1000 * LykkehjulLogic.fårAntalDuplikeretBogstav(hemmeligtOrd, brugerIndtastet)
            pointsNumber.setText(points.toString())
        } else if (randomOutcome.contains("2.500kr")) {
            points += 2500 * LykkehjulLogic.fårAntalDuplikeretBogstav(hemmeligtOrd, brugerIndtastet)
            pointsNumber.setText(points.toString())
        } else if (randomOutcome.contains("5.000kr")) {
            points += 5000 * LykkehjulLogic.fårAntalDuplikeretBogstav(hemmeligtOrd, brugerIndtastet)
            pointsNumber.setText(points.toString())
        } else if (randomOutcome.contains("10.000kr")) {
            points += 10000 * LykkehjulLogic.fårAntalDuplikeretBogstav(
                hemmeligtOrd,
                brugerIndtastet
            )
            pointsNumber.setText(points.toString())
        } else if (randomOutcome.contains("500kr")) {
            points += 500 * LykkehjulLogic.fårAntalDuplikeretBogstav(hemmeligtOrd, brugerIndtastet)
            pointsNumber.setText(points.toString())
        } else if (randomOutcome.contains("10kr")) {
            points += 10 * LykkehjulLogic.fårAntalDuplikeretBogstav(hemmeligtOrd, brugerIndtastet)
            pointsNumber.setText(points.toString())
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            playGame().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}