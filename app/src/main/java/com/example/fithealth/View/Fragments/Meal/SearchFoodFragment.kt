package com.example.fithealth.View.Fragments.Meal

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fithealth.Model.DataClass.FoodApiResources.FoodNameSearch.Food
import com.example.fithealth.Model.Utils.ExtensionUtils.dissmissLoadingScreen
import com.example.fithealth.Model.Utils.ExtensionUtils.showLoadingScreen
import com.example.fithealth.Model.Utils.ExtensionUtils.toast
import com.example.fithealth.View.ReyclerAdapters.Meal.SearchFood.SearchFoodAdapter
import com.example.fithealth.ViewModel.FoodApi.FatSecretViewModel
import com.example.fithealth.ViewModel.FoodApi.FatSecretViewModelBuilder
import com.example.fithealth.databinding.FragmentSearchFoodBinding


class SearchFoodFragment : Fragment() {

    private var _binding: FragmentSearchFoodBinding? = null
    private val binding: FragmentSearchFoodBinding get() = _binding!!

    private val fatSecretViewModel: FatSecretViewModel by activityViewModels {
        FatSecretViewModelBuilder.getFatSecretViewModelFactory()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchFoodBinding.inflate(layoutInflater, container, false)
        setupUI()
        return binding.root
    }

    private fun setupUI() {
        setupRecyclers()
        setupOnClicks()
        setupObservers()
    }


    private fun setupRecyclers() {
        binding.apply {
            rvSearchFood.apply {
                layoutManager = LinearLayoutManager(requireContext())
                adapter = SearchFoodAdapter(
                    emptyList(),
                    onCheckAgreeMeal = ::onCheckAgreeMeal,
                    onUncheckDeleteMeal = ::onUncheckAgreeMeal,
                    shouldCheckFoodItem = ::shouldCheckFoodItem
                )
            }
        }
    }

    private fun onCheckAgreeMeal(food: Food) {
        addMeal(food)
    }

    private fun onUncheckAgreeMeal(food: Food) {
        removeMeal(food)
    }

    private fun shouldCheckFoodItem(food: Food): Boolean =
        fatSecretViewModel.isFoodInList(food)


    private fun removeMeal(food: Food) {
        fatSecretViewModel.removeAgreedFood(food)
    }

    private fun addMeal(food: Food) {
        fatSecretViewModel.addFoodToAgreeList(food)
    }

    private fun setupObservers() {
        fatSecretObservers()
    }

    private fun fatSecretObservers() {
        fatSecretViewModel.apply {
            foodListResult.observe(viewLifecycleOwner) { foodResultList ->
                updateFoodList(foodResultList)
            }

            loading.observe(viewLifecycleOwner) { loading ->
                if (loading) activity?.showLoadingScreen()
                else activity?.dissmissLoadingScreen()
            }

        }
    }

    private fun updateFoodList(foodList: List<Food>) {
        if (binding.rvSearchFood.adapter is SearchFoodAdapter) {
            val adapter = binding.rvSearchFood.adapter as SearchFoodAdapter
            adapter.updateList(foodList)
        } else toast("Algo salio mal intentalo mas tarde")
        changeNoElementVisibility(foodList.isEmpty())
    }


    private fun changeNoElementVisibility(empty: Boolean) {
        binding.tvNoElements.visibility = if (empty) View.VISIBLE else View.GONE
    }

    private fun setupOnClicks() {
        binding.apply {
            btnSearchFood.setOnClickListener {
                val searchText = binding.etFoodSearch.text.toString().trim()
                searchFood(searchText)
            }
        }
    }

    private fun searchFood(searchText: String) {
        fatSecretViewModel.searchFood(foodName = searchText)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}