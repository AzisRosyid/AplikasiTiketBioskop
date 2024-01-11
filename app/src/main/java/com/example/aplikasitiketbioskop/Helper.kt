package com.example.aplikasitiketbioskop

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.ContentResolver
import android.content.ContentUris
import android.content.Context
import android.net.Uri
import android.os.Environment
import android.os.Handler
import android.os.Looper
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.view.View
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.DatePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.aplikasitiketbioskop.model.Seat
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okio.BufferedSink
import java.io.File
import java.io.FileInputStream
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object Helper {
    var id: Int = 0
    var name: String = ""
    var username: String = ""
    var email: String = ""
    var token: String = ""

    val BASE_IMAGE = "http://192.168.21.1/Bioskop API/bioskop_api/public/images/"
    val BASE_URL = "http://192.168.21.1/Bioskop API/bioskop_api/public/api/"
    val DATE_PATTERN = "EEEE, dd MMMM yyyy"

    fun message(message: String, activity: Activity, action: Boolean = false) {
        val alertDialog = AlertDialog.Builder(activity)
            .setTitle("Message")
            .setMessage(message)
            .setPositiveButton("Ok") { dialog, which ->
                if (action) {
                    activity.finish()
                }
            }
        alertDialog.show()
    }

    fun currencyFormat(price: Double): String {
        return NumberFormat.getCurrencyInstance(Locale("id", "ID")).format(price)
    }
}
