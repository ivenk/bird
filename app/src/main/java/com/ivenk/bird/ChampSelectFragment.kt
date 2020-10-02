package com.ivenk.bird

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.fragment.app.Fragment

/**
 * A simple [Fragment] subclass.
 * Use the [ChampSelectFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ChampSelectFragment : Fragment(), AdapterView.OnItemClickListener {
    var listener: ChampSelectListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as? ChampSelectListener
        if (listener == null) {
            throw ClassCastException("$context must implement OnArticleSelectedListener")
        }
    }

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

        val autoCompleteTextView = view.findViewById<AutoCompleteTextView>(R.id.champ_select_dropdown)
        autoCompleteTextView.threshold = 0
        autoCompleteTextView.setAdapter(arrayAdapter)
        autoCompleteTextView.onItemClickListener = this
        //TODO: Configure dropdown to show immediately once the text field is selected
    }



    interface ChampSelectListener {
        fun onChampSelected(champ: String)
    }

    companion object {
        @JvmStatic
        fun newInstance() = ChampSelectFragment()
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        println("ChampSelectFragment.onItemClick")
        listener?.onChampSelected("sth")
    }

}