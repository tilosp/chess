package de.tilosp.chess

import de.tilosp.chess.gui.NewGameGUI

object Main {

  def main(args: Array[String]): Unit = {
    // Open NewGameGUI Window
    new NewGameGUI().setVisible(true)
  }

}
