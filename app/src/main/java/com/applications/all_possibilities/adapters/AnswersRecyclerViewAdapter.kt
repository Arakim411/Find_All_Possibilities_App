package com.applications.all_possibilities.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.applications.all_possibilities.Data.AnswersRecyclerViewItem
import com.applications.all_possibilities.R
import kotlinx.android.synthetic.main.answer_recycler_view_item.view.*

class AnswersRecyclerViewAdapter: RecyclerView.Adapter<AnswersRecyclerViewAdapter.MyViewHolder>() {

   private var items = ArrayList<AnswersRecyclerViewItem>()
   private var showAnimation = true


     class MyViewHolder(val view:View): RecyclerView.ViewHolder(view){


            fun startShowAnim(){
                view.animation = AnimationUtils.loadAnimation(view.context, R.anim.answer_item_add_anim)
                view.isGoodAnswerImage.animation = AnimationUtils.loadAnimation(view.context, R.anim.show_anim)

                view.isGoodAnswerImage.animation.start()
                view.animation.start()
            }

         fun bind(item: AnswersRecyclerViewItem, index: Int){
            view.value1.text = item.value1
             view.sign1.text = item.sign1String
             view.value2.text = item.value2
             view.sign2.text = item.sign2String
             view.value3.text = item.value3
             view.result.text = item.result
             view.index.text = index.toString()

             if(item.isGoodAnswer)
                 view.isGoodAnswerImage.setImageResource(R.drawable.ic_good_answer)
             else
                 view.isGoodAnswerImage.setImageResource(R.drawable.ic_bad_answer)

         }
     }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.answer_recycler_view_item,parent,false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = items[position]
        val index = items.size - position
        holder.bind(currentItem,index)


        if(position == 0 && showAnimation) {
            holder.startShowAnim()

        }

    }

    override fun getItemCount(): Int = items.size



    fun setAnswers(answers: ArrayList<AnswersRecyclerViewItem>){
        answers.reverse()
        this.items = answers
        notifyDataSetChanged()
    }

}