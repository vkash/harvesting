package ua.com.vkash.harvesting.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import ua.com.vkash.harvesting.core.designsystem.component.HarvestingTopAppBar
import ua.com.vkash.harvesting.core.designsystem.theme.HarvestingTheme
import ua.com.vkash.harvesting.feature.appmenu.navigation.hasMainRoute
import ua.com.vkash.harvesting.feature.brigade.navigation.hasBrigadesRoute
import ua.com.vkash.harvesting.feature.brigade.navigation.navigateToBrigade
import ua.com.vkash.harvesting.feature.harvest.navigation.hasHarvestsRoute
import ua.com.vkash.harvesting.feature.harvest.navigation.navigateToHarvest
import ua.com.vkash.harvesting.feature.settings.navigation.navigateToSettings
import ua.com.vkash.harvesting.feature.works.navigation.hasWorksRoute
import ua.com.vkash.harvesting.feature.works.navigation.navigateToWork
import ua.com.vkash.harvesting.navigation.AppNavHost

@Composable
fun HarvestingApp(
    modifier: Modifier = Modifier,
    viewModel: HarvestingAppViewModel = hiltViewModel()
) {
    HarvestingTheme {
        val navController = rememberNavController()
        val currentBackStack by navController.currentBackStackEntryAsState()
        val currentDestination = currentBackStack?.destination

        var titleState by remember { mutableStateOf("") }
        var subtitleState by remember { mutableStateOf("") }

        val hasBrigadesRoute = currentDestination.hasBrigadesRoute()
        val hasWorksRoute = currentDestination.hasWorksRoute()
        val hasHarvestsRoute = currentDestination.hasHarvestsRoute()
        val fabVisible = hasBrigadesRoute || hasWorksRoute || hasHarvestsRoute

        Scaffold(
            modifier = modifier,
            topBar = {
                HarvestingTopAppBar(
                    title = titleState,
                    subtitle = subtitleState,
                    isTopDestination = currentDestination.hasMainRoute(),
                    onNavigationIconClick = navController::navigateUp,
                    onActionClick = navController::navigateToSettings
                )
            },
            floatingActionButton = {
                AnimatedVisibility(fabVisible) {
                    FloatingActionButton(
                        onClick = {
                            when {
                                hasWorksRoute -> {
                                    viewModel.createWork { id ->
                                        navController.navigateToWork(id)
                                    }
                                }

                                hasBrigadesRoute -> {
                                    viewModel.createBrigade { id ->
                                        navController.navigateToBrigade(id)
                                    }
                                }

                                hasHarvestsRoute -> {
                                    viewModel.createHarvest { id ->
                                        navController.navigateToHarvest(id)
                                    }
                                }
                            }
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Add,
                            contentDescription = null
                        )
                    }
                }
            }
        ) { innerPadding ->
            AppNavHost(
                navController = navController,
                modifier = Modifier.padding(innerPadding),
                onTitleChange = { titleState = it },
                onSubtitleChange = { subtitleState = it }
            )
        }
    }
}
