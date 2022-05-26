package com.glowka.rafal.pokemon.domain.model

class Pokemon(
  val id: Int,
  val name: String,
  val imageFront: String?,
  val imageFrontFemale: String?,
  val imageBack: String?,
  val imageBackFemale: String?,
  val games: List<Game>,
  val abilities: List<String>,
)
