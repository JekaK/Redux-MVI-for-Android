package com.krykun.sample

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.krykun.sample.presentation.MainProps
import com.krykun.sample.presentation.MainViewModel
import com.krykun.sample.theme.ReduxMVITheme
import com.krykun.sample.ui.view.AddButton
import com.krykun.sample.ui.view.CounterView
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {

    val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ReduxMVITheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val props = viewModel.mainProps().collectAsState(initial = MainProps())
                    val propertyPropsFlow = viewModel.propertyProps()

                    val counter by propertyPropsFlow.collectAsState(0)

                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        CounterView(counter)
                        Spacer(modifier = Modifier.height(20.dp))
                        AddButton {
                            props.value.addCounterAction()
                        }
                    }
                }
            }
        }
    }
}