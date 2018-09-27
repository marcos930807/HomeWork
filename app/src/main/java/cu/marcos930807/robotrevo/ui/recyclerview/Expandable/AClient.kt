package cu.marcos930807.robotrevo.ui.recyclerview.Expandable

import com.chad.library.adapter.base.entity.AbstractExpandableItem
import com.chad.library.adapter.base.entity.MultiItemEntity
import cu.marcos930807.robotrevo.db.model.Client
import cu.marcos930807.robotrevo.db.model.Notice

class AClient(var client: Client) : AbstractExpandableItem<ANotice>(),MultiItemEntity {
    override fun getItemType(): Int {
        return ExpandableClientAdapter.TYPE_CLIENT
    }

    override fun getLevel(): Int {
        return 0
    }




}