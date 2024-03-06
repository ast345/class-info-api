package models

import slick.jdbc.MySQLProfile.api._

case class ClassFacilitator(classroomId: Long, facilitatorId: Long)

class ClassFacilitatorTable(tag: Tag) extends Table[ClassFacilitator](tag, "class_facilitator") {
  def classroomId = column[Long]("classroom_id")
  def facilitatorId = column[Long]("facilitator_id")
  
  def * = (classroomId, facilitatorId) <> ((ClassFacilitator.apply _).tupled, ClassFacilitator.unapply)

  def pk = primaryKey("pk_class_facilitator", (classroomId, facilitatorId))

  def classroom = foreignKey("class_fk", classroomId, Classrooms.classrooms)(_.id)
  def facilitator = foreignKey("facilitator_fk", facilitatorId, Facilitator.facilitators)(_.id)
}

object ClassFacilitator {
    val classFacilitators = TableQuery[ClassFacilitatorTable]
}