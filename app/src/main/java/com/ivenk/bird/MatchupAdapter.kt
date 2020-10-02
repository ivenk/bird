package com.ivenk.bird

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.get
import androidx.recyclerview.widget.RecyclerView
import com.ivenk.bird.web.MatchupScrap

class MatchupAdapter(private val myDataset: List<MatchupScrap>) :
    RecyclerView.Adapter<MatchupAdapter.MyViewHolder>() {

    class MyViewHolder(val view: LinearLayout) : RecyclerView.ViewHolder(view)


    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): MyViewHolder {
        val linearLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.matchup_item, parent, false) as LinearLayout

        return MyViewHolder(linearLayout)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val nameView = holder.view.get(0) as TextView
        nameView.text = myDataset[position].champion

        val messageView = holder.view.get(1) as TextView
        messageView.text = myDataset[position].description
    }

    override fun getItemCount() = myDataset.size
}
