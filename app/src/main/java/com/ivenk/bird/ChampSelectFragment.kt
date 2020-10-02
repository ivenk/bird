package com.ivenk.bird

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.fragment.app.Fragment

/**
 * A simple [Fragment] subclass.
 * Use the [ChampSelectFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ChampSelectFragment : Fragment() {

    lateinit var selectionView : AutoCompleteTextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.champ_select, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val arrayAdapter = ArrayAdapter(
            view.context,
            android.R.layout.select_dialog_item,
            arrayOf("One", "Two", "Three")
        )

        selectionView = view.findViewById<AutoCompleteTextView>(R.id.champ_select_dropdown)
        selectionView.threshold = 0
        selectionView.setAdapter(arrayAdapter)
        //TODO: Configure dropdown to show immediately once the text field is selected
    }

    fun getSelectedChampion() : String = selectionView.text.toString()

    companion object {
        @JvmStatic
        fun newInstance() = ChampSelectFragment()
    }

}