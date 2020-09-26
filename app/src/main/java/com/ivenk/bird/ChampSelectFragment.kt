package com.ivenk.bird

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.fragment.app.Fragment

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ChampSelectFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ChampSelectFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        println("ChampSelectFragment.onCreateView")

        if (container != null) {
            val arrayAdapter = ArrayAdapter(
                container.context,
                android.R.layout.select_dialog_item,
                arrayOf("One", "Two", "Three")
            )

            println("Adapter was set")

            val autoCompleteTextView = container.findViewById<AutoCompleteTextView>(R.id.champ_select_dropdown)
            autoCompleteTextView.threshold = 1
            autoCompleteTextView.setAdapter(arrayAdapter)
        }


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.champ_select, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance() = ChampSelectFragment()
    }
}