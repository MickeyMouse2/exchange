package com.example.test.fragments

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import com.example.test.R
import com.example.test.base.BaseFragment
import com.example.test.databinding.ConverterFragmentBinding
import com.example.test.viewmodels.ConverterViewModel
import com.example.test.viewmodels.event.CoursesEvent
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentConverter : BaseFragment<ConverterFragmentBinding>(),
    AdapterView.OnItemClickListener {

    private val viewModel: ConverterViewModel by viewModels()

    var toConvert = "USD"
    var fromConvert = "USD"
    var eur = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ConverterFragmentBinding.inflate(inflater).also(attachViews).root
    }

    @SuppressLint("ResourceType")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.event.observe(viewLifecycleOwner, this::handleEvent)

        withViews {
            val feelings = resources.getStringArray(R.array.courses)
            val arrayAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, feelings)
            course.setAdapter(arrayAdapter)
            courseReceive.setAdapter(arrayAdapter)

            course.onItemClickListener = this@FragmentConverter
            courseReceive.onItemClickListener =
                AdapterView.OnItemClickListener { p0, p1, p2, p3 ->
                    p0?.let {
                        toConvert = p0.getItemAtPosition(p2).toString()
                        viewModel.getCourses(fromConvert, toConvert)
                    }
                }

            btnSubmit.setOnClickListener {
                val dialogBuilder = AlertDialog.Builder(requireContext())

                dialogBuilder.setTitle("Currency converted")
                    .setMessage("You have converted ${editSell.text} $fromConvert to ${textReceiveConvert.text} $toConvert. Commission Fee - 0.70 EUR")
                    .setCancelable(false)
                    .setPositiveButton("Done") { dialog, _ ->
                        dialog.cancel()
                    }

                val alert = dialogBuilder.create()
                alert.setTitle("AlertDialogExample")
                alert.show()
            }
        }

        viewModel.startCourseUpdate(fromConvert, toConvert)
    }

    private fun handleEvent(event: CoursesEvent) {
        when (event) {
            is CoursesEvent.GetCourse -> {
                event.rates.rates

                withViews {
                    eur = editSell.text.toString().toInt()

                    if(editSell.text.isNullOrBlank())
                        return@withViews

                    when (toConvert) {
                        "EUR" -> {
                            textReceiveConvert.text = String.format("%.3f", eur * event.rates.rates.eur)
                        }
                        "USD" -> {
                            textReceiveConvert.text = String.format("%.3f", eur * event.rates.rates.uSD)
                        }
                        "BGN" -> {
                            textReceiveConvert.text = String.format("%.3f", eur * event.rates.rates.gBP)
                        }
                    }
                }
            }
            is CoursesEvent.ShowError -> withViews {
                Toast.makeText(requireContext(), event.message, Toast.LENGTH_SHORT)
            }
        }
    }

    override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        p0?.let {
            fromConvert = p0.getItemAtPosition(p2).toString()
            viewModel.getCourses(fromConvert, toConvert)
        }
    }

}