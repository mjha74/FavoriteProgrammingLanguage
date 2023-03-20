package mykt.tutorials.favoriteprogramminglanguage.di.component


import dagger.Component
import mykt.tutorials.favoriteprogramminglanguage.di.ActivityScope
import mykt.tutorials.favoriteprogramminglanguage.di.module.ActivityModule
import mykt.tutorials.favoriteprogramminglanguage.view.MainActivity

@ActivityScope
@Component(dependencies = [ApplicationComponent::class], modules = [ActivityModule::class])
interface ActivityComponent {
    fun inject(activity: MainActivity)
}