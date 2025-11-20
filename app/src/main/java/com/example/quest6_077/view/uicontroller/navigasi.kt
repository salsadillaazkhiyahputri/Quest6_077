package com.example.quest6_077.view.uicontroller

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.quest6_077.model.DataJK.JenisK
import com.example.quest6_077.viewmodel.SiswaViewModel
import com.example.quest6_077.view.FormIsian
import com.example.quest6_077.view.TampilData


enum class Navigasi{
    Formulir,
    Detail

}

@Composable
fun DataApp(
    modifier: Modifier,
    viewModel: SiswaViewModel = viewModel(),
    navController: NavHostController = rememberNavController(),

){
    Scaffold { isiRuang->
        val uiState = viewModel.statusUI.collectAsState()
        NavHost(
            navController = navController,
            startDestination = Navigasi.Formulir.name,

            modifier = Modifier.padding(isiRuang)
        ) {
            composable (route = Navigasi.Formulir.name) {
                val konteks = LocalContext.current
                FormIsian(
                    pilihanJK = JenisK.map { id -> konteks.resources.getString(id)},
                    OnSubmitBtnClick = {
                        viewModel.setSiswa(it)
                        navController.navigate(Navigasi.Detail.name)
                    }
                )
            }
            composable(route = Navigasi.Detail.name) {
                TampilData(
                    statusUiSiswa = uiState.value,
                    onBackBtnClick = {
                        cancelAndBackToFormulir(navController)
                    }
                )
            }
        }
    }
}



private fun cancelAndBackToFormulir(
    navController: NavController
){
    navController.popBackStack(Navigasi.Formulir.name,
        inclusive = false)
}

