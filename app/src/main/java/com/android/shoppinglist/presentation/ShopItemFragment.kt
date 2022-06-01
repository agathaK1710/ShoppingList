package com.android.shoppinglist.presentation

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.android.shoppinglist.R
import com.android.shoppinglist.databinding.FragmentShopItemBinding
import com.android.shoppinglist.domain.ShopItem
import com.google.android.material.textfield.TextInputLayout
import javax.inject.Inject

class ShopItemFragment : Fragment() {

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[ShopItemViewModel::class.java]
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    val component by lazy{
        (requireActivity().application as ShopApp).component
    }

    private var shopItemId: Int = ShopItem.UNDEFIND_ID
    private var screenMode: String = MODE_UNKNOWN

    private var _binding: FragmentShopItemBinding? = null
    private val binding: FragmentShopItemBinding
    get() = _binding ?: throw RuntimeException("FragmentShopItemBinding is null")

    private lateinit var onFinishedEditingListener: OnFinishedEditingListener

    interface OnFinishedEditingListener {
        fun onFinishedEditingListener() {}
    }

    override fun onAttach(context: Context) {
        component.inject(this)
        super.onAttach(context)
        if (context is OnFinishedEditingListener) {
            onFinishedEditingListener = context
        } else {
            throw RuntimeException("OnFinoshedEditingListener is not implements")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentShopItemBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        parseParams()
        binding.shopItemViewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        addTextChangeListener()
        launchRightMode()
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.shouldCloseScreen.observe(viewLifecycleOwner) {
           onFinishedEditingListener.onFinishedEditingListener()
        }
    }

    private fun launchRightMode() {
        when (screenMode) {
            MODE_ADD -> launchAddScreen()
            MODE_EDIT -> launchEditScreen()
        }
    }

    private fun addTextChangeListener() {
        binding.etName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.resetErrorInputName()
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        })

        binding.etCount.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.resetErrorInputCount()
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        })
    }

    private fun launchAddScreen() {
        binding.saveButton.setOnClickListener {
            viewModel.addShopItem(binding.etName.text?.toString(), binding.etCount.text?.toString())
        }
    }

    private fun launchEditScreen() {
        viewModel.getShopItem(shopItemId)
        binding.saveButton.setOnClickListener {
            viewModel.editShopItem(binding.etName.text?.toString(), binding.etCount.text?.toString())
        }
    }

    private fun parseParams() {
        val args = requireArguments()
        if (!args.containsKey(EXTRA_SCREEN_MODE)) {
            throw RuntimeException("Param screen mode is absent!")
        }
        val mode = args.getString(EXTRA_SCREEN_MODE)
        if (mode != MODE_ADD && mode != MODE_EDIT) {
            throw RuntimeException("Unknown screen mode $mode!")
        }
        screenMode = mode
        if (screenMode == MODE_EDIT) {
            if (!args.containsKey(EXTRA_SHOP_ITEM_ID)) {
                throw RuntimeException("Param shopItem id is absent!")
            }
            shopItemId = args.getInt(EXTRA_SHOP_ITEM_ID, -1)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    companion object {
        private const val EXTRA_SCREEN_MODE = "extra_mode"
        private const val EXTRA_SHOP_ITEM_ID = "extra_shop_item_id"
        private const val MODE_EDIT = "mode_edit"
        private const val MODE_ADD = "mode_add"
        private const val MODE_UNKNOWN = ""

        fun newInstanceAddItem(): ShopItemFragment {
            return ShopItemFragment().apply {
                arguments = Bundle().apply {
                    putString(EXTRA_SCREEN_MODE, MODE_ADD)
                }
            }
        }

        fun newInstanceEditItem(shopItemId: Int): ShopItemFragment {
            return ShopItemFragment().apply {
                arguments = Bundle().apply {
                    putString(EXTRA_SCREEN_MODE, MODE_EDIT)
                    putInt(EXTRA_SHOP_ITEM_ID, shopItemId)
                }
            }
        }
    }
}