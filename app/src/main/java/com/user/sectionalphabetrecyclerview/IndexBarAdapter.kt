package com.user.sectionalphabetrecyclerview

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class IndexBarAdapter(context: Context, data: List<DataModel>?) : RecyclerView.Adapter<IndexBarAdapter.ViewHolder>() {
    private val mInflater: LayoutInflater = LayoutInflater.from(context)
    private var mData: List<DataModel>?
    private val mContext: Context

    init {
        mData = data
        mContext = context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = mInflater.inflate(R.layout.item, parent, false)
        val viewHolder = ViewHolder(view)
        viewHolder.textHeader = view.findViewById(R.id.textHeader)
        viewHolder.textName = view.findViewById(R.id.textName)
        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val section = getSectionForPosition(position)
        if (position == getPositionForSection(section)) {
            holder.textHeader!!.visibility = View.VISIBLE
            holder.textHeader!!.text = mData!![position].header
        } else {
            holder.textHeader!!.visibility = View.GONE
        }
        if (mOnItemClickListener != null) {
            holder.itemView.setOnClickListener { mOnItemClickListener!!.onItemClick(holder.itemView, position) }
        }
        holder.textName!!.text = mData!![position].name
        holder.textName!!.setOnClickListener { Toast.makeText(mContext, mData!![position].name, Toast.LENGTH_SHORT).show() }
    }

    override fun getItemCount(): Int {
        return if (mData != null) mData!!.size else 0
    }

    fun getItem(position: Int): Any {
        return mData!![position]
    }

    /**
     * itemClick
     */
    private var mOnItemClickListener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun onItemClick(view: View?, position: Int)
    }

    fun setOnItemClickListener(mOnItemClickListener: OnItemClickListener?) {
        this.mOnItemClickListener = mOnItemClickListener
    }

    fun updateList(list: List<DataModel>?) {
        mData = list
        notifyDataSetChanged()
    }

    fun getSectionForPosition(position: Int): Int {
        return mData!![position].header!![0].toInt()
    }

    fun getPositionForSection(section: Int): Int {
        for (i in 0 until itemCount) {
            val sortStr = mData!![i].header
            val firstChar = sortStr!!.toUpperCase()[0]
            if (firstChar.toInt() == section) {
                return i
            }
        }
        return -1
    }

    class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
        var textHeader: TextView? = null
        var textName: TextView? = null
    }

}