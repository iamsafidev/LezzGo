package com.confiz.lezzgo.utils

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.confiz.lezzgo.R

fun Context.showFigFolderAlertDialog(
    title: String? = null,
    message: String?,
    onClick: (alert: AlertDialog) -> Unit = {
        it.dismiss()
    }
) {
    val builder = AlertDialog.Builder(this)
    var alertDialog: AlertDialog? = null
    val inflater = this.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    val dialogLayout: View =
        inflater.inflate(R.layout.alert_dialogue, null)
    title?.let {
        builder.setTitle(title)
        dialogLayout.findViewById<TextView>(R.id.tvDialogTitle).apply {
            visibility = View.VISIBLE
            text = it
        }
    }
    val label = dialogLayout.findViewById<TextView>(R.id.tvTitle)
    label.text = message
    val ok = dialogLayout.findViewById<TextView>(R.id.tvButtonTitle)
    ok.text = getString(R.string.ok)
    ok.setOnClickListener {
        alertDialog?.let { dialog -> onClick(dialog) }
    }

    builder.setView(dialogLayout)
    builder.setCancelable(false)
    alertDialog = builder.create()

    alertDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
    alertDialog.show()

}