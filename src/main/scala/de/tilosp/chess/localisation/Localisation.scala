package de.tilosp.chess.localisation

import java.util.ResourceBundle

object Localisation {

  private val resourceBundle = ResourceBundle.getBundle("de.tilosp.chess.localisation.localisation")

  def getString(key: String): String = resourceBundle.getString(key)

}
