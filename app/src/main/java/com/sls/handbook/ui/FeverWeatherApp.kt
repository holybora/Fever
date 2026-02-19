package com.sls.handbook.ui

import androidx.compose.animation.AnimatedContentTransitionScope.SlideDirection.Companion.End
import androidx.compose.animation.AnimatedContentTransitionScope.SlideDirection.Companion.Start
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.sls.handbook.feature.fever.FeverRoute
import com.sls.handbook.feature.fever.theme.FeverTheme
import com.sls.handbook.navigation.FeverDestination
import com.theapache64.rebugger.Rebugger

@Suppress("LongMethod")
@Composable
fun FeverWeatherApp(
    modifier: Modifier = Modifier,
) {
    FeverTheme {
        val navController = rememberNavController()
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination

        Rebugger(
            composableName = "FeverWeatherApp",
            trackMap = mapOf(
                "currentDestination" to currentDestination,
            ),
        )

        val isEdgeToEdgeScreen = currentDestination?.hasRoute<FeverDestination>() == true

        Scaffold(modifier = modifier.fillMaxSize()) { innerPadding ->
            Column(modifier = if (isEdgeToEdgeScreen) Modifier else Modifier.padding(innerPadding)) {
                FeverWeatherNavHost(
                    navController = navController,
                    modifier = Modifier.weight(1f),
                )
            }
        }
    }
}

@Suppress("LongMethod")
@Composable
private fun FeverWeatherNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = FeverDestination,
        modifier = modifier,
        enterTransition = {
            fadeIn(tween(300)) +
                slideIntoContainer(Start, tween(300, easing = EaseOut))
        },
        exitTransition = {
            fadeOut(tween(300)) +
                slideOutOfContainer(Start, tween(300, easing = EaseIn))
        },
        popEnterTransition = {
            fadeIn(tween(300)) +
                slideIntoContainer(End, tween(300, easing = EaseOut))
        },
        popExitTransition = {
            fadeOut(tween(300)) +
                slideOutOfContainer(End, tween(300, easing = EaseIn))
        },
    ) {
        composable<FeverDestination> {
            FeverRoute()
        }
    }
}
