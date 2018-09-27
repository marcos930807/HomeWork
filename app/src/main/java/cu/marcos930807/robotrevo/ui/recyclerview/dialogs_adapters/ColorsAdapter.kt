package cu.marcos930807.robotrevo.ui.recyclerview.dialogs_adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cu.marcos930807.robotrevo.R
import io.multimoon.colorful.ThemeColor
import kotlinx.android.synthetic.main.item_color.view.*
import org.jetbrains.anko.backgroundColor


class ColorsAdapter(val options: Array<ThemeColor>, val itemClick: (ThemeColor) -> Unit): RecyclerView.Adapter<ColorsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_color, parent, false)
        return ViewHolder(view, itemClick)
    }

    override fun getItemCount(): Int {
        return options.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindOptions(options[position])
    }




    class ViewHolder(view: View, private val itemClick: (ThemeColor) -> Unit) : RecyclerView.ViewHolder(view) {


        fun bindOptions(option:ThemeColor){

            with(option){
                itemView.setOnClickListener { itemClick(this) }


                itemView.icon.backgroundColor = option.getColorPack().normal().asInt()
                itemView.title.setText(option.name)
            }

         }


    }



}