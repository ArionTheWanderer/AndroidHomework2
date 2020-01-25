package com.example.android.ui.report

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.android.R
import kotlinx.android.synthetic.main.fragment_report.*

class ReportFragment : Fragment() {
    private var listener: OnButtonClick? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_report, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toNextFragment.setOnClickListener {
            listener?.onReportButtonClick()
        }
    }

    interface OnButtonClick {
        fun onReportButtonClick()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnButtonClick) {
            listener = context
        } else {
            throw RuntimeException("$context must implement onHallButtonClick")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }
}