package com.example.fithealth.View.Fragments.Social

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fithealth.Model.DataClass.SearchUser
import com.example.fithealth.Model.Utils.ExtensionUtils.dissmissLoadingScreen
import com.example.fithealth.Model.Utils.ExtensionUtils.showLoadingScreen
import com.example.fithealth.Model.Utils.ExtensionUtils.toast
import com.example.fithealth.R
import com.example.fithealth.View.ReyclerAdapters.Social.MessageAdapter.BusquedaSocial.UserSearchAdapter
import com.example.fithealth.ViewModel.Auth.User.UserViewModel
import com.example.fithealth.ViewModel.Auth.User.UserViewModelBuilder
import com.example.fithealth.databinding.FragmentSearchUsersBinding

class SearchUsersFragment : Fragment() {


    private var _binding: FragmentSearchUsersBinding? = null
    private val binding get() = _binding!!

    private val userViewModel: UserViewModel by viewModels {
        UserViewModelBuilder.getUserViewModelFactory()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchUsersBinding.inflate(inflater, container, false)

        setupUI()

        return binding.root
    }

    private fun setupUI() {
        setupRecyclerView()
        setupOnClicks()
        setupObversers()
    }

    private fun setupRecyclerView() {
        binding.apply {
            rvListaUsuarios.let { recycler ->
                recycler.layoutManager = LinearLayoutManager(context)

                recycler.adapter = UserSearchAdapter(emptyList()) {

                }
            }

        }
    }

    private fun setupObversers() {
        userViewModel.apply {

            isLoading.observe(viewLifecycleOwner) { isLoading ->
                if (isLoading) requireActivity().showLoadingScreen() else requireActivity().dissmissLoadingScreen()
            }


            userListByName.observe(viewLifecycleOwner) { userList ->
                userList?.let { list ->
                    if (list.isEmpty()) toast("No existen usuarios")
                    else {
                        Log.i("userList", userList.toString())
                        updateSearchUserList(list)
                    }
                }
            }
        }

    }

    private fun updateSearchUserList(userList: List<SearchUser>) {
        val adapter = binding.rvListaUsuarios.adapter as UserSearchAdapter
        adapter.updateList(userList)
    }


    private fun setupOnClicks() {
        binding.apply {
            btnSearchUsers.setOnClickListener {
                searchUsersByName(etSearchUsers.text.toString())
            }
        }
    }

    private fun searchUsersByName(userName: String) {
        userViewModel.getUserByName(userName)
    }

    override fun onDestroyView() {
        binding.etSearchUsers.setText("")
        super.onDestroyView()
        userViewModel.clearSearchResults()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}