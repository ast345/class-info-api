// FacilitatorとStudent間のリレーションを管理するレイヤ-
// serviceレイヤーとは、MVCに加えて、アプリケーションのロジックを管理するレイヤー
package services

import javax.inject._
import models._
import scala.concurrent.{Future, ExecutionContext}
import slick.jdbc.JdbcProfile
import slick.jdbc.MySQLProfile.api._
import play.api.db.slick.DatabaseConfigProvider

// deConfigProviderはデータベースの接続に利用
class StudentService @Inject()(val dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext) {
  val db = dbConfigProvider.get[JdbcProfile].db

  def findByFacilitator(facilitatorId: Long, page: Option[Int], limit: Option[Int], order: Option[String], sort: Option[String], nameLike: Option[String], loginIdLike: Option[String]): Future[(Seq[Student], Int)] = {
    // Classroom と Student テーブルを結合して、指定された facilitatorId に対応する Student を取得するクエリ
    val baseQuery = for {
      cf <- TableQuery[ClassFacilitatorTable] if cf.facilitatorId === facilitatorId
      c <- TableQuery[ClassroomTable] if c.id === cf.classroomId
      s <- TableQuery[StudentTable] if s.classroomId === c.id
    } yield s



    // 部分一致検索
    // filterOptメソッドはOption型の値がsomeの時にフィルタ条件を適用するもの
    val filteredQuery = baseQuery
        .filterOpt(nameLike) { (query, name) => query.name like s"%$name%" }
        .filterOpt(loginIdLike) { (query, loginId) => query.loginId like s"%$loginId%" }

    // ソート条件を適用
    val sortedQuery = (sort, order) match {
        case (Some("loginId"), Some("asc")) => filteredQuery.sortBy(_.loginId.asc)
        case (Some("loginId"), Some("desc")) => filteredQuery.sortBy(_.loginId.desc)
        case (Some("name"), Some("asc")) => filteredQuery.sortBy(_.name.asc)
        case (Some("name"), Some("desc")) => filteredQuery.sortBy(_.name.desc)
        case (_, Some("asc")) => filteredQuery.sortBy(_.id.asc)
        case (_, Some("desc")) => filteredQuery.sortBy(_.id.desc)
        case _ => filteredQuery
    }


    // ページネーション適用
    // getOrElseでOption[Int]→ Int 初期値も設定
    val currentPage = page.getOrElse(1)
    val currentLimit = limit.getOrElse(10)

    // offsetで最初のレコードからいくつ目までが不要なレコードかを計算
    val offset = (currentPage - 1) * currentLimit
    val paginatedQuery = sortedQuery.drop(offset).take(currentLimit)

    // ページネーション適用後のデータ取得
    val students = db.run(paginatedQuery.result)
    val totalCount = db.run(baseQuery.length.result)

    for {
        students <- students
        totalCount <- totalCount
    } yield (students, totalCount)
  }
}
