package cu.marcos930807.robotrevo.ui.recyclerview

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.squareup.picasso.Picasso
import cu.marcos930807.robotrevo.R
import cu.marcos930807.robotrevo.R.id.photoa
import cu.marcos930807.robotrevo.db.mappers.getCategoryNamefromRevoid
import cu.marcos930807.robotrevo.db.model.Notice
import cu.marcos930807.robotrevo.ui.utils.BitmapfromPath
import org.jetbrains.anko.find
import java.io.File

class QuickAdapter(NoticeList: MutableList<Notice>) : BaseQuickAdapter<Notice, BaseViewHolder>(R.layout.item_data,NoticeList) {

    override fun convert(helper: BaseViewHolder, item: Notice) {


        helper.setText(R.id.dettitle,item.title)
        helper.setText(R.id.debody,item.body)
        helper.setText(R.id.deprice,item.price)
        if (!item.category.isEmpty()){
            helper.setText(R.id.labelcategory,getCategoryNamefromRevoid(item.category.toInt()))
        }
        if(!item.photoa.isEmpty()){
           Glide.with(helper.itemView.context).load(File(item.photoa)).into(helper.itemView.find<ImageView>(R.id.icImage))
         //  helper.setImageBitmap(R.id.icImage, BitmapfromPath(item.photoa))
        }else{
            helper.setImageResource(R.id.icImage, R.drawable.wall)
        }
//Aqui se puede establecer una condicion en base si se quiere que un hijo de un Notice especifico sea cliqueable o no en mi caso todos son clickeables
        helper.addOnClickListener(R.id.icImage)



    }


}