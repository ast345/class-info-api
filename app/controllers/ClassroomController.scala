package controllers

import models.{Classroom, Classrooms}
import slick.jdbc.MySQLProfile.api._
import javax.inject.Inject
import play.api.mvc._
import scala.concurrent.ExecutionContext.Implicits.global


class ClassroomController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {
  val db = Database.forConfig("slick.dbs.default.db")

  def createClassroom(name: String) = Action.async { implicit request: Request[AnyContent] =>
    val classroom = Classroom(None, name)
    val insertAction = (Classrooms.classrooms returning Classrooms.classrooms.map(_.id)) += classroom
    db.run(insertAction).map { classroomId =>
      Ok(s"Classroom created with ID: $classroomId")
    }
  }
}