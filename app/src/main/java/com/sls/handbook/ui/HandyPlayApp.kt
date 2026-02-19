package com.sls.handbook.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sls.handbook.core.designsystem.theme.HandyPlayTheme
import com.sls.handbook.feature.fever.FeverRoute
import com.sls.handbook.navigation.FeverDestination

@Composable
fun HandyPlayApp(
    modifier: Modifier = Modifier,
) {
    HandyPlayTheme {
        val navController = rememberNavController()

        @Suppress("UnusedMaterial3ScaffoldPaddingParameter")
        Scaffold(modifier = modifier.fillMaxSize()) { _ ->
            HandyPlayNavHost(
                navController = navController,
                modifier = Modifier.fillMaxSize(),
            )
        }
    }
}

@Composable
private fun HandyPlayNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = FeverDestination,
        modifier = modifier,
    ) {
        composable<FeverDestination> {
            FeverRoute()
        }
    }
}
