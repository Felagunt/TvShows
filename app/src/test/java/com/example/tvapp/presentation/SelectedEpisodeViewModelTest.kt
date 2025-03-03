package com.example.tvapp.presentation

import com.example.tvapp.data.remote.dto.episodes.EpisodesDtoItem
import com.example.tvapp.data.remote.dto.episodes.Links
import com.example.tvapp.data.remote.dto.episodes.Rating
import com.example.tvapp.data.remote.dto.episodes.Self
import com.example.tvapp.data.remote.dto.episodes.Show
import com.example.tvapp.data.remote.mappers.toEpisode
import com.example.tvapp.domain.models.Episode
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test

class SelectedEpisodeViewModelTest {

    @Test
    fun test_selected_episode() = runTest {
        val viewModel = SelectedEpisodeViewModel()

        viewModel.onSelectedEpisode(episodes().map { it.toEpisode() }.first())
        assertEquals(episodes().map { it.toEpisode() }.first(), viewModel.selectedEpisode.value)
    }

    private fun episodes(): List<EpisodesDtoItem> {
        return listOf(
            EpisodesDtoItem(
                _links = Links(
                    self = Self(
                        href = " "
                    ),
                    show = Show(
                        href = "TODO()",
                        name = "TODO()"
                    )
                ),
                airdate = "2011-09-22",
                airstamp = "2011-09-23T01:00:00+00:00",
                airtime = "21:00",
                id = 28,
                image = com.example.tvapp.data.remote.dto.episodes.Image(
                    medium = "TODO()",
                    original = " ",
                ),
                name = "Pilot",
                number = 1,
                rating = Rating(
                    average = 7.6
                ),
                runtime = 60,
                season = 1,
                summary = "\u003Cp\u003EJohn Reese is a former CIA operative living off the grid in NYC. When Reese is hauled into NYPD's 8th Precinct for a skirmish on the subway, he's bailed out by a man he doesn't know: Mr. Finch, a mysterious billionaire. Finch offers Reese a choice: stay on the streets and drink himself to death -- or join him in his mission to stop violent crimes before they happen. Finch has access to a secret source of information, one that identifies people who are about to be involved in violent crimes. There's just one catch: he doesn't know if these people will be the victims or the perpetrators. Skeptical but intrigued, Reese agrees to help. Together, Reese and Finch work to discover what danger is about to befall Assistant District Attorney Diane Hansen. Their actions garner the unwanted attention of NYPD homicide detective Carter -- a dedicated veteran cop who's not fond of vigilantes -- and Detective Fusco, a precinct detective who stands in the way of Reese's plans.\u003C/p\u003E",
                type = "regular",
                url = ""
            )
        )
    }

}
//    private val _selectedEpisode = MutableStateFlow<Episode?>(null)
//    val selectedEpisode = _selectedEpisode.asStateFlow()
//
//    fun onSelectedEpisode(episode: Episode?) {
//        _selectedEpisode.value = episode
//    }
//}
