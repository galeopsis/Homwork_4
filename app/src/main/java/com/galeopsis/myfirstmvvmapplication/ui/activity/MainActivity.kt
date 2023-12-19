package com.galeopsis.myfirstmvvmapplication.ui.activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
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

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val viewModel: MainViewModel by viewModels()

    var ctx: Context? = null // Make it nullable

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        val bundle: Bundle? = intent.extras
        when (bundle?.getString("theme")) {
            "one" -> {
                setTheme(R.style.Theme_MyFirstMVVMApplication)
            }

            "two" -> {
                setTheme(R.style.Theme_MaterialComponents_Light)
            }
        }
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)


        setContentView(binding.root)

        ctx = this@MainActivity


        this.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;

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
        try {
            ctx = this@MainActivity
            binding.apply {
                arrayOf(
                    btn0,
                    btn1,
                    btn2,
                    btn3,
                    btn4,
                    btn5,
                    btn6,
                    btn7,
                    btn8,
                    btn9
                ).forEachIndexed { index, btn ->
                    // Check that btn is not null
                    btn.setOnClickListener { viewModel.event(ViewEvent.DigitClick(index.toString())) }
                }
                btnBack.setOnClickListener { viewModel.event(ViewEvent.EraseClick) }
                btnAC.setOnClickListener { viewModel.event(ViewEvent.ACClick) }
                linearLayout.setOnClickListener { viewModel.evaluateClipboardExpression(ctx) }
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
                btnRaise.setOnClickListener { viewModel.event(ViewEvent.RaiseClick) }
            }
        } catch (e: Exception) {
        }

    }

    private fun menuClick() {
        val intent = Intent(this, SetThemeActivity::class.java)
        startActivity(intent)
    }

    private fun initObservers() {
        viewModel.expression.observe(
            this
        ) {
            binding.expressionEditText.setText(it)
        }
        viewModel.answer.observe(
            this
        ) {
            binding.tvOperation.text = it
        }
        viewModel.viewEffect.observe(
            this
        ) {
            trigger(it)
        }
    }

    private fun trigger(effect: ViewEffect) {
        when (effect) {
            is ViewEffect.ShowToast -> {
                Toast.makeText(this, R.string.wrong_format, Toast.LENGTH_LONG).show()
            }
        }
    }
}