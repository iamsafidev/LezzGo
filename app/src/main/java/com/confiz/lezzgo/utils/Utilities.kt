package com.confiz.lezzgo.utils

import android.app.AlertDialog
import android.content.Context
import android.graphics.drawable.Drawable
import android.icu.text.SimpleDateFormat
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.confiz.lezzgo.R
import java.util.*

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

@BindingAdapter("imageUrl", "holderImage")
fun loadImage(
    image: AppCompatImageView,
    url: String,
    placeHolder: Drawable
) { // This methods should not have any return type, = declaration would make it return that object declaration.
    if (!url.isNullOrEmpty()) {
        Glide.with(image.context).load(url).centerCrop()
            .placeholder(R.drawable.ic_logo_lezzgo)
            .into(image)
    } else {
        image.setImageDrawable(placeHolder)
    }
}


@RequiresApi(Build.VERSION_CODES.N)
fun getDate(seconds: Long, nanoseconds: Long): String? {
    val date = Date(seconds * 1000 + nanoseconds / 1000000)
    return SimpleDateFormat("MMMM dd, yyyy - hh:mm a", Locale.getDefault()).format(date)

}

@RequiresApi(Build.VERSION_CODES.N)
fun getDay(seconds: Long, nanoseconds: Long): String {
    val date = Date(seconds * 1000 + nanoseconds / 1000000)
    return SimpleDateFormat("EEEE", Locale.getDefault()).format(date).take(3)

}
@RequiresApi(Build.VERSION_CODES.N)
fun getMonthDate(seconds: Long, nanoseconds: Long): String {
    val date = Date(seconds * 1000 + nanoseconds / 1000000)
    return SimpleDateFormat("dd", Locale.getDefault()).format(date)

}

