package com.user.sectionalphabetrecyclerview

import java.util.*

class CustomComparator : Comparator<DataModel> {
    override fun compare(o1: DataModel, o2: DataModel): Int {
        return if (o1.header == "@" || o2.header == "#") {
            -1
        } else if (o1.header == "#" || o2.header == "@") {
            1
        } else {
            o1.header!!.compareTo(o2.header!!)
        }
    }

    companion object {
        private fun isNumber(s: String): Boolean {
            return s.matches("[-+]?\\d*\\.?\\d+".toRegex())
        }

        private fun isThereAnyNumber(a: String, b: String): Boolean {
            return isNumber(a) || isNumber(b)
        }
    }
}