package com.baka3k.architecture.multifeatures

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.navigation.compose.rememberNavController
import com.baka3k.architecture.multifeatures.navigation.HiNavHost
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainComposeActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
//            MovieRoute()
            val navController = rememberNavController()
//            val niaTopLevelNavigation = remember(navController) {
//                NiaTopLevelNavigation(navController)
//            }

            HiNavHost(
                windowSizeClass = calculateWindowSizeClass(this),
                navController = navController,
                modifier = Modifier
                    .padding(0.dp)
            )
        }
    }
}