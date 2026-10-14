package com.example.podly.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.media3.common.Player
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.podly.ui.home.HomeScreen
import com.example.podly.ui.podcast.AddPodcastScreen
import com.example.podly.ui.podcast.PodcastDetailsScreen

@Composable
fun AppNavHost(
    player: Player?,
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController,
        NavDestination.Home,
        modifier = modifier
    ) {
        composable<NavDestination.Home>{
            HomeScreen(
                navigateToPodcastDetails = { navController.navigate(NavDestination.PodcastDetails(it)) },
                navigateToAddPodcast = { navController.navigate(NavDestination.AddPodcast)}
            )
        }
        composable<NavDestination.AddPodcast>{
            AddPodcastScreen(
                navigateBack = { navController.popBackStack() },
                navigateToPodcastDetails = { navController.navigate(NavDestination.PodcastDetails(it))}
            )
        }
        composable<NavDestination.PodcastDetails>{
            PodcastDetailsScreen(
                player = player,
                navigateBack = { navController.popBackStack() }
            )
        }
    }
}