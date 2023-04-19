package com.example.contactapptoken.presentation.dialog

import android.os.Bundle
import android.view.View
import androidx.fragment.app.DialogFragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.contactapptoken.R
import com.example.contactapptoken.databinding.DialogAddContactBinding

class AddContactDialog : DialogFragment(R.layout.dialog_add_contact) {

    private val binding by viewBinding(DialogAddContactBinding::bind)
    private var addContactClickListener: ((String, String, String) -> Unit)? = null
    fun setAddContactClickListener(block: (String, String, String) -> Unit) {
        addContactClickListener = block
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_FRAME, R.style.DialogStyle)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.close.setOnClickListener { dismiss() }

        binding.add.setOnClickListener {
            addContactClickListener?.invoke(
                binding.firstName.text.toString(),
                binding.lastName.text.toString(),
                binding.number.text.toString()
            )
            dismiss()
        }
    }
}