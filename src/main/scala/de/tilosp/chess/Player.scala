package de.tilosp.chess

import java.io.{DataInputStream, DataOutputStream, IOException}
import java.net.Socket
import javax.swing.SwingUtilities

import de.tilosp.chess.gui.ChessboardGUI
import de.tilosp.chess.lib.{ChessEngine, Chessboard, PlayerColor}

abstract class Player {
  protected var color: PlayerColor = _
  protected var gui: ChessboardGUI = _

  def init(color: PlayerColor, gui: ChessboardGUI) {
    this.color = color
    this.gui = gui
  }

  def sendUpdate(chessboard: Chessboard)

  protected def update(chessboard: Chessboard): Unit = {
    SwingUtilities.invokeLater(new Runnable {
      override def run(): Unit = gui.externalUpdate(chessboard)
    })
  }


  def onClosed()
}

final class ComputerPlayer extends Player {

  override def sendUpdate(chessboard: Chessboard) {
    if ((chessboard.playerColor == color) && !chessboard.isDraw && !chessboard.isWin(PlayerColor.WHITE) && !chessboard.isWin(PlayerColor.BLACK)) {
      new Thread() {
        override def run(): Unit = update(ChessEngine.compute(chessboard, 2, 3))
      }.start()
    }
  }

  override def onClosed() {}
}

final class LocalPlayer extends Player {

  override def sendUpdate(chessboard: Chessboard) {}

  override def onClosed() {}
}

final class NetworkPlayer(val socket: Socket) extends Player {
  private val in = new DataInputStream(socket.getInputStream)
  private val out = new DataOutputStream(socket.getOutputStream)
  private var running = true

  new Thread() {
    override def run() {
      while (running) {
        try {
          if (in.available > 0)
            update(Chessboard.read(in))
          Thread.sleep(100)
        } catch {
          case e: IOException => e.printStackTrace()
          case _: InterruptedException =>
        }
      }
    }
  }.start()

  override def sendUpdate(chessboard: Chessboard) {
    if ((chessboard.playerColor eq color) && !chessboard.isDraw && !chessboard.isWin(PlayerColor.WHITE) && !chessboard.isWin(PlayerColor.BLACK)) {
      try {
        chessboard.write(out)
        out.flush()
      } catch {
        case e: IOException => e.printStackTrace()
      }
    }
  }

  override def onClosed() {
    running = false
    try {
      in.close()
      out.close()
      socket.close()
    } catch {
      case e: IOException => e.printStackTrace()
    }
  }
}
