package com.example.podly.ui.home

import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.podly.R
import com.example.podly.ui.theme.AppTheme
import com.example.test.add

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTopBar(
    navigateToAddPodcast: () -> Unit
) {
    TopAppBar(
        title = {
            Text(stringResource(R.string.app_name),
                fontFamily = FontFamily(fonts = listOf(Font(R.font.aclonica))))
        },
        actions = {
            IconButton(onClick = navigateToAddPodcast) {
                Icon(
                    add,
                    null,
                    modifier = Modifier.size(48.dp)
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent
        )
    )
}

@Preview
@Composable
private fun HomeTopBarPrev() {
    AppTheme {
        HomeTopBar {  }
    }
}