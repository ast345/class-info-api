package models

import slick.jdbc.MySQLProfile.api._

case class Facilitator(id: Option[Long], name: String, loginId: String, password: String)

class FacilitatorTable(tag: Tag) extends Table[Facilitator](tag, "facilitator") {
  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
  def name = column[String]("name")
  def loginId = column[String]("login_id", O.Unique)
  def password = column[String]("password")

  def * = (id.?, name, loginId, password) <> ((Facilitator.apply _).tupled, Facilitator.unapply)
}

object Facilitator {
    val facilitators = TableQuery[FacilitatorTable]
}