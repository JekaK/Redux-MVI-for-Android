package com.krykun.sample

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.krykun.sample.navigation.MainNavigation
import com.krykun.sample.presentation.MainState
import com.krykun.sample.presentation.MainViewModel
import com.krykun.sample.theme.ReduxMVITheme
import com.krykun.sample.ui.view.AddButton
import com.krykun.sample.ui.view.CounterView
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {

    val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val props = viewModel.mainProps().collectAsState(initial = MainState())
            ReduxMVITheme {
                val context = LocalContext.current
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    CounterView(props.value.counter)
                    Spacer(modifier = Modifier.height(20.dp))
                    AddButton(viewModel::addCounter)
                }
                LaunchedEffect(key1 = viewModel.navigationEventsState.value) {
                    when (val event = viewModel.navigationEventsState.value) {
                        is MainNavigation.ShowCounterToast -> {
                            Toast.makeText(
                                context,
                                "Counter is ${event.counter}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                        else -> {}
                    }
                }
            }
        }
    }
}