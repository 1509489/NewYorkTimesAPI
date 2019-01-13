package com.pixelart.newyorktimesapi.ui.homescreen.dialog

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.pixelart.newyorktimesapi.R
import kotlinx.android.synthetic.main.dialog_fragmen.view.*
import java.lang.ClassCastException

class FilterFragment: DialogFragment(){
    private lateinit var spinnerItems: Array<String>
    private lateinit var inputListener: OnInputListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        spinnerItems = resources.getStringArray(R.array.search_filter)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return if (container == null){
            val view = inflater.inflate(R.layout.dialog_fragmen, container, false)

            view.spinner.setItems(spinnerItems)
            view.tvButtonFilter.setOnClickListener {
                val selectedItemsList = view.spinner.selectedStrings
                var filtered = ""

                for (i in 0 until selectedItemsList.size){
                    filtered = selectedItemsList.joinToString("\" \"", "news_desk:(\"", "\")", -1, "")
                }
                if (filtered.isBlank()) {
                    Toast.makeText(activity, "Please Select category", Toast.LENGTH_LONG).show()
                    view.spinner.isFocused
                }else{
                    inputListener.sendData(filtered)

                    dialog?.dismiss()
                }
            }
            view.tvCancel.setOnClickListener { dialog?.cancel() }
            view
        } else
            super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            inputListener = activity as OnInputListener
        }catch (e: ClassCastException){
            e.printStackTrace()
        }
    }

    interface OnInputListener{
        fun sendData(input: String)
    }
}