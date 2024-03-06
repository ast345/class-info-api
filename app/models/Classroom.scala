package models

import slick.jdbc.MySQLProfile.api._

case class Classroom(id: Option[Long], name: String)

class ClassroomTable(tag: Tag) extends Table[Classroom](tag, "classroom") {
  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
  def name = column[String]("name")
  
  def * = (id.?, name) <> ((Classroom.apply _).tupled, Classroom.unapply)
}

object Classrooms {
  val classrooms = TableQuery[ClassroomTable]
}
