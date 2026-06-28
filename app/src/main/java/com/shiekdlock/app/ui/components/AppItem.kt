package com.shiekdlock.app.ui.components

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.shiekdlock.app.model.AppUiState

@Composable
fun AppItem(

    app: AppUiState,

    checked: Boolean,

    onCheckedChange: (Boolean) -> Unit

) {

    Card(

        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),

        shape = RoundedCornerShape(16.dp),

        elevation = CardDefaults.cardElevation(
            defaultElevation = 3.dp
        )

    ) {

        Row(

            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),

            verticalAlignment = Alignment.CenterVertically

        ) {

            app.icon?.let {

                Image(

                    bitmap = drawableToBitmap(it).asImageBitmap(),

                    contentDescription = app.name,

                    modifier = Modifier.size(48.dp)

                )

            }

            Spacer(
                modifier = Modifier.width(16.dp)
            )

            Column(
                modifier = Modifier.weight(1f)
            ) {

                Text(

                    text = app.name,

                    style = MaterialTheme.typography.titleMedium,

                    fontWeight = FontWeight.SemiBold

                )

                Text(

                    text = app.packageName,

                    style = MaterialTheme.typography.bodySmall

                )

            }

            Checkbox(

                checked = checked,

                onCheckedChange = onCheckedChange

            )

        }

    }

}

fun drawableToBitmap(
    drawable: Drawable
): Bitmap {

    val bitmap = Bitmap.createBitmap(
        drawable.intrinsicWidth,
        drawable.intrinsicHeight,
        Bitmap.Config.ARGB_8888
    )

    val canvas = Canvas(bitmap)

    drawable.setBounds(
        0,
        0,
        canvas.width,
        canvas.height
    )

    drawable.draw(canvas)

    return bitmap

}