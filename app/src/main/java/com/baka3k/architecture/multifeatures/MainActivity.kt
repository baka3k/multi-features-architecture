package com.baka3k.architecture.multifeatures

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle

import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.baka3k.architecture.multifeatures.databinding.ActivityMainBinding
import com.baka3k.core.common.result.Result
import com.baka3k.core.data.repository.MovieRepositoryImpl
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

        binding.fab.setOnClickListener { view ->
//            viewModel.test()
        }
        binding.fab1.setOnClickListener { view ->
            viewModel.test1()
        }
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {

                viewModel.uiState.collect {
                    when (it) {
                        is MovieUiState.Movies -> {
                            Log.d(
                                "haha",
                                "MainActivity ${it.nowPlayingMovies.size} / ${it.popularMovies.size}"
                            )
                        }
                        is MovieUiState.Loading -> {
                            Log.d(
                                "haha",
                                "MainActivity Loading"
                            )
                        }
                        is MovieUiState.Empty -> {
                            Log.d(
                                "haha",
                                "MainActivity Empty"
                            )
                        }
                    }
                }

            }
        }
    }

    //    val testRepository = RetrofitNetworkDataSource(networkJson = Json {
//        ignoreUnknownKeys = true
//    })
    @Inject
    lateinit var retrofitRepository: MovieRepositoryImpl
    private val viewModel: TestViewModel by viewModels()
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}