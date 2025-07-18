package ua.com.vkash.harvesting.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import ua.com.priberry.harvesting.feature.calculator.navigation.calculatorScreen
import ua.com.priberry.harvesting.feature.calculator.navigation.navigateToCalculator
import ua.com.vkash.harvesting.feature.appmenu.MainCard
import ua.com.vkash.harvesting.feature.appmenu.navigation.MainRoute
import ua.com.vkash.harvesting.feature.appmenu.navigation.mainScreen
import ua.com.vkash.harvesting.feature.brigade.navigation.brigadeScreen
import ua.com.vkash.harvesting.feature.brigade.navigation.brigadesScreen
import ua.com.vkash.harvesting.feature.brigade.navigation.navigateToBrigade
import ua.com.vkash.harvesting.feature.brigade.navigation.navigateToBrigades
import ua.com.vkash.harvesting.feature.fields.navigation.fieldsScreen
import ua.com.vkash.harvesting.feature.fields.navigation.navigateToFields
import ua.com.vkash.harvesting.feature.fields.navigation.navigateToSquares
import ua.com.vkash.harvesting.feature.fields.navigation.squaresScreen
import ua.com.vkash.harvesting.feature.harvest.navigation.harvestScreen
import ua.com.vkash.harvesting.feature.harvest.navigation.harvestsScreen
import ua.com.vkash.harvesting.feature.harvest.navigation.navigateToHarvest
import ua.com.vkash.harvesting.feature.harvest.navigation.navigateToHarvests
import ua.com.vkash.harvesting.feature.reports.navigation.navigateToReport
import ua.com.vkash.harvesting.feature.reports.navigation.navigateToReports
import ua.com.vkash.harvesting.feature.reports.navigation.reportScreen
import ua.com.vkash.harvesting.feature.reports.navigation.reportsScreen
import ua.com.vkash.harvesting.feature.settings.navigation.settingsScreen
import ua.com.vkash.harvesting.feature.sku.navigation.TARA
import ua.com.vkash.harvesting.feature.sku.navigation.navigateToSkus
import ua.com.vkash.harvesting.feature.sku.navigation.skusScreen
import ua.com.vkash.harvesting.feature.timesheet.navigation.navigateToTimeSheet
import ua.com.vkash.harvesting.feature.timesheet.navigation.timeSheetScreen
import ua.com.vkash.harvesting.feature.works.navigation.navigateToWork
import ua.com.vkash.harvesting.feature.works.navigation.navigateToWorkTypes
import ua.com.vkash.harvesting.feature.works.navigation.navigateToWorks
import ua.com.vkash.harvesting.feature.works.navigation.workScreen
import ua.com.vkash.harvesting.feature.works.navigation.workTypesScreen
import ua.com.vkash.harvesting.feature.works.navigation.worksScreen

@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    onTitleChange: (String) -> Unit = {},
    onSubtitleChange: (String) -> Unit = {}
) {
    NavHost(
        navController = navController,
        startDestination = MainRoute,
        modifier = modifier
    ) {
        mainScreen(
            onTitle = onTitleChange,
            onSubtitle = onSubtitleChange,
            onCardSelected = {
                when (it) {
                    MainCard.BRIGADES -> navController.navigateToBrigades()
                    MainCard.HARVESTS -> navController.navigateToHarvests()
                    MainCard.WORKS -> navController.navigateToWorks()
                    MainCard.REPORTS -> navController.navigateToReports()
                    MainCard.TIMESHEET -> navController.navigateToTimeSheet()
                }
            }
        )

        settingsScreen(
            onTitle = onTitleChange,
            onSubtitle = onSubtitleChange,
            onFieldClick = navController::navigateToFields
        )

        fieldsScreen(
            onTitle = onTitleChange,
            onSubtitle = onSubtitleChange,
            onFieldSelect = { id ->
                navController.navigateUpWithArgs("fieldId" to id)
            }
        )

        workTypesScreen(
            onTitle = onTitleChange,
            onSubtitle = onSubtitleChange,
            onSelectWorkType = { id ->
                navController.navigateUpWithArgs("workTypeId" to id)
            }
        )

        reportsScreen(
            onTitle = onTitleChange,
            onSubtitle = onSubtitleChange,
            onReportSelected = navController::navigateToReport
        )

        reportScreen(
            onTitle = onTitleChange,
            onSubtitle = onSubtitleChange
        )

        timeSheetScreen(
            onTitle = onTitleChange,
            onSubtitle = onSubtitleChange
        )

        worksScreen(
            onTitle = onTitleChange,
            onSubtitle = onSubtitleChange,
            onWorkSelect = navController::navigateToWork
        )

        workScreen(
            onTitle = onTitleChange,
            onSubtitle = onSubtitleChange,
            onWorkTypeSelect = navController::navigateToWorkTypes,
            onSquareSelect = navController::navigateToSquares,
            onQtySelect = { d ->
                navController.navigateToCalculator(inputValue = d, isFractional = true)
            },
            onFinish = navController::navigateUp
        )

        brigadesScreen(
            onTitle = onTitleChange,
            onSubtitle = onSubtitleChange,
            onBrigadeSelect = navController::navigateToBrigade
        )

        brigadeScreen(
            onTitle = onTitleChange,
            onSubtitle = onSubtitleChange,
            onFinish = navController::navigateUp
        )

        harvestsScreen(
            onTitle = onTitleChange,
            onSubtitle = onSubtitleChange,
            onHarvestSelect = navController::navigateToHarvest
        )

        harvestScreen(
            onTitle = onTitleChange,
            onSubtitle = onSubtitleChange,
            onFinish = navController::navigateUp,
            onSquareSelect = navController::navigateToSquares,
            onTaraSelect = {
                navController.navigateToSkus(TARA)
            },
            onWeightSelect = { d ->
                navController.navigateToCalculator(
                    inputKey = "weight",
                    inputValue = d,
                    isFractional = true
                )
            },
            onQtySelect = { i ->
                navController.navigateToCalculator(
                    inputKey = "qty",
                    inputValue = i.toDouble(),
                    isFractional = false
                )
            }
        )

        skusScreen(
            onTitle = onTitleChange,
            onSubtitle = onSubtitleChange,
            onSkuSelect = { id ->
                navController.navigateUpWithArgs("skuId" to id)
            }
        )

        squaresScreen(
            onTitle = onTitleChange,
            onSubtitle = onSubtitleChange,
            onSelectSquare = { id ->
                navController.navigateUpWithArgs("squareId" to id)
            }
        )

        calculatorScreen(
            onTitle = onTitleChange,
            onSubtitle = onSubtitleChange,
            onConfirmInput = { k, v ->
                navController.navigateUpWithArgs(k to v)
            }
        )
    }
}
