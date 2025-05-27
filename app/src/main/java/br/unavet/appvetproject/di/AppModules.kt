import br.unavet.appvetproject.data.local.AppDatabase
import br.unavet.appvetproject.data.repository.GameRepositoryImpl
import br.unavet.appvetproject.data.repository.UserStatsRepositoryImpl
import br.unavet.appvetproject.domain.repository.GameRepository
import br.unavet.appvetproject.domain.repository.UserStatsRepository
import br.unavet.appvetproject.ui.game.GameViewModel
import br.unavet.appvetproject.ui.menu.MainMenuViewModel
import br.unavet.appvetproject.ui.profile.ProfileViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val databaseModule = module {
    single { AppDatabase.getInstance(get()) }
    single { get<AppDatabase>().questionDao() }
    single { get<AppDatabase>().userStatsDao() }
}

val repositoryModule = module {
    single<GameRepository> { GameRepositoryImpl(get()) }
    single<UserStatsRepository> { UserStatsRepositoryImpl(get()) }
}

val viewModelModule = module {
    viewModel { GameViewModel(get(), get()) }
    viewModel { MainMenuViewModel(get()) }
    viewModel { ProfileViewModel(get()) }
}

val appModules = listOf(
    databaseModule,
    repositoryModule,
    viewModelModule
)