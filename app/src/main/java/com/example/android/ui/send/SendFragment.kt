package com.example.android.ui.send

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.android.R
import kotlinx.android.synthetic.main.fragment_send.*

class SendFragment : Fragment() {
    private var listener: onButtonClick? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_send, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        send_report.setOnClickListener {
            listener?.onSendButtonClick(
                et_report.text.toString(),
                et_author.text.toString(),
                et_email.text.toString()
            )
        }
    }

    interface onButtonClick {
        fun onSendButtonClick(report: String, author: String, email: String)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is onButtonClick) {
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