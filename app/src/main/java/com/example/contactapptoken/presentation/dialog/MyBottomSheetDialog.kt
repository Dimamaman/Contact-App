package com.example.contactapptoken.presentation.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.LinearLayoutCompat
import com.example.contactapptoken.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class MyBottomSheetDialog: BottomSheetDialogFragment(R.layout.bottom_sheet_dialog) {

    private var clickEditButtonListener : (()-> Unit)? = null
    fun setClickEditButtonListener(block: () -> Unit) {
        clickEditButtonListener = block
    }

    private var clickDeleteButtonListener : (()-> Unit)? = null
    fun setClickDeleteButtonListener(block: () -> Unit) {
        clickDeleteButtonListener = block
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.bottom_sheet_dialog,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<LinearLayoutCompat>(R.id.lineEdit).setOnClickListener {
            clickEditButtonListener?.invoke()
        }

        view.findViewById<LinearLayoutCompat>(R.id.lineDelete).setOnClickListener {
            clickDeleteButtonListener?.invoke()
        }
    }
}