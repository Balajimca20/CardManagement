package com.example.animation.ui.navigation

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.vectorResource
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.animation.R
import com.example.animation.ui.addcard.AddCardRoute
import com.example.animation.ui.allcards.ALLCardRoute
import com.example.animation.ui.cardsdetail.CardDetailRoute
import com.example.animation.ui.dashboard.DashboardRoute
import com.example.animation.ui.dashboard.MainViewModel
import com.example.animation.ui.dashboard.ProfileRoute

@Composable
fun NavigationGraph(
    navController: NavHostController,
    viewModel: MainViewModel,
) {
    NavHost(
        navController = navController,
        startDestination = BottomBarNavItem.Dashboard.route
    ) {

        composable(BottomBarNavItem.Dashboard.route) {
            DashboardRoute(
                viewModel = viewModel,
                onClickCardItem = {
                    navController.navigate(BottomBarNavItem.CardDetail.route) {
                        navController.graph.startDestinationRoute?.let { screenRoute ->
                            popUpTo(screenRoute) {
                                saveState = true
                            }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
        composable(BottomBarNavItem.CardDetail.route) {
            CardDetailRoute(
                viewModel = viewModel,
            )
        }
        composable(BottomBarNavItem.Empty.route) {
            AddCardRoute(
                viewModel = viewModel,
                onBack = {
                    if (navController.previousBackStackEntry != null) {
                        navController.popBackStack()
                    }
                }
            )

        }
        composable(BottomBarNavItem.AllCards.route) {
            ALLCardRoute(
                viewModel = viewModel,
            )
        }
        composable(BottomBarNavItem.Profile.route) {
            ProfileRoute(
                viewModel = viewModel,
            )
        }
        composable(BottomBarNavItem.AddCard.route) {
            AddCardRoute(
                viewModel = viewModel,
                onBack = {
                    if (navController.previousBackStackEntry != null) {
                        navController.popBackStack()
                    }
                }
            )
        }
    }
}


@Composable
fun BottomNavigationComponent(
    navController: NavHostController, state: MutableState<Boolean>, modifier: Modifier = Modifier
) {
    val screens = listOf(
        BottomBarNavItem.Dashboard,
        BottomBarNavItem.CardDetail,
        BottomBarNavItem.Empty,
        BottomBarNavItem.AllCards,
        BottomBarNavItem.Profile,
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    NavigationBar(
        modifier = modifier
            .fillMaxWidth(),
        containerColor = Color.White
    ) {

        screens.forEachIndexed { index, screen ->
            NavigationBarItem(
                alwaysShowLabel = false,
                label = {},
                icon = {
                    if (index != 2) {
                        Icon(
                            imageVector = ImageVector.vectorResource(id = screen.icon),
                            contentDescription = ""
                        )
                    }
                },
                selected = currentRoute == screen.route,
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                            state.value = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = colorResource(id = R.color.primary),
                    unselectedIconColor = colorResource(id = R.color.unselect),
                )
            )
        }
    }

}