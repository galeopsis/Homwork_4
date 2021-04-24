package com.galeopsis.myfirstmvvmapplication.ui.activity

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.galeopsis.myfirstmvvmapplication.R
import com.galeopsis.myfirstmvvmapplication.databinding.ActivityMainBinding
import com.galeopsis.myfirstmvvmapplication.ui.ViewEffect
import com.galeopsis.myfirstmvvmapplication.ui.ViewEvent
import com.galeopsis.myfirstmvvmapplication.vm.MainViewModel
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.expressionEditText.showSoftInputOnFocus = false
        binding.expressionEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                binding.expressionEditText.setSelection(binding.expressionEditText.text.toString().length)
            }
        })

        initListeners()
        initObservers()
    }

    private fun initListeners() {
        with(binding) {
            btn0.setOnClickListener { viewModel.event(ViewEvent.DigitClick("0")) }
            btn1.setOnClickListener { viewModel.event(ViewEvent.DigitClick("1")) }
            btn2.setOnClickListener { viewModel.event(ViewEvent.DigitClick("2")) }
            btn3.setOnClickListener { viewModel.event(ViewEvent.DigitClick("3")) }
            btn4.setOnClickListener { viewModel.event(ViewEvent.DigitClick("4")) }
            btn5.setOnClickListener { viewModel.event(ViewEvent.DigitClick("5")) }
            btn6.setOnClickListener { viewModel.event(ViewEvent.DigitClick("6")) }
            btn7.setOnClickListener { viewModel.event(ViewEvent.DigitClick("7")) }
            btn8.setOnClickListener { viewModel.event(ViewEvent.DigitClick("8")) }
            btn9.setOnClickListener { viewModel.event(ViewEvent.DigitClick("9")) }
            btnBack.setOnClickListener { viewModel.event(ViewEvent.EraseClick) }
            btnAC.setOnClickListener { viewModel.event(ViewEvent.ACClick) }
            btnResult.setOnClickListener { viewModel.event(ViewEvent.EqualClick) }
            btnDot.setOnClickListener { viewModel.event(ViewEvent.CommaClick) }
            btnLeftBracket.setOnClickListener { viewModel.event(ViewEvent.LeftBracketClick) }
            btnRightBracket.setOnClickListener { viewModel.event(ViewEvent.RightBracketClick) }
            btnDivide.setOnClickListener { viewModel.event(ViewEvent.DivideClick) }
            btnMultiply.setOnClickListener { viewModel.event(ViewEvent.MultiplyClick) }
            btnMinus.setOnClickListener { viewModel.event(ViewEvent.MinusClick) }
            btnPlus.setOnClickListener { viewModel.event(ViewEvent.PlusClick) }
            btnSqrt.setOnClickListener { viewModel.event(ViewEvent.SqrtClick) }
            btnCos.setOnClickListener { viewModel.event(ViewEvent.CosClick) }
            btnSin.setOnClickListener { viewModel.event(ViewEvent.SinClick) }
            btnShutdown.setOnClickListener { exitProcess(0) }
        }
    }

    private fun initObservers() {
        viewModel.expression.observe(
            this,
            {
                binding.expressionEditText.setText(it)
            }
        )
        viewModel.answer.observe(
            this,
            {
                binding.tvOperation.text = it
            }
        )
        viewModel.viewEffect.observe(
            this,
            {
                trigger(it)
            }
        )
    }

    private fun trigger(effect: ViewEffect) {
        when (effect) {
            is ViewEffect.ShowToast -> {
                Toast.makeText(this, R.string.wrong_format, Toast.LENGTH_LONG).show()
            }
        }
    }
}