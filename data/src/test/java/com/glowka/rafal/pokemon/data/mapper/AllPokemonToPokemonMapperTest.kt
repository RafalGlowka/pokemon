package com.glowka.rafal.pokemon.data.mapper

import com.glowka.rafal.pokemon.data.graphql.PokemonListQuery
import org.junit.Assert
import org.junit.After
import org.junit.Before
import org.junit.Test

class AllPokemonToPokemonMapperTest {

  lateinit var mapper: AllPokemonToPokemonMapper

  @Before
  fun prepare() {
    mapper = AllPokemonToPokemonMapperImpl()
  }

  @After
  fun finish() {
  }

  @Test
  fun parseTest() {
    // Given
    val POKEMON_ID = 124
    val POKEMON_NAME = "test name"
    val POKEMON_SPRITE1 = "sprite1_test_url"
    val POKEMON_SPRITE2 = "sprite2_test_url"
    val POKEMON_GAME1_ID = 432
    val POKEMON_GAME1_NAME = "game1"
    val POKEMON_ABILITY1_NAME = "ability1"
    val POKEMON_ABILITY2_NAME = "ability2"

    val dataIn = PokemonListQuery.AllPokemon(
      id = POKEMON_ID,
      name = POKEMON_NAME,
      games = listOf(
        PokemonListQuery.Game(
          id = POKEMON_GAME1_ID,
          name = POKEMON_GAME1_NAME,
        )
      ),
      abilities = listOf(
        PokemonListQuery.Ability(POKEMON_ABILITY1_NAME),
        PokemonListQuery.Ability(POKEMON_ABILITY2_NAME),
      ),
      sprites = PokemonListQuery.Sprites(
        front_default = POKEMON_SPRITE1,
        back_default = POKEMON_SPRITE2,
        front_female = null,
        front_shiny = null,
        front_shiny_female = null,
        back_female = null,
        back_shiny = null,
        back_shiny_female = null,
      )
    )

    // When
    val response = mapper(dataIn)

    // Then
    Assert.assertEquals(POKEMON_ID, response.id)
    Assert.assertEquals(POKEMON_NAME, response.name)
    Assert.assertEquals(POKEMON_SPRITE1, response.imageFront)
    Assert.assertEquals(POKEMON_SPRITE2, response.imageBack)
    Assert.assertEquals(POKEMON_GAME1_ID, response.games[0].id)
    Assert.assertEquals(POKEMON_GAME1_NAME, response.games[0].name)
    Assert.assertEquals(POKEMON_ABILITY1_NAME, response.abilities[0])
    Assert.assertEquals(POKEMON_ABILITY2_NAME, response.abilities[1])
  }

}