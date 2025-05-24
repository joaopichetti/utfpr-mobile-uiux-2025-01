package br.edu.utfpr.trabalhofinal.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import br.edu.utfpr.trabalhofinal.ui.lancamento.form.FormularioLancamentoScreen
import br.edu.utfpr.trabalhofinal.ui.lancamento.lista.ListaLancamentosScreen

private object Screens {
    const val LISTA_LANCAMENTOS = "listaLancamentos"
    const val FORMULARIO_LANCAMENTO = "formularioLancamento"
}

object Arguments {
    const val ID_LANCAMENTO = "idLancamento"
}

private object Routes {
    const val LISTA_LANCAMENTOS = Screens.LISTA_LANCAMENTOS
    const val FORMULARIO_LANCAMENTOS = "${Screens.FORMULARIO_LANCAMENTO}?${Arguments.ID_LANCAMENTO}={${Arguments.ID_LANCAMENTO}}"
}

@Composable
fun AppLancamentos(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = Screens.LISTA_LANCAMENTOS
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(route = Routes.LISTA_LANCAMENTOS) {
            ListaLancamentosScreen(
                onAdicionarPressed = {
                    navController.navigate(Screens.FORMULARIO_LANCAMENTO)
                },
                onLancamentoPressed = { lancamento ->
                    navController.navigate("${Screens.FORMULARIO_LANCAMENTO}?${Arguments.ID_LANCAMENTO}=${lancamento.id}")
                }
            )
        }
        composable(
            route = Routes.FORMULARIO_LANCAMENTOS,
            arguments = listOf(
                navArgument(name = Arguments.ID_LANCAMENTO) { type = NavType.StringType; nullable = true }
            )
        ) {
            FormularioLancamentoScreen(
                onVoltarPressed = {
                    navController.popBackStack()
                }
            )
        }
    }
}