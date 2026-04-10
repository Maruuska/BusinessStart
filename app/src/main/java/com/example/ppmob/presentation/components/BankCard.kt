package com.example.ppmob.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.example.ppmob.R
import com.example.ppmob.domain.model.Bank
import com.example.ppmob.ui.theme.ActiveBlue
import com.example.ppmob.ui.theme.NoActiveBlue
import com.example.ppmob.ui.theme.RadioCanadaRegular
import com.example.ppmob.ui.theme.RadioCanadaSemiBold

@Composable
fun BankCard(
    bank: Bank,
    modifier: Modifier,
    isSelected: Boolean = false,
    onClick: () -> Unit,
    onDetailClick: () -> Unit,
) {

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Card(
            modifier = modifier
                .fillMaxWidth()
                .clickable { onClick() }
                .border(
                    width = 2.dp,
                    color = if (isSelected) Color(0xFF7DC23F) else ActiveBlue,
                    shape = RoundedCornerShape(16.dp)
                ),
            colors = CardDefaults.cardColors(containerColor = Color.White),
        )
        {
            Column(
                modifier = Modifier.padding(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                //состояние картинки для загрузки изображения
                val imageState = rememberAsyncImagePainter(
                    model = ImageRequest.Builder(LocalContext.current).data(bank.image)
                        .size(Size.ORIGINAL).build()
                ).state

                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(50.dp)
                        .clip(shape = RoundedCornerShape(10.dp)  /*MaterialTheme.shapes.medium*/)
                ) {
                    if (imageState is AsyncImagePainter.State.Loading) {
                        CircularProgressIndicator()
                    }
                    if (imageState is AsyncImagePainter.State.Error) {
                        Image(
                            painter = painterResource(R.drawable.picture),  //заглушка
                            contentDescription = "",
                            modifier = Modifier.size(70.dp),
                            contentScale = ContentScale.Crop
                        )
                    }
                    if (imageState is AsyncImagePainter.State.Success) {
                        Image(
                            painter = imageState.painter,
                            contentDescription = "",
                            modifier = Modifier.size(70.dp),
                            contentScale = ContentScale.Crop
                        )
                    }
                }

                Column(
                    horizontalAlignment = Alignment.Start,
                ) {
                    Text(
                        text = bank.name,
                        fontFamily = RadioCanadaRegular,
                        fontSize = 12.sp,
                        color = Color.Black,
                        textAlign = TextAlign.Left,
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = bank.specialization,
                        fontFamily = RadioCanadaRegular,
                        fontSize = 11.sp,
                        color = Color(0xFF696969),
                        textAlign = TextAlign.Left,
                        lineHeight = 14.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = bank.time,
                        fontFamily = RadioCanadaRegular,
                        fontSize = 11.sp,
                        color = Color(0xFF696969),
                        textAlign = TextAlign.Left,
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.info),
                            contentDescription = "",
                            modifier = Modifier.size(15.dp)
                        )
                        Text(
                            text = bank.comment,
                            fontFamily = RadioCanadaRegular,
                            fontSize = 11.sp,
                            color = Color(0xFF696969),
                            textAlign = TextAlign.Left,
                            modifier = Modifier.padding(start = 12.dp),
                            lineHeight = 14.sp
                        )
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(7.dp))
        ButtonCustom(
            "Подробнее",
            true,
            ActiveBlue,
            NoActiveBlue,
            15.sp,
            12.dp,
            100.dp,
            35.dp
        ) {
            onDetailClick()
        }
    }
}