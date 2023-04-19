package com.example.contactapptoken.presentation.screen.home

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.contactapptoken.R
import com.example.contactapptoken.data.source.remote.request.AddContactRequest
import com.example.contactapptoken.data.source.remote.request.EditContactRequest
import com.example.contactapptoken.data.source.remote.response.AllContacts
import com.example.contactapptoken.databinding.BottomSheetDialogBinding
import com.example.contactapptoken.databinding.ScreenHomeBinding
import com.example.contactapptoken.presentation.adapter.HomeAdapter
import com.example.contactapptoken.presentation.dialog.AddContactDialog
import com.example.contactapptoken.presentation.dialog.EditDialogFragment
import com.example.contactapptoken.presentation.dialog.MyBottomSheetDialog
import com.example.contactapptoken.presentation.screen.home.viewmodel.HomeScreenRepository
import com.example.contactapptoken.presentation.screen.home.viewmodel.impl.HomeScreenRepositoryImpl
import com.example.contactapptoken.utils.myApply
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class HomeScreen : Fragment(R.layout.screen_home) {
    private val binding by viewBinding(ScreenHomeBinding::bind)
    private val homeAdapter = HomeAdapter()
    private val viewModel: HomeScreenRepository by viewModels<HomeScreenRepositoryImpl>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = binding.myApply {
        homeRecyclerView.adapter = homeAdapter

        viewModel.getContacts()

        viewModel.getContacts.observe(viewLifecycleOwner) {
            homeAdapter.submitList(it)
        }

        homeAdapter.setOnClick {
            val currentContact = AllContacts(it.id,it.firstName,it.lastName,it.phone)
            val bottomSheetDialog = MyBottomSheetDialog()

            bottomSheetDialog.setClickEditButtonListener {
                val editDialog = EditDialogFragment(currentContact.firstName,currentContact.lastName,currentContact.phone)
                bottomSheetDialog.dismiss()

                editDialog.setEditContactClick { firstName, lastName, phone ->
                    val editContact = EditContactRequest(currentContact.id,firstName, lastName, phone)
                    viewModel.editContact(editContact)
                }
                editDialog.show(parentFragmentManager,"editDialog")
            }

            bottomSheetDialog.setClickDeleteButtonListener {
                viewModel.deleteNote(currentContact.id)
                bottomSheetDialog.dismiss()
            }

            bottomSheetDialog.show(parentFragmentManager, "BottomSheet")
        }

        addBtn.setOnClickListener {
            val addDialog = AddContactDialog()
            addDialog.setAddContactClickListener { firstName, lastName, phone ->
                Log.d("DDD","$firstName $lastName $phone")

                val contact = AddContactRequest(firstName, lastName, phone)
                viewModel.addContact(contact)
            }
            addDialog.show(parentFragmentManager,"AddContact")
        }

        viewModel.progressLoadingLivedata.observe(viewLifecycleOwner) {
            if (it) binding.progress.show()
            else binding.progress.hide()
        }

        viewModel.openLoginScreenLiveData.observe(viewLifecycleOwner) {
            findNavController().navigate(R.id.action_homeScreen_to_loginFragment)
        }

        binding.btnLogOut.setOnClickListener {
            viewModel.logOut()
        }

        binding.swiper.setOnRefreshListener {
            viewModel.getContacts()
            binding.swiper.isRefreshing = false
        }
    }
}