package mysamples.textbox
import scala.swing.MainFrame
import scala.swing.BoxPanel
import scala.swing.Orientation
import scala.swing.Label
import reswing.ReLabel._
import reswing.ReTextField._
import reswing._
import scala.swing.SimpleSwingApplication
import rescala.Signal
import makro.SignalMacro.{ SignalM => Signal }
import scala.swing.FlowPanel
import scala.swing.Button
import scala.swing.event.ButtonClicked
import scala.swing.event.KeyTyped
import scala.swing.event.KeyPressed
import java.awt.{ Color, Graphics2D }
import swing.event.WindowClosing
import swing.MenuBar
import scala.swing._
import Swing._
import java.awt.Color
import scala.swing.Publisher

object Time {
  private val form = new java.text.SimpleDateFormat("HH:mm:ss")
  def current = form.format(java.util.Calendar.getInstance().getTime)
}

object Timer {
  def apply(interval: Int, repeats: Boolean = true)(op: => Unit) {
    val timeOut = new javax.swing.AbstractAction() {
      def actionPerformed(e : java.awt.event.ActionEvent) = op
    }
    val t = new javax.swing.Timer(interval, timeOut)
    t.setRepeats(repeats)
    t.start()
  }
}

object textboxsample extends SimpleSwingApplication {
  val output = new ReTextField(text = "  ", columns = 10)

  def top = new MainFrame {
    title = "Haripriya Text field"

    val textFields = new ReTextField(text = " Enter text here ", columns = 10)
    textFields.foreground_=(Color.orange);
    textFields.background_=(Color.gray);
    
    val inputText = Signal { textFields.text() }

    val button = new Button("Save button")

    contents = new BoxPanel(Orientation.Vertical) {

      contents += new FlowPanel {
        contents += new Label("write text here")
        contents += textFields
      }

      contents += new FlowPanel {
        contents += new Label("written: ")
        contents += output
      }

      contents += new FlowPanel {
        contents += button
      }

    menuBar = new MenuBar {
      contents += new Menu("File") {
        contents += new MenuItem(Action("Exit") {
          sys.exit(0)
        })
      }
    }
   
    listenTo(button, textFields)
      reactions += 
      {
        case ButtonClicked(button) => 
          output.text_=(textFields.text())
          textFields.foreground_=(Color.GREEN);
        case WindowClosing(e) => System.exit(0)
      }
      
      Timer(5000, true) 
        {           
          output.text_=(textFields.text())
          textFields.foreground_=(Color.GREEN);
          println("After 5 seconds updated automatically ")
        } 
    }
      
    centerOnScreen

  }
}