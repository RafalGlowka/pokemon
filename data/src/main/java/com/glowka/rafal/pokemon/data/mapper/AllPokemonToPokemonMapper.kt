package com.glowka.rafal.pokemon.data.mapper

import com.glowka.rafal.pokemon.data.graphql.PokemonListQuery
import com.glowka.rafal.pokemon.domain.model.Game
import com.glowka.rafal.pokemon.domain.model.Pokemon

interface AllPokemonToPokemonMapper : Mapper<PokemonListQuery.AllPokemon, Pokemon>

class AllPokemonToPokemonMapperImpl : AllPokemonToPokemonMapper {
  override fun invoke(data: PokemonListQuery.AllPokemon): Pokemon {
    return Pokemon(
      id = data.id!!, // Any exception will be catch in mapSuccess method and remapped to error.
      name = data.name!!,
      imageFront = data.sprites?.front_default,
      imageFrontFemale = data.sprites?.front_female,
      imageBack = data.sprites?.back_default,
      imageBackFemale = data.sprites?.back_female,
      games = data.games?.mapNotNull { game ->
        Game(
          id = game?.id!!,
          name = game.name!!,
        )
      } ?: emptyList(),
      abilities = data.abilities?.mapNotNull { ability ->
        ability?.name!!
      } ?: emptyList()
    )
  }
}