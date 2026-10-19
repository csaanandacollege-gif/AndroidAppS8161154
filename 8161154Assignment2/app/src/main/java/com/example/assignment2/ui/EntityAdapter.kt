package com.example.assignment2.ui
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.assignment2.R
import com.example.assignment2.data.Entity
class EntityAdapter(private val onClick: (Entity) -> Unit) : RecyclerView.Adapter<EntityAdapter.EntityViewHolder>() {
 private var entities: List<Entity> = emptyList()
 fun setData(items: List<Entity>) { entities = items; notifyDataSetChanged() }
 override fun getItemCount(): Int = entities.size
 override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EntityViewHolder =
  EntityViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_entity, parent, false))
 override fun onBindViewHolder(holder: EntityViewHolder, position: Int) { holder.bind(entities[position]) }
 inner class EntityViewHolder(view: View) : RecyclerView.ViewHolder(view) {
  fun bind(entity: Entity) {
   itemView.findViewById<TextView>(R.id.entitySummary).text = entity.summary()
   itemView.setOnClickListener { onClick(entity) }
  }
 }
}
