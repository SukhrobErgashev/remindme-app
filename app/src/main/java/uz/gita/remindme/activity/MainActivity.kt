package uz.gita.remindme.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import uz.gita.remindme.R
import uz.gita.remindme.databinding.ActivityMainBinding
import uz.gita.remindme.fragments.birthdays.BirthdaysFragmentDirections
import uz.gita.remindme.fragments.notes.NotesFragmentDirections
import uz.gita.remindme.fragments.tasks.TasksFragmentDirections

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bottomNavigationView = binding.bottomNavigationView
        val navController = findNavController(R.id.fragment)

        bottomNavigationView.background = null

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.notesFragment,
                R.id.tasksFragment,
                R.id.birthdaysFragment,
                R.id.settingsFragment
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        bottomNavigationView.setupWithNavController(navController)

        binding.fab.setOnClickListener {
            when(navController.currentDestination?.id) {
                R.id.notesFragment -> {
                    navController.navigate(NotesFragmentDirections.actionNotesFragmentToAddNoteFragment())
                }
                R.id.tasksFragment -> {
                    navController.navigate(TasksFragmentDirections.actionTasksFragmentToAddTaskFragment())
                }
                R.id.birthdaysFragment -> {
                    navController.navigate(BirthdaysFragmentDirections.actionBirthdaysFragmentToAddBirthdayFragment())
                }
            }
        }

        controlBottomNav()
    }

    private fun controlBottomNav() {
        findNavController(R.id.fragment).addOnDestinationChangedListener { _, destination, _ ->
            if (
                destination.id == R.id.notesFragment || destination.id == R.id.tasksFragment ||
                destination.id == R.id.birthdaysFragment || destination.id == R.id.settingsFragment
            ) {
                binding.apply {
                    bottomNavigationView.visibility = View.VISIBLE
                    bottomAppBar.visibility = View.VISIBLE
                    fab.visibility = View.VISIBLE
                }
            } else {
                binding.apply {
                    bottomNavigationView.visibility = View.GONE
                    bottomAppBar.visibility = View.GONE
                    fab.visibility = View.GONE
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        return super.onCreateOptionsMenu(menu)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.fragment)
        return navController.navigateUp() or super.onSupportNavigateUp()
    }
}