package utils

import models._
import slick.jdbc.MySQLProfile.api._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.concurrent.Await

object DatabaseSeeder {

  def seed(): Unit = {
    // Facilitatorのダミーデータ
    val facilitators = Seq(
      Facilitator(Some(1), "小川", "ogawa01", "password"),
      Facilitator(Some(1), "高田", "takada02", "password")
    )

    // Classroomのダミーデータ
    val classrooms = Seq(
      Classroom(Some(1), "1組"),
      Classroom(Some(1), "2組")
    )

    // ClassFacilitatorのダミーデータ
    val classFacilitators = Seq(
      ClassFacilitator(1, 1),
      ClassFacilitator(1, 2),
      ClassFacilitator(2, 2)
    )

    // Studentのダミーデータ
    val students = (1 to 10).flatMap { i =>
    Seq(
        Student(None, s"1組$i", s"1-$i", "password", 1),
        Student(None, s"2組$i", s"2-$i", "password", 2)
    )
    }

    val db = Database.forConfig("slick.dbs.default.db")
    val setupAction = DBIO.seq(
      // データベースにダミーデータを挿入
      Facilitator.facilitators ++= facilitators,
      Classrooms.classrooms ++= classrooms,
      ClassFacilitator.classFacilitators ++= classFacilitators,
      Student.students ++= students
    )

    // セットアップアクションを実行
    Await.result(db.run(setupAction), 10.seconds)
    println("データベースにダミーデータを挿入しました。")
  }
}

