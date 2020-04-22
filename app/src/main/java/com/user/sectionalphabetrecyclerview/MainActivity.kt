package com.user.sectionalphabetrecyclerview

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.user.sectionalphabetrecyclerview.SideBar.OnTouchingLetterChangedListener
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import java.util.regex.Pattern

class MainActivity : AppCompatActivity() {
    var manager: LinearLayoutManager? = null
    private var adapter: IndexBarAdapter? = null
    private var sourceDataList: List<DataModel>? = null
    private var customComparator: CustomComparator? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()
    }

    private fun initViews() {
        customComparator = CustomComparator()
        sideBar.setTextView(dialog)
        sideBar.setOnTouchingLetterChangedListener(object : OnTouchingLetterChangedListener {
            override fun onTouchingLetterChanged(s: String?) {
                val position = adapter!!.getPositionForSection(s!![0].toInt())
                if (position != -1) {
                    manager?.scrollToPositionWithOffset(position, 0)
                }
            }
        })
        sideBar.setParentView(recyclerView)
        sourceDataList = filledData(resources.getStringArray(R.array.dataArray))
        //  Collections.sort(sourceDataList, customComparator)
        manager = LinearLayoutManager(this)
        manager!!.orientation = LinearLayoutManager.VERTICAL
        recyclerView.layoutManager = manager
        adapter = IndexBarAdapter(this, sourceDataList)
        recyclerView.adapter = adapter
        adapter!!.setOnItemClickListener(object : IndexBarAdapter.OnItemClickListener {
            override fun onItemClick(view: View?, position: Int) {
                Toast.makeText(this@MainActivity, (adapter!!.getItem(position) as DataModel).name, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun filledData(data: Array<String>): List<DataModel> {
        val mSortList: MutableList<DataModel> = ArrayList()
        for (i in data.indices) {
            val sortModel = DataModel()
            sortModel.name = data[i]
            val sortString = data[i].substring(0, 1).toUpperCase()
            if (sortString.matches("[A-Z]".toRegex())) {
                sortModel.header = sortString.toUpperCase()
            } else {
                sortModel.header = "#"
            }

            mSortList.add(sortModel)
        }
        mSortList.sortBy { it.name }
        checkOrderOfList(mSortList)
        return mSortList
    }

    private fun checkOrderOfList(sourceDataList: MutableList<DataModel>?): MutableList<DataModel>? {
        this.sourceDataList = sourceDataList
        val tempList: MutableList<DataModel> = ArrayList()
        val regex = Pattern.compile("[$&+,:;=\\\\?@#|/'<>.^*()%!-]")
        val litr = sourceDataList!!.iterator()
        while (litr.hasNext()) {
            val element = litr.next()
            if (regex.matcher(element.name!!.get(0).toString()).find() || CustomComparator.isNumber(element.name!!.get(0).toString())) {
                tempList.add(element)
                litr.remove()
            }
        }
        sourceDataList.addAll(tempList)
        return sourceDataList
    }
}