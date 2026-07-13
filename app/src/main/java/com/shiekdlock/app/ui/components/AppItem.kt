package com.shiekdlock.app.ui.components

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.shiekdlock.app.model.AppUiState

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween


@Composable
fun AppItem(
    app: AppUiState,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {

    val cardColor = animateColorAsState(
    targetValue =
        if (checked)
            MaterialTheme.colorScheme.primaryContainer
        else
            MaterialTheme.colorScheme.surface,
    animationSpec = tween(300),
    label = "cardColor"
).value

 ///val cardElevation = animateDpAsState(
    ///targetValue = if (checked) 6.dp else 2.dp,
    ///animationSpec = tween(300),
    ///label = "cardElevation"
///).value

    Card(
        

        modifier = Modifier.fillMaxWidth(),

        shape = RoundedCornerShape(22.dp),

        colors = CardDefaults.cardColors(

            containerColor = cardColor

        ),

        ///elevation = CardDefaults.cardElevation(

            ///defaultElevation = cardElevation

       ///)

    ) {

        Row(

            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),

            verticalAlignment = Alignment.CenterVertically,

            horizontalArrangement = Arrangement.SpaceBetween

        ) {

            app.icon?.let {

                Image(

                    bitmap = drawableToBitmap(it).asImageBitmap(),

                    contentDescription = app.name,

                    modifier = Modifier.size(52.dp)

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

                    fontWeight = FontWeight.Bold,

                    maxLines = 1,

                    overflow = TextOverflow.Ellipsis

                )

                Text(

                    text = app.packageName,

                    style = MaterialTheme.typography.bodySmall,

                    color = MaterialTheme.colorScheme.onSurfaceVariant,

                    maxLines = 1,

                    overflow = TextOverflow.Ellipsis

                )

                Spacer(
                    modifier = Modifier.size(6.dp)
                )

                Text(

                    text =
                        if (checked)
                            "🛡 Protegida"
                        else
                            "Sin protección",

                    style = MaterialTheme.typography.labelMedium,

                    color =
                        if (checked)
                            MaterialTheme.colorScheme.primary
                        else
                            MaterialTheme.colorScheme.onSurfaceVariant,

                    fontWeight = FontWeight.SemiBold

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

        drawable.intrinsicWidth.coerceAtLeast(1),

        drawable.intrinsicHeight.coerceAtLeast(1),

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