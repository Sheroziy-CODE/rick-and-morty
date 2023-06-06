package rick_and_morty.ui.episodes.composables

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import rick_and_morty.data.model.CharacterResultsDto
import rick_and_morty.data.model.episodes.EpisodesResultDto

@Composable
fun EpisodesRow(
    episodeResultsDto: EpisodesResultDto,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp)
            .border(border = BorderStroke(width = 1.dp, color = Color.White))
            .clickable (onClick = {}),
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            horizontalArrangement = Arrangement.Center
        ) {
            Column (
                modifier = Modifier
                    .padding(5.dp)
            ) {
                Text (
                    text = episodeResultsDto.name,
                    fontSize = 30.sp,
                    color = Color.White)
                Spacer(modifier = Modifier.padding(top = 10.dp))
                Text(
                    text = "Episode: \n${episodeResultsDto.episode}",
                    fontSize = 15.sp,
                    color = Color.White)
                Spacer(modifier = Modifier.padding(top = 10.dp))
                Text (
                    text = "Air Date: \n${episodeResultsDto.air_date}",
                    fontSize = 15.sp,
                    color = Color.White)
            }
        }
    }
}