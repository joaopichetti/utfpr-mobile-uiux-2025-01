package br.edu.utfpr.appcontatos.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import br.edu.utfpr.appcontatos.ui.contact.details.ContactDetailsScreen
import br.edu.utfpr.appcontatos.ui.contact.list.ContactsListScreen

@Composable
fun AppContacts(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = "list"
    ) {
        composable(route = "list") {
            ContactsListScreen(
                onAddPressed = {},
                onContactPressed = { contact ->
                    navController.navigate("details/${contact.id}")
                }
            )
        }
        composable(
            route = "details/{id}",
            arguments = listOf(
                navArgument(name = "id") {
                    type = NavType.IntType
                }
            )
        ) {
            ContactDetailsScreen(
                onBackPressed = {
                    navController.popBackStack()
                },
                onEditPressed = {},
                onContactDeleted = {
                    navController.popBackStack()
                }
            )
        }
    }
}