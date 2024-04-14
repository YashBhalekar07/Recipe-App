//package com.example.recipeapp
//
//import android.content.Context
//import android.view.LayoutInflater
//import android.view.ViewGroup
//import androidx.recyclerview.widget.RecyclerView
//import androidx.recyclerview.widget.RecyclerView.ViewHolder
//import com.example.recipeapp.databinding.PopularRvItemBinding
//
//class PopularAdapter (var dataList:ArrayList<Recipe>,var context : Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
//
//    inner class viewHolding(var binding:PopularRvItemBinding):RecyclerView.ViewHolder(binding.root)
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
//        var binding:PopularRvItemBinding.inflate(LayoutInflater.from(context),parent,false)
//        return ViewHolder(binding)
//    }
//
//    override fun getItemCount(): Int {
//        return dataList.size
//    }
//
//    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        holder.binding.popularImg
//        holder.binding.popularTxt
//        holder.binding.popularTime
//
//    }
//}

package com.example.recipeapp

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.recipeapp.databinding.PopularRvItemBinding

class PopularAdapter(private val dataList: ArrayList<Recipe>, private val context: Context) :
    RecyclerView.Adapter<PopularAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: PopularRvItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = PopularRvItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val recipe = dataList[position]
        with(holder.binding) {

            //popularImg .setImageResource(recipe.imageResource)
            Glide.with(context).load(dataList.get(position).img).into(holder.binding.popularImg)

            popularTxt.text = dataList.get(position).tittle  //recipe.title

            var time =
                dataList.get(position).ing.split("\n".toRegex()).dropLastWhile { it.isEmpty() }
                    .toTypedArray()
            popularTime.text = time.get(0)     //recipe.preparationTime

        }
        holder.itemView.setOnClickListener{
            var intent = Intent(context,RecipeActivity::class.java)
            intent.putExtra("img",dataList.get(position).img)
            intent.putExtra("tittle",dataList.get(position).tittle)
            intent.putExtra("des",dataList.get(position).des)
            intent.putExtra("ing",dataList.get(position).ing)
            intent.flags=Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(intent)
        }
    }
}
