

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import cu.marcos930807.robotrevo.R
import cu.marcos930807.robotrevo.db.mappers.getCategoryNamefromRevoid
import cu.marcos930807.robotrevo.db.model.Notice
import cu.marcos930807.robotrevo.ui.utils.getDark
import cu.marcos930807.robotrevo.ui.utils.mypref
import kotlinx.android.synthetic.main.item_data.*
import kotlinx.android.synthetic.main.item_data.view.*
import org.jetbrains.anko.backgroundDrawable
import org.jetbrains.anko.support.v4.act
import java.io.File


/**
 * Created by mtoranzot on 15/01/2018.
 */

class NoticeListAdapter(var NoticeList: MutableList<Notice>,
                        val itemClick: (Notice) -> Unit,
                        val itemLongClick: (Notice) -> Boolean) : RecyclerView.Adapter<NoticeListAdapter.ViewHolder>() {

    override fun getItemCount(): Int {
        return NoticeList.size
    }
    fun setData( NoticeList: MutableList<Notice>){
        this.NoticeList = NoticeList
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_data, parent, false)
        return ViewHolder(view, itemClick,itemLongClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindForecast(NoticeList[position])
    }


    class ViewHolder(view: View,
                     private val itemClick: (Notice) -> Unit,
                     private val itemLongClick: (Notice)-> Boolean
                    ) : RecyclerView.ViewHolder(view) {


        fun bindForecast(notice: Notice) {

            with(notice) {
                if(!photoa.isEmpty()){

                    Picasso.with(itemView.context).load(File(photoa)).into(itemView.icImage)
                }else{
                    itemView.icImage.setBackgroundResource(R.drawable.wall)
                }
//                if(getDark(mypref(itemView.context))){
//                    itemView.well.setBackgroundColor(itemView.context.resources.getColor(R.color.cardview_dark_background))
//                }
                if (!category.isEmpty()){
                    itemView.labelcategory.text = getCategoryNamefromRevoid(category.toInt())
                }

                itemView.dettitle.text = title.toString()
                itemView.debody.text = body.toString()
                itemView.deprice.text = price.toString()
//                itemView.minTemperature.text = "${low}º"
                itemView.setOnClickListener { itemClick(this) }
                itemView.setOnLongClickListener { itemLongClick(this) }
                }
        }
    }




}