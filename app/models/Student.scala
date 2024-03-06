package models

import slick.jdbc.MySQLProfile.api._
import play.api.libs.json.{Json, Writes}

case class Student(id: Option[Long], name: String, loginId: String, password: String, classroomId: Long)

class StudentTable(tag: Tag) extends Table[Student](tag, "student") {
  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
  def name = column[String]("name")
  def loginId = column[String]("login_id", O.Unique)
  def password = column[String]("password")
  def classroomId = column[Long]("classroom_id")
  
  def * = (id.?, name, loginId, password, classroomId) <> ((Student.apply _).tupled, Student.unapply)
  
  def classroom = foreignKey("class_fk", classroomId, Classrooms.classrooms)(_.id)
}

object Student {
  val students = TableQuery[StudentTable]
  implicit val studentWrites: Writes[Student] = new Writes[Student] {
    def writes(student: Student) = Json.obj(
      "id" -> student.id,
      "name" -> student.name,
      "loginId" -> student.loginId,
      "classroomId" -> student.classroomId
    )
  }
}