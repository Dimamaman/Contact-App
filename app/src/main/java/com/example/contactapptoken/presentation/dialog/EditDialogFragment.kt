package com.example.contactapptoken.presentation.dialog

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.FrameLayout
import androidx.fragment.app.DialogFragment
import com.example.contactapptoken.R

class EditDialogFragment(
    private val oldFirstName: String,
    private val oldLastName: String,
    private val oldPhone: String,
): DialogFragment(R.layout.edit_dialog) {

    private var editContactClick: ((String,String,String) -> Unit)? = null
    fun setEditContactClick(block: (String,String,String) -> Unit) {
        editContactClick = block
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_FRAME, R.style.DialogStyle)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val inputFirstName : EditText = view.findViewById(R.id.firstName)
        val inputLastName : EditText = view.findViewById(R.id.lastName)
        val inputPhone : EditText = view.findViewById(R.id.number)
        val btnSave : FrameLayout = view.findViewById(R.id.save)
        val btnClose : FrameLayout = view.findViewById(R.id.close)

        inputFirstName.setText(oldFirstName)
        inputLastName.setText(oldLastName)
        inputPhone.setText(oldPhone)

        btnSave.setOnClickListener {
            if (inputFirstName.text.length < 3 || inputLastName.text.length < 3 || !inputPhone.text.startsWith("+998")) {
                return@setOnClickListener
            } else if (inputPhone.text.length < 13) {
                return@setOnClickListener
            } else {
                editContactClick?.invoke(inputFirstName.text.toString(),inputLastName.text.toString(),inputPhone.text.toString())
            }
            dismiss()
        }

        btnClose.setOnClickListener { dismiss() }
    }
}